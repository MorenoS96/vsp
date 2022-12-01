package tron.controller.impl.basicController.components.gameLoopManager.impl;
import tron.controller.impl.basicController.components.configHandler.interfaces.IGetConfig;
import tron.controller.impl.basicController.components.gameLoopManager.interfaces.IGameLoop;
import tron.lobby.impl.BasicLobby;
import tron.lobby.impl.BasicLobbyManager;
import tron.lobby.impl.BasicLobyManagerFactory;
import tron.lobby.interfaces.ILobby;
import tron.lobby.interfaces.ILobbyManager;
import tron.lobby.interfaces.ILobbyManagerFactory;
import tron.lobby.interfaces.IRegistrator;
import tron.model.interfaces.IModelController;
import tron.registrator.interfaces.IRegistrator;
import tron.registrator.util.InterfaceType;

public class GameLoopManager implements IGameLoop {
    private int playerCount;
    IGetConfig iGetConfig;
    IModelController iModelController;
    IRegistrator iRegistrator;
    public GameLoopManager(IRegistrator registrator,IGetConfig iGetConfig) {
        this.iModelController=(IModelController) registrator.getInterfaceOfType(InterfaceType.IModelController);
        this.iGetConfig=iGetConfig;
        playerCount=Integer.valueOf(iGetConfig.getConfigVal("defaultPlayerCount"));
        this.iRegistrator=iRegistrator;
    }
    @Override
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    @Override
    public ILobby joinGame(int playerID) {
        ILobbyManagerFactory iLobbyManagerFactory=new BasicLobyManagerFactory();
        ILobbyManager iLobbyManager=iLobbyManagerFactory.getInstance();
        return iLobbyManager.joinLobby(playerCount,playerID);
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
