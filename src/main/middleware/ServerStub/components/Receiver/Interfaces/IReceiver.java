package middleware.ServerStub.components.Receiver.Interfaces;

public interface IReceiver {
  public void receiveUDP( int port);
  public  void receiveTCP( int port);
}
