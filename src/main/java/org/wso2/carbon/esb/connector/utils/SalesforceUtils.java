/*
 * Copyright (c) 2023, WSO2 LLC. (http://www.wso2.com).
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
package org.wso2.carbon.esb.connector.utils;

import au.com.bytecode.opencsv.CSVReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.commons.json.JsonUtil;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.wso2.carbon.esb.connector.exception.InvalidConfigurationException;
import org.wso2.carbon.esb.connector.exception.ResponseParsingException;
import org.wso2.carbon.esb.connector.pojo.SalesforceConfig;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SalesforceUtils {

    private static final Log log = LogFactory.getLog(SalesforceUtils.class);
    private static final String NO_ENTITY_BODY = "NO_ENTITY_BODY";
    private static final String HTTP_SC = "HTTP_SC";

    /**
     * Retrieves connection name from message context if configured as configKey attribute
     * or from the template parameter
     *
     * @param messageContext Message Context from which the parameters should be extracted from
     * @return connection name
     */
    public static String getConnectionName(MessageContext messageContext) throws InvalidConfigurationException {

        String connectionName = (String) messageContext.getProperty(SalesforceConstants.SF_OAUTH_CONFIG_NAME);
        if (connectionName == null) {
            throw new InvalidConfigurationException("Connection name is not set.");
        }
        return connectionName;
    }

    public static String getCreateJobUrl(SalesforceConfig salesforceConfig) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_INGEST_RELATIVE_PATH;
    }

    public static String getCreateQueryJobUrl(SalesforceConfig salesforceConfig) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_QUERY_RELATIVE_PATH;
    }

    public static String getUploadJobDataUrl(SalesforceConfig salesforceConfig, String jobId) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_INGEST_RELATIVE_PATH
                + jobId + SalesforceConstants.SF_API_JOBS_BATCHES;
    }

    public static String getCloseJobUrl(SalesforceConfig salesforceConfig, String jobId) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_INGEST_RELATIVE_PATH + jobId +  "/";
    }

    public static String getAbortJobUrl(SalesforceConfig salesforceConfig, String jobId) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_INGEST_RELATIVE_PATH + jobId;
    }

    public static String getDeleteJobUrl(SalesforceConfig salesforceConfig, String jobId) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_INGEST_RELATIVE_PATH + jobId;
    }

    public static String getAbortQueryJobUrl(SalesforceConfig salesforceConfig, String jobId) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_QUERY_RELATIVE_PATH + jobId;
    }

    public static String getDeleteQueryJobUrl(SalesforceConfig salesforceConfig, String jobId) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_QUERY_RELATIVE_PATH + jobId;
    }

    public static String getGetAllJobInfoUrl(SalesforceConfig salesforceConfig, Boolean isPkChunkingEnabled,
                                             String jobType, String queryLocator) {
        String paramString = "";
        if (isPkChunkingEnabled != null) {
            paramString += SalesforceConstants.IS_PK_CHUNKING_ENABLED + "=" + isPkChunkingEnabled;
        }
        if (StringUtils.isNotEmpty(queryLocator)) {
            if (StringUtils.isNotEmpty(paramString)) {
                paramString += "&";
            }
            paramString += SalesforceConstants.QUERY_LOCATOR + "=" + queryLocator;
        }
        if (StringUtils.isNotEmpty(jobType) & !StringUtils.equalsIgnoreCase(jobType,"All")) {
            if (StringUtils.isNotEmpty(paramString)) {
                paramString += "&";
            }
            paramString += SalesforceConstants.JOB_TYPE + "=" + jobType;
        }
        if (StringUtils.isNotEmpty(paramString)) {
            paramString = "?" + paramString;
        }
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_INGEST_RELATIVE_PATH + paramString;
    }

    public static String getGetAllQueryJobInfoUrl(SalesforceConfig salesforceConfig, Boolean isPkChunkingEnabled,
                                                  String jobType, String queryLocator) {
        String paramString = "";
        if (isPkChunkingEnabled != null) {
            paramString += SalesforceConstants.IS_PK_CHUNKING_ENABLED + "=" + isPkChunkingEnabled;
        }
        if (StringUtils.isNotEmpty(queryLocator)) {
            if (StringUtils.isNotEmpty(paramString)) {
                paramString += "&";
            }
            paramString += SalesforceConstants.QUERY_LOCATOR + "=" + queryLocator;
        }
        if (StringUtils.isNotEmpty(jobType) & !StringUtils.equalsIgnoreCase(jobType,"All")) {
            if (StringUtils.isNotEmpty(paramString)) {
                paramString += "&";
            }
            paramString += SalesforceConstants.JOB_TYPE + "=" + jobType;
        }
        if (StringUtils.isNotEmpty(paramString)) {
            paramString = "?" + paramString;
        }
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_QUERY_RELATIVE_PATH + paramString;
    }

    public static String getGetJobInfoUrl(SalesforceConfig salesforceConfig, String jobId) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_INGEST_RELATIVE_PATH + jobId;
    }

    public static String getGetQueryJobInfoUrl(SalesforceConfig salesforceConfig, String jobId) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_QUERY_RELATIVE_PATH + jobId;
    }

    public static String getGetQueryJobResultUrl(SalesforceConfig salesforceConfig, String queryJobId,
                                                 Integer maxRecords, String locator) {
        StringBuilder paramString = new StringBuilder();
        // Append locator if it is not null and not empty
        if (StringUtils.isNotEmpty(locator)) {
            paramString.append(SalesforceConstants.LOCATOR).append("=").append(locator);
        }

        // Append maxRecords if it is not null
        if (maxRecords != null) {
            if (paramString.length() > 0) {
                paramString.append("&");
            }
            paramString.append(SalesforceConstants.MAX_RECORDS).append("=").append(maxRecords);
        }

        // Prepend ? if there are any parameters
        if (paramString.length() > 0) {
            paramString.insert(0, "?");
        }

        // Construct the final URL
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_QUERY_RELATIVE_PATH + queryJobId
                + SalesforceConstants.SF_API_JOBS_QUERY_RESULTS_RELATIVE_PATH
                + paramString;
    }

    public static String getGetJobFailedResultsUrl(SalesforceConfig salesforceConfig, String queryJobId) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_INGEST_RELATIVE_PATH + queryJobId
                + SalesforceConstants.SF_API_JOB_FAILED_RESULTS_RELATIVE_PATH;
    }

    public static String getGetJobSuccessfulResultsUrl(SalesforceConfig salesforceConfig, String queryJobId) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_INGEST_RELATIVE_PATH + queryJobId
                + SalesforceConstants.SF_API_JOB_SUCCESSFUL_RESULTS_RELATIVE_PATH;
    }

    public static String getGetJobUnprocessedResultsUrl(SalesforceConfig salesforceConfig, String queryJobId) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_INGEST_RELATIVE_PATH + queryJobId
                + SalesforceConstants.SF_API_JOB_UNPROCESSED_RESULTS_RELATIVE_PATH;
    }

    public static String getSFTokenUrl(SalesforceConfig salesforceConfig) {

        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_TOKEN_RELATIVE_PATH;
    }

    /**
     * Sets the error code and error detail in message
     *
     * @param messageContext Message Context
     * @param statusCode     Status code to be set
     * @param message        Error message to be set
     */
    public static void setErrorsInMessage(MessageContext messageContext, int statusCode, String message) {
        messageContext.setProperty(ResponseConstants.PROPERTY_ERROR_CODE, statusCode);
        messageContext.setProperty(ResponseConstants.PROPERTY_ERROR_MESSAGE, message);
    }

    public static BulkJobOperationType getBulkJobOperationTypeEnum(String enumString)
            throws InvalidConfigurationException {
        try {
            return BulkJobOperationType.valueOf(enumString);
        } catch (IllegalArgumentException e) {
            throw new InvalidConfigurationException("Invalid operation type provided: " + enumString);
        }
    }

    public static BulkQueryJobOperationType getBulkQueryJobOperationTypeEnum(String enumString)
            throws InvalidConfigurationException {
        try {
            return BulkQueryJobOperationType.valueOf(enumString);
        } catch (IllegalArgumentException e) {
            throw new InvalidConfigurationException("Invalid operation type provided: " + enumString);
        }
    }

    public static ColumnDelimiter getColumnDelimiterEnum(String enumString) throws InvalidConfigurationException {
        try {
            return ColumnDelimiter.valueOf(enumString);
        } catch (IllegalArgumentException e) {
            throw new InvalidConfigurationException("Invalid content type provided: " + enumString);
        }
    }

    public static LineEnding getLineEndingEnum(String enumString) throws InvalidConfigurationException {
        try {
            return LineEnding.valueOf(enumString);
        } catch (IllegalArgumentException e) {
            throw new InvalidConfigurationException("Invalid content type provided: " + enumString);
        }
    }

    public static String csvToJson(String csvString) throws IOException {

        String escapedString = StringEscapeUtils.unescapeXml(csvString);
        CSVReader csvReader =
                new CSVReader(new StringReader(escapedString), CSVReader.DEFAULT_SEPARATOR, CSVReader.DEFAULT_QUOTE_CHARACTER);
        List<String[]> lines = csvReader.readAll();
        String[] headerRow = lines.get(0);

        for (int i = 0; i < headerRow.length; i++) {
            headerRow[i] = removeQuotes(headerRow[i]);
        }
        Stream<String[]> csvArrayStream = lines.stream();
        csvArrayStream = csvArrayStream.skip(1);
        List<JsonObject> jsonObjectList = csvArrayStream
                .map(row -> {
                    JsonObject jsonObject = new JsonObject();

                    for (int i = 0; i < row.length; i++) {
                        JsonPrimitive value = getCellValue(row, i);
                        String key = headerRow[i];
                        jsonObject.add(key, value);
                    }

                    return jsonObject;
                })
                .collect(Collectors.toList());
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();
        String jsonString = gson.toJson(jsonObjectList);
        return jsonString;
    }

    public static JsonPrimitive getCellValue(String[] row, int index) {

        JsonPrimitive cellValue = null;
        String cellValueString = removeQuotes(row[index]);
        if (StringUtils.isNotBlank(cellValueString)) {
            cellValue = new JsonPrimitive(cellValueString);
        } else {
            cellValue = new JsonPrimitive("");
        }
        return cellValue;
    }

    private static String removeQuotes(String field) {

        field = field.replace("\"", "");
        return field;
    }

    public static void generateErrorOutput(MessageContext messageContext, Exception e) {
        org.apache.axis2.context.MessageContext axisCtx =
                ((Axis2MessageContext) messageContext).getAxis2MessageContext();
        axisCtx.setProperty(Constants.Configuration.MESSAGE_TYPE, SalesforceConstants.APPLICATION_JSON);
        axisCtx.setProperty(Constants.Configuration.CONTENT_TYPE, SalesforceConstants.APPLICATION_JSON);
        axisCtx.removeProperty(NO_ENTITY_BODY);
        String jsonString = "{\"error\":\"" + e.getMessage() + "\"}";
        try {
            JsonUtil.getNewJsonPayload(axisCtx, jsonString, true, true);
            if (e instanceof InvalidConfigurationException) {
                axisCtx.setProperty(HTTP_SC, ResponseConstants.HTTP_BAD_REQUEST);
            } else if (e instanceof ResponseParsingException) {
                axisCtx.setProperty(HTTP_SC, ResponseConstants.HTTP_INTERNAL_SERVER_ERROR);
            } else {
                axisCtx.setProperty(HTTP_SC, ResponseConstants.HTTP_INTERNAL_SERVER_ERROR);
            }
        } catch (AxisFault ex) {
            log.error("Error while generating error output", ex);
            axisCtx.setProperty(HTTP_SC, 500);
        }
    }
}
