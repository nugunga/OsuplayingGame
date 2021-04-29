package Economy;

import java.util.ArrayList;
import java.util.List;

import Core.SystemConsole;
import Economy.Currency.Coin;
import Economy.Currency.Estate;
import Economy.Currency.Stock;
import Economy.Currency.CurrencyType.CType;

public class Economy {
    
    public static List<Coin> Coins = new ArrayList<>();
    public static List<Stock> Stocks = new ArrayList<>();
    public static List<Estate> Estates = new ArrayList<>();

    public Economy(long money)
    {
        if(!SystemConsole.isdebug)
            new MyAccount(money);
        else
            new MyAccount(1000000);
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

    private void ShowTargetPrice(CType cType)
    {
        List<String> list = new ArrayList<>();
        switch(cType)
        {
            case Coin:
                System.out.println("Coins");
                for (Coin coin : Coins)
                    list.add("  " + coin.Name() + " : " + coin.Price() + "(" + coin.RecentPrice() + ")");
                Core.Prints.Show(list, 1);
                break;
            case Stock:
                System.out.println("Stocks");
                for (Stock stock : Stocks)
                    list.add("  " + stock.Name() + " : " + stock.Price() + "(" + stock.RecentPrice() + ")");
                Core.Prints.Show(list, 1);
                break;
            case Estate:
                System.out.println("Estates");
                for (Estate estate : Estates)
                    list.add(new String("  " + estate.Name() + "("+ estate.Type().Type().toString() + ") : " + estate.Price() + "(" + (estate.AveragePrice() - estate.Price()) + ")"));
                Core.Prints.Show(list, 1);
                break;
            case None:
                return;
        }
    }

    public void Buy()
    {
        SystemConsole.ClearConsole();
        CType Target;

        String[] Currency = {
            "코인 구매",
            "주식 구매",
            "땅 및 건물 구매"
        };

        do
        {
            System.out.println("구매 메뉴(구매)");
            
            Core.Prints.Show(Currency, 1);
            System.out.print("입력해주세요 Ex) 1, 코인 >>> ");
            String cmd = SystemConsole.sc.next();
            Target =
            switch(cmd)
            {
                case "1", "코인" -> CType.Coin;
                case "2", "주식" -> CType.Stock;
                case "3", "땅", "건물" -> CType.Estate;
                default -> CType.None;
            };

            if(Target != CType.None)
                break;
            SystemConsole.ClearConsole();
        }
        while (true);
        SystemConsole.ClearConsole();
        
        ShowTargetPrice(Target);
        System.out.print("입력해주세요 Ex 1 : ");
        int index = SystemConsole.sc.nextInt();
        SystemConsole.ClearConsole();
        
        String[] BuyLoad = {
            "전체 구매 : 현재 구매할 수 있는 최대 개수 구입",
            "개수 구매 : 특정 개수만큼 구입",
            "퍼센트 구매 : 현재 구매할 수 있는 개수 중 비율 만큼 구입"
        };
        int load;

        do
        {
            System.out.println("구매 방법을 선택해주세요");
            Core.Prints.Show(BuyLoad, 1);
            System.out.print("입력해주세요 Ex) 1, 전체 >>> ");
            String cmd = SystemConsole.sc.next();
            load = 
            switch (cmd) {
                case "1", "전체", "풀", "Full" -> 1;
                case "2", "부분", "개수", "Count" -> 2;
                case "3", "퍼센트", "비율" -> 3;
                default -> 4;
            };
            
            if(load != 4)
                break;
            SystemConsole.ClearConsole();
        }
        while (true);
        SystemConsole.ClearConsole();

        switch(Target)
        {
            case Coin:
                switch(load)
                {
                    case 1: Coins.get(index - 1).Buy(); break;
                    case 2: Coins.get(index - 1).Buy(SystemConsole.sc.nextLong()); break;
                    case 3: Coins.get(index - 1).Buy(SystemConsole.sc.nextDouble()); break;
                }
                break;
            case Stock:
                switch(load)
                {
                    case 1: Stocks.get(index - 1).Buy(); break;
                    case 2: Stocks.get(index - 1).Buy(SystemConsole.sc.nextLong()); break;
                    case 3: Stocks.get(index - 1).Buy(SystemConsole.sc.nextDouble()); break;
                }
                break;
            case Estate:
                switch(load)
                {
                    case 1: Estates.get(index - 1).Buy(); break;
                    case 2: Estates.get(index - 1).Buy(SystemConsole.sc.nextLong()); break;
                    case 3: Estates.get(index - 1).Buy(SystemConsole.sc.nextDouble()); break;
                }
                break;
        }
    }

    public void Sell()
    {

    }

    public void InvestPrice()
    {
        if(MyAccount.InvestCoin.size() + MyAccount.InvestStock.size() + MyAccount.InvestEstate.size() == 0)
        {
            System.out.println("보유하고 있는 자산은 없습니다.");
            return;
        }

        System.out.println("MyMoney");
        List<String> lists = new ArrayList<>();
        lists.add(new String("현금 : " + Long.toString(MyAccount.Money())));
        lists.add(new String("전체 자산 : " + Long.toString(MyAccount.AllMoney())));
        Core.Prints.Show(lists);
        System.out.println("");

        if(MyAccount.InvestStock.size() > 0)
        {
            System.out.println("Coins");
            List<String> list = new ArrayList<>();
            for (Stock stock : MyAccount.InvestStock)
                list.add(new String("  " + stock.Name() + "("+ stock.Count() + ") : " + stock.Price() + "(" + stock.getSellMoney() + ", " + (stock.AveragePrice() - stock.Price()) + ")"));
            Core.Prints.Show(list);
            System.out.println("\n");
        }

        if(MyAccount.InvestCoin.size() > 0)
        {
            System.out.println("Stock");
            List<String> list = new ArrayList<>();
            for (Coin coin : MyAccount.InvestCoin)
                list.add(new String("  " + coin.Name() + "(" + coin.getCount() + ") : " + coin.Price() + "(" + coin.getSellMoney() + ", " + (coin.AveragePrice() - coin.Price()) + ")"));
            Core.Prints.Show(list);
            System.out.println("\n");
        }

        if(MyAccount.InvestEstate.size() > 0)
        {
            System.out.println("Estate");
            List<String> list = new ArrayList<>();
            for (Estate estate : MyAccount.InvestEstate)
                list.add(new String("  " + estate.Name() + "("+ estate.Type().Type().toString() + ") : " + estate.Price() + "(" + estate.getSellMoney() + ", " + (estate.AveragePrice() - estate.Price()) + ")"));
            Core.Prints.Show(list);
            System.out.println("\n");
        }
    }

    public void SmallInvestPrice()
    {
        System.out.println("MyMoney");
        List<String> lists = new ArrayList<>();
        lists.add(new String("현금 : " + Long.toString(MyAccount.Money())));
        lists.add(new String("전체 자산 : " + Long.toString(MyAccount.AllMoney())));
        Core.Prints.Show(lists);
        System.out.println("");

        if(MyAccount.InvestCoin.size() + MyAccount.InvestStock.size() + MyAccount.InvestEstate.size() == 0)
        {
            System.out.println("보유하고 있는 자산은 없습니다.");
            return;
        }
        
        if(MyAccount.InvestStock.size() > 0)
        {
            System.out.println("Coins");
            List<String> list = new ArrayList<>();
            for (Stock stock : MyAccount.InvestStock)
                list.add(new String("  " + stock.Name() + " : " + stock.Price() + "(" + (stock.AveragePrice() - stock.Price()) + ")"));
            Core.Prints.Show(list);
            System.out.println("");
        }

        if(MyAccount.InvestCoin.size() > 0)
        {
            System.out.println("Stock");
            List<String> list = new ArrayList<>();
            for (Coin coin : MyAccount.InvestCoin)
                list.add(new String("  " + coin.Name() + " : " + coin.Price() + "(" + (coin.AveragePrice() - coin.Price()) + ")"));
            Core.Prints.Show(list);
            System.out.println("");
        }

        if(MyAccount.InvestEstate.size() > 0)
        {
            System.out.println("Estate");
            List<String> list = new ArrayList<>();
            for (Estate estate : MyAccount.InvestEstate)
                list.add(new String("  " + estate.Name() + "("+ estate.Type().Type().toString() + ") : " + estate.Price() + "(" + (estate.AveragePrice() - estate.Price()) + ")"));
            Core.Prints.Show(list);
            System.out.println("");
        }
    }

    public void showPrice()
    {
        List<String> list = new ArrayList<>();

        System.out.println("MyMoney");
        List<String> lists = new ArrayList<>();
        lists.add(new String("현금 : " + Long.toString(MyAccount.Money())));
        lists.add(new String("전체 자산 : " + Long.toString(MyAccount.AllMoney())));
        Core.Prints.Show(lists);
        System.out.println("");
        
        System.out.println("Coins");
        for (Coin coin : Coins)
        {
            if(MyAccount.InvestCoin.contains(coin))
                list.add(new String("  " + coin.Name() +"(" +coin.getCount() + ") : " + coin.Price() + "(" + coin.getSellMoney() + ", " + (coin.AveragePrice() - coin.Price()) + ")"));
            else
                list.add(new String("  " + coin.Name() + " : " + coin.Price() + "(" + coin.RecentPrice() + ")"));
        }
        Core.Prints.Show(list);

        list = new ArrayList<>();
        System.out.println("\n\nStocks");
        for (Stock stock : Stocks)
        {
            if(MyAccount.InvestStock.contains(stock))
                list.add(new String("  " + stock.Name() + "(" + stock.Count() + ") : " + stock.Price() + "(" + stock.getSellMoney() + ", " + (stock.AveragePrice() - stock.Price()) + ")"));
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
