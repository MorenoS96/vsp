package tron.controller.impl.basicController.components.tastaturHandler.interfaces;

import lc.kra.system.keyboard.event.GlobalKeyListener;

public interface IGameKey  {
 char[] getInputsForCurrentCycle();
 void reMapKeys();
 void pushInput(char c);
}
