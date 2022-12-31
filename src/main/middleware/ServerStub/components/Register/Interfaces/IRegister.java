package middleware.ServerStub.components.Register.Interfaces;

public interface IRegister {
    public int register(String funcName, String[] paramTypes, int id);

    public void heartbeat_resp(int id);
}
