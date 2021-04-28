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
    public Coin(String name, double price, double dividend, List<String> data, double Up, double Down) {
        super(name, CType.Coin, price, dividend, data, Up, Down);
    }
    
    @Override
    public boolean Buy() {
        double Count = MyAccount.Money() / super.DPrice;
        return Buy(Count);
    }

    @Override
    public boolean Buy(long count) {
        return Buy((double)Count);
    }

    @Override
    public boolean Buy(float Percent) {
        double Count = MyAccount.Money() / super.DPrice * Percent;
        return Buy(Count);
    }

    @Override
    public boolean Buy(double Count)
    {
        if(Count < 0 || Count * super.DPrice > MyAccount.Money())
        {
            System.out.println("구매할 수 있는 수량이 아닙니다.");
            return false;
        }

        super.DCount += Count;

        return true;
    }

    @Override
    public boolean Sell() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean Sell(long count) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean Sell(float Percent) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean Sell(double Count) {
        // TODO Auto-generated method stub
        return false;
    }

    
}
