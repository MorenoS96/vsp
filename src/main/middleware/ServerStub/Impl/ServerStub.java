package middleware.ServerStub.Impl;

import middleware.ServerStub.Interfaces.IServerStubApplicationStub;
import middleware.ServerStub.components.Receiver.Impl.Receiver;
import middleware.ServerStub.components.Receiver.Interfaces.IReceiver;
import middleware.ServerStub.components.Register.Impl.Register;
import middleware.ServerStub.components.Register.Interfaces.IRegister;
import middleware.ServerStub.components.UnMarshall.Impl.UnMarshall;
import middleware.ServerStub.components.UnMarshall.Interfaces.IUnMarshall;
import middleware.util.Impl.ConfigHelper;
import middleware.util.interfaces.IConfigHelper;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class ServerStub implements IServerStubApplicationStub {

   private IUnMarshall unMarshall;
    private IReceiver receiver;
    private IRegister register;
    public int default_udp_port;
    public int default_tcp_port;
    public ServerStub() throws IOException, ParseException {
        unMarshall=new UnMarshall();
        register=new Register();
        IConfigHelper iConfigHelper=new ConfigHelper();
         default_udp_port= Integer.parseInt(iConfigHelper.getConfigValue("default_udp_receiver_port"));
        default_tcp_port= Integer.parseInt(iConfigHelper.getConfigValue("default_tcp_receiver_port"));;
        receiver=new Receiver(default_udp_port,default_tcp_port,unMarshall);
    }

    @Override
    public void receiveUDP(int port) {
        receiver.receiveUDP(port);
    }

    @Override
    public void receiveTCP(int port) {
        receiver.receiveTCP(port);
    }

    @Override
    public int register(String funcName, String[] paramTypes, int id) {
        return register.register(funcName, paramTypes, id);
    }

    @Override
    public void heartbeat_resp(int id) {
        register.heartbeat_resp(id);
    }

    @Override
    public void unmarshallAndCall(JSONObject obj) {
        unMarshall.unmarshallAndCall(obj);
    }
}
