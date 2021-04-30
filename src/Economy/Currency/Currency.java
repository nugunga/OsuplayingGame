package Economy.Currency;

import java.util.ArrayList;
import java.util.List;

import Economy.MyAccount;
import Economy.Currency.CurrencyType.CType;
import Economy.Currency.Interface.IOther;
import Economy.Currency.Interface.ISellAndBuy;

/**
 * Currency Class
 * 
 * Made By Nugunga, 2021-04-28
 */
public abstract class Currency implements IOther, ISellAndBuy {
    
    /**
     * Currency Name
     */
    private final String Name;
    
    /**
     * Get a Currency Name
     * @return Currency Name
     */
    public String Name() { return this.Name; }
    
    /**
     * Currency Type Setting
     */
    private CurrencyType Type;

    /**
     * Get a Currency Type
     * @return Currency Type
     */
    public CurrencyType Type() { return this.Type; }


    /**
     * Currency Now Price
     */
    protected long Price;
    
    /**
     * Get a Standard Price
     * @return Standard Price
     */
    public long Price() { return this.Price; }
    public void Price(long Money) { this.Price += Money; }

    /**
     * Recent Price Standard
     */
    protected long RecentPrice;

    /**
     * Get a RecentPrice
     * @return RecentPrice
     */
    public long RecentPrice() { return this.RecentPrice; }

    /**
     * Buy a Price Standard
     */
    protected long BuyPrice;

    /**
     * Get a Buy a Price
     * @return BuyPrice
     */
    public long BuyPrice() { return this.BuyPrice; }

    /**
     * Average Price Standard
     */
    protected double AveragePrice;
    
    /**
     * Get a AveragePrice
     * @return AveragePrice
     */
    public double AveragePrice() { return this.AveragePrice; }

    /**
     * Dividend
     */
    private double Dividend;
    
    /**
     * Get a Dividend
     * @return Dividend
     */
    public double Dividend() { return this.Dividend; }

    /**
     * Information Data
     */
    private List<String> InformationData;

    protected boolean isBuy;
    
    private double Up;
    private double Down;

    /**
     * Currency
     * @param name Name
     * @param price Price
     * @param dividend Dividend
     * @param data Information Data
     * @param Up Up Percent
     * @param Down Down Percent
     */
    public Currency(String name, CType type, long price, double dividend, List<String> data, double up, double down)
    {
        this.Name = name;
        this.Type = new CurrencyType(type);
        this.Price = price;
        this.RecentPrice = 0;
        this.BuyPrice = 0;
        this.AveragePrice = 0;
        this.Dividend = dividend;
        this.InformationData = data;
        this.Up = up;
        this.Down = down;
        this.isBuy = false;
    }

    @Override
    public void Information()
    {
        List<String> list = new ArrayList<>();

        // 이름
        list.add(new String("이름 : " + this.Name));
        // 타입
        list.add(new String("타입 : " + this.Type));
        list.add("\n가격 관련");
        // 가격
        list.add(new String("  현재 가격 : " + this.Type));
        // 최근 가격
        list.add(new String("  이전 가격 : " + this.RecentPrice));

        if(this.isBuy)
        {
            // 구매 가격
            list.add(new String("  구매 가격 : " + this.BuyPrice));
            // 평균 가격
            list.add(new String("  평단가 : " + this.AveragePrice));
        }

        // 화폐 설명
        for (String string : InformationData)
            list.add(string);
        
        System.out.println("화폐 정보");
        Core.Prints.Show(list);
    }

    @Override
    public void Update()
    {
        this.RecentPrice = this.Price;

        long money = this.Price;
        if(Math.random() * 10 > 5)
        { // +
            money *= Math.random() * this.Up;
            this.Price(money);
        }
        else
        { // -
            money *= Math.random() * this.Down;
            this.Price(-money);
        }

    }

    @Override
    public void UpdateDividend() {
        long Money = this.Price;
        Money *= this.Dividend;
        MyAccount.Money(Money);
    }
}
