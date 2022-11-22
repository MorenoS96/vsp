package tron.controller.impl.basicController.composite;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import tron.controller.impl.basicController.components.clickHandler.impl.ClickHandler;
import tron.controller.impl.basicController.components.clickHandler.interfaces.IClick;
import tron.controller.impl.basicController.components.configHandler.impl.ConfigHandler;
import tron.controller.impl.basicController.components.configHandler.interfaces.*;
import tron.controller.impl.basicController.components.factory.impl.TastaturHandlerFactory;
import tron.controller.impl.basicController.components.factory.interfaces.IFactory;
import tron.controller.impl.basicController.components.gameLoopManager.impl.GameLoopManager;
import tron.controller.impl.basicController.components.gameLoopManager.interfaces.IGameLoop;
import tron.controller.impl.basicController.components.tastaturHandler.impl.*;
import tron.controller.impl.basicController.components.tastaturHandler.interfaces.*;
import tron.controller.interfaces.*;
import tron.controller.util.OsUtil;
import com.github.kwhat.jnativehook.GlobalScreen;
import tron.model.interfaces.IModelController;
import tron.registrator.impl.Registrator;
import tron.registrator.util.InterfaceType;

import java.util.Map;

public class BasicController implements IController {
    IGetConfig iGetConfig;
    IGetInput iGetInput;
    IGameKey iGameKey;
    IFactory<TastaturHandler> tastaturHandlerIFactory;
    TastaturHandler tastaturHandler;
    IClick iClick;
    IGameLoop iGameLoop;
    public BasicController(Registrator registrator) {
        this.iGetConfig = new ConfigHandler();
        tastaturHandlerIFactory=new TastaturHandlerFactory(iGetConfig);

        this.iGetInput = tastaturHandlerIFactory.getInstance();
        iGameKey=tastaturHandlerIFactory.getInstance();
        tastaturHandler= tastaturHandlerIFactory.getInstance();
        iGameLoop=new GameLoopManager(iGetConfig,(IModelController) registrator.getInterfaceOfType(InterfaceType.IModelController));
        iClick=new ClickHandler(iGameLoop);
        if(OsUtil.getOS().equals( OsUtil.OS.WINDOWS)){
            GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(false);
            keyboardHook.addKeyListener(tastaturHandler);

        }else {
            GlobalScreen.addNativeKeyListener(tastaturHandler);
        }
        registrator.registerComponent(InterfaceType.IControllerModel,this);
        registrator.registerComponent(InterfaceType.IControllerView,this);
        registrator.registerComponent(InterfaceType.IConfig,this);
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

    @Override
    public void startGame(int playerCount) {
        iClick.pushInput("changePlayerCount",String.valueOf(playerCount));
        iGameLoop.startGame();
    }

    @Override
    public int getPlayerCount() {
        return iGameLoop.getPlayerCount();
    }

    @Override
    public void pushClick(String elementIdentifier) {
        iClick.pushClick(elementIdentifier);

    }

    @Override
    public void pushInput(String elementIdentifier, String input) {
        iClick.pushInput(elementIdentifier,input);
    }
}
