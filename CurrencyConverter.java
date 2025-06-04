import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class CurrencyConverter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Currency Selection
        System.out.print("Enter base currency (e.g., USD): ");
        String baseCurrency = sc.next().toUpperCase();

        System.out.print("Enter target currency (e.g., INR): ");
        String targetCurrency = sc.next().toUpperCase();

        // 2. Amount Input
        System.out.print("Enter amount to convert: ");
        double amount = sc.nextDouble();

        try {
            // 3. API Call to fetch exchange rate
            String apiURL = "https://api.exchangerate.host/convert?from=" + baseCurrency + "&to=" + targetCurrency + "&amount=" + amount;

            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 4. Read response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder apiResponse = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                apiResponse.append(inputLine);
            }
            in.close();

            // 5. Parse JSON and display result
            JSONObject json = new JSONObject(apiResponse.toString());
            double result = json.getDouble("result");
            String symbol = json.getJSONObject("info").getString("rate") + " per " + baseCurrency;

            System.out.printf("Converted Amount: %.2f %s\n", result, targetCurrency);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
