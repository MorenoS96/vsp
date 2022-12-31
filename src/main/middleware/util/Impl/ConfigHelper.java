package middleware.util.Impl;

import middleware.util.interfaces.IConfigHelper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigHelper implements IConfigHelper {
    public static final String DEFAULT_PATH_TO_CONFIG ="src/res/config_middleware.json";
    private HashMap<String,String > config_Map=null;
    private HashMap<String,String> buildConfigMap() throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(DEFAULT_PATH_TO_CONFIG)); //the location of the file
        JSONObject jsonObject = (JSONObject) obj;
        HashMap<String,String> configMap=new HashMap<>();
        jsonObject.entrySet().forEach(e->{
            String k=((Map.Entry)e).getKey().toString();
            String v=((Map.Entry)e).getValue().toString();
            configMap.put(k,v);
        });

        return configMap;

    }
    @Override
    public String getConfigValue(String configName) throws IOException, ParseException {
        if(config_Map==null){
            config_Map=buildConfigMap();
        }
        return config_Map.get(configName);
    }
}
