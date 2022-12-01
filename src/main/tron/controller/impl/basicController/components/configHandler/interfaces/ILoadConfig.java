package tron.controller.impl.basicController.components.configHandler.interfaces;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public interface ILoadConfig {
    Map<String,String> loadConfigMap(String pathToConfig) throws IOException, ParseException, org.json.simple.parser.ParseException;
}
