package org.wso2.carbon.esb.connector.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JobInfo {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String operation;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String object;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createdById;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createdDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String systemModstamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String state;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String concurrencyMode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String contentType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lineEnding;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String columnDelimiter;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double apiVersion;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String jobType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String externalIdFieldName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String contentUrl;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long apexProcessingTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long apiActiveProcessingTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMessage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long numberRecordsProcessed;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long numberRecordsFailed;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long totalProcessingTime;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private int retries;

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getOperation() {

        return operation;
    }

    public void setOperation(String operation) {

        this.operation = operation;
    }

    public String getObject() {

        return object;
    }

    public void setObject(String object) {

        this.object = object;
    }

    public String getCreatedById() {

        return createdById;
    }

    public void setCreatedById(String createdById) {

        this.createdById = createdById;
    }

    public String getCreatedDate() {

        return createdDate;
    }

    public void setCreatedDate(String createdDate) {

        this.createdDate = createdDate;
    }

    public String getSystemModstamp() {

        return systemModstamp;
    }

    public void setSystemModstamp(String systemModstamp) {

        this.systemModstamp = systemModstamp;
    }

    public String getState() {

        return state;
    }

    public void setState(String state) {

        this.state = state;
    }

    public String getConcurrencyMode() {

        return concurrencyMode;
    }

    public void setConcurrencyMode(String concurrencyMode) {

        this.concurrencyMode = concurrencyMode;
    }

    public String getContentType() {

        return contentType;
    }

    public void setContentType(String contentType) {

        this.contentType = contentType;
    }

    public String getLineEnding() {

        return lineEnding;
    }

    public void setLineEnding(String lineEnding) {

        this.lineEnding = lineEnding;
    }

    public String getColumnDelimiter() {

        return columnDelimiter;
    }

    public void setColumnDelimiter(String columnDelimiter) {

        this.columnDelimiter = columnDelimiter;
    }

    public Double getApiVersion() {

        return apiVersion;
    }

    public void setApiVersion(Double apiVersion) {

        this.apiVersion = apiVersion;
    }

    public String getJobType() {

        return jobType;
    }

    public void setJobType(String jobType) {

        this.jobType = jobType;
    }

    public String getExternalIdFieldName() {

        return externalIdFieldName;
    }

    public void setExternalIdFieldName(String externalIdFieldName) {

        this.externalIdFieldName = externalIdFieldName;
    }

    public String getContentUrl() {

        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {

        this.contentUrl = contentUrl;
    }

    public long getApexProcessingTime() {

        return apexProcessingTime;
    }

    public void setApexProcessingTime(long apexProcessingTime) {

        this.apexProcessingTime = apexProcessingTime;
    }

    public long getApiActiveProcessingTime() {

        return apiActiveProcessingTime;
    }

    public void setApiActiveProcessingTime(long apiActiveProcessingTime) {

        this.apiActiveProcessingTime = apiActiveProcessingTime;
    }

    public String getErrorMessage() {

        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {

        this.errorMessage = errorMessage;
    }

    public long getNumberRecordsProcessed() {

        return numberRecordsProcessed;
    }

    public void setNumberRecordsProcessed(long numberRecordsProcessed) {

        this.numberRecordsProcessed = numberRecordsProcessed;
    }

    public long getNumberRecordsFailed() {

        return numberRecordsFailed;
    }

    public void setNumberRecordsFailed(long numberRecordsFailed) {

        this.numberRecordsFailed = numberRecordsFailed;
    }

    public long getTotalProcessingTime() {

        return totalProcessingTime;
    }

    public void setTotalProcessingTime(long totalProcessingTime) {

        this.totalProcessingTime = totalProcessingTime;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    public static JobInfo fromJson(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, JobInfo.class);
    }

//    public int getRetries() {
//        return retries;
//    }
//
//    public void setRetries(int retries) {
//        this.retries = retries;
//    }
}
