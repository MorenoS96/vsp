package middleware.ServerStub.Interfaces;

import middleware.ServerStub.components.Receiver.Interfaces.IReceiver;
import middleware.ServerStub.components.Register.Interfaces.IRegister;
import middleware.ServerStub.components.UnMarshall.Interfaces.IUnMarshall;

public interface IServerStubApplicationStub extends IReceiver, IRegister, IUnMarshall {
}
