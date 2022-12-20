package middleware;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.*;
import java.util.Objects;

public class ClientStub {

    public void invoke(String funcName, String[] params, int id) {

    }

    //Snychron Asynchron (snyc braucht einen Rückgabe Typ also neue Methode falls wir beides machen)
    // TCP oder UDP
    void send(byte[] msg, String adress, int port, String ConnectionType) {
        if (Objects.equals(ConnectionType, "UDP")) {

            DatagramSocket datagramSocket = null;

            try {
                datagramSocket = new DatagramSocket();

                DatagramPacket datagramPacket = new DatagramPacket(msg, msg.length, InetAddress.getByName(adress), port);

                datagramSocket.send(datagramPacket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (datagramSocket != null) {
                    datagramSocket.close();
                }
            }


        } else if (Objects.equals(ConnectionType, "TCP")) {

        } else {
            System.out.println("Diesen Connection Type unterstützen wir nicht.");
        }

    }

    public JSONObject marshall(String funcName, String[] params, String[] paramNames, int id) {
        if (params.length == paramNames.length) {
            throw new IllegalArgumentException("Parameter Zahl und Paramater Namen sind nicht gleich viel");
        }
        JSONObject jsObject = new JSONObject();
        jsObject.put("id", id);
        jsObject.put("methodName", funcName);

        for (int i = 0; i < params.length; i++) {
            jsObject.put(paramNames, params);
        }
        return jsObject;
    }

    public String[] lookUp() {
        return new String[0];
    }

}
