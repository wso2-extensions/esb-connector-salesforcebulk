package org.wso2.carbon.esb.connector.operations;

import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.esb.connector.exception.InvalidConfigurationException;
import org.wso2.carbon.esb.connector.exception.SalesforceConnectionException;
import org.wso2.carbon.esb.connector.pojo.SalesforceConfig;
import org.wso2.carbon.esb.connector.requests.SalesforceRequest;
import org.wso2.carbon.esb.connector.store.SalesforceConfigStore;
import org.wso2.carbon.esb.connector.utils.SalesforceConstants;
import org.wso2.carbon.esb.connector.utils.SalesforceUtils;

public class GetQueryJobResult extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) {
        try {
            log.info("Get query job result operation now started.");
            String sfOAuthConfigName = SalesforceUtils.getConnectionName(messageContext);
            SalesforceConfig salesforceConfig = SalesforceConfigStore.getSalesforceConfig(sfOAuthConfigName);
            SalesforceRequest salesforceRequest = new SalesforceRequest(salesforceConfig);
            String queryJobId = (String) getParameter(messageContext, SalesforceConstants.QUERY_JOB_ID);
            String filePath = (String) getParameter(messageContext, SalesforceConstants.FILE_PATH);
            String maxRecords = (String) getParameter(messageContext, SalesforceConstants.MAX_RECORDS);
            Integer maxRecordsInt = null;
            if (maxRecords != null) {
                try {
                    maxRecordsInt = Integer.parseInt(maxRecords);
                } catch (NumberFormatException e) {
                    SalesforceUtils.setErrorsInMessage(messageContext, 1, "MaxRecords '" + maxRecords
                            + "' is not numeric");
                    handleException(e.getMessage(), e, messageContext);
                    return;
                }
            }
            String locator = (String) getParameter(messageContext, SalesforceConstants.LOCATOR);

            salesforceRequest.getQueryJobResults(queryJobId, filePath, maxRecordsInt, locator);
            SalesforceUtils.generateOutput(messageContext, SalesforceUtils.getSuccessXml());
        } catch (InvalidConfigurationException | SalesforceConnectionException e) {
            SalesforceUtils.setErrorsInMessage(messageContext, 1, e.getMessage());
            handleException(e.getMessage(), e, messageContext);
        }
    }
}
