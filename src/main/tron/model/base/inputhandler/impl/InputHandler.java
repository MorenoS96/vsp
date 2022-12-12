package tron.model.base.inputhandler.impl;

import tron.controller.interfaces.IControllerModel;
import tron.lobby.impl.Registrator;
import tron.lobby.util.InterfaceType;

import java.util.Map;

public class InputHandler {

    Registrator registrator;

    Map<String,String> config;

    public InputHandler(Registrator registrator) {
        this.registrator = registrator;
        this.config = ((IControllerModel) registrator.getInterfaceOfType(InterfaceType.IControllerModel)).getConfig();
    }

    public char[] getII() {
        return  ((IControllerModel) registrator.getInterfaceOfType(InterfaceType.IControllerModel)).getInputForCurrentCycle();
    }
}
