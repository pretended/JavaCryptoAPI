import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;


public class Ticker {
    private String name;
    private String id;
    private String symbol;
    private String price_usd;
    private String volume_24h_usd;
    private JSONParser parser;
    private String rank;
    private String percent_change_1h;
    private String percent_change_24h;
    private String percent_change_7d;
    private String lastUpdated;
    private String circulatingSupply;
    private String priceInBTC;

    public Ticker(String n) throws IOException, ParseException {
            n = upperCaseFirstChar(n);
            String json = getURLSource("https://api.coinpaprika.com/v1/ticker/" + getIDbyName(n));
            parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(json);
            name = (String) jsonObject.get("name");
            rank = (String) jsonObject.get("rank");
            percent_change_1h = (String) jsonObject.get("percent_change_1h");
            percent_change_24h = (String) jsonObject.get("percent_change_24h");
            percent_change_7d = (String) jsonObject.get("percent_change_7d");
            lastUpdated = (String) jsonObject.get("last_updated");
            priceInBTC = (String) jsonObject.get("price_btc");
            circulatingSupply = (String) jsonObject.get("circulating_supply");
            id = (String) jsonObject.get("id");
            symbol = (String) jsonObject.get("symbol");
            price_usd = (String) jsonObject.get("price_usd");
            volume_24h_usd = (String) jsonObject.get("volume_24h_usd");
        }
        private String upperCaseFirstChar(String n) {
            n = n.toLowerCase();
            String s1 = n.substring(0, 1).toUpperCase();
            String s2 = s1 + n.substring(1);
            return s2;
        }
        private String getIDbyName(String n) throws FileNotFoundException {
        String s2 = upperCaseFirstChar(n);
        int lineNumber = getLineOfName(s2);
        //File f = new File("C:\\Users\\Robert\\IdeaProjects\\JavaCryptoAPI\\src\\main\\resources\\ids.txt");
        //Scanner reader = new Scanner(f);
        String realLine = "";
        try (Stream<String> all_lines = Files.lines(Paths.get("C:\\Users\\Robert\\IdeaProjects\\JavaCryptoAPI\\src\\main\\resources\\ids.txt"))) {
            realLine = all_lines.skip(lineNumber - 1 ).findFirst().get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return realLine;
    }
    private int getLineOfName(String name) throws FileNotFoundException {
        File file = new File("C:/Users/Robert/IdeaProjects/JavaCryptoAPI/src/main/resources/names.txt");
        Scanner reader = new Scanner(file);
        String line;
        int i = 0;
        name = upperCaseFirstChar(name);
        while ((line = reader.nextLine()) != null) {
            i++;
            if (line.equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }
    public String getPercent_change_1h() { return percent_change_1h; }

    public String getPercent_change_24h() { return percent_change_24h; }

    public String getPercent_change_7d() { return percent_change_7d; }

    public String getRank() { return rank; }

    public String getLastUpdated() { return lastUpdated; }

    public String getCirculatingSupply() { return circulatingSupply; }

    public String getPriceInBTC() { return priceInBTC; }

    public String getId() { return id; }

    public String getSymbol() { return symbol; }

    public String getPrice_usd() { return price_usd; }

    public String getVolume_24h_usd() { return volume_24h_usd; }

    public String getName() { return name; }

    private static String getURLSource(String url) throws IOException
    {
        URL urlObject = new URL(url);
        URLConnection urlConnection = urlObject.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        urlConnection.setRequestProperty("Accept","*/*");
        return toString(urlConnection.getInputStream());
    }
    private static String toString(InputStream inputStream) throws IOException
    {

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)))
        {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }

            return stringBuilder.toString();
        }
    }
}