import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;

public class WeatherApp {

    // Your OpenWeatherMap API Key
    static final String API_KEY = "57beb96467b9a368a6e44bfca7f4d906";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter city name: ");
            String city = scanner.nextLine().trim();

            if (city.isEmpty()) {
                System.out.println("City name cannot be empty.");
                return;
            }

            // Construct API URL
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY + "&units=metric";

            // Send HTTP GET request
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) { // OK
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse JSON response
                JSONObject json = new JSONObject(response.toString());

                String cityName = json.getString("name");
                JSONObject main = json.getJSONObject("main");
                double temp = main.getDouble("temp");
                double humidity = main.getDouble("humidity");

                JSONArray weatherArray = json.getJSONArray("weather");
                String description = weatherArray.getJSONObject(0).getString("description");

                // Print weather info
                System.out.println("\nWeather in " + cityName + ":");
                System.out.println("Temperature: " + temp + " °C");
                System.out.println("Humidity: " + humidity + " %");
                System.out.println("Condition: " + description);

            
            } else {
                System.out.println("Error: Server returned response code " + responseCode);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}