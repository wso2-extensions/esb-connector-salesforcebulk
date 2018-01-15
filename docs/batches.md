# Working with Batches in Salesforce Bulk

[[Overview]](#overview)  [[Operation details]](#operation-details)  [[Sample configuration]](#sample-configuration)

### Overview 

The following operations allow you to work with batches. Click an operation name to see details on how to use it.
For a sample proxy service that illustrates how to work with batches, see [Sample configuration](#sample-configuration).

| Operation        | Description |
| ------------- |-------------|
| [addBatch](#adding-a-new-batch-to-a-job)    | Adds a new batch to a job. |
| [getBatchStatus](#retrieving-the-status-of-a-batch)      | Retrieves the status of an individual batch.     |
| [getBatchResults](#retrieving-results-of-a-batch-that-has-completed-processing)    | Retrieves results of a batch that has completed processing. |
| [getBatchRequest](#retrieving-a-batch-request)      | Retrieves a batch request.     |
| [listBatches](#retrieving-details-of-all-batches-in-a-job)    | Retrieves details of all batches in a job. |

### Operation details

This section provides more details on each of the operations.

#### Adding a new batch to a job

The addBatch operation adds a new batch to a job based on the properties that you specify.

**addBatch**
```xml
<salesforcebulk.addBatch>
    <jobId>{$ctx:jobId}</jobId>
    <objects>{$ctx:objects}</objects>
    <contentType>{$ctx:contentType}</contentType>
</salesforcebulk.addBatch>
```

**Properties**
* jobId: The unique identifier of the job to which you want add a new batch.
* objects: A list of records to process.
* contentType: The content type of the batch data. The content type you specify should be compatible with the content type of the associated job. Possible values are application/xml and text/csv.

**Sample request**

Following is a sample request that can be handled by the addBatch operation, where the content type of the batch data is in application/xml format.

```xml
<addBatch>
   <apiUrl>https://ap2.salesforce.com</apiUrl>
   <apiVersion>34.0</apiVersion>
   <accessToken>5Aep861TSESvWeug_xOdumSVTdDsD7OrADzhKVu9YrPFLB1zce_I21lnWIBR7uaGvedTTXJ4uPswE676H2pQpCZ</accessToken>
   <refreshToken>5Aep861TSESvWeug_wHqvFVePrOMjj7CUFncs.cGdlPln68mKYpAbAJ9l7A5FTFsmqFY8Jl0m6fkIMWkIKc4WKL</refreshToken>
   <clientId>3MVG9ZL0ppGP5UrDGNWmP9oSpiNtudQv6b06Ru7K6UPW5xQhd6vakhfjA2HUGsLSpDOQmO8JGozttODpABcnY</clientId>
   <clientSecret>5437293348319318299</clientSecret>
   <intervalTime>1000000</intervalTime>
   <registryPath>connectors/SalesforceBulk</registryPath>
   <contentType>application/xml</contentType>
   <jobId>75028000000McSwAAK</jobId>
   <objects>
      <values>
         <sObject>
            <description>Created from Bulk API on Tue Apr 14 11:15:59 PDT 2009</description>
            <name>Account 711 (batch 0)</name>
         </sObject>
         <sObject>
            <description>Created from Bulk API on Tue Apr 14 11:15:59 PDT 2009</description>
            <name>Account 37811 (batch 5)</name>
         </sObject>
      </values>
   </objects>
</addBatch>
```
Following is a sample request that can be handled by the addBatch operation, where the content type of the batch data is in text/csv format.

```xml
<addBatch>
   <apiUrl>https://ap2.salesforce.com</apiUrl>
   <apiVersion>34.0</apiVersion>
   <accessToken>5Aep861TSESvWeug_xOdumSVTdDsD7OrADzhKVu9YrPFLB1zce_I21lnWIBR7uaGvedTTXJ4uPswE676H2pQpCZ</accessToken>
   <refreshToken>5Aep861TSESvWeug_wHqvFVePrOMjj7CUFncs.cGdlPln68mKYpAbAJ9l7A5FTFsmqFY8Jl0m6fkIMWkIKc4WKL</refreshToken>
   <clientId>3MVG9ZL0ppGP5UrDGNWmP9oSpiNtudQv6b06Ru7K6UPW5xQhd6vakhfjA2HUGsLSpDOQmO8JGozttODpABcnY</clientId>
   <clientSecret>5437293348319318299</clientSecret>
   <intervalTime>1000000</intervalTime>
   <registryPath>connectors/SalesforceBulk</registryPath>
   <contentType>text/csv</contentType>
   <jobId>75028000000McSwAAK</jobId>
   <objects>
      <values>Name,description
        Tom Dameon,Created from Bulk API
      </values>
   </objects>
</addBatch>
```

**Related Salesforce Bulk documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_batches_create.htm](https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_batches_create.htm)

#### Retrieving the status of a batch

The getBatchStatus operation retrieves the status of a batch based on the properties that you specify.

**getBatchStatus**
```xml
<salesforcebulk.getBatchStatus>
    <jobId>{$ctx:jobId}</jobId>
    <batchId>{$ctx:batchId}</batchId>   
</salesforcebulk.getBatchStatus>
```

**Properties**
* jobId: The unique identifier of the job to which the batch you specify belongs.
* batchId: The unique identifier of the batch for which you want to retrieve the status.


**Sample request**

Following is a sample request that can be handled by the getBatchStatus operation.

```xml
<getBatchStatus>
    <apiUrl>https://ap2.salesforce.com</apiUrl>
    <accessToken>5Aep861TSESvWeug_xOdumSVTdDsD7OrADzhKVu9YrPFLB1zce_I21lnWIBR7uaGvedTTXJ4uPswE676H2pQpCZ</accessToken>
    <apiVersion>34.0</apiVersion>
    <refreshToken>5Aep861TSESvWeug_wHqvFVePrOMjj7CUFncs.cGdlPln68mKYpAbAJ9l7A5FTFsmqFY8Jl0m6fkIMWkIKc4WKL</refreshToken>
    <clientId>3MVG9ZL0ppGP5UrDGNWmP9oSpiNtudQv6b06Ru7K6UPW5xQhd6vakhfjA2HUGsLSpDOQmO8JGozttODpABcnY</clientId>
    <clientSecret>5437293348319318299</clientSecret>
    <intervalTime>1000000</intervalTime>
    <registryPath>connectors/SalesforceBulk</registryPath>
    <jobId>75028000000M5X0</jobId>
    <batchId>75128000000OZzq</batchId>
</getBatchStatus>
```
**Related Salesforce Bulk documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_quickstart_check_status.htm ](https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_quickstart_check_status.htm)

#### Retrieving results of a batch that has completed processing

The getBatchResults operation retrieves results of a batch that has completed processing.

**getBatchResults**
```xml
<salesforcebulk.getBatchResults>
    <jobId>{$ctx:jobId}</jobId>
    <batchId>{$ctx:batchId}</batchId>
</salesforcebulk.getBatchResults>
```

**Properties**
* jobId: The unique identifier of the job to which the batch you specify belongs.
* batchId: The unique identifier of the batch for which you want to retrieve results.

**Sample request**

Following is a sample request that can be handled by the getBatchResults operation.

```xml
<getBatchResults>
   <apiUrl>https://ap2.salesforce.com</apiUrl>
   <apiVersion>34.0</apiVersion>
   <accessToken>5Aep861TSESvWeug_xOdumSVTdDsD7OrADzhKVu9YrPFLB1zce_I21lnWIBR7uaGvedTTXJ4uPswE676H2pQpCZ</accessToken>
   <refreshToken>5Aep861TSESvWeug_wHqvFVePrOMjj7CUFncs.cGdlPln68mKYpAbAJ9l7A5FTFsmqFY8Jl0m6fkIMWkIKc4WKL</refreshToken>
   <clientId>3MVG9ZL0ppGP5UrDGNWmP9oSpiNtudQv6b06Ru7K6UPW5xQhd6vakhfjA2HUGsLSpDOQmO8JGozttODpABcnY</clientId>
   <clientSecret>5437293348319318299</clientSecret>
   <intervalTime>1000000</intervalTime>
   <registryPath>connectors/SalesforceBulk</registryPath>
   <jobId>75028000000M5X0</jobId>
   <batchId>75128000000OZzq</batchId>
</getBatchResults>
```

**Related Salesforce Bulk documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_batches_get_results.htm](https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_batches_get_results.htm)

#### Retrieving a batch request

The getBatchRequest operation retrieves a batch request based on the properties that you specify.

**getBatchRequest**
```xml
<salesforcebulk.getBatchRequest>
    <jobId>{$ctx:jobId}</jobId>
    <batchId>{$ctx:batchId}</batchId>
</salesforcebulk.getBatchRequest>
```

**Properties**
* jobId: The unique identifier of the job to which the batch you specify belongs.
* batchId: The unique identifier of the batch for which you want to retrieve the batch request.

**Sample request**

Following is a sample request that can be handled by the getBatchRequest operation.

```xml
<getBatchRequest>
   <apiVersion>34.0</apiVersion>
   <accessToken>00D280000011oQO!ARwAQFPbKzWInyf.4veB3NY0hiKNQTxaSiZnPh9AybHplDpix34y_UOdwiKcL3e1_IquaUuO3A54A4thmSplNUQei9ARsNFV</accessToken>
   <apiUrl>https://ap2.salesforce.com</apiUrl>
   <refreshToken>5Aep861TSESvWeug_wHqvFVePrOMjj7CUFncs.cGdlPln68mKYpAbAJ9l7A5FTFsmqFY8Jl0m6fkIMWkIKc4WKL</refreshToken>
   <clientId>3MVG9ZL0ppGP5UrDGNWmP9oSpiNtudQv6b06Ru7K6UPW5xQhd6vakhfjA2HUGsLSpDOQmO8JGozttODpABcnY</clientId>
   <clientSecret>5437293348319318299</clientSecret>
   <intervalTime>1000000</intervalTime>
   <registryPath>connectors/SalesforceBulk</registryPath>
   <jobId>75028000000MCtIAAW</jobId>
   <batchId>75128000000OpZFAA0</batchId>
</getBatchRequest>
```
**Related Salesforce Bulk documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_batches_get_request.htm](https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_batches_get_request.htm)

#### Retrieving details of all batches in a job

The listBatches operation retrieves details of all batches in a job that you specify.

**listBatches**
```xml
<salesforcebulk.listBatches>
    <jobId>{$ctx:jobId}</jobId>
</salesforcebulk.listBatches>
```

**Properties**
* jobId: The unique identifier of the job for which you want to retrieve batch details.

**Sample request**

Following is a sample request that can be handled by the listBatches operation.

```xml
<listBatches>
   <apiVersion>34.0</apiVersion>
   <accessToken>00D280000011oQO!ARwAQFPbKzWInyf.4veB3NY0hiKNQTxaSiZnPh9AybHplDpix34y_UOdwiKcL3e1_IquaUuO3A54A4thmSplNUQei9ARsNFV</accessToken>
   <apiUrl>https://ap2.salesforce.com</apiUrl>
   <refreshToken>5Aep861TSESvWeug_wHqvFVePrOMjj7CUFncs.cGdlPln68mKYpAbAJ9l7A5FTFsmqFY8Jl0m6fkIMWkIKc4WKL</refreshToken>
   <clientId>3MVG9ZL0ppGP5UrDGNWmP9oSpiNtudQv6b06Ru7K6UPW5xQhd6vakhfjA2HUGsLSpDOQmO8JGozttODpABcnY</clientId>
   <clientSecret>5437293348319318299</clientSecret>
   <intervalTime>1000000</intervalTime>
   <registryPath>connectors/SalesforceBulk</registryPath>
   <jobId>75028000000MCqEAAW</jobId>
</listBatches>
```
**Related Salesforce Bulk documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_batches_get_info_all.htm](https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_batches_get_info_all.htm)


### Sample configuration

Following is a sample proxy service that illustrates how to connect to SalesforceBulk with the init operation, and then use the addBatch operation. The sample request for this proxy can be found in the addBatch sample request. You can use this sample as a template for using other operations in this category.

**Sample Proxy**
```xml
<?xml version="1.0" encoding="UTF-8"?>
    <proxy xmlns="http://ws.apache.org/ns/synapse" name="salesforcebulk_addBatch" transports="https,http" statistics="disable" trace="disable" startOnLoad="true">
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
      <property name="jobId" expression="//jobId/text()"/>
      <property name="objects" expression="//objects"/>
      <property name="contentType" expression="//contentType"/>
      <salesforcebulk.init>
         <apiVersion>{$ctx:apiVersion}</apiVersion>
         <accessToken>{$ctx:accessToken}</accessToken>
         <apiUrl>{$ctx:apiUrl}</apiUrl>
         <refreshToken>{$ctx:refreshToken}</refreshToken>
         <clientId>{$ctx:clientId}</clientId>
         <clientSecret>{$ctx:clientSecret}</clientSecret>
         <intervalTime>{$ctx:intervalTime}</intervalTime>
         <registryPath>{$ctx:registryPath}</registryPath>
      </salesforcebulk.init>
      <salesforcebulk.addBatch>
         <jobId>{$ctx:jobId}</jobId>
         <objects>{$ctx:objects}</objects>
         <contentType>{$ctx:contentType}</contentType>
      </salesforcebulk.addBatch>
       <respond/>
     </inSequence>
      <outSequence>
       <log/>
       <send/>
      </outSequence>
     </target>
   <description/>
  </proxy>
```
