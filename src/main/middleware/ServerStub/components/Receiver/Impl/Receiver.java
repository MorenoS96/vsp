package middleware.ServerStub.components.Receiver.Impl;

import middleware.ServerStub.components.Receiver.Interfaces.IReceiver;
import middleware.ServerStub.components.UnMarshall.Interfaces.IUnMarshall;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Receiver implements IReceiver {
    private int udpPort;
    private int tcpPort;
    private Thread udpThread;
    private Thread tcpThread;
    private IUnMarshall iUnMarshall;


    public Receiver(int udpPort, int tcpPort,IUnMarshall iUnMarshall) {
        this.udpPort = udpPort;
        this.tcpPort = tcpPort;
        this.iUnMarshall=iUnMarshall;
    }

    public void stop() {
        // Interrupt the threads to stop them
        udpThread.interrupt();
        tcpThread.interrupt();

    }

    private void processUDPPacket(DatagramPacket packet) {
        // Process the UDP packet
        String string=new String(packet.getData(), StandardCharsets.UTF_8);




            iUnMarshall.unmarshallAndCall(string);

    }

    private void processTCPConnection(Socket s) {
        DataInputStream dis= null;
        try {
            dis = new DataInputStream(s.getInputStream());
            String string=(String)dis.readUTF();




                iUnMarshall.unmarshallAndCall(string);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void receiveUDP(int port) {
        udpPort=port;
        udpThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Create a DatagramSocket for receiving UDP packets
                    DatagramSocket socket = new DatagramSocket(udpPort);
                    while (true) {
                        // Create a buffer to store incoming UDP packets
                        byte[] buffer = new byte[1024];
                        // Create a DatagramPacket for receiving the packet
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        // Receive the packet
                        socket.receive(packet);
                        // Process the packet
                        processUDPPacket(packet);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        udpThread.start();
    }

    @Override
    public void receiveTCP(int port) {
        tcpPort=port;
        tcpThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Create a ServerSocket for accepting incoming TCP connections
                    ServerSocket serverSocket = new ServerSocket(tcpPort);
                    while (true) {
                        // Accept an incoming connection
                        Socket socket = serverSocket.accept();
                        // Process the connection
                        processTCPConnection(socket);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        tcpThread.start();
    }
}

