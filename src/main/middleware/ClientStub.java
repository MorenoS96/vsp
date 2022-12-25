package middleware;

import org.json.simple.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ClientStub {

    public void invoke(String funcName, String[] params, String[] paramNames, int id) {
        // TODO ParamNames mit in die Signatur, da so das unmarshalling einfacher (mit Key) sein sollte

        JSONObject jsonObject = marshall(funcName, params, paramNames, id);

        //String adress = nameService.lookUp();

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
    void send(JSONObject jsonObject, String adress, int port, String ConnectionType,boolean isSync) {
        if(isSync) {
            sendSync(jsonObject,adress,port,ConnectionType);
        } else {
            sendAsync(jsonObject,adress,port,ConnectionType);
        }
    }

    void sendAsync(JSONObject jsonObject, String adress, int port, String ConnectionType) {
        if (Objects.equals(ConnectionType, "UDP")) {

            DatagramSocket datagramSocket = null;

            try {
                datagramSocket = new DatagramSocket();

                //TODO auch für TCP was wenn zu lang?
                byte[] msg = jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8);
                DatagramPacket datagramPacket = new DatagramPacket(msg, msg.length, InetAddress.getByName(adress), port);

                datagramSocket.send(datagramPacket);
            } catch (IOException e) {
                error("sendAsync","Es ist ein Fehler aufgetreten beim versenden von einem UDP Packet welches zur Adresse: " + adress + " und Port: " + port + " sollte");
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
                dataOutputStream.writeUTF(jsonObject.toJSONString());
                dataOutputStream.flush();

                //TODO video wegen empfangen
                dataOutputStream.close();
                socket.close();

            } catch (IOException e) {
                error("sendAsync","Es ist ein Fehler aufgetreten beim versenden mit TCP auf der Adresse: " + adress + " mit dem Port: " + port);
                throw new RuntimeException(e);
            }


        } else {
            System.out.println("Diesen Connection Type unterstützen wir nicht.");
        }

    }

    /**
     * Für den Anfang
     * @return
     */
    public int sendSync(JSONObject jsonObject, String adress, int port, String ConnectionType) {
        return 0;
    }

    public JSONObject marshall(String funcName, String[] params, String[] paramNames, int id) {
        if (params.length == paramNames.length) {
            error("marshall","Es wurden nicht gleich viele Parameter wie Parameter Namen verschickt.");
            //throw new IllegalArgumentException("Parameter Zahl und Paramater Namen sind nicht gleich viel");
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

        return new String[0];
    }

    void error(String method, String msg) {
        String errorMsg = "middleware.Clientstub." + method + "|" + msg;
        System.out.println(errorMsg);
    }

    void heartbeat_resp(int id) {
        //TODO erst wenn request impl ist.
    }

}
