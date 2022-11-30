package tron.controller.impl.basicController.components.configHandler.impl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import tron.controller.impl.basicController.components.configHandler.interfaces.*;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class ConfigHandler implements IGetConfig,ILoadConfig{
    static String defaultPath="src/res/config.json";
    private Map<String,String> classConfigMap;
    private static String filePath=null;
    @Override
    public Map<String, String> loadConfigMap(String absolutePathToConfigFile) throws IOException, ParseException, org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(absolutePathToConfigFile)); //the location of the file
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
    public String getConfigVal(String configName) {
        if(classConfigMap==null){
            try {
                if(filePath!=null){
                    classConfigMap=loadConfigMap(filePath);
                }else{
                    classConfigMap=loadConfigMap(defaultPath);
                }

                return classConfigMap.get(configName);
            } catch (Exception e) {
                e.printStackTrace();
                return "notFound";
            }
        }else {
            return classConfigMap.get(configName);
        }

    }

    @Override
    public void setConfigPath(String configPath) {
        filePath=configPath;
    }

    @Override
    public Map<String, String> getConfigMap() {
        if(classConfigMap==null){
            try {
                classConfigMap=loadConfigMap(defaultPath);
            } catch (Exception e) {
                return new HashMap<>();
            }
        }
        return new HashMap<>(classConfigMap);
    }
}
