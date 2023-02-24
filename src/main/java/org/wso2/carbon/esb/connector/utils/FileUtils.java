package org.wso2.carbon.esb.connector.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static void verifyFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found at path: " + filePath);
        }
        if (!file.canRead()) {
            throw new IOException("File cannot be read at path: " + filePath);
        }
    }
}
