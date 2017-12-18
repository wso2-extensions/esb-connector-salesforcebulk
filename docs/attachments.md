# Working with Binary Attachments in Salesforce Bulk

[[  Overview ]](#overview)  [[ Operation details ]](#operation-details)  [[  Sample configuration  ]](#sample-configuration)

### Overview 

The following operations allow you to work with binary attachments. Click an operation name to see details on how to use it.
For a sample proxy service that illustrates how to work with binary attachments, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [createJobToUploadBatchFile](#creating-a-job-for-batches-containing-attachment-records)    | Creates a job for batches containing attachment records. |
| [uploadBatchFile](#creating-a-batch-of-attachment-records)      | Creates a batch of attachment records. |

### Operation details

This section provides more details on each of the operations.

#### Creating a job for batches containing attachment records

The createJobToUploadBatchFile operation creates a job for batches containing attachment records.

**createJobToUploadBatchFile**
```xml
<salesforcebulk.createJobToUploadBatchFile>
</salesforcebulk.createJobToUploadBatchFile>
```
**Sample request**

Following is a sample request that can be handled by the createJobToUploadBatchFile operation.

```xml
http://localhost:8280/services/salesforcebulk_createJobToUploadBatchFile?apiUrl=https://ap2.salesforce.com&accessToken=00D280000011oQO!ARwAQFPbKzWInyf.4veB3NY0hiKNQTxaSiZnPh9AybHplDpix34y_UOdwiKcL3e1_IquaUuO3A54A4thmSplNUQei9ARsNFV&apiVersion=34.0&refreshToken=5Aep861TSESvWeug_wHqvFVePrOMjj7CUFncs.cGdlPln68mKYpAbAJ9l7A5FTFsmqFY8Jl0m6fkIMWkIKc4WKL&clientId=3MVG9ZL0ppGP5UrDGNWmP9oSpiNtudQv6b06Ru7K6UPW5xQhd6vakhfjA2HUGsLSpDOQmO8JGozttODpABcnY&clientSecret=5437293348319318299&intervalTime=1000000
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
* jobId: The ID of the job to attach batch file.

**Sample request**

Following is a sample request that can be handled by the uploadBatchFile operation.

```xml
http://localhost:8280/services/salesforcebulk_uploadBatchFile?apiUrl=https://ap2.salesforce.com&accessToken=00D280000011oQO!ARwAQFPbKzWInyf.4veB3NY0hiKNQTxaSiZnPh9AybHplDpix34y_UOdwiKcL3e1_IquaUuO3A54A4thmSplNUQei9ARsNFV&apiVersion=34.0&refreshToken=5Aep861TSESvWeug_wHqvFVePrOMjj7CUFncs.cGdlPln68mKYpAbAJ9l7A5FTFsmqFY8Jl0m6fkIMWkIKc4WKL&clientId=3MVG9ZL0ppGP5UrDGNWmP9oSpiNtudQv6b06Ru7K6UPW5xQhd6vakhfjA2HUGsLSpDOQmO8JGozttODpABcnY&clientSecret=5437293348319318299&intervalTime=1000000&jobId=75028000000MCv9AAG
```
**Related Salesforce Bulk documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/binary_create_batch.htm](https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/binary_create_batch.htm)

#### Sample configuration

Following is a sample proxy service that illustrates how to connect to Salesforce Bulk with the init operation, and then use the createTopicSubscription operation. The sample request for this proxy can be found in the createJobToUploadBatchFile sample request. You can use this sample as a template for using other operations in this category.

**Sample Proxy**
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