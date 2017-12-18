# Working with Project Jobs in Salesforce Bulk

[[  Overview ]](#overview)  [[ Operation details ]](#operation-details)  [[  Sample configuration  ]](#sample-configuration)

### Overview 

The following operations allow you to work with jobs. Click an operation name to see details on how to use it.
For a sample proxy service that illustrates how to work with jobs, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [createJob](#creating-a-new-job)    | Creates a new job. |
| [updateJob](#closing-a-job-or-aborting-an-existing-job)      | Closes a job or aborts an existing job. |
| [getJob](#retrieving-messages-published-to-a-topic)      | Retrieves all details for an existing job. |

### Operation details

This section provides more details on each of the operations.

#### Creating a new job
The createJob operation creates a new job by sending a POST request.

**createJob**
```xml
<salesforcebulk.createJob>
    <operation>{$ctx:operation}</operation>
    <contentType>{$ctx:contentType}</contentType>
    <object>{$ctx:object}</object>
</salesforcebulk.createJob>
```

**Properties**
* operation: The processing operation for batches in the job.
* contentType: The content type for the job.
* object: The object type for the data being processed.

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
</createJob>
```

**Related Salesforce Bulk documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_quickstart_create_job.htm](https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_quickstart_create_job.htm)

#### Closing a job or aborting an existing job

The updateJob operation closes a job by sending a POST request or aborts an existing job by sending a POST request.

**updateJob**
```xml
<salesforcebulk.updateJob>
    <jobId>{$ctx:jobId}</jobId>
    <state>{$ctx:state}</state>
</salesforcebulk.updateJob>
```

**Properties**
* jobId: The ID of the job.
* state: The state of processing for the job.

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
**Related Salesforce Bulk documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_quickstart_close_job.htm](https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_quickstart_close_job.htm)

#### Retrieving all details for an existing job

The getJob operation retrieves all details for an existing job by sending a GET request.

**getJob**
```xml
<salesforcebulk.getJob>
    <jobId>{$ctx:jobId}</jobId>
</salesforcebulk.getJob>
```

**Properties**
* jobId: The ID of the job.

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
**Related Salesforce Bulk documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_jobs_get_details.htm](https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_jobs_get_details.htm)

#### Sample configuration

Following is a sample proxy service that illustrates how to connect to Salesforce Bulk with the init operation, and then use the createJob operation. The sample request for this proxy can be found in the createJob sample request. You can use this sample as a template for using other operations in this category.

**Sample Proxy**
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