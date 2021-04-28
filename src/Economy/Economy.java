package Economy;

import java.util.ArrayList;
import java.util.List;

import Economy.Currency.Coin;
import Economy.Currency.Estate;
import Economy.Currency.Stock;

public class Economy {
    
    public static List<Coin> Coins = new ArrayList<>();
    public static List<Stock> Stocks = new ArrayList<>();
    public static List<Estate> Estates = new ArrayList<>();

    public Economy()
    {
        new MyAccount(500000);
        
        System.out.println("Test");

        for (Coin coin : Coins) {
            System.out.println(coin.Name());
        }
        for (Stock coin : Stocks) {
            System.out.println(coin.Name());
        }
        for (Estate coin : Estates) {
            System.out.println(coin.Name());
        }

    }
}
