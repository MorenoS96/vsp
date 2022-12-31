package tron.controller.impl.basicController.components.tastaturHandler.impl;

import  tron.controller.impl.basicController.components.tastaturHandler.interfaces.*;

import  tron.controller.impl.basicController.components.configHandler.interfaces.*;
import tron.controller.util.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;


public class TastaturHandler implements IGetInput, IGameKey, KeyListener {
    private HashSet<Character> mappedKeys;
    private  HashMap<Character,Long> pressedKeys=new HashMap<>();

    private char lastInput;
    private String[] moveConfigNames;
    private IGetConfig iGetConfig;
    public TastaturHandler(IGetConfig iGetConfig) {
        mappedKeys  = new HashSet<>();

        moveConfigNames= ConfigHelper.getMoveConfigNames();
        this.iGetConfig=iGetConfig;
        reMapKeys();
    }
    @Override
    public void reMapKeys(){
        mappedKeys  = new HashSet<>();
        for (String moveConfigName : moveConfigNames) {
            String mappedKey = iGetConfig.getConfigVal(moveConfigName).trim().toLowerCase();
            mappedKeys.add(mappedKey.charAt(0));
        }
    }

    @Override
    public void pushInput(char c) {
        handleKeyBoardChar(c);
    }

    @Override
    public void clearInputs() {
        pressedKeys.clear();
    }


    @Override
    public char getInput() {
        return lastInput;
    }

    @Override
    public char[] getInputsForCurrentCycle() {
        char[] arrayToReturn=new char[6];
        int moveDirections=4;
        for(int i=0;i< moveConfigNames.length;i+=moveDirections){
            char moveRight=iGetConfig.getConfigVal(moveConfigNames[i]).trim().toLowerCase().charAt(0);
            char moveLeft=iGetConfig.getConfigVal(moveConfigNames[i+1]).trim().toLowerCase().charAt(0);
            char moveUp=iGetConfig.getConfigVal(moveConfigNames[i+2]).trim().toLowerCase().charAt(0);
            char moveDown=iGetConfig.getConfigVal(moveConfigNames[i+3]).trim().toLowerCase().charAt(0);
            char[] moves=new char[]{moveRight,moveLeft,moveUp,moveDown};
            Long maxTime=0l;
            int maxTimeIndex=-1;
            for(int a=0;a<moves.length;a++){
                Long pressedKeyTime=this.pressedKeys.get(moves[a]);
                if(pressedKeyTime!=null){
                    if(pressedKeyTime>maxTime){
                        maxTime=pressedKeyTime;
                        maxTimeIndex=a;
                    }
                }

            }
            if(maxTimeIndex>=0){
                arrayToReturn[i/moveDirections]=moves[maxTimeIndex];
            }else{
                arrayToReturn[i/moveDirections]=' ';
            }





        }

     return arrayToReturn;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        handleKeyBoardChar(e.getKeyChar());
    }
    private void  handleKeyBoardChar(char c){

        this.lastInput= c;
        if(mappedKeys.contains(c)){
            pressedKeys.put(lastInput,new Date().getTime());

        }



    }
    @Override
    public void keyPressed(KeyEvent e) {
        keyTyped(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}
