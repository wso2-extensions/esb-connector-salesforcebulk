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
package org.wso2.carbon.esb.connector.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.wso2.carbon.esb.connector.exception.ResponseParsingException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryJobInfo {
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int retries;

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

    public String toJson() throws ResponseParsingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new ResponseParsingException("Error while parsing the response to JSON", e);
        }
    }

    public static QueryJobInfo fromJson(String json) throws ResponseParsingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, QueryJobInfo.class);
        } catch (JsonProcessingException e) {
            throw new ResponseParsingException("Error while converting Jobinfo response string JobInfo object", e);
        }
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public String getXmlStringWithoutRoot() {
        StringBuilder sb = new StringBuilder();
        sb.append("<id>").append(id).append("</id>");
        sb.append("<createdById>").append(createdById).append("</createdById>");
        sb.append("<createdDate>").append(createdDate).append("</createdDate>");
        sb.append("<systemModstamp>").append(systemModstamp).append("</systemModstamp>");
        sb.append("<state>").append(state).append("</state>");
        sb.append("<contentUrl>").append(contentUrl).append("</contentUrl>");
        sb.append("<numberRecordsProcessed>").append(numberRecordsProcessed).append("</numberRecordsProcessed>");
        sb.append("<numberRecordsFailed>").append(numberRecordsFailed).append("</numberRecordsFailed>");
        sb.append("<totalProcessingTime>").append(totalProcessingTime).append("</totalProcessingTime>");
        sb.append("<apiActiveProcessingTime>").append(apiActiveProcessingTime).append("</apiActiveProcessingTime>");
        sb.append("<apexProcessingTime>").append(apexProcessingTime).append("</apexProcessingTime>");
        sb.append("<errorMessage>").append(errorMessage).append("</errorMessage>");
        sb.append("<operation>").append(operation).append("</operation>");
        sb.append("<object>").append(object).append("</object>");
        sb.append("<concurrencyMode>").append(concurrencyMode).append("</concurrencyMode>");
        sb.append("<contentType>").append(contentType).append("</contentType>");
        sb.append("<lineEnding>").append(lineEnding).append("</lineEnding>");
        sb.append("<columnDelimiter>").append(columnDelimiter).append("</columnDelimiter>");
        sb.append("<apiVersion>").append(apiVersion).append("</apiVersion>");
        sb.append("<jobType>").append(jobType).append("</jobType>");
        sb.append("<externalIdFieldName>").append(externalIdFieldName).append("</externalIdFieldName>");
        return sb.toString();
    }

    public String getXmlString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<result>");
        sb.append(getXmlStringWithoutRoot());
        sb.append("</result>");
        return sb.toString();
    }
}
