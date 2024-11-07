package nl.fontys.s3;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDataSender {
    public static void main(String[] args) {
        String serverUrl = "http://192.168.78.210:8080/cameras";
        String macAddress = MacAddressFetcher.getMacAddress();

        if (macAddress == null) {
            System.out.println("Mac Address is null");
            return;
        }

        String jsonPayload = "{ \"macAddress\": \"" + macAddress + "\" }";

        try {
            URL url = new URL(serverUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Write JSON payload to request
            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");

                outputStream.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();

            System.out.println("Response Code: " + responseCode);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
