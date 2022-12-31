package middleware.ServerStub.components.UnMarshall.Impl;

import middleware.ServerStub.components.UnMarshall.Interfaces.IUnMarshall;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UnMarshall implements IUnMarshall {

    @Override
    public void unmarshallAndCall(String jSONString) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject= (JSONObject)parser.parse(jSONString);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
