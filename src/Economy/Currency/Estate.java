package Economy.Currency;

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
    
}
