package Economy;

import java.util.ArrayList;
import java.util.List;

import Economy.Currency.Coin;
import Economy.Currency.Estate;
import Economy.Currency.Stock;

public class MyAccount {
    
    private static long Money;
    public static long Money() { return MyAccount.Money; }
    public static void Money(long price) { MyAccount.Money += price; }

    public static List<Coin> InvestCoin;
    public static List<Stock> InvestStock;
    public static List<Estate> InvestEstate;

    public MyAccount(long money)
    {
        MyAccount.Money = money;
        InvestCoin = new ArrayList<>();
        InvestStock = new ArrayList<>();
        InvestEstate = new ArrayList<>();
    }
}
