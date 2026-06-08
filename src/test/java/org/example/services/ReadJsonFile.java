package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ReadJsonFile {

    public static List<HashMap<String, String>> getJsonData(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String,String > > data = mapper.readValue(new File(filename),
                new TypeReference<List<HashMap<String,String>>>() {
        });
        return data;


    }
}
