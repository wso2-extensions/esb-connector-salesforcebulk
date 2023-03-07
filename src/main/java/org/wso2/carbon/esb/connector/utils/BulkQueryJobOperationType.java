package org.wso2.carbon.esb.connector.utils;

public enum BulkQueryJobOperationType {
    QUERY("query"),
    QUERY_ALL("queryAll");

    String operationType;

    BulkQueryJobOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationType() {
        return operationType;
    }
}
