package tron.controller.impl.basicController.components.gameLoopManager.impl;
import tron.controller.impl.basicController.components.configHandler.interfaces.IGetConfig;
import tron.controller.impl.basicController.components.gameLoopManager.interfaces.IGameLoop;
import tron.model.interfaces.IModelController;

public class GameLoopManager implements IGameLoop {
    private int playerCount;
    IGetConfig iGetConfig;
    IModelController iModelController;
    public GameLoopManager(IGetConfig iGetConfig, IModelController iModelController) {
        this.iModelController=iModelController;
        this.iGetConfig=iGetConfig;
        playerCount=Integer.valueOf(iGetConfig.getConfigVal("defaultPlayerCount"));
    }
    @Override
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
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
