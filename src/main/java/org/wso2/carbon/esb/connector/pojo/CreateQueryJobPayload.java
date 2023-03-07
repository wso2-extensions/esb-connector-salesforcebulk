package org.wso2.carbon.esb.connector.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.wso2.carbon.esb.connector.exception.InvalidConfigurationException;
import org.wso2.carbon.esb.connector.exception.ResponseParsingException;
import org.wso2.carbon.esb.connector.utils.BulkJobOperationType;
import org.wso2.carbon.esb.connector.utils.BulkQueryJobOperationType;
import org.wso2.carbon.esb.connector.utils.ColumnDelimiter;
import org.wso2.carbon.esb.connector.utils.LineEnding;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateQueryJobPayload {
    private String query;
    private ColumnDelimiter columnDelimiter;
    private LineEnding lineEnding;
    private BulkQueryJobOperationType operation;

    public CreateQueryJobPayload(BulkQueryJobOperationType operation, String query) throws InvalidConfigurationException {
        if (operation == null || query == null) {
            throw new InvalidConfigurationException("Operation and object cannot be null when creating a Bulk Job");
        }
        this.operation = operation;
        this.query = query;
    }

    public ColumnDelimiter getColumnDelimiter() {
        return columnDelimiter;
    }

    public LineEnding getLineEnding() {
        return lineEnding;
    }

    public String getOperation() {
        return operation.getOperationType();
    }

    public void setColumnDelimiter(ColumnDelimiter columnDelimiter) {
        this.columnDelimiter = columnDelimiter;
    }

    public void setLineEnding(LineEnding lineEnding) {
        this.lineEnding = lineEnding;
    }

    public String toJson() throws ResponseParsingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new ResponseParsingException("Error while parsing the response to JSON", e);
        }
    }
}
