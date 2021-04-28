package Economy.Currency;

import java.util.List;

import Economy.MyAccount;
import Economy.Currency.CurrencyType.CType;

public class Stock extends Currency {

    /**
     * Stock
     * @param name Name
     * @param price Price
     * @param dividend Dividend
     * @param data Information Data
     * @param Up Up Percent
     * @param Down Down Percent
     */
    public Stock(String name, long price, double dividend, List<String> data, double Up, double Down) {
        super(name, CType.Stock, price, dividend, data, Up, Down);
    }

    @Override
    public boolean Buy() {
        long count = MyAccount.Money() / super.Price;
        return Buy(count);
    }

    @Override
    public boolean Buy(long count) {
        if(count <= 0 || count > MyAccount.Money() / super.Price)
        {
            System.out.println("구매할 수 없는 수량입니다.");
            return false;
        }
        
        // count
        super.Count += count;

        // Buy
        super.BuyPrice = super.Price();
        
        // Average
        if(super.AveragePrice == 0)
            super.AveragePrice = super.Price;
        else
            super.AveragePrice = (super.AveragePrice + super.Price) / 2.0;

        // myaccount
        MyAccount.Money(-(count * super.Price));
        if(!MyAccount.InvestStock.contains(this))
            MyAccount.InvestStock.add(this);

        return true;
    }

    @Override
    public boolean Buy(float Percent) {
        long count = (long) (MyAccount.Money() / super.Price * Percent);
        System.out.println(count);
        return Buy(count);
    }

    @Override
    public boolean Buy(double Count) {
        return Buy((float) Count);
    }

    @Override
    public boolean Sell() {
        return Buy(super.Count);
    }

    @Override
    public boolean Sell(long count) {
        if(count <= 0 || count > super.Count)
        {
            System.out.println("판매할 수 있는 수량이 아닙니다.");
            return false;
        }
        if(super.Count == 0)
        {
            System.out.println("가지고 있는 자산이 아닙니다.");
            return false;
        }

        // money
        MyAccount.Money(count * super.Price);

        // count
        super.Count -= count;
        
        // count == 0
        if(super.Count == 0)
        {
            if(MyAccount.InvestStock.contains(this))
                MyAccount.InvestStock.remove(this);
            
            // Buy Price
            super.BuyPrice = 0;

            // Average
            super.AveragePrice = 0;
        }
        return true;
    }

    @Override
    public boolean Sell(float Percent) {
        return Sell((long) (this.Count * Percent));
    }

    @Override
    public boolean Sell(double Count) {
        return Sell((float) Count);
    }
}

