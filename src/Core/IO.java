package Core;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Economy.Economy;
import Economy.MyAccount;
import Economy.Currency.Coin;
import Economy.Currency.Estate;
import Economy.Currency.Stock;

public class IO {

    private String Currency_File_Path = "Resource/Currency.json";
    private String Save_File_Path = "Resource/SaveData.json";
    
    public static List<String> SaveData_List = new ArrayList<String>();

    public IO()
    {
        List<String> Currency_List = new ArrayList<String>();
        try
        {
            Currency_List = Files.readAllLines(Paths.get(Currency_File_Path), StandardCharsets.UTF_8);
            SaveData_List = Files.readAllLines(Paths.get(Save_File_Path), StandardCharsets.UTF_8);
        }
        catch(IOException e)
        {
            e.printStackTrace();
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



    public static void GameSave(String GameSaveName)
    {
        int index = 0;
        // End 직전의 데이터를 찾아야함
        for (String string : SaveData_List) {
            if(string.equals("        {\"cmd\" : \"end\"}"))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();

                // 현재 가지고 있는 정보를 처리
                String stockData = "";
                String coinData = "";
                String estateData = "";
                for (int i = 0; i < MyAccount.InvestStock.size(); i++)
                {
                    Stock stock = MyAccount.InvestStock.get(i);

                    stockData += "                        {";
                    stockData += "\"name\" : \"" + stock.Name() + "\", ";
                    stockData += "\"price\" : " + stock.Price() + ", ";
                    stockData += "\"count\" : " + stock.Count() + ", ";
                    stockData += "\"recentPrice\" : " + stock.RecentPrice() + ", ";
                    stockData += "\"price\" : " + stock.AveragePrice() + " ";

                    if(i + 1 == MyAccount.InvestStock.size())
                        stockData += "}";
                    else
                        stockData += "},\n";
                }
                

                for (int i = 0; i < MyAccount.InvestCoin.size(); i++)
                {
                    Coin coin = MyAccount.InvestCoin.get(i);

                    coinData += "                        {";
                    coinData += "\"name\" : \"" + coin.Name() + "\", ";
                    coinData += "\"price\" : " + coin.Price() + ", ";
                    coinData += "\"count\" : " + coin.Count() + ", ";
                    coinData += "\"recentPrice\" : " + coin.RecentPrice() + ", ";
                    coinData += "\"price\" : " + coin.AveragePrice() + " ";

                    if(i + 1 == MyAccount.InvestCoin.size())
                        coinData += "}";
                    else
                        coinData += "},\n";
                }

                SaveData_List.add(index, new String
                (
                    "        " +
                    "{\n" +
                    "                \"cmd\" : \"GameSave\",\n" + 
                    "                \"time\" : \"" + format.format(date) + "\",\n" +
                    "                \"name\" : \"" + GameSaveName + "\",\n" +
                    "                \"GameDate\" : \"" + SystemConsole.thisDay + "\",\n" +
                    "                \"GameEndDate\" : \"" + SystemConsole.Years + "\",\n" +
                    "                \"GameDifference\" : \"" + SystemConsole.Difference + "\"," +


                    // 현금 관련
                    "\n                \"Money : \"" + MyAccount.Money() + "\",\n" +
                    "                \"MyLoan : \"" + MyAccount.Loan() + "\",\n" +

                    // 주식 관련 정보들
                    "\n                \"Stocks\" :" +
                    "\n                [" + 
                    "\n"                  +  stockData +
                    "\n                ]," +
                    
                    // 코인 관련 정보들
                    "\n                \"Coins\" :" +
                    "\n                [" + 
                    "\n"                  +  coinData +
                    "\n                ]," +

                    // 땅 관련 정보들
                    "\n                \"Estate\" :" +
                    "\n                [" + 
                    "\n"                  +  estateData +
                    "\n                ]" +
                    "\n        },"
                ));
                break;
            }
            else
                index++;
        }

        for (String string : SaveData_List) {
            System.out.println(string);
        }

        // try
        // {
        //     String temp = "";
        //     for (String string : SaveData_List)
        //         temp += string;

        //     JSONParser jsp = new JSONParser();
        //     JSONObject SaveData = (JSONObject) jsp.parse(temp);

        //     FileWriter FileWriter = new FileWriter(Save_File_Path);
        //     FileWriter.write(SaveData.toJSONString());
        //     FileWriter.flush();
        //     FileWriter.close();
        // }
        // catch (Exception e)
        // {
        //     e.getStackTrace();
        // }
    }
}
