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

public class AbortQueryJob extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) {
        try {
            log.info("AbortQueryJob operation now started.");
            String sfOAuthConfigName = SalesforceUtils.getConnectionName(messageContext);
            SalesforceConfig salesforceConfig = SalesforceConfigStore.getSalesforceConfig(sfOAuthConfigName);
            String queryJobId = (String) getParameter(messageContext, SalesforceConstants.QUERY_JOB_ID);
            String url = SalesforceUtils.getAbortQueryJobUrl(salesforceConfig, queryJobId);
            messageContext.setProperty(SalesforceConstants.ABORT_QUERY_JOB_URL, url);
        } catch (InvalidConfigurationException e) {
            SalesforceUtils.setErrorsInMessage(messageContext, 1, e.getMessage());
            handleException(e.getMessage(), e, messageContext);
        }
    }
}