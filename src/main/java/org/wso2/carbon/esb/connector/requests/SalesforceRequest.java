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
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.carbon.esb.connector.exception.InvalidConfigurationException;
import org.wso2.carbon.esb.connector.exception.ResponseParsingException;
import org.wso2.carbon.esb.connector.exception.SalesforceConnectionException;
import org.wso2.carbon.esb.connector.pojo.CreateQueryJobPayload;
import org.wso2.carbon.esb.connector.pojo.GetAllJobResponse;
import org.wso2.carbon.esb.connector.pojo.GetAllQueryJobResponse;
import org.wso2.carbon.esb.connector.pojo.JobInfo;
import org.wso2.carbon.esb.connector.pojo.CreateJobPayload;
import org.wso2.carbon.esb.connector.pojo.QueryJobInfo;
import org.wso2.carbon.esb.connector.pojo.SalesforceConfig;
import org.wso2.carbon.esb.connector.utils.FileUtils;
import org.wso2.carbon.esb.connector.utils.HttpMethod;
import org.wso2.carbon.esb.connector.utils.RequestConstants;
import org.wso2.carbon.esb.connector.utils.ResponseConstants;
import org.wso2.carbon.esb.connector.utils.SalesforceUtils;

import java.util.HashMap;

public class SalesforceRequest {
    private Log log = LogFactory.getLog(this.getClass());
    private SalesforceConfig salesforceConfig;
    private static final String ACCESS_TOKEN = "access_token";

    public SalesforceRequest(SalesforceConfig salesforceConfig) {
        this.salesforceConfig = salesforceConfig;
    }

