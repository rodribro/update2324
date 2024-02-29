package ncc.up.pt.updateBackend.Common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;


public class RecaptchaValidator {
    private static String buildJson(String token, String expectedAction, String siteKey) {
        return "{\n" +
                "  \"event\": {\n" +
                "    \"token\": \"" + token + "\",\n" +
                "    \"expectedAction\": \"" + expectedAction + "\",\n" +
                "    \"siteKey\": \"" + siteKey + "\"\n" +
                "  }\n" +
                "}";
    }

    public static boolean validate(String token) {
        String apiKey = "AIzaSyCdofNoM8EH0jvUauPLwcr1kT9HiRp5D7U";
        try {
            URL url = new URL(
                    "https://recaptchaenterprise.googleapis.com/v1/projects/update2024-1709135802483/assessments?key="
                            + apiKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String input = buildJson(token, "register", "6LfZloMpAAAAAFnvPGjJmF0eOooECG6dNzxHRsOM");

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
                return false;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONObject tokenProperties = jsonResponse.optJSONObject("tokenProperties");
            if (tokenProperties != null) {
                return tokenProperties.optBoolean("valid", false);
            }

            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Default to false if an exception occurs
    }

}
