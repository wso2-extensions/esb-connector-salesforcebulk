##  Integration tests for WSO2 ESB Salesforce Bulk connector

### Pre-requisites:

 - Maven 3.x
 - Java 1.6 or above
 - The org.wso2.esb.integration.integration-base project is required. The test suite has been configured to download this project automatically. If the automatic download fails, download the following project and compile it using the mvn clean install command to update your local repository:
   
   https://github.com/wso2-extensions/esb-integration-base/

### Tested Platform: 

 - UBUNTU 16.04
 - WSO2 EI - 6.5.0
 - Java 1.8

### Steps to follow in setting integration test.

 1. Download EI from official website.

 2. Navigate to location "<ESB_HOME>/repository/conf/axis2" and add/uncomment following lines in "axis2.xml".
	
      Message Formatters :-
      
   ```xml
       <messageFormatter contentType="text/csv" class="org.wso2.carbon.relay.ExpandingMessageFormatter"/>
   ```    
   Message Builders :-  
        
   ```xml
       <messageBuilder contentType="text/csv" class="org.wso2.carbon.relay.BinaryRelayBuilder"/>
   ```
   
 3. Create a Salesforce developer account and derive the API Token.
 
        i.    Using the URL "https://www.salesforce.com/" create a Salesforce developer account.
        ii.   Using the URL "https://developer.salesforce.com/docs/atlas.en-us.api_rest.meta/api_rest/intro_understanding_authentication.htm" setup the connected app, get the 'client id','client secret' and obtain the 'Access Token' and 'Refresh Token'.
        iii.  Create a .txt file by referring the URL "https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/binary_create_job.htm" and place it under <SALESFORCEBULK_CONNECTOR_HOME>/src/test/resources/artifacts/ESB/config/resources/salesforcebulk/.
              (File is already available in the above location.)
         iv.  Create a .csv file by referring the URL "https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/datafiles_csv_sample_file.htm" and place it under <SALESFORCEBULK_CONNECTOR_HOME>/src/test/resources/artifacts/ESB/config/resources/salesforcebulk/.
              (File is already available in the above location.)
 **Hint:** <br/>
       - Change the 'Permitted Users' as 'All users may self-authorize' under 'OAuth Policies'. And makesure that the 'Refresh Token Policy' is not set to either 'Refresh token is valid until revoked' or 'Immediately expire refresh token'.<br/>
       - Getting Authorization Code: Make a 'GET' call to https://login.salesforce.com/services/oauth2/authorize?response_type=code&client_id=<client_id>&redirect_uri=<redirect_uri> <br/>
       - Getting Access Token      : Make a 'POST' call to  https://login.salesforce.com/services/oauth2/token?client_secret=<client_secret>&client_id=<client_id>&grant_type=authorization_code&code=<code>&redirect_uri=<redirect_uri> 
 
 4. If required place the certificate from https://{instance_name}.salesforce.com to location "<SALESFORCEBULK_CONNECTOR_HOME>/repository/".

 5. Compress the modified EI and copy that zip file in to location "<SALESFORCEBULK_CONNECTOR_HOME>/repository/".

 6. Update the esb-connector-salesforcebulk properties file at location "<SALESFORCEBULK_CONNECTOR_HOME>/repository/ with the suited values.

 7. Following are the properties used in the 'esb-connector-salesforcebulk.properties' file and salesforcebulk properties file (<SALESFORCEBULK_CONNECTOR_HOME>/src/test/resources/artifacts/EI/connector/config/)repository to run the integration tests.
   
        i)      apiUrl                -  Use following value 'https://{instance_name}.salesforce.com'
        ii)     refreshToken          -  Obtain and use the Refresh Token as mentioned in step 3 ii).
        iii)    clientId              -  Obtain and use the 'client id' as mentioned in step 3 ii).
        iv)     clientSecret          -  Obtain and use the 'client secret' as mentioned in step 3 ii).
        v)      apiVersion            -  Use 34.0 or above version.
        vi)     timeOut               -  Salesforcebulk API is take some time to proceed the uploaded file. Keep a suitable time gap between create and retrieve calls.
        vii)    jobFileName           -  Name of the file created in step 3 iv).
        viii)   jobContentType        -  Content type of the job which is going to create by uploading file.
        ix)     batchFileName         -  Name of the file created in step 3 v).
        x)      description1          -  The description of first object of batch.
        xi)     name1                 -  The name of first object of batch.
        xii)    description2          -  The description of second object of batch.
        xiii)   name2                 -  The name of second object of batch.
        xiv)    registryPath          -  The registry path
        xv)     intervalTime          -  The access token validity period in seconds.
        xvi)    tokenEndpointHostname -  Instance url for OAuth 2.0 token endpoint when issuing authentication requests in your application.
   
 8. Navigate to "<SALESFORCEBULK_CONNECTOR_HOME>/" and run the following command. <br/>
         `$ mvn clean install -Dskip-tests=false`

