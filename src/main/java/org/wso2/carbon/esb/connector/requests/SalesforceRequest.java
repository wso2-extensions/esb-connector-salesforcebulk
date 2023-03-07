package org.wso2.carbon.esb.connector.requests;

import org.wso2.carbon.esb.connector.exception.InvalidConfigurationException;
import org.wso2.carbon.esb.connector.exception.ResponseParsingException;
import org.wso2.carbon.esb.connector.exception.SalesforceConnectionException;
import org.wso2.carbon.esb.connector.pojo.CreateQueryJobPayload;
import org.wso2.carbon.esb.connector.pojo.GetAllJobResponse;
import org.wso2.carbon.esb.connector.pojo.JobInfo;
import org.wso2.carbon.esb.connector.pojo.CreateJobPayload;
import org.wso2.carbon.esb.connector.pojo.QueryJobInfo;
import org.wso2.carbon.esb.connector.pojo.SalesforceConfig;
import org.wso2.carbon.esb.connector.utils.BulkJobOperationType;
import org.wso2.carbon.esb.connector.utils.BulkJobState;
import org.wso2.carbon.esb.connector.utils.BulkQueryJobState;
import org.wso2.carbon.esb.connector.utils.ColumnDelimiter;
import org.wso2.carbon.esb.connector.utils.FileUtils;
import org.wso2.carbon.esb.connector.utils.HttpMethod;
import org.wso2.carbon.esb.connector.utils.RequestConstants;
import org.wso2.carbon.esb.connector.utils.SalesforceUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.HashMap;

public class SalesforceRequest {

    private SalesforceConfig salesforceConfig;

    public SalesforceRequest(SalesforceConfig salesforceConfig) {
        this.salesforceConfig = salesforceConfig;
    }

    public JobInfo createJob(CreateJobPayload createJobPayload) throws ResponseParsingException, SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.POST);
        restRequest.setUrl(SalesforceUtils.getCreateJobUrl(salesforceConfig));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getActiveAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);

        String jobInfoJson = createJobPayload.toJson();
        InputStream inputStream = new ByteArrayInputStream(jobInfoJson.getBytes());
        restRequest.setBody(inputStream);

        RestRequest.RestResponse restResponse = restRequest.send();
        return JobInfo.fromJson(restResponse.getResponse());
    }

    public QueryJobInfo createQueryJob(CreateQueryJobPayload createQueryJobPayload) throws ResponseParsingException, SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.POST);
        restRequest.setUrl(SalesforceUtils.getCreateQueryJobUrl(salesforceConfig));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getActiveAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);

        String queryJobInfoJson = createQueryJobPayload.toJson();
        InputStream inputStream = new ByteArrayInputStream(queryJobInfoJson.getBytes());
        restRequest.setBody(inputStream);

        RestRequest.RestResponse restResponse = restRequest.send();
        return QueryJobInfo.fromJson(restResponse.getResponse());
    }

    public void uploadJobData(String jobId, String filePath) throws SalesforceConnectionException, InvalidConfigurationException {
        FileUtils.verifyFile(filePath);
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.PUT);
        restRequest.setUrl(SalesforceUtils.getUploadJobDataUrl(salesforceConfig, jobId));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getActiveAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.TEXT_CSV);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);

        File file = new File(filePath);
        InputStream inputStream = null;
        try {
            inputStream = Files.newInputStream(file.toPath());
        } catch (IOException e) {
            throw new SalesforceConnectionException("Error while reading file: " + filePath, e);
        }
        restRequest.setBody(inputStream);

        restRequest.send();
    }

    public void closeJob(String jobId) throws SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.PATCH);
        restRequest.setUrl(SalesforceUtils.getCloseJobUrl(salesforceConfig, jobId));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getActiveAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);

        String jobStateJson = "{\"state\":\"" + BulkJobState.JobComplete + "\"}";
        restRequest.setBody(new ByteArrayInputStream(jobStateJson.getBytes()));


        restRequest.send();
    }

    public void abortJob(String jobId) throws SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.PATCH);
        restRequest.setUrl(SalesforceUtils.getAbortJobUrl(salesforceConfig, jobId));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getActiveAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);

        String jobStateJson = "{\"state\":\"" + BulkJobState.Aborted + "\"}";
        restRequest.setBody(new ByteArrayInputStream(jobStateJson.getBytes()));

        restRequest.send();
    }

    public void abortQueryJob(String queryJobId) throws SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.PATCH);
        restRequest.setUrl(SalesforceUtils.getAbortQueryJobUrl(salesforceConfig, queryJobId));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getActiveAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);

        String jobStateJson = "{\"state\":\"" + BulkQueryJobState.Aborted + "\"}";
        restRequest.setBody(new ByteArrayInputStream(jobStateJson.getBytes()));

        restRequest.send();
    }

    public void deleteJob(String jobId) throws SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.DELETE);
        restRequest.setUrl(SalesforceUtils.getDeleteJobUrl(salesforceConfig, jobId));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getActiveAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);

        restRequest.send();
    }

    public void deleteQueryJob(String queryJobId) throws SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.DELETE);
        restRequest.setUrl(SalesforceUtils.getDeleteQueryJobUrl(salesforceConfig, queryJobId));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getActiveAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);

        restRequest.send();
    }

    public GetAllJobResponse getAllJobInfo() throws ResponseParsingException, SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.GET);
        restRequest.setUrl(SalesforceUtils.getGetAllJobInfoUrl(salesforceConfig));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getActiveAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);

        RestRequest.RestResponse restResponse = restRequest.send();
        return GetAllJobResponse.fromJson(restResponse.getResponse());
    }

    public JobInfo getJobInfo(String jobId) throws ResponseParsingException, SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.GET);
        restRequest.setUrl(SalesforceUtils.getGetJobInfoUrl(salesforceConfig, jobId));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getActiveAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);

        RestRequest.RestResponse restResponse = restRequest.send();
        return JobInfo.fromJson(restResponse.getResponse());
    }

    public QueryJobInfo getQueryJobInfo(String queryJobId) throws ResponseParsingException, SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.GET);
        restRequest.setUrl(SalesforceUtils.getGetQueryJobInfoUrl(salesforceConfig, queryJobId));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getActiveAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);

        RestRequest.RestResponse restResponse = restRequest.send();
        return QueryJobInfo.fromJson(restResponse.getResponse());
    }

    public void getQueryJobResult(String queryJobId, String filePath, Integer maxRecords, String locator)
            throws SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.GET);
        restRequest.setUrl(SalesforceUtils.getGetQueryJobResultUrl(salesforceConfig, queryJobId, maxRecords, locator));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getActiveAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);
        restRequest.sendGetAndReceiveToFile(filePath);
    }
}
