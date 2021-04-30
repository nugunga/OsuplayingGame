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

    public Economy(long money)
    {
        if(!SystemConsole.debugMode)
            new MyAccount(money);
        else
            new MyAccount(1000000);
        System.out.println("Test");

        if(SystemConsole.debugMode)
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

    public void Buy()
    {
        CType Target;

        String[] Currency = {
            "코인 구매",
            "주식 구매",
            "땅 및 건물 구매",
            "취소, 돌아가기"
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

            if(cmd.equals("4") || cmd.equals("X") || cmd.equals("취소") || cmd.equals("돌아가기"))
                return;

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
            "비율 구매 : 현재 구매할 수 있는 개수 중 비율 만큼 구입",
            "취소, 돌아가기"
        };
        String[] BuyLoadCoin = {
            "전체 구매 : 현재 구매할 수 있는 최대 개수 구입",
            "개수 구매 : 특정 개수만큼 구입 <실수만큼 구입 가능>",
            "비율 구매 : 현재 구매할 수 있는 개수 중 비율 만큼 구입",
            "취소, 돌아가기"
        };
        LoadType load;

        if(Target != CType.Estate)
        {
            do
            {
                System.out.println("구매 방법을 선택해주세요");
                if(Target != CType.Coin)
                    Core.Prints.Show(BuyLoad, 1);
                else
                    Core.Prints.Show(BuyLoadCoin, 1);    
                System.out.print("입력해주세요 Ex) 1, 전체 >>> ");
                String cmd = SystemConsole.sc.next();
                load = 
                switch (cmd) {
                    case "1", "전체", "풀", "Full" -> LoadType.Full;
                    case "2", "부분", "개수", "Count" -> LoadType.Count;
                    case "3", "퍼센트", "비율" -> LoadType.Percent;
                    default -> LoadType.None;
                };
                
                if(cmd.equals("4") || cmd.equals("X") || cmd.equals("취소") || cmd.equals("돌아가기"))
                    return;
                
                if(load != LoadType.None)
                    break;
                SystemConsole.ClearConsole();
            }
            while (true);
        }
        else
            load = LoadType.Full;

        // 줄 바꿈
        System.out.println("");

        if(Target != CType.Estate)
        {
            switch (load)
            {
                case Full:
                    BuyExecute(Target, index, load, 0.0);
                    break;
                case Count:
                    System.out.print("개수를 입력해주세요 : ");
                    BuyExecute(Target, index, load, SystemConsole.sc.nextDouble());
                    break;
                case Percent:
                    System.out.print("비율을 입력해주세요 : ");
                    BuyExecute(Target, index, load, SystemConsole.sc.nextDouble());
                    break;
            default:
                break;
            }
        }
        else
            BuyExecute(Target, index, load, 0.0);
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
            System.out.println("보유하고 있는 자산은 없습니다.");
            return;
        }

        CType Target;
        boolean NoneFlag = false;

        String[] Currency =
        {
            "코인 판매",
            "주식 판매",
            "땅이나 건물 판매",
            "취소, 돌아가기"
        };

        if(MyAccount.InvestCoin.size() + MyAccount.InvestStock.size() + MyAccount.InvestEstate.size() > 0)
        {
            if(MyAccount.InvestCoin.size() > 0)
                Currency[0] += " (판매 가능)";
            if(MyAccount.InvestStock.size() > 0)
                Currency[1] += " (판매 가능)";
            if(MyAccount.InvestEstate.size() > 0)
                Currency[2] += " (판매 가능)";
        }

        do
        {
            System.out.println("판매 메뉴(판매)");
            Core.Prints.Show(Currency, 1);
            System.out.print("입력해주세요 Ex) 1, 코인 : ");
            String cmd = SystemConsole.sc.next();
            Target =
            switch (cmd) {
                case "1", "코인", "코인 판매": 
                    if(MyAccount.InvestCoin.size()== 0)
                        NoneFlag = true;
                    yield CType.Coin;
                case "2", "주식", "주식 판매": 
                    if(MyAccount.InvestStock.size()== 0)
                        NoneFlag = true;
                    yield CType.Stock;
                case "3", "땅", "건물", "땅 판매", "건물 판매", "땅이나 건물 판매": 
                    if(MyAccount.InvestEstate.size()== 0)
                        NoneFlag = true;
                    yield CType.Estate;
                default: yield CType.None;
            };

            if(cmd.equals("4") || cmd.equals("X") || cmd.equals("취소") || cmd.equals("돌아가기"))
                return;

            if(Target != CType.None)
                break;
            SystemConsole.ClearConsole();
        }
        while (true);
        SystemConsole.ClearConsole();

        if(NoneFlag)
        {
            System.out.println("보유하고 있는 자산은 없습니다.");
            return;
        }

        ShowTargetInvestPrice(Target);
        System.out.print("입력해주세요 Ex 1 : ");
        int index = SystemConsole.sc.nextInt();
        SystemConsole.ClearConsole();

        String[] SellLoad =
        {
            "전체 판매 : 소지하고 있는 개수 전부 판매",
            "개수 판매 : 특정 소지하고 있는 개수 판매",
            "비율 판매 : 소지하고 있는 개수의 비율 만큼 판매",
            "취소, 돌아가기"
        };
        String[] SellLoadCoin =
        {
            "전체 판매 : 소지하고 있는 개수 전부 판매",
            "개수 판매 : 특정 소지하고 있는 개수 판매 <실수만큼 판매 가능>",
            "비율 판매 : 소지하고 있는 개수의 비율 만큼 판매",
            "취소, 돌아가기"
        };

        LoadType load;


        if(Target != CType.Estate)
        {
            do
            {
                System.out.println("판매 방법을 선택해주세요 (" + (Target == CType.Coin ? MyAccount.InvestCoin.get(index - 1).Count() : MyAccount.InvestStock.get(index - 1).Count()) + ")");
                if(Target == CType.Coin)
                    Core.Prints.Show(SellLoadCoin, 1);
                else
                    Core.Prints.Show(SellLoad, 1);
                System.out.print("입력해주세요 Ex) 1, 전체 >>> ");
                String cmd = SystemConsole.sc.next();
                load = 
                switch (cmd)
                {
                    case "1", "전체", "Full", "풀" -> LoadType.Full;
                    case "2", "개수", "부분" -> LoadType.Count;
                    case "3", "비율", "퍼센트" -> LoadType.Percent;
                    default -> LoadType.None;
                };
                
                if(cmd.equals("4") || cmd.equals("X") || cmd.equals("취소") || cmd.equals("돌아가기"))
                    return;

                if(load != LoadType.None)
                    break;
                SystemConsole.ClearConsole();
            }
            while(true);
        }
        else
            load = LoadType.Full;
        
        // 줄 바꿈
        System.out.println("");

        if(Target != CType.Estate)
        {
            switch (load)
            {
                case Full: SellExecute(Target, index, load, 0.0); break;
                case Count: 
                    System.out.print("개수를 입력해주세요 : ");
                    SellExecute(Target, index, load, SystemConsole.sc.nextDouble());
                    break;
                case Percent:
                    System.out.print("비율을 입력해주세요 : ");
                    SellExecute(Target, index, load, SystemConsole.sc.nextDouble());
                    break;
                default: break;
            }
        }
        else
            SellExecute(Target, index, load, 0.0);
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
        lists.add(new String("현금 : " + Long.toString(MyAccount.Money())));
        lists.add(new String("전체 자산 : " + Long.toString(MyAccount.AllMoney())));
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
                list.add(new String(estate.Name() + "("+ estate.Type().Type().toString() + ") : " + estate.Price() + "(" + estate.getSellMoney() + ", " + (estate.AveragePrice() - estate.Price()) + ")"));
            else
                list.add(new String(estate.Name() + "("+ estate.Type().Type().toString() + ") : " + estate.Price() + "(" + estate.RecentPrice() + ")"));
        }
        Core.Prints.Show(list);
    }

    public enum UpdateType { Currency, Dividend, All };
    /**
     * 
     * @param UpdateType true : 화폐 가격, false : 배당금
     */
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
}
