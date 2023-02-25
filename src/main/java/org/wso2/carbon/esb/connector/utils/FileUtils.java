package org.wso2.carbon.esb.connector.utils;

import org.wso2.carbon.esb.connector.exception.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static void verifyFile(String filePath) throws InvalidConfigurationException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new InvalidConfigurationException("File not found at path: " + filePath);
        }
        if (!file.canRead()) {
            throw new InvalidConfigurationException("File cannot be read at path: " + filePath);
        }
    }
}
