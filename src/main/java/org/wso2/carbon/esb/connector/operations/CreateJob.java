package org.wso2.carbon.esb.connector.operations;

import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.esb.connector.exception.InvalidConfigurationException;
import org.wso2.carbon.esb.connector.exception.ResponseParsingException;
import org.wso2.carbon.esb.connector.exception.SalesforceConnectionException;
import org.wso2.carbon.esb.connector.pojo.CreateJobPayload;
import org.wso2.carbon.esb.connector.pojo.JobInfo;
import org.wso2.carbon.esb.connector.pojo.SalesforceConfig;
import org.wso2.carbon.esb.connector.requests.SalesforceRequest;
import org.wso2.carbon.esb.connector.store.SalesforceConfigStore;
import org.wso2.carbon.esb.connector.utils.SalesforceConstants;
import org.wso2.carbon.esb.connector.utils.SalesforceUtils;

public class CreateJob extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) {
        try {
            log.info("Createjob operation now started.");
            String sfOAuthConfigName = SalesforceUtils.getConnectionName(messageContext);
            SalesforceConfig salesforceConfig = SalesforceConfigStore.getSalesforceConfig(sfOAuthConfigName);

            SalesforceRequest salesforceRequest = new SalesforceRequest(salesforceConfig);
            CreateJobPayload createJobPayload = getCreateJobPayload(messageContext);

            JobInfo jobInfo = salesforceRequest.createJob(createJobPayload);
            log.info("Payload response: " + jobInfo.getXmlString());
            SalesforceUtils.generateOutput(messageContext, jobInfo.getXmlString());

            log.info("Payload env: " + messageContext.getEnvelope().getBody().toString());
            log.info("create job : oauthConfig accessToken" + (salesforceConfig == null ? "Its null" : salesforceConfig.getAccessToken()));
        } catch (InvalidConfigurationException | ResponseParsingException | SalesforceConnectionException e) {
            SalesforceUtils.setErrorsInMessage(messageContext, 1, e.getMessage());
            handleException(e.getMessage(), e, messageContext);
        }
    }

    private CreateJobPayload getCreateJobPayload(MessageContext messageContext) throws InvalidConfigurationException {
        String object = (String) getParameter(messageContext, SalesforceConstants.OBJECT);
        String operation = (String) getParameter(messageContext, SalesforceConstants.OPERATION);
        String externalIdFieldName = (String) getParameter(messageContext, SalesforceConstants.EXTERNAL_ID_FIELD_NAME);
        String lineEnding = (String) getParameter(messageContext, SalesforceConstants.LINE_ENDING);
        String columnDelimiter = (String) getParameter(messageContext, SalesforceConstants.COLUMN_DELIMITER);
        String assignmentRuleId = (String) getParameter(messageContext, SalesforceConstants.ASSIGNMENT_RULE_ID);

        CreateJobPayload createJobPayload =
                new CreateJobPayload(SalesforceUtils.getBulkJobOperationTypeEnum(operation),
                        object, externalIdFieldName);
        createJobPayload.setLineEnding(SalesforceUtils.getLineEndingEnum(lineEnding));
        createJobPayload.setColumnDelimiter(SalesforceUtils.getColumnDelimiterEnum(columnDelimiter));
        createJobPayload.setAssignmentRuleId(assignmentRuleId);
        return createJobPayload;
    }
}
