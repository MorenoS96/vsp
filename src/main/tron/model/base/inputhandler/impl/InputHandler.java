package tron.model.base.inputhandler.impl;

import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IConfig;
import tron.controller.interfaces.IControllerModel;
import tron.registrator.impl.Registrator;
import tron.registrator.util.InterfaceType;

import java.util.Map;

public class InputHandler {

    Registrator registrator;

    Map<String,String> config;

    public InputHandler(Registrator registrator) {
        this.registrator = registrator;
        this.config = ((IControllerModel) registrator.getInterfaceOfType(InterfaceType.IControllerModel)).getConfig();
    }
}
