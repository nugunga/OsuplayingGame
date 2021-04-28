package System;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Economy.Economy;
import Economy.Currency.Coin;
import Economy.Currency.Estate;
import Economy.Currency.Stock;

public class IO {

    private String path = "Resource/Currency.json";
    public IO()
    {
        //파일 내용담을 리스트
        List<String> list = new ArrayList<String>();
        try
        {
            list = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
        String temp = "";
        
        for (String string : list)
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
