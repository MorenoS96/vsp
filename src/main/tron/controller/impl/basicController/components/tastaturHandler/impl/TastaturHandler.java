package tron.controller.impl.basicController.components.tastaturHandler.impl;
import tron.controller.impl.basicController.components.configHandler.interfaces.IGetConfig;
import tron.controller.impl.basicController.components.tastaturHandler.interfaces.IGameKey;
import tron.controller.impl.basicController.components.tastaturHandler.interfaces.IGetInput;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Date;
import tron.controller.util.ConfigHelper;
public class TastaturHandler implements IGetInput, IGameKey,KeyListener {
    private HashSet<Character> mappedKeys;
    private HashMap<Character,Long> pressedKeys;
    private char lastInput;
    private String[] moveConfigNames;
    private IGetConfig iGetConfig;
    TextArea area;
    public TastaturHandler(IGetConfig iGetConfig) {
        mappedKeys  = new HashSet<>();
        pressedKeys=new HashMap<>();
        moveConfigNames= ConfigHelper.getMoveConfigNames();
        this.iGetConfig=iGetConfig;
        reMapKeys();

    }
    @Override
    public void reMapKeys(){
        mappedKeys  = new HashSet<>();
        for (int i=0;i< moveConfigNames.length;i++){
            String mappedKey=iGetConfig.getConfigVal(moveConfigNames[i]).strip().toLowerCase();
            mappedKeys.add(mappedKey.charAt(0));
        }
    }


    @Override
    public char getInput() {
        return lastInput;
    }

    @Override
    public char[] getInputsForCurrentCycle() {
        char[] arrayToReturn=new char[6];
        for(int i=0;i< moveConfigNames.length;i+=2){


            Long timeMoveRight=pressedKeys.get(moveConfigNames[i].charAt(0));
            Long timeMoveLeft=pressedKeys.get(moveConfigNames[i+1].charAt(0));

            if(timeMoveLeft==null){
                if(timeMoveRight==null){//keine haben wert
                    arrayToReturn[i/2]=' ';
                }else{//nur rechts hat wert
                    arrayToReturn[i/2]=moveConfigNames[i+1].charAt(0);
                }
            }else{
                if(timeMoveRight==null){ //nur links hat wert
                    arrayToReturn[i/2]=moveConfigNames[i+1].charAt(0);
                }else{ //beide haben wert, nehm den neuesten
                    if(timeMoveRight>timeMoveLeft){
                        arrayToReturn[i/2]=moveConfigNames[i].charAt(0);
                    }else {
                        arrayToReturn[i/2]=moveConfigNames[i+1].charAt(0);
                    }
                }
            }

        }
        pressedKeys.clear();
        return arrayToReturn;
    }

    @Override
    public void keyTyped(KeyEvent e) {

        lastInput= e.getKeyChar();
        if(mappedKeys.contains(Character.valueOf(lastInput))|| true){
            pressedKeys.put(lastInput,new Date().getTime());
        }


    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyChar());


        keyTyped(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
