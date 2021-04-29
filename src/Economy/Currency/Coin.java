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

    /**
     * Coin Count
     */
    private double Count = 0;
    /**
     * Get a Coin Count
     * @return
     */
    public double Count() { return Count; }
    
    /**
     * Full Buy
     */
    @Override
    public boolean Buy() {
        double Count = MyAccount.Money() / super.Price;
        return Buy(Count);
    }

    /**
     * Count Buy
     */
    @Override
    public boolean Buy(long count) {
        return Buy((double)count);
    }

    /**
     * PercentBuy
     */
    @Override
    public boolean Buy(float Percent) {
        double count = (MyAccount.Money() / super.Price) * Percent;
        return Buy(count);
    }

    /**
     * double Count Buy
     */
    @Override
    public boolean Buy(double count)
    {
        if(count <= 0 || count * super.Price > MyAccount.Money())
        {
            System.out.println("구매할 수 있는 수량이 아닙니다.");
            return false;
        }

        // count
        this.Count += count;

        // Buy
        super.BuyPrice = super.Price();
        
        // Average
        if(super.AveragePrice == 0)
            super.AveragePrice = super.Price;
        else
            super.AveragePrice = (super.AveragePrice + super.Price) / 2.0;

        MyAccount.Money(-(long) (count * super.Price));
        if(!MyAccount.InvestCoin.contains(this))
            MyAccount.InvestCoin.add(this);

        super.isBuy = true;
        return true;
    }

    /**
     * Full Sell
     */
    @Override
    public boolean Sell() {
        return Sell(this.Count);
    }
    
    /**
     * Count Sell
     */
    @Override
    public boolean Sell(long count) {
        return Sell((double) count);
    }

    /**
     * Percent Sell
     */
    @Override
    public boolean Sell(float Percent) {
        long count = (long) (this.Count * Percent * 10000);
        return Sell(count / 10000.0);
    }

    /**
     * double Count Sell
     */
    @Override
    public boolean Sell(double Count) {

        if(Count < 0 || this.Count < Count)
        {
            System.out.println("판매할 수 있는 수량이 아닙니다.");
            return false;
        }
        if(this.Count == 0)
        {
            System.out.println("가지고 있는 자산이 아닙니다.");
            return false;
        }

        // money
        MyAccount.Money((long)(this.Count * super.Price));

        // count
        this.Count -= Count;
        super.isBuy = false;
        
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

    @Override
    public long getSellMoney() {
        return (long) (this.Count * super.Price);
    }
    
}
