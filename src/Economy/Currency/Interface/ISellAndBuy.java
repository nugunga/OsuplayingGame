package Economy.Currency.Interface;

/**
 * Sell And Buy Method Interface
 * 
 * Made By Nugunga, 2021-04-28
 */
public interface ISellAndBuy {
    public boolean Buy();

    public boolean Buy(long count);

    public boolean Buy(float Percent);

    public boolean Buy(double Count);

    public boolean Sell();

    public boolean Sell(long count);

    public boolean Sell(float Percent);

    public boolean Sell(double Count);
    
}
