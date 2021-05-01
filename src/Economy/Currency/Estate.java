package Economy.Currency;

import java.util.ArrayList;
import java.util.List;

import Economy.MyAccount;
import Economy.Currency.CurrencyType.CType;

public class Estate extends Currency {

    public Estate(String name, long price, double dividend, List<String> data, double up, double down, EstateType type) {
        super(name, CType.Estate, price, dividend, data, up, down);
        this.Type = type;
    }

    private boolean Count;
    public long Count() { return Count ? 1 : 0; }

    public enum EstateType
    {
        Building,
        House,
        Land,
        None
    }

    public static EstateType StringToEType(String Type)
    {
        switch (Type) {
            case "Building": return EstateType.Building;
            case "House": return EstateType.House;
            case "Land": return EstateType.Land;
            default : return EstateType.None;
        }
    }

    private EstateType Type;
    public final EstateType EstateType() { return this.Type; }

    private long Loan;
    public long Loan() { return this.Loan; } 
    public void Loan(long money) 
    { 
        this.Loan -= money;
        MyAccount.UseLoan(money);
    }

    @Override
    public boolean Buy() {
        if(Math.abs(MyAccount.Money() - super.Price - MyAccount.UseLoan()) >= MyAccount.Loan())
        {
            System.out.println("집의 가격이 현금과 대출의 돈보다 많습니다.");
            return false;
        }

        MyAccount.Money(-super.Price);
        if(MyAccount.Money() < 0)
        {
            this.Loan = Math.abs(MyAccount.Money());
            MyAccount.UseLoan(this.Loan);
            MyAccount.Money(Math.abs(MyAccount.Money()));
        }

        super.AveragePrice = super.Price;
        super.BuyPrice = super.Price;
        this.Count = true;

        if(!MyAccount.InvestEstate.contains(this))
            MyAccount.InvestEstate.add(this);
        super.isBuy = true;
        super.AccountUpdate("출금", super.Price - this.Loan);
        return true;
    }

    @Override
    public boolean Buy(long count) {
        return Buy();
    }

    @Override
    public boolean Buy(float Percent) {
        return Buy();
    }

    @Override
    public boolean Buy(double Count) {
        return Buy();
    }

    @Override
    public boolean Sell() {
        if(!this.Count)
        {
            System.out.println("가지고 있는 자산이 아닙니다.");
            return false;
        }
        
        // Money
        MyAccount.Money(super.Price - this.Loan);
        this.Loan = 0;

        // Count
        this.Count = false;

        // Price
        super.AveragePrice = 0;
        super.BuyPrice = 0;

        MyAccount.InvestEstate.remove(this);
        MyAccount.UseLoan(-this.Loan);
        super.isBuy = false;
        super.AccountUpdate("입금", super.Price - this.Loan);

        return true;
    }

    @Override
    public boolean Sell(long count) {
        return Sell();
    }

    @Override
    public boolean Sell(float Percent) {
        return Sell();
    }

    @Override
    public boolean Sell(double Count) {
        return Sell();
    }
    @Override
    public long getSellMoney() {
        return super.Price - this.Loan;
    }
    
    @Override
    public void Information() {
        List<String> list = new ArrayList<>();

        // 이름
        list.add(new String("이름 : " + super.Name()));
        // 타입
        list.add(new String("타입 : " + super.Type().TypeString()));
        list.add(new String("건물 및 땅 타입 : " + this.Type));
        list.add("");
        list.add("가격 관련");
        // 가격
        list.add(new String("  현재 가격 : " + this.Price));
        // 최근 가격
        list.add(new String("  이전 가격 : " + this.RecentPrice));

        if(this.isBuy)
        {
            // 구매 가격
            list.add(new String("  구매 가격 : " + this.BuyPrice));
            // 평균 가격
            list.add(new String("  평단가 : " + this.AveragePrice));
        }

        // 화폐 설명
        list.add("화폐 설명");
        for (String string : super.InformationData)
            list.add("  " + string);
        
        System.out.println((this.Type == EstateType.House ? "주택" : this.Type == EstateType.Building ? "빌딩" : "땅") + " 정보");
        Core.Prints.Show(list);
    }

}
