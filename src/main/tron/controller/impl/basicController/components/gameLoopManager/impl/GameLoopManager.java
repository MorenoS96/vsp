package tron.controller.impl.basicController.components.gameLoopManager.impl;
import tron.controller.impl.basicController.components.configHandler.interfaces.IGetConfig;
import tron.controller.impl.basicController.components.gameLoopManager.interfaces.IGameLoop;
import tron.lobby.interfaces.IRegistrator;
import tron.lobby.util.InterfaceType;
import tron.model.interfaces.IModelController;


public class GameLoopManager implements IGameLoop {
    private int playerCount;
    IGetConfig iGetConfig;
    IModelController iModelController;
    IRegistrator iRegistrator;
    public GameLoopManager(IRegistrator registrator, IGetConfig iGetConfig) {
        this.iModelController=(IModelController) registrator.getInterfaceOfType(InterfaceType.IModelController);
        this.iGetConfig=iGetConfig;
        playerCount=Integer.parseInt(iGetConfig.getConfigVal("defaultPlayerCount"));
    }
    @Override
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    @Override
    public void startApplication() throws InterruptedException {
        iModelController.startApplication();
    }

    @Override
    public int getPlayerCount() {
        return playerCount;
    }

    @Override
    public void startGame() {
        iModelController.startGame(playerCount);
    }
}
