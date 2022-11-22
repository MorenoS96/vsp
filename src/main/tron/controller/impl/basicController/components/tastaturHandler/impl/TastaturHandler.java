package tron.controller.impl.basicController.components.tastaturHandler.impl;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;
import tron.controller.impl.basicController.components.configHandler.interfaces.IGetConfig;
import tron.controller.impl.basicController.components.tastaturHandler.interfaces.IGameKey;
import tron.controller.impl.basicController.components.tastaturHandler.interfaces.IGetInput;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

import tron.controller.util.ConfigHelper;
public class TastaturHandler implements IGetInput, IGameKey, GlobalKeyListener,KeyListener, NativeKeyListener {
    private HashSet<Character> mappedKeys;
    private HashMap<Character,Long> pressedKeys;
    private char lastInput;
    private String[] moveConfigNames;
    private IGetConfig iGetConfig;
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
        int moveDirections=4;
        for(int i=0;i< moveConfigNames.length;i+=moveDirections){
            char moveRight=iGetConfig.getConfigVal(moveConfigNames[i]).strip().toLowerCase().charAt(0);
            char moveLeft=iGetConfig.getConfigVal(moveConfigNames[i+1]).strip().toLowerCase().charAt(0);
            char moveUp=iGetConfig.getConfigVal(moveConfigNames[i+2]).strip().toLowerCase().charAt(0);
            char moveDown=iGetConfig.getConfigVal(moveConfigNames[i+3]).strip().toLowerCase().charAt(0);
            char[] moves=new char[]{moveRight,moveLeft,moveUp,moveDown};
            Long maxTime=0l;
            int maxTimeIndex=-1;
            for(int a=0;a<moves.length;a++){
                Long pressedKeyTime=pressedKeys.get(moves[a]);
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
        pressedKeys.clear();
        return arrayToReturn;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        handleKeyBoardChar(e.getKeyChar());
    }
    private void handleKeyBoardChar(char c){
        System.out.println("key typed "+String.valueOf(c));
        lastInput= c;
        if(mappedKeys.contains(Character.valueOf(lastInput))|| true){
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

    @Override
    public void keyPressed(GlobalKeyEvent globalKeyEvent) {
        handleKeyBoardChar( globalKeyEvent.getKeyChar());
    }

    @Override
    public void keyReleased(GlobalKeyEvent globalKeyEvent) {

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
        handleKeyBoardChar( nativeEvent.getKeyChar());
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        handleKeyBoardChar( nativeEvent.getKeyChar());
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {

    }
}
