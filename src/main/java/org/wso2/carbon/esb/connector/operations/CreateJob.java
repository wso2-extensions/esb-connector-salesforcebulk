package org.wso2.carbon.esb.connector.operations;

import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.esb.connector.pojo.SalesforceConfig;
import org.wso2.carbon.esb.connector.store.SalesforceConfigStore;
import org.wso2.carbon.esb.connector.utils.SalesforceUtils;

public class CreateJob extends AbstractConnector {

        @Override
        public void connect(MessageContext messageContext) throws ConnectException {

            try {
                log.info("Createjob operation now started.");
                String sfOAuthConfigName = SalesforceUtils.getConnectionName(messageContext);
                SalesforceConfig oAuthConfig = SalesforceConfigStore.getSalesforceConfig(sfOAuthConfigName);
                log.info("create job : oauthConfig accessToken" + (oAuthConfig == null ? "Its null" : oAuthConfig.getAccessToken()));
            } catch (Exception e) {
                log.error("Error occured: " , e);
                throw new ConnectException(e);
            }
        }
}
