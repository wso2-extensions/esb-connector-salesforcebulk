# Working with Jobs in Salesforce Bulk

[[Overview]](#overview)  [[Operation details]](#operation-details)  [[Sample configuration]](#sample-configuration)

### Overview 

The following operations allow you to work with jobs. Click an operation name to see details on how to use it.
For a sample proxy service that illustrates how to work with jobs, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [createJob](#creating-a-new-job)    | Creates a new job. |
| [updateJob](#closing-a-job-or-aborting-an-existing-job)      | Closes a job or aborts an existing job. |
| [getJob](#retrieving-details-of-an-existing-job)      | Retrieves all details for an existing job. |

### Operation details

This section provides more details on each of the operations.

#### Creating a new job
The createJob operation creates a new job based on the properties that you specify.

**createJob**
```xml
<salesforcebulk.createJob>
    <operation>{$ctx:operation}</operation>
    <contentType>{$ctx:contentType}</contentType>
    <object>{$ctx:object}</object>
    <externalIdFieldName>{$ctx:externalIdFieldName}</externalIdFieldName>
</salesforcebulk.createJob>
```

**Properties**
* operation: The processing operation that the job should perform.
* contentType: The content type of the job.
* object: The object type of data that is to be processed by the job.
* externalIdFieldName: The id of the external object.

**Sample request**

Following is a sample request that can be handled by the createJob operation.

```xml
<createJob>
   <apiVersion>34.0</apiVersion>
   <accessToken>00D280000011oQO!ARwAQFPbKzWInyf.4veB3NY0hiKNQTxaSiZnPh9AybHplDpix34y_UOdwiKcL3e1_IquaUuO3A54A4thmSplNUQei9ARsNFV</accessToken>
   <apiUrl>https://ap2.salesforce.com</apiUrl>
   <refreshToken>5Aep861TSESvWeug_wHqvFVePrOMjj7CUFncs.cGdlPln68mKYpAbAJ9l7A5FTFsmqFY8Jl0m6fkIMWkIKc4WKL</refreshToken>
   <clientId>3MVG9ZL0ppGP5UrDGNWmP9oSpiNtudQv6b06Ru7K6UPW5xQhd6vakhfjA2HUGsLSpDOQmO8JGozttODpABcnY</clientId>
   <clientSecret>5437293348319318299</clientSecret>
   <intervalTime>1000000</intervalTime>
   <registryPath>connectors/SalesforceBulk</registryPath>
   <operation>insert</operation>
   <contentType>CSV</contentType>
   <object>Contact</object>
   <externalIdFieldName>Languages__c</externalIdFieldName>
</createJob>
```
**Sample response**

Given below is a sample response for the createJob operation.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<jobInfo
   xmlns="http://www.force.com/2009/06/asyncapi/dataload">
    <id>7500K00000EVfY2QAL</id>
    <operation>query</operation>
    <object>Account</object>
    <createdById>00528000000ToIrAAK</createdById>
    <createdDate>2018-04-24T07:46:34.000Z</createdDate>
    <systemModstamp>2018-04-24T07:46:34.000Z</systemModstamp>
    <state>Open</state>
    <concurrencyMode>Parallel</concurrencyMode>
    <contentType>XML</contentType>
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

[https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_quickstart_create_job.htm](https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_quickstart_create_job.htm)

#### Closing a job or aborting an existing job

The updateJob operation closes or aborts a job that you specify.

**updateJob**
```xml
<salesforcebulk.updateJob>
    <jobId>{$ctx:jobId}</jobId>
    <state>{$ctx:state}</state>
</salesforcebulk.updateJob>
```

**Properties**
* jobId: The ID of the job that you either want to close or abort.
* state: The state of processing of the job.

**Sample request**

Following is a sample request that can be handled by the updateJob operation.

```xml
<updateJob>
   <apiVersion>34.0</apiVersion>
   <accessToken>00D280000011oQO!ARwAQFPbKzWInyf.4veB3NY0hiKNQTxaSiZnPh9AybHplDpix34y_UOdwiKcL3e1_IquaUuO3A54A4thmSplNUQei9ARsNFV</accessToken>
   <apiUrl>https://ap2.salesforce.com</apiUrl>
   <refreshToken>5Aep861TSESvWeug_wHqvFVePrOMjj7CUFncs.cGdlPln68mKYpAbAJ9l7A5FTFsmqFY8Jl0m6fkIMWkIKc4WKL</refreshToken>
   <clientId>3MVG9ZL0ppGP5UrDGNWmP9oSpiNtudQv6b06Ru7K6UPW5xQhd6vakhfjA2HUGsLSpDOQmO8JGozttODpABcnY</clientId>
   <clientSecret>5437293348319318299</clientSecret>
   <intervalTime>1000000</intervalTime>
   <registryPath>connectors/SalesforceBulk</registryPath>
   <jobId>75028000000MCtIAAW</jobId>
   <state>Closed</state>
</updateJob>
```
**Sample response**

Given below is a sample response for the updateJob operation.

```xml
<jobInfo xmlns="http://www.force.com/2009/06/asyncapi/dataload">
 <id>7500K00000GV5p5QAD</id>
 <operation>insert</operation>
 <object>Contact</object>
 <createdById>00528000000ToIrAAK</createdById>
 <createdDate>2018-10-25T16:45:43.000Z</createdDate>
 <systemModstamp>2018-10-25T16:45:43.000Z</systemModstamp>
 <state>Closed</state>
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

[https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_quickstart_close_job.htm](https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_quickstart_close_job.htm)

#### Retrieving details of an existing job

The getJob operation retrieves all details of an existing job based on the job ID that you specify.

**getJob**
```xml
<salesforcebulk.getJob>
    <jobId>{$ctx:jobId}</jobId>
</salesforcebulk.getJob>
```

**Properties**
* jobId: The ID of the job to retrieve details.

**Sample request**

Following is a sample request that can be handled by the getJob operation.

```xml
<getJob>
   <apiVersion>34.0</apiVersion>
   <accessToken>00D280000011oQO!ARwAQFPbKzWInyf.4veB3NY0hiKNQTxaSiZnPh9AybHplDpix34y_UOdwiKcL3e1_IquaUuO3A54A4thmSplNUQei9ARsNFV</accessToken>
   <apiUrl>https://ap2.salesforce.com</apiUrl>
   <refreshToken>5Aep861TSESvWeug_wHqvFVePrOMjj7CUFncs.cGdlPln68mKYpAbAJ9l7A5FTFsmqFY8Jl0m6fkIMWkIKc4WKL</refreshToken>
   <clientId>3MVG9ZL0ppGP5UrDGNWmP9oSpiNtudQv6b06Ru7K6UPW5xQhd6vakhfjA2HUGsLSpDOQmO8JGozttODpABcnY</clientId>
   <clientSecret>5437293348319318299</clientSecret>
   <intervalTime>1000000</intervalTime>
   <registryPath>connectors/SalesforceBulk</registryPath>
   <jobId>75028000000MCqEAAW</jobId>
</getJob>
```
**Sample response**

Given below is a sample response for the getJob operation.

```xml
<jobInfo xmlns="http://www.force.com/2009/06/asyncapi/dataload">
 <id>7500K00000GV5p5QAD</id>
 <operation>insert</operation>
 <object>Contact</object>
 <createdById>00528000000ToIrAAK</createdById>
 <createdDate>2018-10-25T16:45:43.000Z</createdDate>
 <systemModstamp>2018-10-25T16:45:43.000Z</systemModstamp>
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

[https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_jobs_get_details.htm](https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_jobs_get_details.htm)

### Sample configuration

Following example illustrates how to connect to Salesforce Bulk with the init operation and createJob operation.

1. Create a sample proxy as below :

```xml
<?xml version="1.0" encoding="UTF-8"?>
    <proxy xmlns="http://ws.apache.org/ns/synapse" name="salesforcebulk_createJob" transports="https,http" statistics="disable" trace="disable" startOnLoad="true">
     <target>
      <inSequence onError="faultHandlerSeq">
      <property name="apiVersion" expression="//apiVersion/text()"/>
      <property name="accessToken" expression="//accessToken/text()"/>
      <property name="apiUrl" expression="//apiUrl/text()"/>
      <property name="refreshToken" expression="//refreshToken/text()"/>
      <property name="clientId" expression="//clientId/text()"/>
      <property name="clientSecret" expression="//clientSecret/text()"/>
      <property name="intervalTime" expression="//intervalTime/text()"/>
      <property name="registryPath" expression="//registryPath/text()"/>
      <property name="operation" expression="//operation/text()"/>
      <property name="contentType" expression="//contentType/text()"/>
      <property name="object" expression="//object/text()"/>   
      <property name="externalIdFieldName" expression="//externalIdFieldName"/>
      <salesforcebulk.init>
         <apiVersion>{$ctx:apiVersion}</apiVersion>
         <accessToken>{$ctx:accessToken}</accessToken>
         <apiUrl>{$ctx:apiUrl}</apiUrl>
         <refreshToken>{$ctx:refreshToken}</refreshToken>
         <clientId>{$ctx:clientId}</clientId>
         <clientSecret>{$ctx:clientSecret}</clientSecret>
         <intervalTime>{$ctx:intervalTime}</intervalTime>
         <registryPath>{$ctx:registryPath}</registryPath>
      </salesforcebulk.init> it>
      <salesforcebulk.createJob>
         <operation>{$ctx:operation}</operation>
         <contentType>{$ctx:contentType}</contentType>
         <object>{$ctx:object}</object>
         <externalIdFieldName>{$ctx:externalIdFieldName}</externalIdFieldName>
      </salesforcebulk.createJob>
       <respond/>
     </inSequence>
      <outSequence>
       <send/>
      </outSequence>
     </target>
   <description/>
  </proxy>
```

2. Create an XML file named createJob.xml and copy the configurations given below to it:

```xml
<createJob>
   <apiVersion>34</apiVersion>
   <accessToken>00D28000000erPd!ARsAQLkIRoO7KdP9dQeqEfZuEYmSBIw3.OqbHUZbFSDwDBEV9ginNZmNi4yJuf14bnLN2cVpkUqVyxpUl5gqZSqFV0v_90hf</accessToken>
   <apiUrl>https://ap2.salesforce.com</apiUrl>
   <refreshToken>5Aep861TSESvWeug_wh8Zdrl_XXXXXXXX.QEq4FGbXtI5ARrLxzibR</refreshToken>
   <clientId>3MVG9ZL0ppGP5UrBKXXXXXXKwHHYHfEh.gTMriEXwhf6DFvyXXXXXXXwQxmFraW3k0KgU</clientId>
   <clientSecret>914846950346786099</clientSecret>
   <intervalTime>1000000</intervalTime>
   <registryPath>connectors/SalesforceBulk</registryPath>
   <operation>insert</operation>
   <contentType>CSV</contentType>
   <object>Contact</object>
</createJob>                        
```
3. Replace the credentials with your values.

4. Execute the following curl command:

```bash
curl http://localhost:8280/services/salesforcebulk_createJob -H "Content-Type: text/xml" -d @createJob.xml
```
5. Salesforce returns an XML response similar to the one shown below:
 
```xml
<?xml version="1.0" encoding="UTF-8"?>
<jobInfo
   xmlns="http://www.force.com/2009/06/asyncapi/dataload">
    <id>7500K00000GV8lpQAD</id>
    <operation>insert</operation>
    <object>Contact</object>
    <createdById>00528000000ToIrAAK</createdById>
    <createdDate>2018-10-26T06:19:18.000Z</createdDate>
    <systemModstamp>2018-10-26T06:19:18.000Z</systemModstamp>
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
