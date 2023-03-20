# esb-connector-salesforcebulk-v2


The Salesforce Bulk V2 [Connector](https://docs.wso2.com/display/EI650/Working+with+Connectors) allows you to access the [Salesforce Bulk REST API](https://developer.salesforce.com/docs/atlas.en-us.api_asynch.meta/api_asynch/asynch_api_intro.htm) through WSO2 Enterprise Integrator (WSO2 EI) and WSO2 Micro Integrator. SalesforceBulk is a RESTful API that allows you to quickly load large sets of your organisation’s data into Salesforce or delete large sets of your organisation’s data from Salesforce. You can use SalesforceBulk to query, insert, update, upsert or delete a large number of records asynchronously, by submitting the records as CSV file.

## Compatibility

| Connector version                                                                                                            | Supported Salesforce Bulk API version | Supported WSO2 ESB/EI version |
|------------------------------------------------------------------------------------------------------------------------------|---------------------------------------| ------------- |
| [1.0.0](https://github.com/wso2-extensions/esb-connector-salesforcebulk/tree/org.wso2.carbon.connector.salesforcebulk-1.1.0) | 57.0                                  | EI 6.5.0, EI 6.6.0, EI 7.0.X, EI 7.1.0 |

## Getting started

#### Download and install the connector

1. Download the connector from the [WSO2 Store](https://store.wso2.com/store/assets/esbconnector/details/3957b581-86e0-4909-ab75-c3cfa2ce6494) by clicking the Download Connector button.
2. Then you can follow this [Documentation](https://docs.wso2.com/display/EI650/Working+with+Connectors+via+the+Management+Console) to add and enable the connector via the Management Console in your EI instance.
3. For more information on using connectors and their operations in your EI configurations, see [Using a Connector](https://docs.wso2.com/display/EI650/Using+a+Connector).
4. If you want to work with connectors via EI tooling, see [Working with Connectors via Tooling](https://docs.wso2.com/display/EI650/Working+with+Connectors+via+Tooling).

## Building From the Source

Follow the steps given below to build the Salesforce Bulk connector from the source code:

1. Get a clone or download the source from [Github](https://github.com/wso2-extensions/esb-connector-salesforcebulk).
2. Checkout to `bulkapi-v2` branch.
2. Run the following Maven command from the `esb-connector-salesforcebulk` directory: `mvn clean install`.
3. The Salesforce connector zip file is created in the `esb-connector-salesforcebulk/target` directory

## How You Can Contribute

As an open source project, WSO2 extensions welcome contributions from the community.
Check the [issue tracker](https://github.com/wso2-extensions/esb-connector-salesforcebulk/issues) for open issues that interest you. We look forward to receiving your contributions.
