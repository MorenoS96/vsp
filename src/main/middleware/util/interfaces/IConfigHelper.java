package middleware.util.interfaces;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface IConfigHelper {
    String getConfigValue(String configName) throws IOException, ParseException;
}
