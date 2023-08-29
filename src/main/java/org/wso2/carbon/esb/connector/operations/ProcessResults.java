/*
 * Copyright (c) 2023, WSO2 LLC. (http://www.wso2.com).
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

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.builder.Builder;
import org.apache.axis2.builder.BuilderUtil;
import org.apache.axis2.transport.TransportUtils;
import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.util.ConnectorUtils;
import org.wso2.carbon.esb.connector.utils.SalesforceConstants;
import org.wso2.carbon.esb.connector.utils.SalesforceUtils;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProcessResults extends AbstractConnector {

    private final String JSON = "JSON";
    private final String FILE = "FILE";

    @Override
    public void connect(MessageContext messageContext) {

        try {
            String output = messageContext.getEnvelope().getBody().getFirstElement().getText();
            String outputType = (String) ConnectorUtils.lookupTemplateParamater(messageContext, SalesforceConstants.OUTPUT_TYPE);
            String includeResultTo = (String) ConnectorUtils.lookupTemplateParamater(messageContext, SalesforceConstants.INCLUDE_RESULT_TO);
            String filePath = (String) ConnectorUtils.lookupTemplateParamater(messageContext, SalesforceConstants.FILE_PATH);
            if (outputType.equals(JSON)) {
                output = toJson(output);
            }
            if (includeResultTo.equals(FILE)) {
                storeInFile(filePath, output);
                String response = "{\"result\":\"success\"}";
                storeInPayload(messageContext, response, SalesforceConstants.APPLICATION_JSON);
            } else {
                String contentType = getContentType(outputType);
                storeInPayload(messageContext, output, contentType);
            }
        } catch (IOException e) {
            SalesforceUtils.setErrorsInMessage(messageContext, 1, e.getMessage());
            SalesforceUtils.generateErrorOutput(messageContext, e);
            log.error("Error occurred while processing the result", e);
        }
    }

    private String toJson(String csv) throws IOException {

        String jsonOutput = "{}";
        if (!csv.isEmpty()) {
            jsonOutput = SalesforceUtils.csvToJson(csv);
        }
        return jsonOutput;
    }

    private void storeInFile(String filePath, String content) throws IOException {

        InputStream inputStream = new ByteArrayInputStream(content.getBytes());
        FileOutputStream outputStream = new FileOutputStream(filePath);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
    }

    private void storeInPayload(MessageContext messageContext, String content, String contentType) throws AxisFault {

        org.apache.axis2.context.MessageContext axis2MsgCtx = ((org.apache.synapse.core.axis2.
                Axis2MessageContext) messageContext).getAxis2MessageContext();
        Builder builder = null;
        builder = BuilderUtil.getBuilderFromSelector(contentType, axis2MsgCtx);

        InputStream jsonStream = new ByteArrayInputStream(content.getBytes());
        OMElement documentElement = builder.processDocument(jsonStream, contentType, axis2MsgCtx);
        documentElement.toString();
        messageContext.setEnvelope(TransportUtils.createSOAPEnvelope(documentElement));
    }

    private String getContentType(String outputType) {

        if (outputType.equals(JSON)) {
            return SalesforceConstants.APPLICATION_JSON;
        } else {
            return SalesforceConstants.TEXT_CSV;
        }
    }
}
