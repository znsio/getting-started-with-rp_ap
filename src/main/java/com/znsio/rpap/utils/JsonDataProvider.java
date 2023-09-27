package com.znsio.rpap.utils;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import com.google.gson.Gson;
import com.znsio.reportportal.integration.properties.Config;

public class JsonDataProvider {
    private static final Properties config = Config.loadProperties(System.getProperty("CONFIG"));

    public static JsonDataManager loadData() throws Exception {
        String filePath = config.getProperty("TEST_DATA_FILE");
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        JsonDataManager data = gson.fromJson(reader, JsonDataManager.class);
        return data;
    }
}
