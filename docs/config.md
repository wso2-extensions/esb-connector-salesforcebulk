# Configuring Salesforce Bulk Operations

[[Initializing the Connector]](#initializing-the-connector)  [[Obtaining user credentials]](#obtaining-user-credentials)

> NOTE: To work with the Salesforce Bulk connector, you need to have a Salesforce account. If you do not have a Salesforce account, go to [https://developer.salesforce.com/signup](https://developer.salesforce.com/signup) and create a Salesforce developer account.

To use the SalesforceBulk connector, add the <salesforcebulk.init> element in your configuration before carrying out any other SalesforceBulk operations. 

Salesforce  uses the OAuth protocol to allow users of applications to securely access data without having to reveal username and password credentials.  For more information, see [Understanding Authentication](https://developer.salesforce.com/docs/atlas.en-us.api_rest.meta/api_rest/intro_understanding_authentication.htm).
## Obtaining user credentials

* **Follow the steps below to create a connected application using Salesforce:**

    1. Go to https://developer.salesforce.com/, and sign up to get a free developer environment. 
    2. Go to **Setup** -> **Create** -> **Apps**, and click **New**. This creates a new connected application.
    3. Specify a name for your application, your email address as well as any other information applicable to your application.
    4. Select **Enable OAuth Settings**.
    5. Specify a callback URL. Depending on the OAuth flow you use, this is typically the URL that your browser is redirected to, after successful authentication. Since this URL is used in some of the OAuth flows to pass an access token, the URL must use secure HTTP or a custom URI scheme.
    6. Add all supported OAuth scopes as **Selected OAuth Scopes**. These OAuth scopes include permission given by the user running the connected application.
    7. Click Specify a **Info URL**. This is where a user can go for more information about your application.
    8. Click **Save**. Once you navigate to the application that you created, you can see the following:    
     * **Consumer Key** created and displayed. 
     * **Consumer Secret** created.
    
* **Follow the steps below to obtain an access token and refresh token:**

    1. Replace the placeholders (i.e., <your_client_id> and <your_redirect_uri>) of the following URL with the values that you created earlier and view the URL using a web browser. 
        ```xml
        https://login.salesforce.com/services/oauth2/authorize?response_type=code&client_id=<your_client_id>&redirect_uri=<your_redirect_uri>
        ```
    2. Approve the application to access your Salesforce account and note that the browser redirects you to the call-back URL (redirect URL):
       ```xml
       https://app.com/oauth_callback?code=aWe...c4w%3D%3D
       ```
    3. From the call-back URL, extract the authorization code.
    4. Send a direct POST request to the authorization server using the following request. Be sure to change the values here to the actual values that you got earlier.
        ```xml
        https://login.salesforce.com/services/oauth2/token?code=aWe...c4w==&grant_type=authorization_code&client_id=<your_client_id>&client_secret=<your_client_secret>&redirect_uri=<your_redirect_uri>&format=json
        ```
        ```xml
        In the above request, you can set the format as urlencoded, json or xml to get the response in one of the three formats.
        ```
    5. From the response that you get, extract the accessToken to access Salesforce via the created app. You also get a refreshToken to renew the accessToken when it expires.

## Initializing the Connector
Specify the init method as follows:

**init**
```xml
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
```
**Properties** 
* apiVersion:  The version of the Salesforce API. 
* accessToken:  Access token of the organizational account.
* apiUrl:  The API URL to access the endpoint.
* refreshToken:  The refresh token to refresh the API access token.
* clientId:  The value of the Consumer Key of your application.
* clientSecret:  The value of the Consumer Secret of your application.
* intervalTime:  The interval time to check the validity of the access token.
* registryPath:  Registry path of the connector where the values are stored. You must give the value as : connectors/<value> 
  For example: registryPath = "connectors/salesforcebulk".
  
Please ensure that the following Axis2 configurations are added and enabled in the <EI_HOME>\conf\axis2\axis2.xml file.

Required message formatters

**messageFormatters**
```xml
<messageFormatter contentType="text/csv" class="org.wso2.carbon.relay.ExpandingMessageFormatter"/>
<messageFormatter contentType="zip/xml" class="org.wso2.carbon.relay.ExpandingMessageFormatter"/>
<messageFormatter contentType="zip/csv" class="org.wso2.carbon.relay.ExpandingMessageFormatter"/>
<messageFormatter contentType="text/xml" class="org.wso2.carbon.relay.ExpandingMessageFormatter"/>
<messageFormatter contentType="text/html" class="org.wso2.carbon.relay.ExpandingMessageFormatter"/>
```
Required message builders

**messageBuilders**
```xml
<messageBuilder contentType="text/csv" class="org.wso2.carbon.relay.BinaryRelayBuilder"/>
<messageBuilder contentType="zip/xml" class="org.wso2.carbon.relay.BinaryRelayBuilder"/>
<messageBuilder contentType="zip/csv" class="org.wso2.carbon.relay.BinaryRelayBuilder"/>
<messageBuilder contentType="text/xml" class="org.wso2.carbon.relay.BinaryRelayBuilder"/>
<messageBuilder contentType="text/html" class="org.wso2.carbon.relay.BinaryRelayBuilder"/>
```

Now that you have connected to Salesforce Bulk, use the information in the following topics to perform various operations with the connector:

[Working with Projects Batches](batches.md)

[Working with Binary Attachments](attachments.md)

[Working with Jobs](jobs.md)