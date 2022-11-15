package tron.controller.impl.basicController.composite;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import tron.controller.impl.basicController.components.configHandler.impl.ConfigHandler;
import tron.controller.impl.basicController.components.configHandler.interfaces.*;
import tron.controller.impl.basicController.components.factory.impl.TastaturHandlerFactory;
import tron.controller.impl.basicController.components.factory.interfaces.IFactory;
import tron.controller.impl.basicController.components.tastaturHandler.impl.*;
import tron.controller.impl.basicController.components.tastaturHandler.interfaces.*;
import tron.controller.interfaces.*;

import java.awt.event.KeyEvent;
import java.util.Map;

public class BasicController implements IController {
    IGetConfig iGetConfig;
    IGetInput iGetInput;
    IGameKey iGameKey;
    IFactory<TastaturHandler> tastaturHandlerIFactory;
    TastaturHandler tastaturHandler;

    public BasicController() {
        this.iGetConfig = new ConfigHandler();
        tastaturHandlerIFactory=new TastaturHandlerFactory(iGetConfig);
        this.iGetInput = tastaturHandlerIFactory.getInstance();
        iGameKey=tastaturHandlerIFactory.getInstance();
        tastaturHandler= tastaturHandlerIFactory.getInstance();
        /*
        Achtung funktioniert nur auf windows!
         */

        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(false);
        keyboardHook.addKeyListener(tastaturHandler);

    }
    @Override
    public String getConfigVal(String ConfigName) {
        return iGetConfig.getConfigVal(ConfigName);
    }

    @Override
    public void setConfigPath(String ConfigPath) {
        iGetConfig.setConfigPath(ConfigPath);
        iGameKey.reMapKeys();
    }

    @Override
    public char getInput() {
        return iGetInput.getInput();
    }

    @Override
    public char[] getInputForCurrentCycle() {
        return iGameKey.getInputsForCurrentCycle();
    }

    @Override
    public Map<String, String> getConfig() {
        return iGetConfig.getConfigMap();
    }

    @Override
    public void joinGame() {

    }
}
