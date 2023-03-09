package org.wso2.carbon.esb.connector.operations;

import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.esb.connector.exception.InvalidConfigurationException;
import org.wso2.carbon.esb.connector.exception.ResponseParsingException;
import org.wso2.carbon.esb.connector.exception.SalesforceConnectionException;
import org.wso2.carbon.esb.connector.pojo.GetAllJobResponse;
import org.wso2.carbon.esb.connector.pojo.SalesforceConfig;
import org.wso2.carbon.esb.connector.requests.SalesforceRequest;
import org.wso2.carbon.esb.connector.store.SalesforceConfigStore;
import org.wso2.carbon.esb.connector.utils.SalesforceConstants;
import org.wso2.carbon.esb.connector.utils.SalesforceUtils;

public class GetUnprocessedResults  extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) {
        try {
            String sfOAuthConfigName = SalesforceUtils.getConnectionName(messageContext);
            SalesforceConfig salesforceConfig = SalesforceConfigStore.getSalesforceConfig(sfOAuthConfigName);
            SalesforceRequest salesforceRequest = new SalesforceRequest(salesforceConfig);
            String jobId = (String) getParameter(messageContext, SalesforceConstants.JOB_ID);
            String filePath = (String) getParameter(messageContext, SalesforceConstants.FILE_PATH);
            log.debug("Getting unprocessed results for job with id: " + jobId + ". File path: " + filePath);
            salesforceRequest.getJobUnprocessedResults(jobId, filePath);
            SalesforceUtils.generateOutput(messageContext, SalesforceUtils.getSuccessXml());
        } catch (InvalidConfigurationException | SalesforceConnectionException e) {
            SalesforceUtils.setErrorsInMessage(messageContext, 1, e.getMessage());
            handleException(e.getMessage(), e, messageContext);
        }
    }
}
