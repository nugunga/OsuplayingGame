package Economy;

import java.util.ArrayList;
import java.util.List;

import Economy.Currency.Coin;
import Economy.Currency.Estate;
import Economy.Currency.Stock;

public class MyAccount {
    
    private static long Money;
    private static long AllMoney;
    public static long Money() { return MyAccount.Money; }
    public static void Money(long price) { MyAccount.Money += price; }
    public static long AllMoney() { return MyAccount.AllMoney; }

    public static List<Coin> InvestCoin;
    public static List<Stock> InvestStock;
    public static List<Estate> InvestEstate;

    private static long Loan;
    public static long Loan() { return MyAccount.Loan; }

    private static long UseLoan;
    public static long UseLoan() { return MyAccount.UseLoan; }
    public static void UseLoan(long money) { MyAccount.UseLoan += money; }


    public enum CreditRating
    {
        AA,         // 1 * 10^15
        A,          // 1 * 10^14
        BB,         // 1 * 10^12
        B,          // 1 * 10^10
        CC,         // 1 * 10^8
        C,          // 1 * 10^6
    }
    private static CreditRating Credit;
    public static CreditRating Credit() { return MyAccount.Credit; }

    public MyAccount(long money)
    {
        MyAccount.Money = money;
        InvestCoin = new ArrayList<>();
        InvestStock = new ArrayList<>();
        InvestEstate = new ArrayList<>();
        Update();
    }

    public static void Update()
    {

        long temp = MyAccount.Money;
        for (Coin coin : InvestCoin)
            temp += coin.Price() * coin.Count();
        for (Stock stock : InvestStock)
            temp += stock.Price() * stock.Count();
        for (Estate estate : InvestEstate)
            temp += estate.Price();
        
        MyAccount.AllMoney = temp - MyAccount.UseLoan;

        if(AllMoney >= 10000000000000L)
        {
            MyAccount.Loan = 1000000000000000L;
            MyAccount.Credit = CreditRating.AA;
        }
        else if(AllMoney < 10000000000000L && AllMoney >= 1000000000000L)
        {
            MyAccount.Loan = 100000000000000L;
            MyAccount.Credit = CreditRating.A;
        }
        else if(AllMoney < 1000000000000L && AllMoney >= 10000000000L)
        {
            MyAccount.Loan = 1000000000000L;
            MyAccount.Credit = CreditRating.BB;
        }
        else if(AllMoney < 10000000000L && AllMoney >= 1000000000L)
        {
            MyAccount.Loan = 10000000000L;
            MyAccount.Credit = CreditRating.B;
        }
        else if(AllMoney < 1000000000L && AllMoney >= 10000000L)
        {
            MyAccount.Loan = 100000000L;
            MyAccount.Credit = CreditRating.CC;
        }
        else
        {
            MyAccount.Loan = 1000000L;
            MyAccount.Credit = CreditRating.C;
        }
    }


}
