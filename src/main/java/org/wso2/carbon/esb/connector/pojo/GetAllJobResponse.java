/*
 * Copyright (c) 2022, WSO2 LLC. (http://www.wso2.com).
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.wso2.carbon.esb.connector.exception.ResponseParsingException;

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

    public static GetAllJobResponse fromJson(String json) throws ResponseParsingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, GetAllJobResponse.class);
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
