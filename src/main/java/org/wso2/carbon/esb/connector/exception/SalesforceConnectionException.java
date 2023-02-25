package org.wso2.carbon.esb.connector.exception;

public class SalesforceConnectionException extends Exception {
    private int reponseCode;

    public SalesforceConnectionException(String message, Throwable cause, int responseCode) {
        super(message, cause);
        this.reponseCode = responseCode;
    }

    public SalesforceConnectionException(String message, int responseCode) {
        super(message);
        this.reponseCode = responseCode;
    }

    public SalesforceConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SalesforceConnectionException(String message) {
        super(message);
    }

    public int getReponseCode() {
        return reponseCode;
    }
}
