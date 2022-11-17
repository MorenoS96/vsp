package tron.test.controller;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.awt.*;
import java.awt.Robot.*;
import java.awt.event.KeyEvent;

import org.junit.jupiter.api.Test;
import tron.controller.interfaces.IController;
import tron.controller.factory.ControllerFactory;
import tron.registrator.impl.Registrator;

import javax.swing.*;

public class controllerTest {
    @Test
  public void testIConfig(){
        Registrator registrator=new Registrator();
        ControllerFactory controllerFactory=new ControllerFactory(registrator);
      IController controller= controllerFactory.getInstance();
      String testConfigPath= "src/test/res/default_config.json";
      controller.setConfigPath(testConfigPath);
      assertEquals(controller.getConfigVal("player1MoveRight"),"e");
      assertEquals(controller.getConfigVal("velocityOfPlayers"),"1");
    }
    @Test
    public void testIController(){

    }


       /*
        Achtung nur auf windows getestet
        Achtung der test schreibt, der cursor sollte also in der console sein oder in einem Kommentar

         */

    @Test
    public void testIControllerModel(){
        Registrator registrator=new Registrator();
        ControllerFactory controllerFactory=new ControllerFactory(registrator);
        IController controller= controllerFactory.getInstance();
        String testConfigPath= "src/test/res/default_config.json";
        controller.setConfigPath(testConfigPath);
        boolean run=true;
        try {
            Robot robot =new Robot();



            char[] inputs=new char[]{'e','e','q','z','t','z',' ','i',' ',' '};
            for(int i=0;i<inputs.length;i++){
               robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(inputs[i]));
               robot.delay(1);
                robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(inputs[i]));
            }
            char[] inputsForCurrentCycle= controller.getInputForCurrentCycle();
            assertArrayEquals(new char[]{'q','z','i',' ',' ',' '},inputsForCurrentCycle);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIControllerView(){
        Registrator registrator=new Registrator();
        ControllerFactory controllerFactory=new ControllerFactory(registrator);
        IController iController=controllerFactory.getInstance();
        iController.pushInput("changePlayerCount","3");
        assertEquals(3,iController.getPlayerCount());
    }
    @Test
    public void testITastatur(){

    }
}
