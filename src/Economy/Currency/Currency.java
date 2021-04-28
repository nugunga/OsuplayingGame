package Economy.Currency;

import java.util.List;

import Economy.Currency.CurrencyType.CType;
import Economy.Currency.Interface.IOthor;
import Economy.Currency.Interface.ISellAndBuy;

/**
 * Currency Class
 * 
 * Made By Nugunga, 2021-04-28
 */
public abstract class Currency implements IOthor, ISellAndBuy {
    
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
     * Convert Now Price
     */
    protected Double DPrice;

    /**
     * Get a Standard Price
     * @return Standard Price
     */
    public long Price() { return this.Price; }

    /**
     * Now DPrice add Price
     * @return Double Price;
     */
    protected double DPrice() { return this.DPrice; }

    /**
     * Recent Price Standard
     */
    protected long RecentPrice;

    /**
     * Convert RecentPrice to double
     */
    protected double DRecentPrice;

    /**
     * Get a RecentPrice
     * @return RecentPrice
     */
    public long RecentPrice() { return this.RecentPrice; }

    /**
     * Get a DRecentPrice
     * @return DRecentPrice
     */
    protected double DRecentPrice() { return this.DRecentPrice(); }

    /**
     * Buy a Price Standard
     */
    protected long BuyPrice;

    /**
     * Buy a Price to double
     */
    protected double DBuyPrice;

    /**
     * Get a Buy a Price
     * @return BuyPrice
     */
    public long BuyPrice() { return this.BuyPrice; }

    /**
     * Get a Buy a DBuyPrice
     * @return DBuyPrice
     */
    protected double DBuyPrice() { return this.DBuyPrice; }

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
    protected double Dividend() { return this.Dividend; }

    /**
     * Information Datas
     */
    private List<String> InformationData;
    

    protected long Count;
    public long Count() { return this.Count; }


    /**
     * Currency
     * @param name Name
     * @param price Price
     * @param dividend Dividend
     * @param data Information Data
     * @param Up Up Percent
     * @param Down Down Percent
     */
    public Currency(String name, CType type, long price, long dividend, List<String> data, double up, double down)
    {
        this.Name = name;
        this.Type = new CurrencyType(type);
        this.Price = price;
        this.DPrice = (double) price;
        this.RecentPrice = 0;
        this.DRecentPrice = 0;
        this.BuyPrice = 0;
        this.DBuyPrice = 0;
        this.AveragePrice = 0;
        this.Dividend = dividend;
        this.InformationData = data;
        this.Count = 0;
    }

    /**
     * Currency
     * @param name Name
     * @param price Price
     * @param dividend Dividend
     * @param data Information Data
     * @param Up Up Percent
     * @param Down Down Percent
     */
    public Currency(String name, CType type, double price, double dividend, List<String> data, double up, double down)
    {
        this.Name = name;
        this.Type = new CurrencyType(type);
        this.Price = 0;
        this.DPrice = price;
        this.RecentPrice = 0;
        this.DRecentPrice = 0;
        this.BuyPrice = 0;
        this.DBuyPrice = 0;
        this.AveragePrice = 0;
        this.Dividend = dividend;
        this.InformationData = data;
        this.Count = 0;
    }

    @Override
    public void Information()
    {
        
    }

    @Override
    public void Update()
    {
        
    }    
}
