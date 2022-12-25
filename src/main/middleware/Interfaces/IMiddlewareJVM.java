package middleware.Interfaces;

public interface IMiddlewareJVM {
    public  void send(byte[] msg, String adress, int port, String ConnectionType,boolean isSync);
    public void receiveUDP(byte[] msg, String adress, int port);
    public void receiveTCP(byte[] msg, String adress, int port);
    public String[] lookUp();
}
