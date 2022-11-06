package java.controller.impl.components.configHandler.impl;
import java.controller.impl.components.configHandler.interfaces.IGetConfig;
import java.controller.impl.components.configHandler.interfaces.ILoadConfig;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class configHandler implements ILoadConfig, IGetConfig {
    static String defaultPath=configHandler.class.getClassLoader().getResource("config.json").getPath();
    private Map<String,String> classConfigMap;
    @Override
    public Map<String, String> loadConfigMap(String absolutePathToConfigFile) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(absolutePathToConfigFile)); //the location of the file
        JSONObject jsonObject = (JSONObject) obj;
        HashMap<String,String> configMap=new HashMap<>();
        jsonObject.entrySet().stream().forEach(e->{
            String k=((Map.Entry)e).getKey().toString();
            String v=((Map.Entry)e).getValue().toString();
            configMap.put(k,v);
        });

        return configMap;
    }


    @Override
    public String getConfigVal(String configName) {
        if(classConfigMap==null){
            try {
                classConfigMap=loadConfigMap(defaultPath);
                return classConfigMap.get(configName);
            } catch (Exception e) {
                e.printStackTrace();
                return "notFound";
            }
        }else {
            return classConfigMap.get(configName);
        }

    }
}
