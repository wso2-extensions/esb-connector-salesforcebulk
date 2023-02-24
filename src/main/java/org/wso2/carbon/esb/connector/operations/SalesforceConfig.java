/*
*  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.carbon.esb.connector.operations;

import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.connector.core.util.ConnectorUtils;
import org.wso2.carbon.esb.connector.store.SalesforceConfigStore;
import org.wso2.carbon.esb.connector.utils.SalesforceConstants;

/**
 * Sample method implementation.
 */
public class SalesforceConfig extends AbstractConnector {

    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        try {
            log.info("salesforce-bulkapi-v2 sample connector received message");
            org.wso2.carbon.esb.connector.pojo.SalesforceConfig oAuthConfig = getOAuthConfig(messageContext);
            SalesforceConfigStore.addSalesforceConfig(oAuthConfig);
            log.info("Successfully added salesforce config");
        } catch (Exception e) {
            log.error("Error occured: " , e);
	        throw new ConnectException(e);
        }
    }

    private org.wso2.carbon.esb.connector.pojo.SalesforceConfig getOAuthConfig(MessageContext messageContext) {
        String clientID = (String) ConnectorUtils.lookupTemplateParamater(messageContext,
                SalesforceConstants.CLIENT_ID);
        String clientSecret = (String) ConnectorUtils.lookupTemplateParamater(messageContext,
                SalesforceConstants.CLIENT_SECRET);
        String refreshToken = (String) ConnectorUtils.lookupTemplateParamater(messageContext,
                SalesforceConstants.REFRESH_TOKEN);
        String tokenUrl = (String) ConnectorUtils.lookupTemplateParamater(messageContext,
                SalesforceConstants.TOKEN_URL);
        String accessToken = (String) ConnectorUtils.lookupTemplateParamater(messageContext,
                SalesforceConstants.ACCESS_TOKEN);
        String salesforceConfigName = (String) ConnectorUtils.lookupTemplateParamater(messageContext,
                SalesforceConstants.SF_OAUTH_CONFIG_NAME);
        // log all of the above variables
        messageContext.getPropertyKeySet().forEach(key -> {
            log.info("key: " + key + " value: " + messageContext.getProperty((String)key));
        });
        log.info("clientID: " + clientID);
        log.info("clientSecret: " + clientSecret);
        log.info("refreshToken: " + refreshToken);
        log.info("tokenUrl: " + tokenUrl);
        log.info("accessToken: " + accessToken);
        log.info("sfOAuthConfigName: " + salesforceConfigName);

        org.wso2.carbon.esb.connector.pojo.SalesforceConfig oAuthConfig = new org.wso2.carbon.esb.connector.pojo.SalesforceConfig();
        oAuthConfig.setClientId(clientID);
        oAuthConfig.setClientSecret(clientSecret);
        oAuthConfig.setRefreshToken(refreshToken);
        oAuthConfig.setTokenUrl(tokenUrl);
        oAuthConfig.setAccessToken(accessToken);
        oAuthConfig.setSalesforceConfigName(salesforceConfigName);
        return oAuthConfig;
    }
}