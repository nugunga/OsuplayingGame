package Core;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Economy.Economy;
import Economy.Currency.Coin;
import Economy.Currency.Estate;
import Economy.Currency.Stock;

public class IO {

    private String Currency_File_Path = "Resource/Currency.json";
    
    public static List<String> SaveData_List = new ArrayList<String>();

    public IO()
    {
        List<String> Currency_List = new ArrayList<String>();
        try
        {
            Currency_List = Files.readAllLines(Paths.get(Currency_File_Path), StandardCharsets.UTF_8);
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, ("(특정 파일 :" + this.Currency_File_Path + ")을 열 수 없습니다."), "파일 읽기 오류", JOptionPane.ERROR_MESSAGE);
            // e.getStackTrace();
            System.exit(0);
        }
        
        // 화폐 정보 설정
        String temp = "";
        for (String string : Currency_List)
            temp += string;
        DataSetting(temp);
    }

    public List<String> DataParsing(String str)
    {
        char ary[] = str.toCharArray();
        List<String> result = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < ary.length; i++) {
            if(ary[i] == '\n')
            {
                result.add(new String(temp));
                temp = "";
            }
            else
                temp += ary[i];
        }
        result.add(new String(temp));
        return result;
    }

    public void DataSetting(String data)
    {
        try
        {
            JSONParser jsp = new JSONParser();
            JSONObject File = (JSONObject) jsp.parse(data);

            JSONArray CoinData = (JSONArray) File.get("Coin");
            JSONArray StockData = (JSONArray) File.get("Stock");
            JSONArray EstateData = (JSONArray) File.get("Estate");


            // coins
            for (int i = 0; i < CoinData.size(); i++)
            {
                JSONObject temp = (JSONObject) CoinData.get(i);
                Economy.Coins.add(new Coin((String) temp.get("name"),
                                           (long) temp.get("price"),
                                           (double) temp.get("dividend"),
                                           (List<String>) DataParsing((String) temp.get("Information")),
                                           (double) temp.get("Up"),
                                           (double) temp.get("Down")));
            }

            // Stocks
            for (int i = 0; i < StockData.size(); i++)
            {
                JSONObject temp = (JSONObject) StockData.get(i);
                Economy.Stocks.add(new Stock((String) temp.get("name"),
                                             (long) temp.get("price"),
                                             (double) temp.get("dividend"),
                                             (List<String>) DataParsing((String) temp.get("Information")),
                                             (double) temp.get("Up"),
                                             (double) temp.get("Down")));
            }

            // Estate
            for (int i = 0; i < EstateData.size(); i++)
            {
                JSONObject temp = (JSONObject) EstateData.get(i);
                Economy.Estates.add(new Estate((String) temp.get("name"),
                                               (long) temp.get("price"),
                                               (double) temp.get("dividend"),
                                               (List<String>) DataParsing((String) temp.get("Information")),
                                               (double) temp.get("Up"),
                                               (double) temp.get("Down"),
                                               Estate.StringToEType((String) temp.get("type"))));
            }
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
    }
}
