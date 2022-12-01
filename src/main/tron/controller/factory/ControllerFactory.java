package tron.controller.factory;

import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IController;

import tron.lobby.interfaces.IRegistrator;

public class ControllerFactory {

    IController controller;
    IRegistrator registrator;
    public ControllerFactory(IRegistrator registrator) {
        this.registrator=registrator;
    }

    public IController getInstance(){
        if(controller==null){
            controller=new BasicController(registrator);
        }
        return controller;
    }
}
