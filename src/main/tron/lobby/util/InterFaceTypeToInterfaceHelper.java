package tron.lobby.util;

import tron.controller.interfaces.IConfig;
import tron.controller.interfaces.IControllerModel;
import tron.controller.interfaces.IControllerView;
import tron.model.interfaces.IModelController;
import tron.view.interfaces.IViewModel;

import java.util.HashMap;

public class InterFaceTypeToInterfaceHelper {
     private HashMap<InterfaceType,Class> interfaceTypeClassHashMap;

    public InterFaceTypeToInterfaceHelper() {
        interfaceTypeClassHashMap=new HashMap<>();
        interfaceTypeClassHashMap.put(InterfaceType.IControllerModel, IControllerModel.class);
        interfaceTypeClassHashMap.put(InterfaceType.IControllerView, IControllerView.class);
        interfaceTypeClassHashMap.put(InterfaceType.IModelController, IModelController.class);
        interfaceTypeClassHashMap.put(InterfaceType.IViewModel, IViewModel.class);
        interfaceTypeClassHashMap.put(InterfaceType.IConfig, IConfig.class);

    }


    public boolean isObjectCompatible(Object object, InterfaceType interfaceType) {
        Class toCheck=interfaceTypeClassHashMap.get(interfaceType);
        return toCheck.isInstance(object);
    }


}
