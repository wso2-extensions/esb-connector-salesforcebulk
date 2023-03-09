package org.wso2.carbon.esb.connector.operations;

import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.esb.connector.pojo.SalesforceConfig;
import org.wso2.carbon.esb.connector.requests.SalesforceRequest;
import org.wso2.carbon.esb.connector.store.SalesforceConfigStore;
import org.wso2.carbon.esb.connector.utils.SalesforceConstants;
import org.wso2.carbon.esb.connector.utils.SalesforceUtils;

public class RenewAccessToken extends AbstractConnector {

    @Override
    public void connect(MessageContext messageContext) {
        try {
            log.info("Renewing access token");
            String sfOAuthConfigName = SalesforceUtils.getConnectionName(messageContext);
            SalesforceConfig salesforceConfig = SalesforceConfigStore.getSalesforceConfig(sfOAuthConfigName);
            SalesforceRequest salesforceRequest = new SalesforceRequest(salesforceConfig);
            String accessToken = salesforceRequest.renewAccessToken();
            messageContext.setProperty(SalesforceConstants.ACCESS_TOKEN, accessToken);
        } catch (Exception e) {
            SalesforceUtils.setErrorsInMessage(messageContext, 1, e.getMessage());
            handleException(e.getMessage(), e, messageContext);
        }
    }
}
