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
import org.wso2.carbon.esb.connector.exception.InvalidConfigurationException;
import org.wso2.carbon.esb.connector.exception.ResponseParsingException;
import org.wso2.carbon.esb.connector.exception.SalesforceConnectionException;
import org.wso2.carbon.esb.connector.pojo.GetAllJobResponse;
import org.wso2.carbon.esb.connector.pojo.SalesforceConfig;
import org.wso2.carbon.esb.connector.requests.SalesforceRequest;
import org.wso2.carbon.esb.connector.store.SalesforceConfigStore;
import org.wso2.carbon.esb.connector.utils.InputOutputType;
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
            String outputType = (String) getParameter(messageContext, SalesforceConstants.OUTPUT_TYPE);
            if (InputOutputType.FILE.toString().equals(outputType)) {
                if (filePath == null || filePath.isEmpty()) {
                    throw new InvalidConfigurationException("File path is not specified");
                }
                log.debug("Getting unprocessed results for job with id: " + jobId + ". File path: " + filePath);
                salesforceRequest.getJobUnprocessedResultsAndStoreInFile(jobId, filePath);
                SalesforceUtils.generateOutput(messageContext, SalesforceUtils.getSuccessXml());
            } else if (InputOutputType.BODY.toString().equals(outputType)){
                log.debug("Getting unprocessed results for job with id: " + jobId + ". File path: " + filePath);
                String response = salesforceRequest.getJobUnprocessedResults(jobId);
                SalesforceUtils.generateOutput(messageContext, SalesforceUtils.csvToXml(response));
            }


        } catch (Exception e) {
            SalesforceUtils.setErrorsInMessage(messageContext, 1, e.getMessage());
            handleException(e.getMessage(), e, messageContext);
        }
    }
}
