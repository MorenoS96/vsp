package tron.test.controller;

import org.junit.jupiter.api.Test;
import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IControllerModel;
import tron.model.base.gamelogic.impl.GameLogic;
import tron.registrator.impl.Registrator;
import tron.view.interfaces.IViewModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestModel {

    Registrator registrator = new Registrator();
    IControllerModel iControllerModel = new BasicController(registrator);
    IViewModel iViewModel = null; // Später
    //GameLogic gameLogic = new GameLogic(iControllerModel,iViewModel);


    @Test
    public void gameLogicCorrectColorTest(){
        //assertEquals(gameLogic.getPlayerColor(0),"Blue");

    }

}


