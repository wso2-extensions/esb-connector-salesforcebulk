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

public class Test  extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) {

        String jobId = (String) getParameter(messageContext, SalesforceConstants.JOB_ID);
        String url = (String) getParameter(messageContext, "url");
        String method = (String) getParameter(messageContext, "method");
        String salesforceInstanceUrl = (String) messageContext.getProperty("salesforceInstanceUrl");
        String salesforceInstanceUrlTest = (String) messageContext.getProperty("salesforceInstanceUrlTest");
        String instanceUrlTest = (String) messageContext.getProperty("instanceUrlTest");
        String instanceUrl = (String) messageContext.getProperty("instanceUrl");
        messageContext.setProperty("accessTokenTest", "hello");
        // log all params
        log.info("jobId: " + jobId);
        log.info("url: " + url);
        log.info("method: " + method);
        log.info("salesforceInstanceUrl: " + salesforceInstanceUrl);
        log.info("instanceUrlTest: " + instanceUrlTest);
        log.info("instanceUrl: " + instanceUrl);
        log.info("salesforceInstanceUrlTest: " + salesforceInstanceUrlTest);

    }
}
