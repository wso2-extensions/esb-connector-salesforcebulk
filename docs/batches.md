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
| [getBulkQueryResults](#retrieving-the-bulk-query-results)    | Retrieves the bulk query results. |

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
    <isQuery>{$ctx:isQuery}</isQuery>
</salesforcebulk.addBatch>
```

**Properties**
* jobId: The unique identifier of the job to which you want add a new batch.
* objects: A list of records to process.
* contentType: The content type of the batch data. The content type you specify should be compatible with the content type of the associated job. Possible values are application/xml and text/csv.
* isQuery: Set to true if the operation is query.

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
   <isQuery>false</isQuery>
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
   <isQuery>false</isQuery>
   <jobId>75028000000McSwAAK</jobId>
   <objects>
      <values>Name,description
        Tom Dameon,Created from Bulk API
      </values>
   </objects>
</addBatch>
```

Following is a sample request that can be handled by the addBatch operation, where the operation is query and the content type of the bulk query results is in application/xml format.

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
   <isQuery>true</isQuery>
   <jobId>75028000000McSwAAK</jobId>
   <objects>
      <values>SELECT Id, Name FROM Account LIMIT 100</values>
   </objects>
</addBatch>
```
**Sample response**

Given below is a sample response for the addBatch operation.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<batchInfo
   xmlns="http://www.force.com/2009/06/asyncapi/dataload">
    <id>7510K00000Kzb6XQAR</id>
    <jobId>7500K00000EVfY2QAL</jobId>
    <state>Queued</state>
    <createdDate>2018-04-24T07:50:45.000Z</createdDate>
    <systemModstamp>2018-04-24T07:50:45.000Z</systemModstamp>
    <numberRecordsProcessed>0</numberRecordsProcessed>
    <numberRecordsFailed>0</numberRecordsFailed>
    <totalProcessingTime>0</totalProcessingTime>
    <apiActiveProcessingTime>0</apiActiveProcessingTime>
    <apexProcessingTime>0</apexProcessingTime>
</batchInfo>
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
**Sample response**

Given below is a sample response for the getBatchStatus operation.

```xml
<batchInfo xmlns="http://www.force.com/2009/06/asyncapi/dataload">
 <id>7510K00000OpbkdQAB</id>
 <jobId>7500K00000GV5p0QAD</jobId>
 <state>Completed</state>
 <createdDate>2018-10-25T16:45:32.000Z</createdDate>
 <systemModstamp>2018-10-25T16:45:32.000Z</systemModstamp>
 <numberRecordsProcessed>2</numberRecordsProcessed>
 <numberRecordsFailed>2</numberRecordsFailed>
 <totalProcessingTime>91</totalProcessingTime>
 <apiActiveProcessingTime>3</apiActiveProcessingTime>
 <apexProcessingTime>0</apexProcessingTime>
</batchInfo>
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
**Sample response**

Given below is a sample response for the getBatchResults operation.

```xml
<result-list xmlns="http://www.force.com/2009/06/asyncapi/dataload">
    <result>7520K000006xcOc</result>
</result-list>
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
**Sample response**

Given below is a sample response for the getBatchRequest operation.

```xml
<sObjects xmlns="http://www.force.com/2009/06/asyncapi/dataload">
  <sObject>
    <description>Open-source</description>
    <name>Qlit</name>
  </sObject>
  <sObject>
    <description>Open-source</description>
    <name>Trient</name>
  </sObject>
</sObjects>
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
**Sample response**

Given below is a sample response for the listBatches operation.

```xml
<batchInfoList xmlns="http://www.force.com/2009/06/asyncapi/dataload">
 <batchInfo>
  <id>7510K00000OpbYhQAJ</id>
  <jobId>7500K00000GV5koQAD</jobId>
  <state>Completed</state>
  <createdDate>2018-10-25T16:30:39.000Z</createdDate>
  <systemModstamp>2018-10-25T16:30:39.000Z</systemModstamp>
  <numberRecordsProcessed>2</numberRecordsProcessed>
  <numberRecordsFailed>2</numberRecordsFailed>
  <totalProcessingTime>138</totalProcessingTime>
  <apiActiveProcessingTime>48</apiActiveProcessingTime>
  <apexProcessingTime>0</apexProcessingTime>
 </batchInfo>
</batchInfoList>
```
**Related Salesforce Bulk documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_batches_get_info_all.htm](https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_batches_get_info_all.htm)

### Retrieving the bulk query results

The getBulkQueryResults operation retrieves the bulk query results that you specify.

**getBulkQueryResults**
```xml
<salesforcebulk.getBulkQueryResults>
    <jobId>{$ctx:jobId}</jobId>
    <batchId>{$ctx:batchId}</batchId>
    <resultsId>{$ctx:resultsId}</resultsId>
</salesforcebulk.getBulkQueryResults>
```

