package Core;

import java.util.Scanner;

import Economy.Economy;

public class SystemConsole {
    
    public static Scanner sc;
    private Economy Console;

    private int Years = 0;
    private String Difference = "Normal";
    private long DifferenceLong = 1000000;
    public static boolean debugMode = true;

    public void init()
    {
        new IO();
        
        Console = new Economy(DifferenceLong);
    }

    public boolean YesOrNo(String msg)
    {
        int result;
        do
        {
            System.out.print(msg);
            String temp = sc.next();
            result = switch (temp)
            {
                case "Y", "y", "yes", "Yes", "YES"-> 1;
                case "N", "n", "no", "No", "NO" -> 2;
                default -> 3;
            };

            if(result == 1 || result == 2)
                break;
        } while (true);
        return result == 1;
    }

    private void Hello()
    {
        ClearConsole();
        String Hello[] = 
        {
            "주식 게임에 오신것을 환영합니다.",
            "",
            "이 게임의 목적은 가장 많은 돈을 지정된 일자까지 버는 것을 목표합니다",
            "",
            "이 게임에서 투자할 수 있는 자산은",

            "1. 주식",
            "2. 코인",
            "3. 땅",
            "이고 가격 및 인센티브는 다음과 같습니다.",                                                 
            "",

            "가격: 코인 < 주식 < 땅",
            "인센티브 : 코인(X) < 주식(배당금), 땅(월세, 지세)",
            "None__",
            "1만원 (10,000, 매우 어려움)",
            "10만원 (100,000, 어려움)",

            "100만원 (1,000,000, 보통) : 기본값",
            "1000만원 (10,000,000 쉬움)",
            "None__",
            "1년",
            "2년",
            "5년",                                               
            "10년",
            "기타",
            "None__"
        };
        Prints.Show(Hello, true);
        ClearConsole("게임에 필요한 몇 가지를 설정하겠습니다.\n");
        do 
        {
            // 난이도 설정
            do
            {
                System.out.println("소지 현금(난이도)을 결정해주세요");
                Prints.Show(Hello, 13, true, 1);
                System.out.print("입력해주세요 (Ex 1, 1만원, 10000, 매우 어려움 등등) : ");
                this.Difference = switch (sc.next()) {
                    case "1", "1만원", "만원", "매우 어려움", "10,000", "10000": this.DifferenceLong = 10000; yield "VeryHard";
                    case "2", "10만원", "어려움", "100,000", "100000": this.DifferenceLong = 100000; yield "Hard";
                    case "3", "100만원", "보통", "1,000,000", "1000000": this.DifferenceLong = 1000000; yield "Normal";
                    case "4", "1000만원", "쉬움", "10,000,000", "10000000": this.DifferenceLong = 10000000; yield "Easy";
                    default: this.DifferenceLong = 1000000; yield "Normal";
                };
                
                boolean result = YesOrNo("\n\n정말로 이 난이도(" + this.Difference + ")를 하실 건가요? (Y, n) : ");
                if(result)
                    break;
                ClearConsole();
            } while(true);
            ClearConsole();
            
            // 게임 엔딩 결정
            do
            {
                System.out.println("게임 엔딩 조건을 설정하겠습니다.");
                Prints.Show(Hello, 18, true, 1);
                System.out.print("입력해주세요 (Ex 1, 1년, 365 등등) : ");
                this.Years = switch (sc.next()) {
                    case "1", "1년", "일년", "365" -> 365;
                    case "2", "2년", "이년", "730" -> 730;
                    case "3", "5년", "오년", "1825"-> 1825;
                    case "4", "10년","십년", "3650"-> 3650;
                    case "5", "기타" -> 0;
                    default -> -1;
                };
                
                if(this.Years == -1)
                {
                    ClearConsole();
                    continue;
                }

                if(this.Years == 0)
                {
                    System.out.print("입력해주세요 (Digit 또는 Inf) : ");
                    String t = sc.next();
                    this.Years = switch (t)
                    {
                        case "Inf", "무한", "제한 없음", "제한없음", "없음" -> 133225;
                        default -> Integer.parseInt(t);
                    };
                }

                boolean result = YesOrNo("\n\n정말로 이 게임 종료 조건(" + (this.Years/365 == 365 ? "Inf" : this.Years/365) + "년)를 하실 건가요? (Y, n) : ");
                if(result)
                    break;
                ClearConsole();
            } while(true);
            ClearConsole();

            // Setting End
            System.out.println("게임 설정을 마쳤습니다.");
            System.out.println("게임 설정 결과를 확인합니다.");

            String[] t = new String[2];
            t[0] = "게임 난이도 : " + this.Difference + "(" + this.DifferenceLong + ")";
            t[1] = "게임 엔딩 조건 : " + (this.Years/365==365 ? "Inf" : this.Years) + "(" + (this.Years/365==365 ? "Inf" : this.Years / 365)+"년)";
            
            Prints.Show(t);
            boolean result = YesOrNo("정말로 이 조건으로 플레이 하시겠습니까? (Y/n) : ");
            if(result)
                break;
            ClearConsole("다시 설정합니다.");
        } while (true);

        ClearConsole("확인하였습니다. 메인화면으로 들어갑니다.");
    }    

    public SystemConsole()
    {
        SystemConsole.sc = new Scanner(System.in);
        if(!SystemConsole.debugMode)
            Hello();
        init();
        String cmd = "";
        String[] Menu = 
        {
            "시세",
            "구매",
            "판매",
            "보유 자산",
            "프로그램 종료"
        };

        do
        {
            Console.SmallInvestPrice();
            
            System.out.println("\n메뉴(명령어)");
            Core.Prints.Show(Menu, 1);
            System.out.print("입력해주세요 Ex) 1, 시세 >>> "); cmd = sc.next();
            
            switch (cmd)
            {
                case "1", "시세": ClearConsole(); Console.showPrice(); ClearConsole(""); break;
                case "2", "구매": ClearConsole(); Console.Buy(); ClearConsole(""); break;
                case "3", "판매": ClearConsole(); Console.Sell(); ClearConsole(""); break;
                case "4", "보유 자산" : ClearConsole(); Console.InvestPrice(); ClearConsole(""); break;
                case "5", "X", "종료", "프로그램 종료": System.exit(0); sc.close(); break;
                default : ClearConsole("잘못된 멸령어입니다 : " + cmd + "\n");
            }

        } while(true);
    }


    public static void ClearConsole(String Message)
    {
        System.out.println("\n\n\n" + Message + " (Please Press Enter...)");
        try
        {
            System.in.read();
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }

        ClearConsole();
    }

    public static void ClearConsole()
    {
        try
        {
            String operatingSystem = System.getProperty("os.name");
              
            if(operatingSystem.contains("Windows"))
            {        
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            }
            else
            {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            } 
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

}
