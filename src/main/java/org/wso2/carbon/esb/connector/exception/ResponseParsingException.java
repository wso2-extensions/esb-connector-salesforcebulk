package org.wso2.carbon.esb.connector.exception;

public class ResponseParsingException extends Exception {

    public ResponseParsingException(String message, Throwable cause) {

        super(message, cause);
    }

    public ResponseParsingException(String message) {

        super(message);

    }

}
