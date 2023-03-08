package org.wso2.carbon.esb.connector.pojo;

/**
 * This class is used to add and get OAuth configurations
 */
public class SalesforceConfig {
    private String grantType = null;
    private String clientId = null;
    private String clientSecret = null;
    private String refreshToken = null;
    private String tokenUrl = null;
    private String tokenId = null;
    private String accessToken = null;
    private String sfOAuthConfigName = null;
    private String instanceUrl = null;

    public String getInstanceUrl() {

        return instanceUrl;
    }

    public void setInstanceUrl(String instanceUrl) {

        this.instanceUrl = instanceUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSalesforceConfigName() {
        return sfOAuthConfigName;
    }

    public void setSalesforceConfigName(String sfOAuthConfigName) {
        this.sfOAuthConfigName = sfOAuthConfigName;
    }
}
