package org.wso2.carbon.esb.connector.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAllJobResponse {
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

    public static GetAllJobResponse fromJson(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, GetAllJobResponse.class);
    }

//    public static void main(String[] args) throws IOException {
//        String json = "{\"done\":true,\"records\":[{\"id\":\"001XXXXXXXXXXXXXXX\", \"numberRecordsFailed\":12312}],\"nextRecordsUrl\":\"/services/data/v50.0/jobs/ingest/750XXXXXXXXXXXXX/batches?fromResultId=751XXXXXXXXXXXXXX\"}";
//
//        GetAllJobResponse response = GetAllJobResponse.fromJson(json);
//
//        System.out.println(response.getRecords().get(0).getId());
//    }
}
