package Economy.Currency;

import java.util.List;

import Economy.MyAccount;
import Economy.Currency.CurrencyType.CType;

public class Estate extends Currency {

    public Estate(String name, long price, long dividend, List<String> data, double up, double down, EstateType type) {
        super(name, CType.Estate, price, dividend, data, up, down);
        this.Type = type;
    }

    public enum EstateType
    {
        Building,
        House,
        land,
        None
    }

    private EstateType Type;
    public final EstateType EstateType() { return this.Type; }

    private long Loan;
    public long Loan() { return this.Loan; } 
    public void Loan(long money) 
    { 
        this.Loan -= money;
    }

    @Override
    public boolean Buy() {
        if(Math.abs(MyAccount.Money() - super.Price) >= MyAccount.Loan())
        {
            System.out.println("집의 가격이 현금과 대출의 돈보다 많습니다.");
            return false;
        }

        MyAccount.Money(-super.Price);
        if(MyAccount.Money() < 0)
        {
            this.Loan = Math.abs(MyAccount.Money());
            MyAccount.Money(Math.abs(MyAccount.Money()));
        }

        super.AveragePrice = super.Price;
        super.BuyPrice = super.Price;
        super.Count = 1;

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
        return false;
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
    
}
