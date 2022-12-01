package tron.lobby.interfaces;


import tron.lobby.util.InterfaceType;

public interface IRegistrator {
   void registerComponent(InterfaceType interfaceType, Object obj);
   Object getInterfaceOfType(InterfaceType interfaceType);


}
