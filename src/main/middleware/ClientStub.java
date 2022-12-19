package middleware;

import org.json.simple.JSONObject;

import java.net.Socket;

public class ClientStub {


    public void invoke(String funcName, String[] params, int id) {

    }

    void send(byte[] msg, String adress, int port, String ConnectionType) { //Snychron Asynchron // TCP oder UDP
        Socket socket;

    }

    public JSONObject marshall(String funcName, String[] params, String[] paramNames, int id) {
        if (params.length == paramNames.length) {
            throw new IllegalArgumentException("Parameter Zahl und Paramater Namen sind nicht gleich viel");
        }
        JSONObject jsObject = new JSONObject();
        for (int i = 0; i < params.length; i++) {
            jsObject.put(paramNames, params);
        }
        jsObject.put("methodName", funcName);
        return jsObject;
    }

    public String[] lookUp() {
        return new String[0];
    }

}
