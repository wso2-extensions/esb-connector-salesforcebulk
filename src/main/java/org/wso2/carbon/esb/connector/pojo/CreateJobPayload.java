package org.wso2.carbon.esb.connector.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.wso2.carbon.esb.connector.exception.InvalidConfigurationException;
import org.wso2.carbon.esb.connector.exception.ResponseParsingException;
import org.wso2.carbon.esb.connector.utils.BulkJobOperationType;
import org.wso2.carbon.esb.connector.utils.ColumnDelimiter;
import org.wso2.carbon.esb.connector.utils.LineEnding;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.ParseException;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateJobPayload {
    private String assignmentRuleId;
    private ColumnDelimiter columnDelimiter;
    private String externalIdFieldName;
    private LineEnding lineEnding;
    private BulkJobOperationType operation;
    private String object;

    public CreateJobPayload(BulkJobOperationType operation, String object) throws InvalidConfigurationException {
        this(operation, object, null);
    }

    public CreateJobPayload(BulkJobOperationType operation, String object, String externalIdFieldName) throws InvalidConfigurationException {
        if (operation == null || object == null) {
            throw new InvalidConfigurationException("Operation and object cannot be null when creating a Bulk Job");
        }
        if (operation == BulkJobOperationType.UPSERT && externalIdFieldName == null) {
            throw new InvalidConfigurationException("External ID field name is required when creating a Bulk Job with upsert operation");
        }
        this.operation = operation;
        this.object = object;
        this.externalIdFieldName = externalIdFieldName;
    }

    public String getAssignmentRuleId() {
        return assignmentRuleId;
    }

    public ColumnDelimiter getColumnDelimiter() {
        return columnDelimiter;
    }

    public String getExternalIdFieldName() {
        return externalIdFieldName;
    }

    public LineEnding getLineEnding() {
        return lineEnding;
    }

    public String getOperation() {
        return operation.getOperationType();
    }

    public String getObject() {
        return object;
    }

    public void setAssignmentRuleId(String assignmentRuleId) {
        this.assignmentRuleId = assignmentRuleId;
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