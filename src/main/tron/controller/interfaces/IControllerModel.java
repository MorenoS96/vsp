package tron.controller.interfaces;

import java.util.Map;

public interface IControllerModel {
   char[] getInputForCurrentCycle();
   Map<String,String> getConfig();
   void joinGame();
   void startGame(int playerCount);
   int getPlayerCount();

}
