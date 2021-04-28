package Economy.Currency;

import java.util.List;

import Economy.Currency.CurrencyType.CType;

public class Estate extends Currency {

    public Estate(String name, long price, long dividend, List<String> data, double up, double down) {
        super(name, CType.Estate, price, dividend, data, up, down);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean Buy() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean Buy(long count) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean Buy(float Percent) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean Buy(double Count) {
        // TODO Auto-generated method stub
        return false;
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
