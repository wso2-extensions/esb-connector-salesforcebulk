/*
 * Copyright (c) 2022, WSO2 LLC. (http://www.wso2.com).
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
package org.wso2.carbon.esb.connector.operations;

import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.connector.core.util.ConnectorUtils;
import org.wso2.carbon.esb.connector.exception.SalesforceConnectionException;
import org.wso2.carbon.esb.connector.pojo.SalesforceConfig;
import org.wso2.carbon.esb.connector.store.SalesforceConfigStore;
import org.wso2.carbon.esb.connector.utils.SalesforceConstants;
import org.wso2.carbon.esb.connector.utils.SalesforceUtils;

/**
 * Sample method implementation.
 */
public class InitSalesforce extends AbstractConnector {

    @Override
    public void connect(MessageContext messageContext) {
        try {
            SalesforceConfig oAuthConfig = getSalesforceConfig(messageContext);
            SalesforceConfigStore.addSalesforceConfig(oAuthConfig);
        } catch (Exception e) {
            SalesforceUtils.setErrorsInMessage(messageContext, 1, e.getMessage());
            handleException(e.getMessage(), e, messageContext);
        }
    }

    private SalesforceConfig getSalesforceConfig(MessageContext messageContext) {
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
        String instanceUrl = (String) ConnectorUtils.lookupTemplateParamater(messageContext,
                SalesforceConstants.INSTANCE_URL);

        SalesforceConfig salesforceConfig = new SalesforceConfig();
        salesforceConfig.setClientId(clientID);
        salesforceConfig.setClientSecret(clientSecret);
        salesforceConfig.setRefreshToken(refreshToken);
        salesforceConfig.setTokenUrl(tokenUrl);
        salesforceConfig.setAccessToken(accessToken);
        salesforceConfig.setSalesforceConfigName(salesforceConfigName);
        salesforceConfig.setInstanceUrl(instanceUrl);
        return salesforceConfig;
    }
}
