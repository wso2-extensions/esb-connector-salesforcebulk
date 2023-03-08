package org.wso2.carbon.esb.connector.store;

import org.wso2.carbon.esb.connector.pojo.SalesforceConfig;

import java.util.concurrent.ConcurrentHashMap;

public class SalesforceConfigStore {

    private static final ConcurrentHashMap<String, SalesforceConfig> salesforceConfigMap = new ConcurrentHashMap<>();

    /**
     * Add Saqlesforce config to the store.
     * @param salesforceConfig Salesforce config to be added.
     */
    public static void addSalesforceConfig(SalesforceConfig salesforceConfig) {
        String key = salesforceConfig.getSalesforceConfigName();
        if (!salesforceConfigMap.contains(key)) {
            salesforceConfigMap.put(key, salesforceConfig);
        }
    }

    /**
     * Remove Salesforce config from the store.
     * @param salesforceConfigName Name of the Salesforce config to be removed.
     */
    public static void removeSalesforceConfig(String salesforceConfigName) {
        salesforceConfigMap.remove(salesforceConfigName);
    }

    /**
     * Update active access token in the store.
     * @param salesforceConfigName Name of the Salesforce config.
     * @param activeAccessToken Active access token.
     */
    public static void updateAccessToken(String salesforceConfigName, String activeAccessToken) {
        salesforceConfigMap.get(salesforceConfigName).setAccessToken(activeAccessToken);
    }

    /**
     * Get Salesforce config from the store.
     * @param salesforceConfigName Name of the Salesforce config.
     */
    public static SalesforceConfig getSalesforceConfig(String salesforceConfigName) {
        return salesforceConfigMap.get(salesforceConfigName);
    }

}
