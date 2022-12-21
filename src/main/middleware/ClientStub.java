package middleware;

import org.json.simple.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Objects;

public class ClientStub {

    public void invoke(String funcName, String[] params, String[] paramNames, int id) {
        // TODO Spezifische ID?
        // TODO ParamNames mit in die Signatur, da so das unmarshalling einfacher (mit Key) sein sollte, sonst halt Zahlen

        JSONObject jsonObject = marshall(funcName, params, paramNames, id); // Doch von dem hier zu byte[] ? dann wär paramsName nicht mehr nötig.

        //String adress = nameService.lookUp();

        // Über jvm senden? [send]


    }

    int register(String funcName, String[] paramTypes, int id) {
        // Eigentliche id bestimmen

        String[] paramsName = new String[1];
        JSONObject jsObject = marshall(funcName, paramTypes, paramsName, id);

        //send(jsObject,readConfig.getAdress,readConfig.getPort,id(?))

        // TODO ?
        return id;
    }

    //Snychron Asynchron (snyc braucht einen Rückgabe Typ also neue Methode falls wir beides machen)
    // TCP oder UDP
    void send(byte[] msg, String adress, int port, String ConnectionType) { //Erstmal nur Asynchron
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
            try {

                Socket socket = new Socket(adress, port);

                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.write(msg, 0, msg.length);
                dataOutputStream.flush();

                socket.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


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


    public String[] lookUp() { //TODO vermutlich Rückgabetyp ändern
        //String[] registeredServices = nameService.getRegisteredServices();

        //send(String[],ApplicationStub,id)

        return new String[0];
    }

    void error(String method, String msg) {
        String errorMsg = "middleware.Clientstub." + method + "|" + msg;
    }

}
