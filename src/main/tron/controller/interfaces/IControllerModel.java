package tron.controller.interfaces;

import java.util.Map;

public interface IControllerModel {
   char[] getInputForCurrentCycle();
   Map<String,String> getConfig();
   void joinGame();
   //void endGame();
   int getPlayerCount();

}
