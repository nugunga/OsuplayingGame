import Core.IO;
import Core.SystemConsole;

public class Main
{

    /**
    테스트 목적
    */
    public Main()
    {
        /* 
            업캐스팅 테스트
            업캐스팅후 변수들을 본 결과 변화 없음
            Coin coin = new Coin("테스트", 109900, 0.025, null, 0.314, 0.058);
            
            Currency test = (Currency) coin;
            System.out.println(test.Type().TypeString());
        */
    }

    public static void main(String[] args)
    {
        // new Main();
        new SystemConsole();
    }
}