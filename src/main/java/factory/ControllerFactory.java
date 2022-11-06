package java.factory;

import java.controller.impl.working.basicController;
import java.controller.interfaces.IController;

public class ControllerFactory {

    IController controller;

    public IController getInstance(){
        if(controller==null){
            controller=new basicController();
        }
        return controller;
    }
}
