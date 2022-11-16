package tron.controller.impl.basicController.components.gameLoopManager.interfaces;

public interface IGameLoop {
    int getPlayerCount();
    void startGame();
    void setPlayerCount(int playerCount);
}
