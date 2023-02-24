package org.wso2.carbon.esb.connector.requests;

import org.wso2.carbon.esb.connector.exception.InvalidConfigurationException;
import org.wso2.carbon.esb.connector.pojo.GetAllJobResponse;
import org.wso2.carbon.esb.connector.pojo.JobInfo;
import org.wso2.carbon.esb.connector.pojo.CreateJobPayload;
import org.wso2.carbon.esb.connector.pojo.SalesforceConfig;
import org.wso2.carbon.esb.connector.utils.BulkJobOperationType;
import org.wso2.carbon.esb.connector.utils.BulkJobState;
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

    public JobInfo createJob(CreateJobPayload createJobPayload) throws IOException {
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

    public void uploadJobData(String jobId, String filePath) throws IOException {
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
        InputStream inputStream = Files.newInputStream(file.toPath());
        restRequest.setBody(inputStream);

        restRequest.send();
    }

    public void closeJob(String jobId) throws IOException {
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

    public void abortJob(String jobId) throws IOException {
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

    public void deleteJob(String jobId) throws IOException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.DELETE);
        restRequest.setUrl(SalesforceUtils.getAbortJobUrl(salesforceConfig, jobId));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getActiveAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);

        restRequest.send();
    }

    public GetAllJobResponse getAllJobInfo() throws IOException {
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

    public JobInfo getJobInfo(String jobId) throws IOException {
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

//
    public static void main(String[] args) throws InvalidConfigurationException, InvalidConfigurationException {
        SalesforceConfig salesforceConfig = new SalesforceConfig();
        salesforceConfig.setAccessToken("00D8d000009qNWB!AQsAQLUs3.AihmKrEtPpYbpX4nVYgRKyp87TOtGit9Mv7THYT619rUMRETP_xbYFonRZ2ZFfNzkKRMeMZqN0wxawdMxUpzxz");
        salesforceConfig.setActiveAccessToken("00D8d000009qNWB!AQsAQLUs3.AihmKrEtPpYbpX4nVYgRKyp87TOtGit9Mv7THYT619rUMRETP_xbYFonRZ2ZFfNzkKRMeMZqN0wxawdMxUpzxz");
        salesforceConfig.setInstanceUrl("https://wso2-b-dev-ed.develop.my.salesforce.com");
        SalesforceRequest salesforceRequest = new SalesforceRequest(salesforceConfig);
//        CreateJobPayload createJobPayload = new CreateJobPayload(BulkJobOperationType.INSERT, "dd");
//        createJobPayload.setColumnDelimiter(ColumnDelimiter.COMMA);
        try {
//            GetAllJobResponse getAllJobResponse = salesforceRequest.getAllJobInfo();
//            System.out.println(getAllJobResponse);
            JobInfo jobInfo = salesforceRequest.getJobInfo("7508d00000HUvzNAAT");
            System.out.println(jobInfo);
//            salesforceRequest.uploadJobData("7508d00000HUwMgAAL", "/home/tharsanan/Documents/RRT/1946/BulkInsertExample/bulkinsert.csv");
//            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
