package tron.controller.impl.basicController.components.configHandler.interfaces;

import java.util.Map;

public interface IGetConfig {

     String getConfigVal(String configName);
     void setConfigPath(String configPath);
     Map<String,String> getConfigMap();

}
