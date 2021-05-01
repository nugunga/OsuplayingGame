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

            됨
        */
        
        /*
            포맷 스트링 공격 테스트
            
            String format = "%70s";
            System.out.printf(format, "test");

            됨
        */
        }

    public static void main(String[] args)
    {
        // new Main();
        new SystemConsole();
    }
}