package Core;
import java.util.List;

public class Prints {

    private static int LineNum = 60;

    private static void ShowDivider()
    {
        for (int i = 1; i <= LineNum; i++)
            if (i == 1 || i == LineNum)
                System.out.print("+");
            else
                System.out.print("=");
        System.out.println();
    }

    public static void Show(String[] str)
    {
        ShowDivider();

        for (String string : str) {
            System.out.println("| " + string);
        }

        ShowDivider();
    }

    public static void Show(String[] str, int index)
    {
        ShowDivider();

        for (String string : str)
            System.out.println("|  " + (index++) + " : " + string);

        ShowDivider();
    }

    public static void Show(String[] str, int start, int end)
    {
        ShowDivider();

        for (int i = start - 1; i <= end - 1; i++)
            System.out.println("| " + str[i]);

        ShowDivider();
    }

    public static void Show(String[] str, int start, int end, int count)
    {
        ShowDivider();

        for (int i = start - 1; i <= end - 1; i++)
            System.out.println("|  " + (count++) +" : "+ str[i]);

        ShowDivider();
    }

    public static void Show(String[] str, boolean isNone)
    {
        ShowDivider();

        for (String string : str)
            if(string == "None__")
                break;
            else
                System.out.println("| " + string);


        ShowDivider();
    }
    public static void Show(String[] str, int start, boolean isNone)
    {
        ShowDivider();

        for (int i = start; str[i] != "None__"; i++)
            System.out.println("| " + str[i]);

        ShowDivider();
    }

    public static void Show(String[] str, int start, boolean isNone, int count)
    {
        ShowDivider();

        for (int i = start; str[i] != "None__"; i++)
            System.out.println("|  " + (count++) +" : "+ str[i]);

        ShowDivider();
    }

    public static void Show(List<String> list)
    {
        ShowDivider();

        for (String string : list)
            System.out.println("|  " + string);

        ShowDivider();
    }
}
