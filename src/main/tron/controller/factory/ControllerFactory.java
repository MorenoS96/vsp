package tron.controller.factory;

import tron.controller.basicController.composite.basicController;
import tron.controller.interfaces.IController;

public class ControllerFactory {

    IController controller;

    public IController getInstance(){
        if(controller==null){
            controller=new basicController();
        }
        return controller;
    }
}
