package com.znsio.rpap.utils;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import com.google.gson.Gson;

public class JsonDataProvider {
    public static JsonDataManager loadData() throws Exception {
        ConfigDataProvider configData = new ConfigDataProvider();
        String filePath = configData.getData("TEST_DATA_FILE");
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
        Gson gson = new Gson();
        JsonDataManager data = (JsonDataManager) gson.fromJson(reader, JsonDataManager.class);
        return data;
    }
}
