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
    private HashMap<String,Long> pressedKeys;
    private String lastInput="";
    private String[] moveConfigNames;
    public TastaturHandler(IGetConfig iGetConfig) {
        mappedKeys  = new HashSet<>();
        pressedKeys=new HashMap<>();
        moveConfigNames= ConfigHelper.getMoveConfigNames();
       for (int i=0;i< moveConfigNames.length;i++){
           String mappedKey=iGetConfig.getConfigVal(moveConfigNames[i]);
           mappedKeys.add(mappedKey);
       }
    }

    @Override
    public String getInput() {
        return lastInput;
    }

    @Override
    public String[] getInputsForCurrentCycle() {
        String[] arrayToReturn=new String[3];
        for(int i=0;i< moveConfigNames.length;i++){
            if(i%2!=0){
                continue;
            }

            Long timeMoveRight=pressedKeys.get(moveConfigNames[i]);
            Long timeMoveLeft=pressedKeys.get(moveConfigNames[i+1]);

            if(timeMoveLeft==null){
                if(timeMoveRight==null){//keine haben wert
                    arrayToReturn[i/2]="";
                }else{//nur rechts hat wert
                    arrayToReturn[i/2]=moveConfigNames[i];
                }
            }else{
                if(timeMoveRight==null){ //nur links hat wert
                    arrayToReturn[i/2]=moveConfigNames[i+1];
                }else{ //beide haben wert, nehm den neuesten
                    if(timeMoveRight>timeMoveLeft){
                        arrayToReturn[i/2]=moveConfigNames[i];
                    }else {
                        arrayToReturn[i/2]=moveConfigNames[i+1];
                    }
                }
            }

        }
        pressedKeys.clear();
        return arrayToReturn;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        String strVal= String.valueOf(e.getKeyChar());
        if(mappedKeys.contains(strVal)){
            pressedKeys.put(strVal,new Date().getTime());
        }
        lastInput=strVal;

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyTyped(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