**Properties**
* jobId: The unique identifier of the job for which you want to retrieve batch details.
* batchId: The unique identifier of the batch for which you want to retrieve the batch request.
* resultsId: The unique identifier of the results for which you want to retrieve.

**Sample request**

Following is a sample request that can be handled by the getBulkQueryResults operation.

```xml
<getBulkQueryResults>
   <apiVersion>34.0</apiVersion>
   <accessToken>00D280000011oQO!ARwAQFPbKzWInyf.4veB3NY0hiKNQTxaSiZnPh9AybHplDpix34y_UOdwiKcL3e1_IquaUuO3A54A4thmSplNUQei9ARsNFV</accessToken>
   <apiUrl>https://ap2.salesforce.com</apiUrl>
   <refreshToken>5Aep861TSESvWeug_wHqvFVePrOMjj7CUFncs.cGdlPln68mKYpAbAJ9l7A5FTFsmqFY8Jl0m6fkIMWkIKc4WKL</refreshToken>
   <clientId>3MVG9ZL0ppGP5UrDGNWmP9oSpiNtudQv6b06Ru7K6UPW5xQhd6vakhfjA2HUGsLSpDOQmO8JGozttODpABcnY</clientId>
   <clientSecret>5437293348319318299</clientSecret>
   <intervalTime>1000000</intervalTime>
   <registryPath>connectors/SalesforceBulk</registryPath>
   <jobId>75028000000MCqEAAW</jobId>
   <batchId>7510K00000Kzb6XQAR</batchId>
   <resultId>7520K000006xofz</resultId>
</getBulkQueryResults>
```
**Sample response**

Given below is a sample response for the getBulkQueryResults operation.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<queryResult xmlns="http://www.force.com/2009/06/asyncapi/dataload" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <records xsi:type="sObject">
        <type>Account</type>
        <Id>00128000005dMcSAAU</Id>
        <Id>00128000005dMcSAAU</Id>
        <Name>GenePoint</Name>
    </records>
    .
    .
</queryResult>
```

**Related Salesforce Bulk documentation**

[https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_code_curl_walkthrough.htm](https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_code_curl_walkthrough.htm)

### Sample configuration

Following example illustrates how to connect to Salesforce Bulk with the init operation and addBatch operation.

1. Create a sample proxy as below :

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
         <isQuery>{$ctx:isQuery}</isQuery>
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
2. Create a xml file called addBatch.xml containing the following xml:

```xml
<addBatch>
   <apiVersion>34</apiVersion>
   <accessToken>00D28000000erPd!ARsAQLkIRoO7KdP9dQeqEfZuEYmSBIw3.OqbHUZbFSDwDBEV9ginNZmNi4yJuf14bnLN2cVpkUqVyxpUl5gqZSqFV0v_90hf</accessToken>
   <apiUrl>https://ap2.salesforce.com</apiUrl>
   <refreshToken>5Aep861TSESvWeug_wh8Zdrl_XXXXXXXX.QEq4FGbXtI5ARrLxzibR</refreshToken>
   <clientId>3MVG9ZL0ppGP5UrBKXXXXXXKwHHYHfEh.gTMriEXwhf6DFvyXXXXXXXwQxmFraW3k0KgU</clientId>
   <clientSecret>914846950346786099</clientSecret>
   <intervalTime>1000000</intervalTime>
   <registryPath>connectors/SalesforceBulk</registryPath>
   <contentType>text/csv</contentType>
   <isQuery>false</isQuery>
   <jobId>7500K00000GV8lBQAT</jobId>
   <objects>
      <values>Name,description
        Tom Dameon,Created from Bulk API
      </values>
   </objects>
</addBatch>                        
```
3. Replace the credentials with your values.

4. Execute the following cURL command:

```bash
curl http://localhost:8280/services/salesforcebulk_addBatch -H "Content-Type: text/xml" -d @addBatch.xml
```
5. Salesforce returns a xml response as below.
 
```xml
<?xml version="1.0" encoding="UTF-8"?>
<batchInfo
   xmlns="http://www.force.com/2009/06/asyncapi/dataload">
    <id>7510K00000NYWUIQA5</id>
    <jobId>7500K00000Frr9CQAR</jobId>
    <state>Queued</state>
    <createdDate>2018-08-22T09:16:52.000Z</createdDate>
    <systemModstamp>2018-08-22T09:16:52.000Z</systemModstamp>
    <numberRecordsProcessed>0</numberRecordsProcessed>
    <numberRecordsFailed>0</numberRecordsFailed>
    <totalProcessingTime>0</totalProcessingTime>
    <apiActiveProcessingTime>0</apiActiveProcessingTime>
    <apexProcessingTime>0</apexProcessingTime>
</batchInfo>
```
