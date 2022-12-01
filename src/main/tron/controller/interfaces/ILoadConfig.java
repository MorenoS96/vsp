package tron.controller.interfaces;

import java.util.Map;

public interface ILoadConfig {
    Map<String,String> getConfigMap(String filePath);
}
