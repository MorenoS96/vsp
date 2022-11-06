package java.controller.impl.components.configHandler.interfaces;

import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface ILoadConfig {
    Map<String,String> loadConfigMap(String absolutePathToConfigFile) throws IOException, ParseException;
}
