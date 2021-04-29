package Economy;

import java.util.ArrayList;
import java.util.List;

import Core.SystemConsole;
import Economy.Currency.Coin;
import Economy.Currency.Estate;
import Economy.Currency.Stock;

public class Economy {
    
    public static List<Coin> Coins = new ArrayList<>();
    public static List<Stock> Stocks = new ArrayList<>();
    public static List<Estate> Estates = new ArrayList<>();

    public Economy(long money)
    {
        new MyAccount(money);
        
        System.out.println("Test");

        if(SystemConsole.isdebug)
        {
            for (Coin coin : Coins)
                System.out.println(coin.Name());
            for (Stock coin : Stocks)
                System.out.println(coin.Name());
            for (Estate coin : Estates)
                System.out.println(coin.Name());
            Core.SystemConsole.ClearConsole("");
        }
    }

    public void Buy()
    {

    }

    public void Sell()
    {

    }

    public void InvestPrice()
    {

    }

    public void showPrice()
    {
        List<String> list = new ArrayList<>();
        
        System.out.println("Coins");
        for (Coin coin : Coins)
        {
            if(MyAccount.InvestCoin.contains(coin))
                list.add(new String("  " + coin.Name() + coin.Count() + " : " + coin.Price() + "(" + coin.getSellMoney() + ", " + (coin.AveragePrice() - coin.Price()) + ")"));
            else
                list.add(new String("  " + coin.Name() + " : " + coin.Price() + "(" + coin.RecentPrice() + ")"));
        }
        Core.Prints.Show(list);

        list = new ArrayList<>();
        System.out.println("\n\nStocks");
        for (Stock stock : Stocks)
        {
            if(MyAccount.InvestStock.contains(stock))
                list.add(new String("  " + stock.Name() + stock.Count() + " : " + stock.Price() + "(" + stock.getSellMoney() + ", " + (stock.AveragePrice() - stock.Price()) + ")"));
            else
                list.add(new String("  " + stock.Name() + " : " + stock.Price() + "(" + stock.RecentPrice() + ")"));
        }
        Core.Prints.Show(list);
        

        list = new ArrayList<>();
        System.out.println("\n\nEstate");
        for (Estate estate : Estates)
        {
            if(MyAccount.InvestEstate.contains(estate))
                list.add(new String("  " + estate.Name() + "("+ estate.Type().Type().toString() + ") : " + estate.Price() + "(" + estate.getSellMoney() + ", " + (estate.AveragePrice() - estate.Price()) + ")"));
            else
                list.add(new String("  " + estate.Name() + "("+ estate.Type().Type().toString() + ") : " + estate.Price() + "(" + estate.RecentPrice() + ")"));
        }
        Core.Prints.Show(list);
    }
}