    public String createJob(CreateJobPayload createJobPayload)
            throws ResponseParsingException, SalesforceConnectionException, InvalidConfigurationException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION,
                RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        String jobInfoJson = createJobPayload.toJson();
        RestRequest restRequest =
                new RestRequest(HttpMethod.POST, SalesforceUtils.getCreateJobUrl(salesforceConfig), jobInfoJson,
                        headers);
        RestResponse restResponse = sendRequest(restRequest);
        return restResponse.getResponse();
    }

    public String createQueryJob(CreateQueryJobPayload createQueryJobPayload)
            throws ResponseParsingException, SalesforceConnectionException, InvalidConfigurationException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION,
                RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        String queryJobInfoJson = createQueryJobPayload.toJson();
        System.out.println(queryJobInfoJson);
        RestRequest restRequest =
                new RestRequest(HttpMethod.POST, SalesforceUtils.getCreateQueryJobUrl(salesforceConfig),
                        queryJobInfoJson, headers);

        RestResponse restResponse = sendRequest(restRequest);
        return restResponse.getResponse();
    }

    public void uploadJobDataFromFile(String jobId, String filePath)
            throws SalesforceConnectionException, InvalidConfigurationException, InvalidConfigurationException {
        FileUtils.verifyFile(filePath);
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER
                + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.TEXT_CSV);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.PUT, SalesforceUtils.getUploadJobDataUrl(salesforceConfig, jobId), null
                        , headers);
        restRequest.setGetBodyFromFile(true);
        restRequest.setInputFilePath(filePath);
        sendRequest(restRequest);
    }

    public void uploadJobData(String jobId, String inputDataString)
            throws SalesforceConnectionException, InvalidConfigurationException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER
                + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.TEXT_CSV);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.PUT, SalesforceUtils.getUploadJobDataUrl(salesforceConfig, jobId), inputDataString
                        , headers);
        sendRequest(restRequest);
    }

    public void deleteJob(String jobId) throws SalesforceConnectionException, InvalidConfigurationException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER
                + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.DELETE, SalesforceUtils.getDeleteJobUrl(salesforceConfig, jobId), null
                        , headers);

        sendRequest(restRequest);
    }

    public void deleteQueryJob(String queryJobId) throws SalesforceConnectionException, InvalidConfigurationException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER
                + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.DELETE, SalesforceUtils.getDeleteQueryJobUrl(salesforceConfig, queryJobId),
                        null, headers);

        sendRequest(restRequest);
    }

    public String getAllJobInfo(Boolean isPkChunkingEnabled, String jobType, String queryLocator) throws SalesforceConnectionException, InvalidConfigurationException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION
                , RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET,
                        SalesforceUtils.getGetAllJobInfoUrl(salesforceConfig, isPkChunkingEnabled, jobType,
                                queryLocator), null, headers);

        RestResponse restResponse = sendRequest(restRequest);
        return restResponse.getResponse();
    }

    public String getAllQueryJobInfo(Boolean isPkChunkingEnabled, String jobType, String queryLocator) throws SalesforceConnectionException, InvalidConfigurationException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER
                + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetAllQueryJobInfoUrl(salesforceConfig, isPkChunkingEnabled, jobType,
                        queryLocator), null, headers);

        RestResponse restResponse = sendRequest(restRequest);
        return restResponse.getResponse();
    }

    public String getJobInfo(String jobId) throws SalesforceConnectionException, InvalidConfigurationException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER
                + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetJobInfoUrl(salesforceConfig, jobId), null,
                        headers);

        RestResponse restResponse = sendRequest(restRequest);
        return restResponse.getResponse();
    }

    public String getQueryJobInfo(String queryJobId) throws SalesforceConnectionException, InvalidConfigurationException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetQueryJobInfoUrl(salesforceConfig, queryJobId),
                        null, headers);

        RestResponse restResponse = sendRequest(restRequest);
        return restResponse.getResponse();
    }

    public String getQueryJobResults(String queryJobId, Integer maxRecords, String locator)
            throws SalesforceConnectionException, InvalidConfigurationException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER
                + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetQueryJobResultUrl(salesforceConfig, queryJobId,
                        maxRecords, locator), null, headers);
        return sendRequest(restRequest).getResponse();
    }

    public void getQueryJobResultsAndStoreInFile(String queryJobId, String filePath, Integer maxRecords, String locator)
            throws SalesforceConnectionException, InvalidConfigurationException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER
                + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetQueryJobResultUrl(salesforceConfig, queryJobId,
                        maxRecords, locator), null, headers);

        restRequest.setOutputFilePath(filePath);
        restRequest.setReceiveToFile(true);
        sendRequest(restRequest);
    }

    public void getJobFailedResultsAndStoreInFile(String jobId, String filePath) throws SalesforceConnectionException, InvalidConfigurationException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER
                + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetJobFailedResultsUrl(salesforceConfig, jobId),
                        null, headers);

        restRequest.setOutputFilePath(filePath);
        restRequest.setReceiveToFile(true);
        sendRequest(restRequest);
    }

    public String getJobFailedResults(String jobId) throws SalesforceConnectionException, InvalidConfigurationException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER
                + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetJobFailedResultsUrl(salesforceConfig, jobId),
                        null, headers);
        return sendRequest(restRequest).getResponse();
    }

    public void getJobSuccessfulResultsAndStoreInFile(String jobId, String filePath) throws SalesforceConnectionException, InvalidConfigurationException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER
                + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetJobSuccessfulResultsUrl(salesforceConfig, jobId),
                        null, headers);
        restRequest.setOutputFilePath(filePath);
        restRequest.setReceiveToFile(true);
        sendRequest(restRequest);
    }

    public String getJobSuccessfulResults(String jobId) throws SalesforceConnectionException, InvalidConfigurationException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER
                + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetJobSuccessfulResultsUrl(salesforceConfig, jobId),
                        null, headers);
        return sendRequest(restRequest).getResponse();
    }

    public void getJobUnprocessedResultsAndStoreInFile(String jobId, String filePath) throws SalesforceConnectionException, InvalidConfigurationException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER
                + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetJobUnprocessedResultsUrl(salesforceConfig, jobId),
                        null, headers);

        restRequest.setOutputFilePath(filePath);
        restRequest.setReceiveToFile(true);
        sendRequest(restRequest);
    }

    public String getJobUnprocessedResults(String jobId) throws SalesforceConnectionException, InvalidConfigurationException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER
                + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        RestRequest restRequest =
                new RestRequest(HttpMethod.GET, SalesforceUtils.getGetJobUnprocessedResultsUrl(salesforceConfig, jobId),
                        null, headers);

        return sendRequest(restRequest).getResponse();
    }

    public String renewAccessToken() throws SalesforceConnectionException, InvalidConfigurationException {
        RestRequest restRequest =
                new RestRequest(HttpMethod.POST, SalesforceUtils.getSFTokenUrl(salesforceConfig), null,
                        null);

        RestResponse restResponse = restRequest.send();
        String responseStr = restResponse.getResponse();
        try {
            JSONObject jsonObject = new JSONObject(responseStr);
            String accessToken = jsonObject.getString(ACCESS_TOKEN);
            salesforceConfig.setAccessToken(accessToken);
            log.info("Access token renewed successfully.");
            return accessToken;
        } catch (JSONException e) {
            throw new SalesforceConnectionException("Error while parsing token response to json. " +
                    "Access token renewal process failed with exception: ", e, ResponseConstants.HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    private RestResponse sendRequest(RestRequest restRequest) throws SalesforceConnectionException, InvalidConfigurationException {
        RestResponse restResponse = restRequest.send();
        if (restResponse.isError()) {
            if (restResponse.getStatusCode() == 401) {
                log.info("Access token expired. Renewing access token");
                renewAccessToken();
                restRequest.getHeaders().put(RequestConstants.HTTP_HEADER_AUTHORIZATION,
                        RequestConstants.BEARER + salesforceConfig.getAccessToken());
                log.info("Retrying request with renewed access token.");
                return restRequest.send();
            } else {
                throw new SalesforceConnectionException("Salesforce server returned a non success response. Status code: " +
                        restResponse.getStatusCode() +
                        ". Error message : " + restResponse.getErrorMessage() +
                        ". Error detail : " + restResponse.getErrorDetails(), restResponse.getStatusCode());
            }
        }
        return restResponse;
    }
}
