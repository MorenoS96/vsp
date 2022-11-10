package tron.test.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import tron.controller.interfaces.IController;
import tron.controller.factory.ControllerFactory;

public class controllerTest {
    @Test
    void testIConfig(){
        ControllerFactory controllerFactory=new ControllerFactory();
      IController controller= controllerFactory.getInstance();
      String testConfigPath= "src/test/res/default_config.json";
      controller.setConfigPath(testConfigPath);
      assertEquals(controller.getConfigVal("player1MoveRight"),"e");
      assertEquals(controller.getConfigVal("velocityOfPlayers"),"1");
    }
    @Test
    void testIController(){

    }
    @Test
    void testIControllerModel(){

    }
    @Test
    void testIControllerView(){

    }
    @Test
    void testITastatur(){

    }
}
