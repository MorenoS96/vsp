package middleware;

import org.json.simple.JSONObject;

public class ClientStub {


    public void invoke(String funcName, String[] params, int id) {

    }

    void send(byte[] msg, String adress, int port, String ConnectionType) { //Snychron Asynchron // TCP oder UDP

    }

    public JSONObject marshall(String funcName, String[] params, String[] paramNames, int id) {
        JSONObject jsObject = new JSONObject();
        return jsObject;
    }

    public String[] lookUp() {
        return new String[0];
    }

}
