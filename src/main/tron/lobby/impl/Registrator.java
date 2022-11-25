package tron.lobby.impl;

import tron.lobby.interfaces.IRegistrator;
import tron.lobby.util.InterFaceTypeToInterfaceHelper;
import tron.lobby.util.InterfaceType;

import java.util.HashMap;
import java.util.Map;

public class Registrator implements IRegistrator {
    Map<InterfaceType,Object> interfaceTypeObjectMap;
    InterFaceTypeToInterfaceHelper interFaceTypeToInterfaceHelper;

    public Registrator() {
        this.interfaceTypeObjectMap = new HashMap<>();
        interFaceTypeToInterfaceHelper=new InterFaceTypeToInterfaceHelper();
    }

    @Override
    public void registerComponent(InterfaceType interfaceType, Object obj) {
        if(interFaceTypeToInterfaceHelper.isObjectCompatible(obj,interfaceType)){
            interfaceTypeObjectMap.put(interfaceType,obj);
        }
    }

    @Override
    public Object getInterfaceOfType(InterfaceType interfaceType) {
        return interfaceTypeObjectMap.get(interfaceType);
    }


}
