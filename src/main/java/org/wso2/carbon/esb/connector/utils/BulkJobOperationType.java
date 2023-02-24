package org.wso2.carbon.esb.connector.utils;

public enum BulkJobOperationType {
    INSERT("insert"),
    DELETE("delete"),
    HARD_DELETE("hardDelete"),
    UPDATE("update"),
    UPSERT("upsert");

    String operationType;

    BulkJobOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationType() {
        return operationType;
    }
}
