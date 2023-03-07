package org.wso2.carbon.esb.connector.requests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.esb.connector.exception.SalesforceConnectionException;
import org.wso2.carbon.esb.connector.utils.HttpMethod;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class RestRequest {
    private Log log = LogFactory.getLog(this.getClass());
    private HttpMethod method;
    private String url;
    private InputStream bodyStream;
    private HashMap<String, String> headers;

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getBody() {
        return bodyStream;
    }

    public void setBody(InputStream body) {
        this.bodyStream = body;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public RestResponse send() throws SalesforceConnectionException {
        try {
            URL endpoint = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
            connection.setRequestMethod(this.method.toString());
            for (String headerName : this.headers.keySet()) {
                connection.setRequestProperty(headerName, this.headers.get(headerName));
            }
            if (this.getMethod() == HttpMethod.GET || this.getMethod() == HttpMethod.DELETE) {
                int responseCode = connection.getResponseCode();
                if (responseCode >= 200 && responseCode < 300) {
                    try (InputStream inputStream = connection.getInputStream()) {
                        BufferedReader inputReader = new BufferedReader(
                                new InputStreamReader(inputStream));
                        String inputLine;
                        StringBuilder responseBody = new StringBuilder();
                        while ((inputLine = inputReader.readLine()) != null) {
                            responseBody.append(inputLine);
                        }
                        inputReader.close();
                        String response = responseBody.toString();
                        return new RestResponse(responseCode, response);
                    }
                } else {
                    String errorMessage = connection.getResponseMessage();
                    String errorDetails;
                    try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = errorReader.readLine()) != null) {
                            sb.append(line);
                        }
                        errorDetails = sb.toString();
                    }
                    throw new SalesforceConnectionException(String.format("Server returned error status code: %s, with error message:" +
                            " %s. Error details: %s", responseCode, errorMessage, errorDetails), responseCode);
                }
            } else if (this.getMethod() == HttpMethod.POST || this.getMethod() == HttpMethod.PATCH
                    || this.getMethod() == HttpMethod.PUT) {
                if (this.bodyStream == null) {
                    throw new SalesforceConnectionException("Body is required for POST and PATCH requests");
                }
                connection.setDoOutput(true);
                try (BufferedInputStream bufferedInputStream = new BufferedInputStream(this.bodyStream);
                     DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream())) {
                    byte[] buffer = new byte[8192]; // read in 8KB chunks
                    int bytesRead;
                    while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                        dataOutputStream.write(buffer, 0, bytesRead);
                    }
                    dataOutputStream.flush();
                }
                int responseCode = connection.getResponseCode();
                if (responseCode >= 200 && responseCode < 300) {
                    try (InputStream inputStream = connection.getInputStream()) {
                        BufferedReader inputReader = new BufferedReader(
                                new InputStreamReader(inputStream));
                        String inputLine;
                        StringBuilder responseBody = new StringBuilder();
                        while ((inputLine = inputReader.readLine()) != null) {
                            responseBody.append(inputLine);
                        }
                        inputReader.close();
                        String response = responseBody.toString();
                        log.info("Returned reponse: " + response);
                        return new RestResponse(responseCode, response);
                    }
                } else {
                    String errorMessage = connection.getResponseMessage();
                    String errorDetails;
                    try (BufferedReader errorReader =
                                 new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = errorReader.readLine()) != null) {
                            sb.append(line);
                        }
                        errorDetails = sb.toString();
                    }
                    throw new SalesforceConnectionException(String.format("Server returned error status code: %s, with error message:" +
                            " %s. Error details: %s", responseCode, errorMessage, errorDetails), responseCode);
                }
            } else {
                throw new SalesforceConnectionException("Unsupported HTTP method");
            }
        } catch (IOException e) {
            throw new SalesforceConnectionException("Error while sending request", e);
        }
    }

    public void sendGetAndReceiveToFile(String filePath) throws SalesforceConnectionException {
        try {
            URL endpoint = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
            connection.setRequestMethod(this.method.toString());
            for (String headerName : this.headers.keySet()) {
                connection.setRequestProperty(headerName, this.headers.get(headerName));
            }
            int responseCode = connection.getResponseCode();
            if (responseCode >= 200 && responseCode < 300) {
                try (InputStream inputStream = connection.getInputStream()) {
                    try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead = -1;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                }
            } else {
                String errorMessage = connection.getResponseMessage();
                String errorDetails;
                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = errorReader.readLine()) != null) {
                        sb.append(line);
                    }
                    errorDetails = sb.toString();
                }
                throw new SalesforceConnectionException(String.format("Server returned error status code: %s, with error message:" +
                        " %s. Error details: %s", responseCode, errorMessage, errorDetails), responseCode);
            }
        } catch (IOException e) {
            throw new SalesforceConnectionException("Error while sending request", e);
        }
    }

    public static class RestResponse {
        private int statusCode;
        private String response;

        public RestResponse(int statusCode, String response) {
            this.statusCode = statusCode;
            this.response = response;
        }

        public int getStatusCode() {

            return statusCode;
        }

        public String getResponse() {

            return response;
        }
    }

}
