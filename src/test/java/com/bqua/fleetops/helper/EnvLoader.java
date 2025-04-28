package com.bqua.fleetops.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvLoader {
    /**
     * Load properties from a file named "test-secret.properties" located in the test resources folder,
     * and set them as system properties so they can be accessed using System.getProperty().
     * <p>
     * Note:
     * 1. Ensure the file "test-secret.properties" exists in the "src/test/resources" directory.
     * 2. Add sensitive keys and values related to test environments in the file.
     * 3. Be cautious and do not commit this file to version control (add it to .gitignore if needed).
     */
    public static void loadFromTestSecretProperties() {
        String filePath = "test-secret.properties";
        try (InputStream inputStream = EnvLoader.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new RuntimeException("Resource file not found: " + filePath);
            }
            Properties properties = new Properties();
            properties.load(inputStream);
            properties.forEach((key, value) -> {
                System.setProperty(key.toString(), value.toString());
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to load resource file: " + filePath, e);
        }
    }
}