# Working with Binary Attachments in Salesforce Bulk

[[Overview]](#overview)  [[Operation details]](#operation-details)  [[Sample configuration]](#sample-configuration)

### Overview 

The following operations allow you to work with binary attachments. Click an operation name to see details on how to use it.
For a sample proxy service that illustrates how to work with binary attachments, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [createJobToUploadBatchFile](#creating-a-job-for-batches-containing-attachment-records)    | Creates a job for batches that contain attachment records. |
| [uploadBatchFile](#creating-a-batch-of-attachment-records)      | Creates a batch of attachment records. |

### Operation details

This section provides more details on each of the operations.

#### Creating a job for batches containing attachment records

The createJobToUploadBatchFile operation creates a job for batches that contain attachment records.

**createJobToUploadBatchFile**
```xml
<salesforcebulk.createJobToUploadBatchFile>
</salesforcebulk.createJobToUploadBatchFile>
```
**Sample request**

Following is a sample request that can be handled by the createJobToUploadBatchFile operation and attach the file as form data.

```xml
http://localhost:8280/services/salesforcebulk_createJobToUploadBatchFile?apiUrl=https://ap2.salesforce.com&accessToken=00D280000011oQO!ARwAQFPbKzWInyf.4veB3NY0hiKNQTxaSiZnPh9AybHplDpix34y_UOdwiKcL3e1_IquaUuO3A54A4thmSplNUQei9ARsNFV&apiVersion=34.0&refreshToken=5Aep861TSESvWeug_wHqvFVePrOMjj7CUFncs.cGdlPln68mKYpAbAJ9l7A5FTFsmqFY8Jl0m6fkIMWkIKc4WKL&clientId=3MVG9ZL0ppGP5UrDGNWmP9oSpiNtudQv6b06Ru7K6UPW5xQhd6vakhfjA2HUGsLSpDOQmO8JGozttODpABcnY&clientSecret=5437293348319318299&intervalTime=1000000
```
**Sample response**

Given below is a sample response for the createJobToUploadBatchFile operation.

```xml
<jobInfo xmlns="http://www.force.com/2009/06/asyncapi/dataload">
 <id>7500K00000GV5pAQAT</id>
 <operation>insert</operation>
 <object>Attachment</object>
 <createdById>00528000000ToIrAAK</createdById>
 <createdDate>2018-10-25T16:45:49.000Z</createdDate>
 <systemModstamp>2018-10-25T16:45:49.000Z</systemModstamp>
 <state>Open</state>
 <concurrencyMode>Parallel</concurrencyMode>
 <contentType>CSV</contentType>
 <numberBatchesQueued>0</numberBatchesQueued>
 <numberBatchesInProgress>0</numberBatchesInProgress>
 <numberBatchesCompleted>0</numberBatchesCompleted>
 <numberBatchesFailed>0</numberBatchesFailed>
 <numberBatchesTotal>0</numberBatchesTotal>
 <numberRecordsProcessed>0</numberRecordsProcessed>
 <numberRetries>0</numberRetries>
 <apiVersion>34.0</apiVersion>
 <numberRecordsFailed>0</numberRecordsFailed>
 <totalProcessingTime>0</totalProcessingTime>
 <apiActiveProcessingTime>0</apiActiveProcessingTime>
 <apexProcessingTime>0</apexProcessingTime>
</jobInfo>
```

**Related Salesforce Bulk documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/binary_create_job.htm](https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/binary_create_job.htm)

#### Creating a batch of attachment records

The uploadBatchFile operation creates a batch of attachment records.

**uploadBatchFile**
```xml
<salesforcebulk.uploadBatchFile>
   <jobId>{$url:jobId}</jobId>
</salesforcebulk.uploadBatchFile>
```

**Properties**
* jobId: The ID of the job for which you want to create a batch of attachment records.

**Sample request**

Following is a sample request that can be handled by the uploadBatchFile operation and attach the file as form data.

```xml
http://localhost:8280/services/salesforcebulk_uploadBatchFile?apiUrl=https://ap2.salesforce.com&accessToken=00D280000011oQO!ARwAQFPbKzWInyf.4veB3NY0hiKNQTxaSiZnPh9AybHplDpix34y_UOdwiKcL3e1_IquaUuO3A54A4thmSplNUQei9ARsNFV&apiVersion=34.0&refreshToken=5Aep861TSESvWeug_wHqvFVePrOMjj7CUFncs.cGdlPln68mKYpAbAJ9l7A5FTFsmqFY8Jl0m6fkIMWkIKc4WKL&clientId=3MVG9ZL0ppGP5UrDGNWmP9oSpiNtudQv6b06Ru7K6UPW5xQhd6vakhfjA2HUGsLSpDOQmO8JGozttODpABcnY&clientSecret=5437293348319318299&intervalTime=1000000&jobId=75028000000MCv9AAG
```
**Sample response**

Given below is a sample response for the uploadBatchFile operation.

```xml
<batchInfo xmlns="http://www.force.com/2009/06/asyncapi/dataload">
 <id>7510K00000OpbksQAB</id>
 <jobId>7500K00000GV5pFQAT</jobId>
 <state>Queued</state>
 <createdDate>2018-10-25T16:45:54.000Z</createdDate>
 <systemModstamp>2018-10-25T16:45:54.000Z</systemModstamp>
 <numberRecordsProcessed>0</numberRecordsProcessed>
 <numberRecordsFailed>0</numberRecordsFailed>
 <totalProcessingTime>0</totalProcessingTime>
 <apiActiveProcessingTime>0</apiActiveProcessingTime>
 <apexProcessingTime>0</apexProcessingTime>
</batchInfo>
```

**Related Salesforce Bulk documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/binary_create_batch.htm](https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/binary_create_batch.htm)

### Sample configuration

Following example illustrates how to connect to Salesforce Bulk with the init operation and createJobToUploadBatchFile operation.

1. Create a sample proxy as below :

```xml
<?xml version="1.0" encoding="UTF-8"?>
   <proxy xmlns="http://ws.apache.org/ns/synapse" name="salesforcebulk_createJobToUploadBatchFile" transports="https,http" statistics="disable" trace="disable" startOnLoad="true">
      <target>
         <inSequence onError="faultHandlerSeq">
            <salesforcebulk.init>
               <apiVersion>{$url:apiVersion}</apiVersion>
               <accessToken>{$url:accessToken}</accessToken>
               <apiUrl>{$url:apiUrl}</apiUrl>
               <refreshToken>{$url:refreshToken}</refreshToken>
               <clientId>{$url:clientId}</clientId>
               <clientSecret>{$url:clientSecret}</clientSecret>
               <intervalTime>{$url:intervalTime}</intervalTime>
            </salesforcebulk.init>
            <salesforcebulk.createJobToUploadBatchFile>
            </salesforcebulk.createJobToUploadBatchFile>
            <respond/>
         </inSequence>
         <outSequence>
            <send/>
         </outSequence>
         </target>
      <description/>
  </proxy>
```
2. Create a file named job.txt and copy the configurations given below to it:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<jobInfo xmlns="http://www.force.com/2009/06/asyncapi/dataload">
    <operation>insert</operation>
    <object>Attachment</object>
    <contentType>CSV</contentType>
</jobInfo>               
```
3. Replace the configurations with your values.

4. Execute the following curl command:

```bash
curl -X POST 'http://localhost:8280/services/salesforcebulk_createJobToUploadBatchFile?apiUrl=https://ap2.salesforce.com&accessToken=00D280000011oQO!ARwAQFPbKzWInyf.4veB3NY0hiKNQTxaSiZnPh9AybHplDpix34y_UOdwiKcL3e1_IquaUuO3A54A4thmSplNUQei9ARsNFV&apiVersion=34.0&refreshToken=5Aep861TSESvWeug_wHqvFVePrOMjj7CUFncs.cGdlPln68mKYpAbAJ9l7A5FTFsmqFY8Jl0m6fkIMWkIKc4WKL&clientId=3MVG9ZL0ppGP5UrDGNWmP9oSpiNtudQv6b06Ru7K6UPW5xQhd6vakhfjA2HUGsLSpDOQmO8JGozttODpABcnY&clientSecret=5437293348319318299&intervalTime=1000000'
-F file=@/path/to/a/job.txt
```
5. Salesforce returns an XML response similar to the one shown below:
 
```xml
<jobInfo xmlns="http://www.force.com/2009/06/asyncapi/dataload">
 <id>7500K00000GV5pAQAT</id>
 <operation>insert</operation>
 <object>Attachment</object>
 <createdById>00528000000ToIrAAK</createdById>
 <createdDate>2018-10-25T16:45:49.000Z</createdDate>
 <systemModstamp>2018-10-25T16:45:49.000Z</systemModstamp>
 <state>Open</state>
 <concurrencyMode>Parallel</concurrencyMode>
 <contentType>CSV</contentType>
 <numberBatchesQueued>0</numberBatchesQueued>
 <numberBatchesInProgress>0</numberBatchesInProgress>
 <numberBatchesCompleted>0</numberBatchesCompleted>
 <numberBatchesFailed>0</numberBatchesFailed>
 <numberBatchesTotal>0</numberBatchesTotal>
 <numberRecordsProcessed>0</numberRecordsProcessed>
 <numberRetries>0</numberRetries>
 <apiVersion>34.0</apiVersion>
 <numberRecordsFailed>0</numberRecordsFailed>
 <totalProcessingTime>0</totalProcessingTime>
 <apiActiveProcessingTime>0</apiActiveProcessingTime>
 <apexProcessingTime>0</apexProcessingTime>
</jobInfo>
```