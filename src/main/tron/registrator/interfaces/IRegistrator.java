package tron.registrator.interfaces;


import tron.registrator.util.InterfaceType;

public interface IRegistrator {
   void registerComponent(InterfaceType interfaceType, Object obj);
   Object getInterfaceOfType(InterfaceType interfaceType);


}
