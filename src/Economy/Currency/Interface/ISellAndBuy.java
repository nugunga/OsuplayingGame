package Economy.Currency.Interface;

/**
 * Sell And Buy Method Interface
 * 
 * Made By Nugunga, 2021-04-28
 */
public interface ISellAndBuy {
    public boolean Buy();

    public boolean Buy(long count);

    public boolean Buy(float percent);

    public boolean Buy(double count);

    public boolean Sell();

    public boolean Sell(long count);

    public boolean Sell(float percent);

    public boolean Sell(double count);
    
}
