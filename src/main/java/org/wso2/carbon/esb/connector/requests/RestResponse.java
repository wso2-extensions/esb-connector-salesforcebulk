package org.wso2.carbon.esb.connector.requests;

public class RestResponse {
    private int statusCode;
    private String response;
    private boolean isError;
    private String errorMessage;
    private String errorDetails;

    public RestResponse(int statusCode, String response) {
        this.statusCode = statusCode;
        this.response = response;
        this.isError = false;
    }

    public RestResponse(int statusCode, String errorMessage, String errorDetails) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
        this.isError = true;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponse() {

        return response;
    }

    public void setStatusCode(int statusCode) {

        this.statusCode = statusCode;
    }

    public void setResponse(String response) {

        this.response = response;
    }

    public boolean isError() {

        return isError;
    }

    public void setError(boolean error) {

        isError = error;
    }

    public String getErrorMessage() {

        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {

        this.errorMessage = errorMessage;
    }

    public String getErrorDetails() {

        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {

        this.errorDetails = errorDetails;
    }
}
