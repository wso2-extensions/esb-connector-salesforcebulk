package org.wso2.carbon.esb.connector.utils;

import org.apache.synapse.MessageContext;
import org.wso2.carbon.esb.connector.exception.InvalidConfigurationException;
import org.wso2.carbon.esb.connector.pojo.SalesforceConfig;

public class SalesforceUtils {
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

    public static String getUploadJobDataUrl(SalesforceConfig salesforceConfig, String jobId) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_INGEST_RELATIVE_PATH + jobId + SalesforceConstants.SF_API_JOBS_BATCHES;
    }

    public static String getCloseJobUrl(SalesforceConfig salesforceConfig, String jobId) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_INGEST_RELATIVE_PATH + jobId;
    }

    public static String getAbortJobUrl(SalesforceConfig salesforceConfig, String jobId) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_INGEST_RELATIVE_PATH + jobId;
    }

    public static String getGetAllJobInfoUrl(SalesforceConfig salesforceConfig) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_INGEST_RELATIVE_PATH;
    }

    public static String getGetJobInfoUrl(SalesforceConfig salesforceConfig, String jobId) {
        return salesforceConfig.getInstanceUrl() + SalesforceConstants.SF_API_JOBS_INGEST_RELATIVE_PATH + jobId;

    }
}
