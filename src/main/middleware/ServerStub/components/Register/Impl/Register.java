package middleware.ServerStub.components.Register.Impl;

import middleware.ServerStub.components.Register.Interfaces.IRegister;

public class Register implements IRegister {
    @Override
    public int register(String funcName, String[] paramTypes, int id) {
        return 0;
    }

    @Override
    public void heartbeat_resp(int id) {

    }
}
