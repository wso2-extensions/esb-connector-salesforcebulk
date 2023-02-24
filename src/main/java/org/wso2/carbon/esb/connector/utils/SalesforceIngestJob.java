package org.wso2.carbon.esb.connector.utils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

public class SalesforceIngestJob {

    public static void main(String[] args) throws Exception {

        String url = "https://wso2-b-dev-ed.develop.my.salesforce.com/services/data/v57.0/jobs/ingest/";
        String token = "00D8d000009qNWB!AQsAQLy9HYDwems8x9YF9tOr6_SmjsiYnRBtanLN6gvZQBNRvfVUTc8gL0du7L68ZfhlhQKw_rvcKDm5qAm9Bs4wH7ezh.8M";
        String contentType = "application/json";
        String prettyPrint = "1";
        String filePath = "/home/tharsanan/Documents/RRT/1946/BulkInsertExample/newinsertjob.json";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Bearer " + token);
        con.setRequestProperty("Content-Type", contentType);
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("X-PrettyPrint", prettyPrint);

        // Send POST request
        con.setDoOutput(true);
        File file = new File(filePath);
        InputStream inputStream = Files.newInputStream(file.toPath());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(con.getOutputStream());
        byte[] buffer = new byte[8192]; // read in 8KB chunks
        int bytesRead;
        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
            dataOutputStream.write(buffer, 0, bytesRead);
        }
        dataOutputStream.flush();
        dataOutputStream.close();

        int responseCode = con.getResponseCode();
        System.out.println("Sending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // print result
        System.out.println(response.toString());

    }

}
