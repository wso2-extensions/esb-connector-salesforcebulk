package org.wso2.carbon.esb.connector.operations;

import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.esb.connector.exception.InvalidConfigurationException;
import org.wso2.carbon.esb.connector.exception.ResponseParsingException;
import org.wso2.carbon.esb.connector.exception.SalesforceConnectionException;
import org.wso2.carbon.esb.connector.pojo.CreateJobPayload;
import org.wso2.carbon.esb.connector.pojo.CreateQueryJobPayload;
import org.wso2.carbon.esb.connector.pojo.JobInfo;
import org.wso2.carbon.esb.connector.pojo.QueryJobInfo;
import org.wso2.carbon.esb.connector.pojo.SalesforceConfig;
import org.wso2.carbon.esb.connector.requests.SalesforceRequest;
import org.wso2.carbon.esb.connector.store.SalesforceConfigStore;
import org.wso2.carbon.esb.connector.utils.SalesforceConstants;
import org.wso2.carbon.esb.connector.utils.SalesforceUtils;

public class CreateQueryJob extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) {
        try {
            log.info("Create query job operation now started.");
            String sfOAuthConfigName = SalesforceUtils.getConnectionName(messageContext);
            SalesforceConfig salesforceConfig = SalesforceConfigStore.getSalesforceConfig(sfOAuthConfigName);

            SalesforceRequest salesforceRequest = new SalesforceRequest(salesforceConfig);
            CreateQueryJobPayload createQueryJobPayload = getCreateQueryJobPayload(messageContext);

            QueryJobInfo jobInfo = salesforceRequest.createQueryJob(createQueryJobPayload);
            SalesforceUtils.generateOutput(messageContext, jobInfo.getXmlString());
        } catch (InvalidConfigurationException | ResponseParsingException | SalesforceConnectionException e) {
            SalesforceUtils.setErrorsInMessage(messageContext, 1, e.getMessage());
            handleException(e.getMessage(), e, messageContext);
        }
    }

    private CreateQueryJobPayload getCreateQueryJobPayload(MessageContext messageContext)
            throws InvalidConfigurationException {
        String operation = (String) getParameter(messageContext, SalesforceConstants.OPERATION);
        String query = (String) getParameter(messageContext, SalesforceConstants.QUERY);
        String lineEnding = (String) getParameter(messageContext, SalesforceConstants.LINE_ENDING);
        String columnDelimiter = (String) getParameter(messageContext, SalesforceConstants.COLUMN_DELIMITER);

        CreateQueryJobPayload createQueryJobPayload =
                new CreateQueryJobPayload(SalesforceUtils.getBulkQueryJobOperationTypeEnum(operation).getOperationType(), query);
        createQueryJobPayload.setLineEnding(SalesforceUtils.getLineEndingEnum(lineEnding));
        createQueryJobPayload.setColumnDelimiter(SalesforceUtils.getColumnDelimiterEnum(columnDelimiter));
        return createQueryJobPayload;
    }
}


