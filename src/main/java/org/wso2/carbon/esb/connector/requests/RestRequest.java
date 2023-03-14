/*
 * Copyright (c) 2022, WSO2 LLC. (http://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.esb.connector.requests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.esb.connector.exception.InvalidConfigurationException;
import org.wso2.carbon.esb.connector.exception.SalesforceConnectionException;
import org.wso2.carbon.esb.connector.utils.HttpMethod;
import org.wso2.carbon.esb.connector.utils.ResponseConstants;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;

public class RestRequest {
    private Log log = LogFactory.getLog(this.getClass());
    private HttpMethod method;
    private String url;
    private String body;
    private String inputFilePath;
    private String outputFilePath;
    private boolean getBodyFromFile = false;
    private HashMap<String, String> headers;
    private boolean receiveToFile = false;

    public RestRequest(HttpMethod method, String url, String body, HashMap<String, String> headers) {
        this.method = method;
        this.url = url;
        this.body = body;
        this.headers = headers;
        this.inputFilePath = null;
        this.outputFilePath = null;
    }

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getInputFilePath() {

        return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath) {

        this.inputFilePath = inputFilePath;
    }

    public boolean getGetBodyFromFile() {

        return getBodyFromFile;
    }

    public void setGetBodyFromFile(boolean getBodyFromFile) {

        this.getBodyFromFile = getBodyFromFile;
    }

    public RestResponse send() throws SalesforceConnectionException, InvalidConfigurationException {
        try {
            log.info("Url: " + this.url + " Method: " + this.method.toString());
            URL endpoint = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
            connection.setRequestMethod(this.method.toString());
            if (headers != null) {
                for (String headerName : this.headers.keySet()) {
                    connection.setRequestProperty(headerName, this.headers.get(headerName));
                }
            }
            if (this.getMethod() == HttpMethod.GET || this.getMethod() == HttpMethod.DELETE) {
                int responseCode = connection.getResponseCode();
                if (responseCode >= 200 && responseCode < 300) {
                    try (InputStream inputStream = connection.getInputStream()) {
                        if (isReceiveToFile()) {
                            try (FileOutputStream outputStream = new FileOutputStream(this.outputFilePath)) {
                                byte[] buffer = new byte[4096];
                                int bytesRead = -1;
                                while ((bytesRead = inputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, bytesRead);
                                }
                                return new RestResponse(responseCode, null);
                            }
                        } else {
                            BufferedReader inputReader = new BufferedReader(
                                    new InputStreamReader(inputStream));
                            String inputLine;
                            StringBuilder responseBody = new StringBuilder();
                            while ((inputLine = inputReader.readLine()) != null) {
                                responseBody.append(inputLine);
                                responseBody.append(System.lineSeparator());
                            }
                            inputReader.close();
                            String response = responseBody.toString();
                            return new RestResponse(responseCode, response);
                        }
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
                        errorDetails += "\nInvoked url: " + this.url;
                        errorDetails += "\nInvoked method: " + this.method.toString();
                    }
                    return new RestResponse(responseCode, errorMessage, errorDetails);
                }
            } else if (this.getMethod() == HttpMethod.POST || this.getMethod() == HttpMethod.PUT) {
                if (this.getBodyFromFile && this.inputFilePath == null) {
                    throw new InvalidConfigurationException("Input file path is required when getBodyFromFile is true");
                }
                connection.setDoOutput(true);
                if (getBodyFromFile) {
                    File file = new File(this.inputFilePath);
                    InputStream inputStream;
                    try {
                        inputStream = Files.newInputStream(file.toPath());
                    } catch (IOException e) {
                        throw new InvalidConfigurationException("Error while reading file: " + this.inputFilePath, e);
                    }
                    try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                         DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream())) {
                        byte[] buffer = new byte[8192]; // read in 8KB chunks
                        int bytesRead;
                        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                            dataOutputStream.write(buffer, 0, bytesRead);
                        }
                        dataOutputStream.flush();
                    }
                } else {
                    if (this.body != null) {
                        try (DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream())) {
                            dataOutputStream.writeBytes(this.body);
                            dataOutputStream.flush();
                        }
                    }
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
                        log.debug("Returned reponse: " + response);
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
                        errorDetails += "\nInvoked url: " + this.url;
                        errorDetails += "\nInvoked method: " + this.method.toString();
                    }
                    return new RestResponse(responseCode, errorMessage, errorDetails);
                }
            } else {
                throw new InvalidConfigurationException("Unsupported HTTP method");
            }
        } catch (IOException e) {
            throw new SalesforceConnectionException("EI/MI Server encountered an exception while sending request", e,
                    ResponseConstants.HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public boolean isReceiveToFile() {
        return receiveToFile;
    }

    public void setReceiveToFile(boolean receiveToFile) {
        this.receiveToFile = receiveToFile;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }
}
