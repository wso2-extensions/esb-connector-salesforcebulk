package org.wso2.carbon.esb.connector.utils;

public class SalesforceConstants {
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";
    public static final String CLIENT_ID = "clientId";
    public static final String CLIENT_SECRET = "clientSecret";
    public static final String TOKEN_URL = "tokenUrl";
    public static final String SF_OAUTH_CONFIG_NAME = "name";
    public static final String INSTANCE_URL = "instanceUrl";
    public static final String SF_API_VERSION = "v57.0";
    public static final String SF_API_JOBS_INGEST_RELATIVE_PATH = "/services/data/" + SF_API_VERSION + "/jobs/ingest/";
    public static final String SF_API_JOBS_QUERY_RELATIVE_PATH = "/services/data/" + SF_API_VERSION + "/jobs/query/";
    public static final String SF_API_JOBS_BATCHES = "/batches/";
    public static final String SF_API_JOB_FAILED_RESULTS_RELATIVE_PATH = "/failedResults/";
    public static final String SF_API_JOB_SUCCESSFUL_RESULTS_RELATIVE_PATH = "/successfulResults/";
    public static final String SF_API_JOB_UNPROCESSED_RESULTS_RELATIVE_PATH = "/unprocessedrecords/";
    public static final String SF_API_JOBS_QUERY_RESULTS_RELATIVE_PATH = "/results";

    // Create constants for assignmentRuleId, columnDelimiter, contentType, externalIdFieldName, lineEnding, operation
    public static final String ASSIGNMENT_RULE_ID = "assignmentRuleId";
    public static final String COLUMN_DELIMITER = "columnDelimiter";
    public static final String CONTENT_TYPE = "contentType";
    public static final String EXTERNAL_ID_FIELD_NAME = "externalIdFieldName";
    public static final String LINE_ENDING = "lineEnding";
    public static final String OPERATION = "operation";
    public static final String OBJECT = "object";

    public static final String JOB_ID = "jobId";
    public static final String QUERY_JOB_ID = "queryJobId";
    public static final String FILE_PATH = "filePath";
    public static final String QUERY = "query";
    public static final String LOCATOR = "locator";
    public static final String MAX_RECORDS = "maxRecords";
    public static final String CLOSE_JOB_URL = "closeJobUrl";
    public static final String ABORT_JOB_URL = "abortJobUrl";

    public static final String ABORT_QUERY_JOB_URL = "abortQueryJobUrl";
}
