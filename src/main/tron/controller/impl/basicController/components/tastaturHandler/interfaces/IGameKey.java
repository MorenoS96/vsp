package tron.controller.impl.basicController.components.tastaturHandler.interfaces;



public interface IGameKey  {
 char[] getInputsForCurrentCycle();
 void reMapKeys();
 void pushInput(char c);
 void clearInputs();
}
