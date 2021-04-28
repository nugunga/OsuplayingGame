package Economy.Currency;

import java.util.List;

import Economy.MyAccount;
import Economy.Currency.CurrencyType.CType;

public class Coin extends Currency {

    /**
     * Coin
     * @param name Name
     * @param price Price
     * @param dividend Dividend
     * @param data Information Data
     * @param Up Up Percent
     * @param Down Down Percent
     */
    public Coin(String name, long price, double dividend, List<String> data, double Up, double Down) {
        super(name, CType.Coin, price, dividend, data, Up, Down);
    }

    private double Count = 0;

    public double getCount() { return Count; }
    
    @Override
    public long Count() {
        System.out.println("코인에서는 Count()함수를 사용할 수 없습니다.\n Count() => get");
        return -1;
    }

    @Override
    public boolean Buy() {
        double Count = MyAccount.Money() / super.Price;
        return Buy(Count);
    }

    @Override
    public boolean Buy(long count) {
        return Buy((double)Count);
    }

    @Override
    public boolean Buy(float Percent) {
        double Count = MyAccount.Money() / super.Price * Percent;
        return Buy(Count);
    }

    @Override
    public boolean Buy(double Count)
    {
        if(Count < 0 || Count * super.Price > MyAccount.Money())
        {
            System.out.println("구매할 수 있는 수량이 아닙니다.");
            return false;
        }

        // count
        this.Count += Count;

        // Buy
        super.BuyPrice = super.Price();
        
        // Average
        if(super.AveragePrice == 0)
            super.AveragePrice = super.Price;
        else
            super.AveragePrice = (super.AveragePrice + super.Price) / 2.0;

        // myaccount
        MyAccount.Money(-(long) (Count * super.Price));
        if(!MyAccount.InvestCoin.contains(this))
            MyAccount.InvestCoin.add(this);

        return true;
    }

    @Override
    public boolean Sell() {
        return Sell(this.Count);
    }

    @Override
    public boolean Sell(long count) {
        return Sell((double) count);
    }

    @Override
    public boolean Sell(float Percent) {
        return Sell((double) Percent);
    }

    @Override
    public boolean Sell(double Count) {
        if(Count < 0 || this.Count < Count)
        {
            System.out.println("판매할 수 있는 수량이 아닙니다.");
            return false;
        }

        // money
        MyAccount.Money((long)(this.Count * super.Price));

        // count
        this.Count -= Count;
        
        // count == 0
        if(this.Count == 0)
        {
            if(MyAccount.InvestCoin.contains(this))
                MyAccount.InvestCoin.remove(this);
            
            // Buy Price
            super.BuyPrice = 0;

            // Average
            super.AveragePrice = 0;
        }
        return true;
    }

    
}
