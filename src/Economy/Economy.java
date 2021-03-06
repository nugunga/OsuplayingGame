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

    public enum LoadType { Full, Count, Percent, None };
    public enum UpdateType { Currency, Dividend, All };

    public Economy(long money)
    {
        if(!SystemConsole.debugMode)
            new MyAccount(money);
        else
            new MyAccount(1000000);
    }

    private void ShowTargetPrice(CType cType)
    {
        List<String> list = new ArrayList<>();
        switch(cType)
        {
            case Coin:
                System.out.println("Coins");
                for (Coin coin : Coins)
                    list.add(coin.Name() + " : " + coin.Price() + "(" + coin.RecentPrice() + ")");
                Core.Prints.Show(list, 1);
                break;
            case Stock:
                System.out.println("Stocks");
                for (Stock stock : Stocks)
                    list.add(stock.Name() + " : " + stock.Price() + "(" + stock.RecentPrice() + ")");
                Core.Prints.Show(list, 1);
                break;
            case Estate:
                System.out.println("Estates");
                for (Estate estate : Estates)
                    list.add(new String(estate.Name() + "("+ estate.Type().Type().toString() + ") : " + estate.Price() + "(" + (estate.AveragePrice() - estate.Price()) + ")"));
                Core.Prints.Show(list, 1);
                break;
            case None:
                return;
        }
    }

    private boolean RangeOutCheck(CType type, boolean isMode, int index)
    {
        switch (type)
        {
            case Coin:
                if(isMode)
                    if(Coins.size() < index - 1)
                        return true;
                    else
                        return false;
                else
                    if(MyAccount.InvestCoin.size() < index - 1)
                        return true;
                    else
                        return false;
            case Stock:
                if(isMode)
                    if(Stocks.size() < index - 1)
                        return true;
                    else
                        return false;
                else
                    if(MyAccount.InvestStock.size() < index - 1)
                        return true;
                    else
                        return false;
            case Estate :
                return false;
            
            default:
                return true;
        }
    }

    private void payslip(boolean isBuyAndSell, boolean func, LoadType load, double count, CType type, int index)
    {
        List<String> data = new ArrayList<String>();

        // ??????
        if(isBuyAndSell)
        {
            switch (type) {
                case Coin:
                    Coin coin = Coins.get(index - 1);
                    data.add("?????? : " + coin.Name());
                    data.add("?????? : " + coin.Type().TypeString());
                    data.add("?????? ?????? : " + (load == LoadType.Full ? "?????? ??????" : load == LoadType.Count ? "?????? ??????" : "?????? ??????"));
                    data.add("?????? ?????? : " + coin.Price());
                    data.add("?????? ?????? : " + count);
                    data.add("?????? ?????? : " + coin.Count());
                    break;
                case Stock:
                    Stock stock = Stocks.get(index - 1);
                    data.add("?????? : " + stock.Name());
                    data.add("?????? : " + stock.Type().TypeString());
                    data.add("?????? ?????? : " + (load == LoadType.Full ? "?????? ??????" : load == LoadType.Count ? "?????? ??????" : "?????? ??????"));
                    data.add("?????? ?????? : " + stock.Price());
                    data.add("?????? ?????? : " + count);
                    data.add("?????? ?????? : " + stock.Count());
                    break;
                case Estate:
                    Estate estate = Estates.get(index - 1);
                    data.add("?????? : " + estate.Name());
                    data.add("?????? : " + estate.Type().TypeString());
                    data.add("?????? ?????? : " + ((Estate) estate).EstateType());
                    data.add("?????? ?????? : " + (load == LoadType.Full ? "?????? ??????" : load == LoadType.Count ? "?????? ??????" : "?????? ??????"));
                    data.add("?????? ?????? : " + estate.Price());
                    break;
                default: break;
            }
        }
        // ??????
        else
        {
            switch (type) {
                case Coin:
                    Coin coin = Coins.get(index - 1);
                    data.add("?????? : " + coin.Name());
                    data.add("?????? : " + coin.Type().TypeString());
                    data.add("?????? ?????? : " + (load == LoadType.Full ? "?????? ??????" : load == LoadType.Count ? "?????? ??????" : "?????? ??????"));
                    data.add("?????? ?????? : " + coin.Price());
                    data.add("?????? ?????? : " + count);
                    data.add("?????? ?????? : " + coin.Count());
                    break;
                case Stock:
                    Stock stock = Stocks.get(index - 1);
                    data.add("?????? : " + stock.Name());
                    data.add("?????? : " + stock.Type().TypeString());
                    data.add("?????? ?????? : " + (load == LoadType.Full ? "?????? ??????" : load == LoadType.Count ? "?????? ??????" : "?????? ??????"));
                    data.add("?????? ?????? : " + stock.Price());
                    data.add("?????? ?????? : " + count);
                    data.add("?????? ?????? : " + stock.Count());
                    break;
                case Estate:
                    Estate estate = Estates.get(index - 1);
                    data.add("?????? : " + estate.Name());
                    data.add("?????? : " + estate.Type().TypeString());
                    data.add("?????? ?????? : " + ((Estate) estate).EstateType());
                    data.add("?????? ?????? : " + (load == LoadType.Full ? "?????? ??????" : load == LoadType.Count ? "?????? ??????" : "?????? ??????"));
                    data.add("?????? ?????? : " + estate.Price());
                    break;
                default: break;
            }
        }

        Core.Prints.Show(data);
        String format = "%" + (Core.Prints.LineNum()-10) + "s";
        System.out.printf(format, (func ? "?????? ?????? (?????? ??????)" : " ?????? ?????? (?????? ??????)"));
    }

    public void Buy()
    {
        CType Target;

        String[] Currency = {
            "?????? ??????",
            "?????? ??????",
            "??? ??? ?????? ??????",
            "??????, ????????????"
        };

        do
        {
            System.out.println("?????? ??????(??????)");
            
            Core.Prints.Show(Currency, 1);
            System.out.print("?????????????????? Ex) 1, ?????? >>> ");
            String cmd = SystemConsole.sc.next();
            Target =
            switch(cmd)
            {
                case "1", "??????" -> CType.Coin;
                case "2", "??????" -> CType.Stock;
                case "3", "???", "??????" -> CType.Estate;
                default -> CType.None;
            };

            if(cmd.equals("4") || cmd.equals("X") || cmd.equals("??????") || cmd.equals("????????????"))
                return;

            if(Target != CType.None)
                break;
            SystemConsole.ClearConsole();
        }
        while (true);
        SystemConsole.ClearConsole();
        
        ShowTargetPrice(Target);
        System.out.print("?????????????????? Ex 1 : ");
        int index = SystemConsole.sc.nextInt();
        SystemConsole.ClearConsole();

        if(RangeOutCheck(Target, true, index))
        {
            System.out.println("????????? ?????????");
            return;
        }
        
        String[] BuyLoad = {
            "?????? ?????? : ?????? ????????? ??? ?????? ?????? ?????? ??????",
            "?????? ?????? : ?????? ???????????? ??????",
            "?????? ?????? : ?????? ????????? ??? ?????? ?????? ??? ?????? ?????? ??????",
            "??????, ????????????"
        };
        String[] BuyLoadCoin = {
            "?????? ?????? : ?????? ????????? ??? ?????? ?????? ?????? ??????",
            "?????? ?????? : ?????? ???????????? ?????? <???????????? ?????? ??????>",
            "?????? ?????? : ?????? ????????? ??? ?????? ?????? ??? ?????? ?????? ??????",
            "??????, ????????????"
        };
        LoadType load;

        if(Target != CType.Estate)
        {
            do
            {
                System.out.println("?????? ????????? ??????????????????");
                if(Target != CType.Coin)
                    Core.Prints.Show(BuyLoad, 1);
                else
                    Core.Prints.Show(BuyLoadCoin, 1);    
                System.out.print("?????????????????? Ex) 1, ?????? >>> ");
                String cmd = SystemConsole.sc.next();
                load = 
                switch (cmd) {
                    case "1", "??????", "???", "Full" -> LoadType.Full;
                    case "2", "??????", "??????", "Count" -> LoadType.Count;
                    case "3", "?????????", "??????" -> LoadType.Percent;
                    default -> LoadType.None;
                };
                
                if(cmd.equals("4") || cmd.equals("X") || cmd.equals("??????") || cmd.equals("????????????"))
                    return;
                
                if(load != LoadType.None)
                    break;
                SystemConsole.ClearConsole();
            }
            while (true);
        }
        else
            load = LoadType.Full;

        // ??? ??????
        System.out.println("");
        double in;
        if(Target != CType.Estate)
        {
            switch (load)
            {
                case Full:
                    payslip
                    (
                        true,
                        BuyExecute(Target, index, load, 0.0),
                        load,
                        0.0,
                        Target,
                        index
                    );
                    break;
                case Count:
                    System.out.print("????????? ?????????????????? : ");
                    in = SystemConsole.sc.nextDouble();
                    payslip
                    (
                        true,
                        BuyExecute(Target, index, load, in),
                        load,
                        in, 
                        Target,
                        index
                    );
                    break;
                case Percent:
                    System.out.print("????????? ?????????????????? : ");
                    in = SystemConsole.sc.nextDouble();
                    payslip
                    (
                        true,
                        BuyExecute(Target, index, load, in),
                        load,
                        in, 
                        Target,
                        index
                    );
                    break;
            default:
                break;
            }
        }
        else
            payslip
            (
                true,
                BuyExecute(Target, index, load, 0.0),
                load,
                0.0, 
                Target,
                index
            );
    }

    private boolean BuyExecute(CType Target, int index, LoadType load, double data)
    {
        switch(Target)
        {
            case Coin:
                switch(load)
                {
                    case Full: return Coins.get(index - 1).Buy();
                    case Count: return Coins.get(index - 1).Buy(data);
                    case Percent: return Coins.get(index - 1).Buy((float)data);
                    default: return false;
                }
            case Stock:
                switch(load)
                {
                    case Full: return Stocks.get(index - 1).Buy();
                    case Count: return Stocks.get(index - 1).Buy((long)data);
                    case Percent: return Stocks.get(index - 1).Buy((float)data);
                    default: return false;
                }
            case Estate:
                return Estates.get(index - 1).Buy();
            default:
                return false;
        }
    }

    private void ShowTargetInvestPrice(CType Target)
    {
        List<String> list = new ArrayList<String>();
        switch (Target) {
            case Coin:
                System.out.println("Coins");
                for (Coin coin : MyAccount.InvestCoin)
                    list.add(new String(coin.Name() + "("+ coin.Count() + ") : " + coin.Price() + "(" + coin.getSellMoney() + ", " + (coin.AveragePrice() - coin.Price()) + ")"));
                Core.Prints.Show(list, 1);
                break;
            case Stock:
                System.out.println("Stock");
                for (Stock stock : MyAccount.InvestStock)
                    list.add(new String(stock.Name() + "("+ stock.Count() + ") : " + stock.Price() + "(" + stock.getSellMoney() + ", " + (stock.AveragePrice() - stock.Price()) + ")"));
                Core.Prints.Show(list, 1);
                break;
            case Estate:
                System.out.println("Estate");
                for (Estate estate : MyAccount.InvestEstate)
                    list.add(new String(estate.Name() + "("+ estate.Type().Type().toString() + ") : " + estate.Price() + "(" + estate.getSellMoney() + ", " + (estate.AveragePrice() - estate.Price()) + ")"));
                Core.Prints.Show(list, 1);
                break;
            default:
                return;
        }
    }

    public void Sell()
    {
        if(MyAccount.InvestCoin.size() + MyAccount.InvestStock.size() + MyAccount.InvestEstate.size() == 0)
        {
            System.out.println("???????????? ?????? ????????? ????????????.");
            return;
        }

        CType Target;
        boolean NoneFlag = false;

        String[] Currency =
        {
            "?????? ??????",
            "?????? ??????",
            "????????? ?????? ??????",
            "??????, ????????????"
        };

        if(MyAccount.InvestCoin.size() + MyAccount.InvestStock.size() + MyAccount.InvestEstate.size() > 0)
        {
            if(MyAccount.InvestCoin.size() > 0)
                Currency[0] += " (?????? ??????)";
            if(MyAccount.InvestStock.size() > 0)
                Currency[1] += " (?????? ??????)";
            if(MyAccount.InvestEstate.size() > 0)
                Currency[2] += " (?????? ??????)";
        }

        do
        {
            System.out.println("?????? ??????(??????)");
            Core.Prints.Show(Currency, 1);
            System.out.print("?????????????????? Ex) 1, ?????? : ");
            String cmd = SystemConsole.sc.next();
            Target =
            switch (cmd) {
                case "1", "??????", "?????? ??????": 
                    if(MyAccount.InvestCoin.size()== 0)
                        NoneFlag = true;
                    yield CType.Coin;
                case "2", "??????", "?????? ??????": 
                    if(MyAccount.InvestStock.size()== 0)
                        NoneFlag = true;
                    yield CType.Stock;
                case "3", "???", "??????", "??? ??????", "?????? ??????", "????????? ?????? ??????": 
                    if(MyAccount.InvestEstate.size()== 0)
                        NoneFlag = true;
                    yield CType.Estate;
                default: yield CType.None;
            };

            if(cmd.equals("4") || cmd.equals("X") || cmd.equals("??????") || cmd.equals("????????????"))
                return;

            if(Target != CType.None)
                break;
            SystemConsole.ClearConsole();
        }
        while (true);
        SystemConsole.ClearConsole();

        if(NoneFlag)
        {
            System.out.println("???????????? ?????? ????????? ????????????.");
            return;
        }

        ShowTargetInvestPrice(Target);
        System.out.print("?????????????????? Ex 1 : ");
        int index = SystemConsole.sc.nextInt();
        SystemConsole.ClearConsole();


        if(RangeOutCheck(Target, true, index))
        {
            System.out.println("????????? ?????????");
            return;
        }

        String[] SellLoad =
        {
            "?????? ?????? : ???????????? ?????? ?????? ?????? ??????",
            "?????? ?????? : ?????? ???????????? ?????? ?????? ??????",
            "?????? ?????? : ???????????? ?????? ????????? ?????? ?????? ??????",
            "??????, ????????????"
        };
        String[] SellLoadCoin =
        {
            "?????? ?????? : ???????????? ?????? ?????? ?????? ??????",
            "?????? ?????? : ?????? ???????????? ?????? ?????? ?????? <???????????? ?????? ??????>",
            "?????? ?????? : ???????????? ?????? ????????? ?????? ?????? ??????",
            "??????, ????????????"
        };

        LoadType load;


        if(Target != CType.Estate)
        {
            do
            {
                System.out.println("?????? ????????? ?????????????????? (" + (Target == CType.Coin ? MyAccount.InvestCoin.get(index - 1).Count() : MyAccount.InvestStock.get(index - 1).Count()) + ")");
                if(Target == CType.Coin)
                    Core.Prints.Show(SellLoadCoin, 1);
                else
                    Core.Prints.Show(SellLoad, 1);
                System.out.print("?????????????????? Ex) 1, ?????? >>> ");
                String cmd = SystemConsole.sc.next();
                load = 
                switch (cmd)
                {
                    case "1", "??????", "Full", "???" -> LoadType.Full;
                    case "2", "??????", "??????" -> LoadType.Count;
                    case "3", "??????", "?????????" -> LoadType.Percent;
                    default -> LoadType.None;
                };
                
                if(cmd.equals("4") || cmd.equals("X") || cmd.equals("??????") || cmd.equals("????????????"))
                    return;

                if(load != LoadType.None)
                    break;
                SystemConsole.ClearConsole();
            }
            while(true);
        }
        else
            load = LoadType.Full;
        
        // ??? ??????
        System.out.println("");
        double in;

        if(Target != CType.Estate)
        {
            switch (load)
            {
                case Full: 
                    payslip
                    (
                        false,
                        SellExecute(Target, index, load, 0.0),
                        load,
                        0.0, 
                        Target,
                        index
                    );
                    break;
                case Count: 
                    System.out.print("????????? ?????????????????? : ");
                    in = SystemConsole.sc.nextDouble();
                    payslip
                    (
                        false,
                        SellExecute(Target, index, load, in),
                        load,
                        in, 
                        Target,
                        index
                    );
                    break;
                case Percent:
                    System.out.print("????????? ?????????????????? : ");
                    in = SystemConsole.sc.nextDouble();
                    payslip
                    (
                        false,
                        SellExecute(Target, index, load, in),
                        load,
                        in, 
                        Target,
                        index
                    );
                    break;
                default: break;
            }
        }
        else
            payslip
            (
                false,
                SellExecute(Target, index, load, 0.0),
                load,
                0.0, 
                Target,
                index
            );
    }

    private boolean SellExecute(CType Target, int index, LoadType load, double data)
    {
        switch (Target)
        {
            case Coin:
                switch (load)
                {
                    case Full: return MyAccount.InvestCoin.get(index - 1).Sell();
                    case Count: return MyAccount.InvestCoin.get(index - 1).Sell(data);
                    case Percent: return MyAccount.InvestCoin.get(index - 1).Sell((float) data);
                    default: return false;
                }
            case Stock:
                switch (load)
                {
                    case Full: return MyAccount.InvestStock.get(index - 1).Sell();
                    case Count: return MyAccount.InvestStock.get(index - 1).Sell((long) data);
                    case Percent: return MyAccount.InvestStock.get(index - 1).Sell((float) data);
                    default: return false;
                }
            case Estate:
                return MyAccount.InvestEstate.get(index - 1).Sell();
            default:
                return false;
        }
        
    }

    public void InvestPrice()
    {
        if(MyAccount.InvestCoin.size() + MyAccount.InvestStock.size() + MyAccount.InvestEstate.size() == 0)
        {
            System.out.println("???????????? ?????? ????????? ????????????.");
            return;
        }

        System.out.println("MyMoney");
        List<String> lists = new ArrayList<>();
        lists.add(new String("?????? : " + Long.toString(MyAccount.Money())));
        lists.add(new String("?????? ?????? : " + Long.toString(MyAccount.AllMoney())));
        Core.Prints.Show(lists);
        System.out.println("");

        if(MyAccount.InvestStock.size() > 0)
        {
            System.out.println("Coins");
            List<String> list = new ArrayList<>();
            for (Stock stock : MyAccount.InvestStock)
                list.add(new String(stock.Name() + "("+ stock.Count() + ") : " + stock.Price() + "(" + stock.getSellMoney() + ", " + (stock.AveragePrice() - stock.Price()) + ")"));
            Core.Prints.Show(list);
            System.out.println("\n");
        }

        if(MyAccount.InvestCoin.size() > 0)
        {
            System.out.println("Stock");
            List<String> list = new ArrayList<>();
            for (Coin coin : MyAccount.InvestCoin)
                list.add(new String(coin.Name() + "(" + coin.Count() + ") : " + coin.Price() + "(" + coin.getSellMoney() + ", " + (coin.AveragePrice() - coin.Price()) + ")"));
            Core.Prints.Show(list);
            System.out.println("\n");
        }

        if(MyAccount.InvestEstate.size() > 0)
        {
            System.out.println("Estate");
            List<String> list = new ArrayList<>();
            for (Estate estate : MyAccount.InvestEstate)
                list.add(new String(estate.Name() + "("+ estate.Type().Type().toString() + ") : " + estate.Price() + "(" + estate.getSellMoney() + ", " + (estate.AveragePrice() - estate.Price()) + ")"));
            Core.Prints.Show(list);
            System.out.println("\n");
        }
    }

    public void SmallInvestPrice()
    {
        System.out.println("MyMoney");
        List<String> lists = new ArrayList<>();
        lists.add(new String("?????? : " + Long.toString(MyAccount.Money())));
        lists.add(new String("?????? ?????? : " + Long.toString(MyAccount.AllMoney())));
        Core.Prints.Show(lists);
        System.out.println("");

        if(MyAccount.InvestCoin.size() + MyAccount.InvestStock.size() + MyAccount.InvestEstate.size() == 0)
        {
            System.out.println("???????????? ?????? ????????? ????????????.");
            return;
        }
        
        if(MyAccount.InvestStock.size() > 0)
        {
            System.out.println("Coins");
            List<String> list = new ArrayList<>();
            for (Stock stock : MyAccount.InvestStock)
                list.add(new String(stock.Name() + " : " + stock.Price() + "(" + (stock.AveragePrice() - stock.Price()) + ")"));
            Core.Prints.Show(list);
            System.out.println("");
        }

        if(MyAccount.InvestCoin.size() > 0)
        {
            System.out.println("Stock");
            List<String> list = new ArrayList<>();
            for (Coin coin : MyAccount.InvestCoin)
                list.add(new String(coin.Name() + " : " + coin.Price() + "(" + (coin.AveragePrice() - coin.Price()) + ")"));
            Core.Prints.Show(list);
            System.out.println("");
        }

        if(MyAccount.InvestEstate.size() > 0)
        {
            System.out.println("Estate");
            List<String> list = new ArrayList<>();
            for (Estate estate : MyAccount.InvestEstate)
                list.add(new String(estate.Name() + "("+ estate.Type().Type().toString() + ") : " + estate.Price() + "(" + (estate.AveragePrice() - estate.Price()) + ")"));
            Core.Prints.Show(list);
            System.out.println("");
        }
    }

    public void showPrice()
    {
        List<String> list = new ArrayList<>();

        System.out.println("MyMoney");
        List<String> lists = new ArrayList<>();
        lists.add(new String("?????? : " + Long.toString(MyAccount.Money())));
        lists.add(new String("?????? ?????? : " + Long.toString(MyAccount.AllMoney())));
        Core.Prints.Show(lists);
        System.out.println("");
        
        System.out.println("Coins");
        for (Coin coin : Coins)
        {
            if(MyAccount.InvestCoin.contains(coin))
                list.add(new String(coin.Name() +"(" +coin.Count() + ") : " + coin.Price() + "(" + coin.getSellMoney() + ", " + (coin.AveragePrice() - coin.Price()) + ")"));
            else
                list.add(new String(coin.Name() + " : " + coin.Price() + "(" + coin.RecentPrice() + ")"));
        }
        Core.Prints.Show(list);

        list = new ArrayList<>();
        System.out.println("\n\nStocks");
        for (Stock stock : Stocks)
        {
            if(MyAccount.InvestStock.contains(stock))
                list.add(new String(stock.Name() + "(" + stock.Count() + ") : " + stock.Price() + "(" + stock.getSellMoney() + ", " + (stock.AveragePrice() - stock.Price()) + ")"));
            else
                list.add(new String(stock.Name() + " : " + stock.Price() + "(" + stock.RecentPrice() + ")"));
        }
        Core.Prints.Show(list);
        

        list = new ArrayList<>();
        System.out.println("\n\nEstate");
        for (Estate estate : Estates)
        {
            if(MyAccount.InvestEstate.contains(estate))
                list.add(new String(estate.Name() + "("+ estate.EstateType().toString() + ") : " + estate.Price() + "(" + estate.getSellMoney() + ", " + (estate.AveragePrice() - estate.Price()) + ")"));
            else
                list.add(new String(estate.Name() + "("+ estate.EstateType().toString() + ") : " + estate.Price() + "(" + estate.RecentPrice() + ")"));
        }
        Core.Prints.Show(list);
    }

    public void Update(UpdateType updateType)
    {
        switch (updateType)
        {
            case Currency: UpdateCurrency(); break;
            case Dividend: UpdateDividend(); break;
            case All: UpdateCurrency(); UpdateDividend();
        }
    }
    
    private void UpdateCurrency()
    {
        for (Coin coin : Coins)
            coin.Update();
        for (Stock stock : Stocks)
            stock.Update();
        for (Estate estate : Estates)
            estate.Update();
    }

    private void UpdateDividend()
    {
        if(MyAccount.InvestCoin.size() + MyAccount.InvestStock.size() + MyAccount.InvestEstate.size() == 0)
            return;
        
        for (Coin coin : MyAccount.InvestCoin)
            coin.Dividend();
        for (Stock stock : MyAccount.InvestStock)
            stock.Dividend();
        for (Estate estate : MyAccount.InvestEstate)
            estate.Dividend();
    }

    public void Information()
    {
        String[] InfoMenu =
        {
            "??? ????????? ?????? ??????",
            "????????? ?????? ??????",
            "????????? ?????? ??????",
        };
        int mode;

        do
        {
            System.out.println("?????? ??????");
            Core.Prints.Show(InfoMenu, 1);
            System.out.println("?????????????????? >> ");
            mode =
            switch (SystemConsole.sc.next())
            {
                case "1", "??????" -> 1;
                case "2", "??????" -> 2;
                case "3", "??????", "?????????" -> 3;
                default -> -1;
            };

            if(mode != -1)
                break;
            SystemConsole.ClearConsole();
        }
        while (true);
        SystemConsole.ClearConsole();

        String[] GameInfo = {
            // ?????? ???????????? ??????
            "?????? ?????? : OsuIsGaming(????????? ?????????)",
            "??? ??????   : 2??? ?????????",
            "?????? ?????? : ?????????, ?????????, ?????????, ?????????",
            "?????? ?????? : 2021-05-01_A00_1",
            "?????? ?????? ?????? ?????? : 3???(??? 28??????)",
            "",

            // ??? ????????? ??????
            "?????? ????????? : " + SystemConsole.Difference,
            "?????? ????????? : " + SystemConsole.Years + " ( " + (SystemConsole.Years - SystemConsole.thisDay) + " ?????? )",
            "?????? ????????? : " + SystemConsole.thisDay,
            "?????? ?????? ????????? : " + SystemConsole.dayTime,
            "?????? ?????? ??????   : " + SystemConsole.format.format(SystemConsole.NextUpdateTime.getTime()),
            "????????? ?????? ????????? : " + SystemConsole.UpdateDividend
        };

        String[] Currency =
        {
            "?????? ?????? ??????",
            "?????? ?????? ??????",
            "??? ??? ?????? ?????? ??????"
        };

        CType Target = CType.None;

        // todo ?????? ??? ????????? ???????????? ????????? ?????????
        switch (mode) {
            case 1: Core.Prints.Show(GameInfo); break;
            case 2:
                do
                {
                    System.out.println("???????????? ??????");
                    Core.Prints.Show(Currency, 1);
                    System.out.print("?????????????????? Ex 1, ?????? : ");
                    Target = 
                    switch (SystemConsole.sc.next()) {
                        case "1", "??????" -> CType.Coin;
                        case "2", "??????" -> CType.Stock;
                        case "3", "???", "??????" -> CType.Estate;
                        default -> CType.None;
                    };

                    if(Target != CType.None)
                        break;
                    SystemConsole.ClearConsole();
                }
                while (true);
                
                System.out.println("");
                ShowTarget(Target);
                System.out.print("?????? ????????? ??????????????????");
                int index = SystemConsole.sc.nextInt();
                SystemConsole.ClearConsole();
                List<String> InvestData = new ArrayList<>();

                switch (Target)
                {
                    case Coin:
                        // ?????? ?????? ??????
                        Coins.get(index - 1).Information();
                        
                        System.out.println("?????? ??????\n");
                        for (String string : MyAccount.Account)
                        {
                            for (Coin coin : Coins)
                                if(string.contains(coin.Name()))
                                    InvestData.add(string);
                        }
                        Core.Prints.Show(InvestData, 1);
                        break;
                    case Stock:
                        Stocks.get(index - 1).Information();
                        
                        System.out.println("?????? ??????\n");
                        for (String string : MyAccount.Account)
                        {
                            for (Stock stock : Stocks)
                                if(string.contains(stock.Name()))
                                    InvestData.add(string);
                        }
                        Core.Prints.Show(InvestData, 1);
                        break;
                    case Estate:
                        Estates.get(index - 1).Information();

                        for (String string : MyAccount.Account)
                        {
                            for (Estate estate : Estates)
                                if(string.contains(estate.Name()))
                                    InvestData.add(string);
                        }
                        if(InvestData.size() > 0)
                        {
                            System.out.println("?????? ??????\n");   
                            Core.Prints.Show(InvestData, 1);
                        }
                        break;
                    default: break;
                }
                break;
            case 3:
                System.out.println("?????? ??????");
                Core.Prints.Show(MyAccount.Account, 1);
                break;
        }

    }

    private void ShowTarget(CType Target)
    {
        List<String> list = new ArrayList<>();

        switch (Target)
        {
            case Coin:
                for (Coin coin : Coins)
                    list.add(new String(coin.Name()));
                break;
            case Stock:
                for (Stock stock : Stocks)
                    list.add(new String(stock.Name()));
                break;
            case Estate:
                for (Estate estate : Estates)
                    list.add(new String(estate.Name()));
                break;
            default: return;
        }
        Core.Prints.Show(list, 1);
    }
}
