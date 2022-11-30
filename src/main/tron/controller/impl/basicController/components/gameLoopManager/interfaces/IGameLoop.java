package tron.controller.impl.basicController.components.gameLoopManager.interfaces;

import tron.lobby.interfaces.ILobby;

public interface IGameLoop {
    int getPlayerCount();
    void startGame();
    void setPlayerCount(int playerCount);
    ILobby joinGame(int playerID);
}
