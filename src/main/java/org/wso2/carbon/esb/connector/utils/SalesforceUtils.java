package org.wso2.carbon.esb.connector.utils;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMException;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axiom.soap.SOAPBody;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.commons.json.JsonUtil;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.wso2.carbon.connector.core.exception.ContentBuilderException;
import org.wso2.carbon.esb.connector.exception.InvalidConfigurationException;
import org.wso2.carbon.esb.connector.pojo.SalesforceConfig;
import org.wso2.carbon.connector.core.util.PayloadUtils;

import javax.xml.stream.XMLStreamException;

public class SalesforceUtils {

    private static final Log log = LogFactory.getLog(SalesforceUtils.class);

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
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_INGEST_RELATIVE_PATH + jobId + SalesforceConstants.SF_API_JOBS_BATCHES;
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

    public static String getGetAllJobInfoUrl(SalesforceConfig salesforceConfig) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_INGEST_RELATIVE_PATH;
    }

    public static String getGetAllQueryJobInfoUrl(SalesforceConfig salesforceConfig) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_QUERY_RELATIVE_PATH;
    }

    public static String getGetJobInfoUrl(SalesforceConfig salesforceConfig, String jobId) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_INGEST_RELATIVE_PATH + jobId;
    }

    public static String getGetQueryJobInfoUrl(SalesforceConfig salesforceConfig, String jobId) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_QUERY_RELATIVE_PATH + jobId;
    }

    public static String getGetQueryJobResultUrl(SalesforceConfig salesforceConfig, String queryJobId,
                                                 Integer maxRecords, String locator) {
        String paramString = "";
        if (maxRecords != null) {
            paramString += SalesforceConstants.LOCATOR + "=" + locator;
        }
        if (StringUtils.isNotEmpty(locator)) {
            if (StringUtils.isNotEmpty(paramString)) {
                paramString += "&";
            }
            paramString += SalesforceConstants.MAX_RECORDS + "=" + maxRecords;
        }
        if (StringUtils.isNotEmpty(paramString)) {
            paramString = "?" + paramString;
        }
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_QUERY_RELATIVE_PATH + queryJobId + SalesforceConstants.SF_API_JOBS_QUERY_RESULTS_RELATIVE_PATH
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

    public static BulkJobOperationType getBulkJobOperationTypeEnum(String enumString) throws InvalidConfigurationException {
        try {
            return BulkJobOperationType.valueOf(enumString);
        } catch (IllegalArgumentException e) {
            throw new InvalidConfigurationException("Invalid operation type provided: " + enumString);
        }
    }

    public static BulkQueryJobOperationType getBulkQueryJobOperationTypeEnum(String enumString) throws InvalidConfigurationException {
        try {
            return BulkQueryJobOperationType.valueOf(enumString);
        } catch (IllegalArgumentException e) {
            throw new InvalidConfigurationException("Invalid operation type provided: " + enumString);
        }
    }

    public static BulkJobState getBulkJobStateTypeEnum(String enumString) throws InvalidConfigurationException {
        try {
            return BulkJobState.valueOf(enumString);
        } catch (IllegalArgumentException e) {
            throw new InvalidConfigurationException("Invalid job state provided: " + enumString);
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

    /**
     * Generates the output payload
     *
     * @param messageContext The message context that is processed
     * @param xmlString   Result of the status
     */
    public static void generateOutput(MessageContext messageContext, String xmlString) throws InvalidConfigurationException {

        try {
            OMElement omElement = AXIOMUtil.stringToOM(xmlString);
            //Detaching first element (soapBody.getFirstElement().detach()) will be done by following method anyway.
            JsonUtil.removeJsonPayload(((Axis2MessageContext) messageContext).getAxis2MessageContext());

            ((Axis2MessageContext) messageContext).getAxis2MessageContext().
                    removeProperty("NO_ENTITY_BODY");
            SOAPBody soapBody = messageContext.getEnvelope().getBody();
            soapBody.addChild(omElement);
        } catch (XMLStreamException e) {
            throw new InvalidConfigurationException(e.getMessage(), e);
        }
    }

    /**
     * Create an OMElement.
     *
     * @param elementName Name of the element
     * @param value       Value to be added
     * @return OMElement or null if error
     */
    public static OMElement createOMElement(String elementName, String value) {
        OMElement resultElement = null;
        try {
            if (StringUtils.isNotEmpty(value)) {
                resultElement = AXIOMUtil.
                        stringToOM("<" + elementName + ">" + value
                                + "</" + elementName + ">");
            } else {
                resultElement = AXIOMUtil.
                        stringToOM("<" + elementName
                                + "></" + elementName + ">");
            }
        } catch (XMLStreamException | OMException e) {
            log.error("FileConnector:unzip: Error while generating OMElement from element name" + elementName, e);
        }
        return resultElement;
    }

    public static String getSuccessXml() {
        return "<result>Success</result>";
    }
}
