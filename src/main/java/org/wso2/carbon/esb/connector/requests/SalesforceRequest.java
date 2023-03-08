package org.wso2.carbon.esb.connector.requests;

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
import org.wso2.carbon.esb.connector.utils.BulkJobState;
import org.wso2.carbon.esb.connector.utils.BulkQueryJobState;
import org.wso2.carbon.esb.connector.utils.FileUtils;
import org.wso2.carbon.esb.connector.utils.HttpMethod;
import org.wso2.carbon.esb.connector.utils.RequestConstants;
import org.wso2.carbon.esb.connector.utils.SalesforceUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);

        String jobInfoJson = createJobPayload.toJson();
        restRequest.setBody(jobInfoJson);

        RestRequest.RestResponse restResponse = restRequest.send();
        return JobInfo.fromJson(restResponse.getResponse());
    }

    public QueryJobInfo createQueryJob(CreateQueryJobPayload createQueryJobPayload) throws ResponseParsingException, SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.POST);
        restRequest.setUrl(SalesforceUtils.getCreateQueryJobUrl(salesforceConfig));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);

        String queryJobInfoJson = createQueryJobPayload.toJson();
        System.out.println(queryJobInfoJson);
        restRequest.setBody(queryJobInfoJson);

        RestRequest.RestResponse restResponse = restRequest.send();
        return QueryJobInfo.fromJson(restResponse.getResponse());
    }

    public void uploadJobData(String jobId, String filePath) throws SalesforceConnectionException, InvalidConfigurationException {
        FileUtils.verifyFile(filePath);
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.PUT);
        restRequest.setUrl(SalesforceUtils.getUploadJobDataUrl(salesforceConfig, jobId));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.TEXT_CSV);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);
        restRequest.setGetBodyFromFile(true);
        restRequest.setInputFilePath(filePath);
        restRequest.send();
    }

    public void closeJob(String jobId) throws SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.PATCH);
        restRequest.setUrl(SalesforceUtils.getCloseJobUrl(salesforceConfig, jobId));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);
        String jobStateJson = "{\"state\":\"" + BulkJobState.UploadComplete + "\"}";
        restRequest.setBody(jobStateJson);
        restRequest.send();
    }

    public void abortJob(String jobId) throws SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.PATCH);
        restRequest.setUrl(SalesforceUtils.getAbortJobUrl(salesforceConfig, jobId));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);

        String jobStateJson = "{\"state\":\"" + BulkJobState.Aborted + "\"}";
        restRequest.setBody(jobStateJson);

        restRequest.send();
    }

    public void abortQueryJob(String queryJobId) throws SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.PATCH);
        restRequest.setUrl(SalesforceUtils.getAbortQueryJobUrl(salesforceConfig, queryJobId));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);
        String jobStateJson = "{\"state\":\"" + BulkQueryJobState.Aborted + "\"}";
        restRequest.setBody(jobStateJson);

        restRequest.send();
    }

    public void deleteJob(String jobId) throws SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.DELETE);
        restRequest.setUrl(SalesforceUtils.getDeleteJobUrl(salesforceConfig, jobId));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
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
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
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
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);

        RestRequest.RestResponse restResponse = restRequest.send();
        return GetAllJobResponse.fromJson(restResponse.getResponse());
    }

    public GetAllQueryJobResponse getAllQueryJobInfo() throws ResponseParsingException, SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.GET);
        restRequest.setUrl(SalesforceUtils.getGetAllQueryJobInfoUrl(salesforceConfig));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);

        RestRequest.RestResponse restResponse = restRequest.send();
        return GetAllQueryJobResponse.fromJson(restResponse.getResponse());
    }

    public JobInfo getJobInfo(String jobId) throws ResponseParsingException, SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.GET);
        restRequest.setUrl(SalesforceUtils.getGetJobInfoUrl(salesforceConfig, jobId));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
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
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);

        RestRequest.RestResponse restResponse = restRequest.send();
        return QueryJobInfo.fromJson(restResponse.getResponse());
    }

    public void getQueryJobResults(String queryJobId, String filePath, Integer maxRecords, String locator)
            throws SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.GET);
        restRequest.setUrl(SalesforceUtils.getGetQueryJobResultUrl(salesforceConfig, queryJobId, maxRecords, locator));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);
        restRequest.sendGetAndReceiveToFile(filePath);
    }

    public void getJobFailedResults(String jobId, String filePath) throws SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.GET);
        restRequest.setUrl(SalesforceUtils.getGetJobFailedResultsUrl(salesforceConfig, jobId));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);
        restRequest.sendGetAndReceiveToFile(filePath);
    }

    public void getJobSuccessfulResults(String jobId, String filePath) throws SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.GET);
        restRequest.setUrl(SalesforceUtils.getGetJobSuccessfulResultsUrl(salesforceConfig, jobId));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);
        restRequest.sendGetAndReceiveToFile(filePath);
    }

    public void getJobUnprocessedResults(String jobId, String filePath) throws SalesforceConnectionException {
        RestRequest restRequest = new RestRequest();
        restRequest.setMethod(HttpMethod.GET);
        restRequest.setUrl(SalesforceUtils.getGetJobUnprocessedResultsUrl(salesforceConfig, jobId));
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestConstants.HTTP_HEADER_AUTHORIZATION, RequestConstants.BEARER + salesforceConfig.getAccessToken());
        headers.put(RequestConstants.HTTP_HEADER_CONTENT_TYPE, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.HTTP_HEADER_ACCEPT, RequestConstants.APPLICATION_JSON);
        headers.put(RequestConstants.X_PRETTY_PRINT, "1");
        restRequest.setHeaders(headers);
        restRequest.sendGetAndReceiveToFile(filePath);
    }

    // TODO delete the main method
    public static void main(String[] args) throws SalesforceConnectionException, ResponseParsingException, InvalidConfigurationException {
        System.out.println(SalesforceUtils.getBulkQueryJobOperationTypeEnum("QUERY").getOperationType());
                SalesforceConfig salesforceConfig1 = new SalesforceConfig();
        salesforceConfig1.setInstanceUrl("https://wso2-b-dev-ed.develop.my.salesforce.com");
        salesforceConfig1.setAccessToken("00D8d000009qNWB!AQsAQArPwK4ph9WgkhSwUkSNRvK93XuwC5qr2LMGwXjKiJBHSzj1Fucczb4brbKnH4.Sb_M6U6Sd6F3heYPds15HEl4Yhy7j");
        SalesforceRequest salesforceRequest = new SalesforceRequest(salesforceConfig1);
        CreateQueryJobPayload createQueryJobPayload = new CreateQueryJobPayload("query", "SELECT Id, Name FROM Account");

//        QueryJobInfo queryJobInfo = salesforceRequest.createQueryJob(createQueryJobPayload);
        // sysout
//        System.out.println(queryJobInfo.toJson());
        GetAllQueryJobResponse getAllQueryJobResponse = salesforceRequest.getAllQueryJobInfo();

        for (JobInfo queryJobInfo1 : getAllQueryJobResponse.getRecords() ) {
            System.out.println(queryJobInfo1.toJson());
            salesforceRequest.getQueryJobResults(queryJobInfo1.getId(), "/home/tharsanan/Documents/RRT/1946/BulkInsertExample/queryresults.csv", null, null);
//            salesforceRequest.abortQueryJob(queryJobInfo1.getId());
            salesforceRequest.deleteQueryJob(queryJobInfo1.getId());

        }

//        salesforceRequest.uploadJobData("7508d00000IgHrVAAV", "/home/tharsanan/Documents/RRT/1946/BulkInsertExample/bulkinsert.csv");
//        GetAllJobResponse getAllJobResponse = salesforceRequest.getAllJobInfo();
//        System.out.println(getAllJobResponse.getXmlString());
//        for (JobInfo jobInfo:getAllJobResponse.getRecords() ) {
//            salesforceRequest.abortJob(jobInfo.getId());
//            salesforceRequest.deleteJob(jobInfo.getId());
//            System.out.println(jobInfo.toJson());
//        }
//        System.out.println(salesforceRequest.getJobInfo("7508d00000IgHrVAAV").toJson());
//        salesforceRequest.getJobSuccessfulResults("7508d00000IgHrVAAV", "/home/tharsanan/Documents/RRT/1946/BulkInsertExample/bulkinsert1.csv");
    }

}
