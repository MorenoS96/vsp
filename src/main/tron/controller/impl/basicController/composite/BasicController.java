package tron.controller.impl.basicController.composite;
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
import tron.lobby.interfaces.IRegistrator;
import tron.lobby.util.InterfaceType;


import java.util.Map;

public class BasicController implements IController {
    IGetConfig iGetConfig;
    IGetInput iGetInput;
    IGameKey iGameKey;
    IFactory<TastaturHandler> tastaturHandlerIFactory;
    TastaturHandler tastaturHandler;
    IClick iClick;
    IGameLoop iGameLoop;
    public BasicController(IRegistrator registrator) {
        this.iGetConfig = new ConfigHandler();
        tastaturHandlerIFactory=new TastaturHandlerFactory(iGetConfig);

        this.iGetInput = tastaturHandlerIFactory.getInstance();
        iGameKey=tastaturHandlerIFactory.getInstance();
        tastaturHandler= tastaturHandlerIFactory.getInstance();
        iGameLoop=new GameLoopManager(registrator,iGetConfig);
        iClick=new ClickHandler(iGameLoop);
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
    public void endGame() {
        iGameLoop.endGame();
        iGameKey.clearInputs();
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
    public void startApplication() throws InterruptedException {
    iGameLoop.startApplication();
    }

    @Override
    public void pushClick(String elementIdentifier) {
        iClick.pushClick(elementIdentifier);

    }

    @Override
    public void pushInput(String elementIdentifier, String input) {
        iClick.pushInput(elementIdentifier,input);
    }

    @Override
    public void pushKeyboardInput(char input) {
        iGameKey.pushInput(input);
    }

    @Override
    public Map<String, String> getConfigMap(String filePath) {
        this.iGetConfig.setConfigPath(filePath);
        return this.iGetConfig.getConfigMap();
    }
}
