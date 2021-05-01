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

        // 구매
        if(isBuyAndSell)
        {
            switch (type) {
                case Coin:
                    Coin coin = Coins.get(index - 1);
                    data.add("이름 : " + coin.Name());
                    data.add("타입 : " + coin.Type().TypeString());
                    data.add("구매 방법 : " + (load == LoadType.Full ? "전체 구매" : load == LoadType.Count ? "개수 구매" : "비율 구매"));
                    data.add("구매 가격 : " + coin.Price());
                    data.add("구매 개수 : " + count);
                    data.add("구매 개수 : " + coin.Count());
                    break;
                case Stock:
                    Stock stock = Stocks.get(index - 1);
                    data.add("이름 : " + stock.Name());
                    data.add("타입 : " + stock.Type().TypeString());
                    data.add("구매 방법 : " + (load == LoadType.Full ? "전체 구매" : load == LoadType.Count ? "개수 구매" : "비율 구매"));
                    data.add("구매 가격 : " + stock.Price());
                    data.add("구매 개수 : " + count);
                    data.add("전체 개수 : " + stock.Count());
                    break;
                case Estate:
                    Estate estate = Estates.get(index - 1);
                    data.add("이름 : " + estate.Name());
                    data.add("타입 : " + estate.Type().TypeString());
                    data.add("건물 타입 : " + ((Estate) estate).EstateType());
                    data.add("구매 방법 : " + (load == LoadType.Full ? "전체 구매" : load == LoadType.Count ? "개수 구매" : "비율 구매"));
                    data.add("구매 가격 : " + estate.Price());
                    break;
                default: break;
            }
        }
        // 판매
        else
        {
            switch (type) {
                case Coin:
                    Coin coin = Coins.get(index - 1);
                    data.add("이름 : " + coin.Name());
                    data.add("타입 : " + coin.Type().TypeString());
                    data.add("판매 방법 : " + (load == LoadType.Full ? "전체 판매" : load == LoadType.Count ? "개수 판매" : "비율 판매"));
                    data.add("판매 가격 : " + coin.Price());
                    data.add("판매 개수 : " + count);
                    data.add("전체 개수 : " + coin.Count());
                    break;
                case Stock:
                    Stock stock = Stocks.get(index - 1);
                    data.add("이름 : " + stock.Name());
                    data.add("타입 : " + stock.Type().TypeString());
                    data.add("판매 방법 : " + (load == LoadType.Full ? "전체 판매" : load == LoadType.Count ? "개수 판매" : "비율 판매"));
                    data.add("판매 가격 : " + stock.Price());
                    data.add("판매 개수 : " + count);
                    data.add("전체 개수 : " + stock.Count());
                    break;
                case Estate:
                    Estate estate = Estates.get(index - 1);
                    data.add("이름 : " + estate.Name());
                    data.add("타입 : " + estate.Type().TypeString());
                    data.add("건물 타입 : " + ((Estate) estate).EstateType());
                    data.add("판매 방법 : " + (load == LoadType.Full ? "전체 판매" : load == LoadType.Count ? "개수 판매" : "비율 판매"));
                    data.add("판매 가격 : " + estate.Price());
                    break;
                default: break;
            }
        }

        Core.Prints.Show(data);
        String format = "%" + (Core.Prints.LineNum()-10) + "s";
        System.out.printf(format, (func ? "거래 승인 (승인 완료)" : " 거래 실패 (승인 거부)"));
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

        if(RangeOutCheck(Target, true, index))
        {
            System.out.println("잘못된 인덱스");
            return;
        }
        
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
                    System.out.print("개수를 입력해주세요 : ");
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
                    System.out.print("비율을 입력해주세요 : ");
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


        if(RangeOutCheck(Target, true, index))
        {
            System.out.println("잘못된 인덱스");
            return;
        }

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
                    System.out.print("개수를 입력해주세요 : ");
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
                    System.out.print("비율을 입력해주세요 : ");
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
            "이 게임에 대한 정보",
            "화폐에 대한 정보",
            "입출금 내역 정보",
        };
        int mode;

        do
        {
            System.out.println("정보 메뉴");
            Core.Prints.Show(InfoMenu, 1);
            System.out.println("입력해주세요 >> ");
            mode =
            switch (SystemConsole.sc.next())
            {
                case "1", "게임" -> 1;
                case "2", "화폐" -> 2;
                case "3", "로그", "입출금" -> 3;
                default -> -1;
            };

            if(mode != -1)
                break;
            SystemConsole.ClearConsole();
        }
        while (true);
        SystemConsole.ClearConsole();

        String[] GameInfo = {
            // 게임 전반적인 정보
            "게임 이름 : OsuIsGaming(오수는 게임중)",
            "팀 이름   : 2인 개발팀",
            "팀원 이름 : 최진원, 이명재, 임오수, 김재승",
            "게임 버전 : 2021-05-01_A00_1",
            "게임 제작 소요 시간 : 3일(총 28시간)",
            "",

            // 이 게임의 정보
            "게임 난이도 : " + SystemConsole.Difference,
            "게임 엔딩일 : " + SystemConsole.Years + " ( " + (SystemConsole.Years - SystemConsole.thisDay) + " 남음 )",
            "현재 게임일 : " + SystemConsole.thisDay,
            "화폐 가격 갱신일 : " + SystemConsole.dayTime,
            "화폐 갱신 시간   : " + SystemConsole.format.format(SystemConsole.NextUpdateTime.getTime()),
            "배당금 분배 갱신일 : " + SystemConsole.UpdateDividend
        };

        String[] Currency =
        {
            "코인 정보 보기",
            "주식 정보 보기",
            "땅 및 건물 정보 보기"
        };

        CType Target = CType.None;

        // todo 일단 각 정보를 선택하는 것까지 제작함
        switch (mode) {
            case 1: Core.Prints.Show(GameInfo); break;
            case 2:
                do
                {
                    System.out.println("대상체의 정보");
                    Core.Prints.Show(Currency, 1);
                    System.out.print("입력해주세요 Ex 1, 코인 : ");
                    Target = 
                    switch (SystemConsole.sc.next()) {
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
                
                System.out.println("");
                ShowTarget(Target);
                System.out.print("화폐 번호를 입력해주세요");
                int index = SystemConsole.sc.nextInt();
                SystemConsole.ClearConsole();
                List<String> InvestData = new ArrayList<>();

                switch (Target)
                {
                    case Coin:
                        // 코인 정보 보기
                        Coins.get(index - 1).Information();
                        
                        System.out.println("투자 내역\n");
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
                        
                        System.out.println("투자 내역\n");
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

                        System.out.println("투자 내역\n");
                        for (String string : MyAccount.Account)
                        {
                            for (Estate estate : Estates)
                                if(string.contains(estate.Name()))
                                    InvestData.add(string);
                        }
                        Core.Prints.Show(InvestData, 1);
                        break;
                    default: break;
                }
                break;
            case 3:
                System.out.println("투자 내역");
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
