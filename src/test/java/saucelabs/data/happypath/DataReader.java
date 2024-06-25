package saucelabs.data.happypath;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class DataReader {


        public List<HashMap<String,String>> getDataToMap() throws IOException {
        //1. Read json to string
        String jsonContent =  FileUtils.
               readFileToString(new File(System.getProperty("user.dir")+"/src/test/java/saucelabs/data/happypath/happypath.json"), StandardCharsets.UTF_8);

        //2. String to HashMap - Jackson Databind
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>() {});


        //{map, map}
        return data;
    }
}
