package tron.controller.factory;

import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IController;

public class ControllerFactory {

    IController controller;

    public IController getInstance(){
        if(controller==null){
            controller=new BasicController();
        }
        return controller;
    }
}
