import Economy.Economy;
import System.IO;
import System.SystemConsole;

public class Main
{
    private SystemConsole sc;    

    public Main()
    {
        new IO();
        new Economy();
        sc = new SystemConsole();

    }


    public static void main(String[] args)
    {
        new Main();
    }
}