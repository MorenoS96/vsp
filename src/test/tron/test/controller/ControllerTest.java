package tron.test.controller;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;
import tron.controller.interfaces.IController;
import tron.controller.factory.ControllerFactory;
import tron.controller.interfaces.IControllerView;
import tron.registrator.impl.Registrator;
import tron.registrator.util.InterfaceType;


public class ControllerTest {
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
    public void testIControllerModel()  {
        Registrator registrator=new Registrator();
        ControllerFactory controllerFactory=new ControllerFactory(registrator);
        IController controller= controllerFactory.getInstance();
        String testConfigPath= "src/test/res/default_config.json";
        controller.setConfigPath(testConfigPath);
        IControllerView iControllerView=(IControllerView)registrator.getInterfaceOfType(InterfaceType.IControllerView);



        char[] inputs=new char[]{'e','e','q','z','t','z',' ','i',' ',' ','h',','};
        for (char input : inputs) {
            iControllerView.pushKeyboardInput(input);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        char[] inputsForCurrentCycle= controller.getInputForCurrentCycle();
        assertArrayEquals(new char[]{'q','z','i',' ',' ',','},inputsForCurrentCycle);
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
