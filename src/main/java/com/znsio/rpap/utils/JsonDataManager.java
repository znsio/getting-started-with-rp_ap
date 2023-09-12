package com.znsio.rpap.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Set;
import com.google.gson.annotations.Expose;

public class JsonDataManager {
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private LinkedHashMap<String, LinkedHashMap<String, JsonDataField>> data;
    public JsonDataManager(String name, String description, LinkedHashMap<String, LinkedHashMap<String, JsonDataField>> data) {
        this.name = name;
        this.description = description;
        this.data = data;

    }
    @SuppressWarnings("rawtypes")
    public Object[][] getData(Class claz, String mtd) throws Exception {
        String fullPageName = claz.getName().toString();

        String pageName = fullPageName.substring(fullPageName.lastIndexOf(".") + 1);
        if (data.containsKey(pageName)) {
            LinkedHashMap<String, JsonDataField> dataMap =  data.get(pageName);
            JsonDataField dataForMtd = dataMap.get(mtd);

            ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList());

            if(dataForMtd.value!= null)
                for(int i=0; i<dataForMtd.value.length; i++) {
                    temp.add(dataForMtd.value[i]);
                }
            if(dataForMtd.expected!=null)
                temp.add(dataForMtd.expected);
            return new Object[][] { temp.toArray() };
        } else {
            throw new Exception(String.format("No test data for the Test : ", pageName));
        }
    }
    public Set<String> keys() {
        return this.data.keySet();
    }

}
