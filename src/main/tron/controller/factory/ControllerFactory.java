package tron.controller.factory;

import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IController;
import tron.registrator.impl.Registrator;

public class ControllerFactory {

    IController controller;
    Registrator registrator;
    public ControllerFactory(Registrator registrator) {
        this.registrator=registrator;
    }

    public IController getInstance(){
        if(controller==null){
            controller=new BasicController(registrator);
        }
        return controller;
    }
}
