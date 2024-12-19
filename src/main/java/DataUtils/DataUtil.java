package DataUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DataUtil {
    private static final String testDataPath="src/test/resources/TestData/";

    public static String getJsonData(String filename, String field) throws FileNotFoundException {
        try {
            FileReader reader= new FileReader(testDataPath+ filename + ".json");
            JsonElement jsonElement= JsonParser.parseReader(reader);
            return jsonElement.getAsJsonObject().get(field).getAsString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "0";
    }

    public static String getPropertyValue(String filename,String key) throws IOException {
        Properties properties= new Properties();
        properties.load(new FileInputStream(testDataPath + filename + ".properties"));
        return properties.getProperty(key);

    }
}
