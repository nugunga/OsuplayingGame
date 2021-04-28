package Economy.Currency;

/**
 * Currency Type Class
 * 
 * Made By Nugunga, 2021-04-28
 */
public class CurrencyType
{
    /**
     * Currency Type Original
     */
    enum CType 
    {
        Stock,
        Coin,
        Estate,
        None
    }
    /**
     * Currency Type
     */
    private final CType Type;

    /**
     * Get a Type
     * @return Currency Type
     */
    public CType Type() { return this.Type(); }

    /**
     * Get a Type to String
     * @return Currency Type to String
     */
    public String TypeString() { return this.Type.toString(); }

    /**
     * Convert String to Currency Type
     * @param Type String Currency Type
     * @return Type Currency Type
     */
    public static CType Type(String Type)
    {
        switch (Type)
        {
            case "Stock" : return CType.Stock;
            case "Coin" : return CType.Coin;
            case "Estate" : return CType.Coin;
            default : return CType.None;
        }
    }

    public CurrencyType(CType type)
    {
        this.Type = type;
    }

    public CurrencyType(String type)
    {
        this.Type = CurrencyType.Type(type);
    }
}
