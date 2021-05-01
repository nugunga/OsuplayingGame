package Core;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import Economy.Economy;
import Economy.MyAccount;
import Economy.Economy.UpdateType;

public class SystemConsole {
    
    public static Scanner sc;
    private Economy Console;

    private static long DifferenceLong = 1000000;

    // 디버그 모드
    public static boolean debugMode = true;
    // 게임 엔딩 년도
    public static int Years = 0;
    // 오늘
    public static long thisDay = 0;
    // 난이도
    public static String Difference = "Normal";
    // 지금 초
    private static long thisTime = 0;
    // 월세 지급 시간
    public static long UpdateDividend = 30;
    // 화폐 변동 시간
    public static int dayTime = 10;
    // 하루 변동 여부
    private static boolean afterDay = false;
    // 실제 시간
    public static Calendar NextUpdateTime;

    // 포멧
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void init()
    {
        new IO();
        Console = new Economy(DifferenceLong);

        SystemConsole.NextUpdateTime = Calendar.getInstance();
        SystemConsole.NextUpdateTime.add(Calendar.SECOND, SystemConsole.dayTime);

        Timer Timer = new Timer();
        TimerTask TimeTask = new TimerTask()
        {
            @Override
            public void run()
            {
                SystemConsole.thisTime += 1000;
                MyAccount.Update();

                // 하루가 지남
                if(SystemConsole.thisTime / 1000 == SystemConsole.dayTime)
                {
                    Console.Update(UpdateType.Currency);
                    SystemConsole.thisDay++;
                    SystemConsole.afterDay = true;
                    SystemConsole.thisTime %= SystemConsole.dayTime;
                    SystemConsole.NextUpdateTime.add(Calendar.SECOND, SystemConsole.dayTime);
                    JOptionPane.showMessageDialog(null, "하루가 지났습니다.", "가격 변동 알림", JOptionPane.INFORMATION_MESSAGE);
                }

                // 한달 또는 증여금을 받는 날 지남
                if(SystemConsole.thisDay % SystemConsole.UpdateDividend == 0)
                    Console.Update(UpdateType.Dividend);
                
                // 게임 엔딩 체크
                if(SystemConsole.Years != 133225)
                {
                    // 게임 종료
                    if(SystemConsole.Years == SystemConsole.thisDay)
                    {
                        // todo 게임 종료 구현
                    }
                }
            }
        };
        Timer.schedule(TimeTask, 1000, 1000);
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
            "None__",
            "30초",

            "45초",
            "60초",
            "90초",
            "기타",
            "None__",
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
                SystemConsole.Difference = switch (sc.next()) {
                    case "1", "1만원", "만원", "매우 어려움", "10,000", "10000": SystemConsole.DifferenceLong = 10000; yield "VeryHard";
                    case "2", "10만원", "어려움", "100,000", "100000": SystemConsole.DifferenceLong = 100000; yield "Hard";
                    case "3", "100만원", "보통", "1,000,000", "1000000": SystemConsole.DifferenceLong = 1000000; yield "Normal";
                    case "4", "1000만원", "쉬움", "10,000,000", "10000000": SystemConsole.DifferenceLong = 10000000; yield "Easy";
                    default: SystemConsole.DifferenceLong = 1000000; yield "Normal";
                };
                
                boolean result = YesOrNo("\n\n정말로 이 난이도(" + SystemConsole.Difference + ")를 하실 건가요? (Y, n) : ");
                if(result)
                    break;
                ClearConsole();
            } while(true);
            ClearConsole();

            // 하루 시간 설정
            do
            {
                System.out.println("하루로 취급하는 시간(초)를 결정해주세요");
                Prints.Show(Hello, 24, true, 1);
                System.out.print("입력해주세요 (Ex 1, 30초, 삼십초, 기타) : ");
                SystemConsole.dayTime =
                switch (sc.next()) {
                    case "1", "30", "30초", "사십초" -> 30;
                    case "2", "45", "45초", "사십오초" -> 45;
                    case "3", "60", "60초", "육십초" -> 60;
                    case "4", "90", "90초", "구십초" -> 90;
                    case "5", "기타" -> 0;
                    default -> -1;
                };

                if(SystemConsole.dayTime == -1)
                {
                    ClearConsole();
                    continue;
                }

                if(SystemConsole.dayTime == 0)
                {
                    System.out.print("입력해주세요 (Digit) : ");
                    SystemConsole.dayTime = sc.nextInt();
                }

                boolean result = YesOrNo("\n\n정말로 하루로 취급하는 시간(초, " + SystemConsole.dayTime + ") 를 하실 건가요? (Y, n) : ");
                if(result)
                    break;
                ClearConsole();
            }
            while (true);
            ClearConsole();
            
