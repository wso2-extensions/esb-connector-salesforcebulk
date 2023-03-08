package org.wso2.carbon.esb.connector.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.wso2.carbon.esb.connector.exception.ResponseParsingException;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAllQueryJobResponse {
    private boolean done;
    private ArrayList<JobInfo> records;
    private String nextRecordsUrl;

    public boolean isDone() {

        return done;
    }

    public void setDone(boolean done) {

        this.done = done;
    }

    public ArrayList<JobInfo> getRecords() {

        return records;
    }

    public void setRecords(ArrayList<JobInfo> records) {

        this.records = records;
    }

    public String getNextRecordsUrl() {

        return nextRecordsUrl;
    }

    public void setNextRecordsUrl(String nextRecordsUrl) {

        this.nextRecordsUrl = nextRecordsUrl;
    }

    public static GetAllQueryJobResponse fromJson(String json) throws ResponseParsingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, GetAllQueryJobResponse.class);
        } catch (
            JsonProcessingException e) {
            throw new ResponseParsingException("Error while parsing the response to GetAllJobResponse object", e);
        }
    }

     public String getXmlStringWithoutRoot() {
        StringBuilder xmlString = new StringBuilder();
        xmlString.append("<done>").append(this.done).append("</done>");
        xmlString.append("<nextRecordsUrl>").append(this.nextRecordsUrl).append("</nextRecordsUrl>");
        for (JobInfo jobInfo : this.records) {
            xmlString.append("<jobInfo>");
            xmlString.append(jobInfo.getXmlString());
            xmlString.append("</jobInfo>");
        }
        return xmlString.toString();
     }

     public String getXmlString() {
        StringBuilder xmlString = new StringBuilder();
        xmlString.append("<result>");
        xmlString.append(this.getXmlStringWithoutRoot());
        xmlString.append("</result>");
        return xmlString.toString();
     }
}
