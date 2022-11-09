package tron.controller.impl.basicController.components.tastaturHandler.impl;
import tron.controller.impl.basicController.components.configHandler.interfaces.IGetConfig;
import tron.controller.impl.basicController.components.tastaturHandler.interfaces.IGameKey;
import tron.controller.impl.basicController.components.tastaturHandler.interfaces.IGetInput;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Date;
import tron.controller.util.ConfigHelper;
public class TastaturHandler implements IGetInput, IGameKey,KeyListener {
    private HashSet<String> mappedKeys;
    private HashSet<String> pressedKeys;

    public TastaturHandler(IGetConfig iGetConfig) {
        mappedKeys  = new HashSet<>();
        pressedKeys=new HashSet<>();
       String[] moveConfigNames= ConfigHelper.getMoveConfigNames();
       for (int i=0;i< moveConfigNames.length;i++){
           String mappedKey=iGetConfig.getConfigVal(moveConfigNames[i]);
           mappedKeys.add(mappedKey);
       }
    }

    @Override
    public String getInput() {
        return null;
    }

    @Override
    public String[] getInputsForCurrentCycle() {

        return new String[0];
    }

    @Override
    public void keyTyped(KeyEvent e) {
        String strVal= String.valueOf(e.getKeyChar());
        if(mappedKeys.contains(strVal)){

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