            // 게임 엔딩 결정
            do
            {
                System.out.println("게임 엔딩 조건을 설정하겠습니다.");
                Prints.Show(Hello, 18, true, 1);
                System.out.print("입력해주세요 (Ex 1, 1년, 365 등등) : ");
                SystemConsole.Years = switch (sc.next()) {
                    case "1", "1년", "일년", "365" -> 365;
                    case "2", "2년", "이년", "730" -> 730;
                    case "3", "5년", "오년", "1825"-> 1825;
                    case "4", "10년","십년", "3650"-> 3650;
                    case "5", "기타" -> 0;
                    default -> -1;
                };
                
                if(SystemConsole.Years == -1)
                {
                    ClearConsole();
                    continue;
                }

                if(SystemConsole.Years == 0)
                {
                    System.out.print("입력해주세요 (Digit 또는 Inf) : ");
                    String t = sc.next();
                    SystemConsole.Years = switch (t)
                    {
                        case "Inf", "무한", "제한없음", "없음" -> 133225;
                        default -> Integer.parseInt(t);
                    };
                }

                boolean result = YesOrNo("\n\n정말로 이 게임 종료 조건(" + (SystemConsole.Years/365 == 365 ? "Inf" : SystemConsole.Years/365) + "년)를 하실 건가요? (Y, n) : ");
                if(result)
                    break;
                ClearConsole();
            } while(true);
            ClearConsole("게임 설정을 마쳤습니다.");

            // Setting End
            System.out.println("게임 설정 결과를 확인합니다.");

            String[] t = new String[3];
            t[0] = "게임 난이도 : " + SystemConsole.Difference + "(" + SystemConsole.DifferenceLong + ")";
            t[1] = "게임 엔딩 조건 : " + (SystemConsole.Years/365==365 ? "Inf" : SystemConsole.Years) + "(" + (SystemConsole.Years/365==365 ? "Inf" : SystemConsole.Years / 365)+"년)";
            t[2] = "하루로 취급하는 시간 : " + SystemConsole.dayTime;
            SystemConsole.UpdateDividend =
                switch (SystemConsole.Difference)
                {
                    case "Easy" -> 15;
                    case "Normal" -> 30;
                    case "Hard" -> 60;
                    case "VeryHard" -> 90;
                    default -> 30;
                };

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
        Hello();
        init();
        String cmd = "";
        String[] Menu = 
        {
            "새로고침",
            "시세",
            "구매",
            "판매",
            "보유 자산",
            "정보 보기",
            "프로그램 종료 (X)"
        };
        
        do
        {
            Console.SmallInvestPrice();
            ShowTime();

            if(SystemConsole.afterDay)
            {
                ClearConsole();
                System.out.println("\n 하루 변동폭은 다음과 같습니다.");
                ShowTime();
                Console.showPrice();
                ClearConsole("");
                SystemConsole.afterDay = false;
                continue;
            }

            System.out.println("\n\n메뉴(명령어)");
            Core.Prints.Show(Menu, 0);
            System.out.print("입력해주세요 Ex) 1, 시세 >>> "); cmd = sc.next();
            
            switch (cmd)
            {
                case "0", "Continue", "계속" : ClearConsole(); break; 
                case "1", "시세": ClearConsole(); Console.showPrice(); ClearConsole(""); break;
                case "2", "구매": ClearConsole(); Console.Buy(); ClearConsole(""); break;
                case "3", "판매": ClearConsole(); Console.Sell(); ClearConsole(""); break;
                case "4", "보유자산" : ClearConsole(); Console.InvestPrice(); ClearConsole(""); break;
                case "5", "정보", "정보보기" : ClearConsole(); Console.Information(); ClearConsole(""); break;
                case "6", "X", "종료", "프로그램종료": System.exit(0); sc.close(); break;
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

    private void ShowTime()
    {
        
        String[] Time = new String[2];

        System.out.println("\n시계");
        Time[0] = ("게임 시간 : ( " + SystemConsole.thisDay + " / "+ (SystemConsole.Years == 133225 ? "Inf" : SystemConsole.Years) + " ) ");
        Time[1] = ("현재 시간 : " + format.format(new Date()) + " : 다음 시간 "+ format.format(SystemConsole.NextUpdateTime.getTime()));
        Core.Prints.Show(Time);
    }
}
