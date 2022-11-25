package tron.lobby.impl;

import tron.controller.impl.basicController.components.gameLoopManager.interfaces.IGameLoop;
import tron.lobby.interfaces.ILobby;
import tron.lobby.interfaces.IRegistrator;

import java.util.HashMap;
import java.util.Map;

public class BasicLobby implements ILobby {
    static Map<Integer, ILobby> lobbies=new HashMap<>();
    private int openPlayerCount=0;
    private int desiredPlayerCount=0;
    public IRegistrator iRegistrator;
    private int[] playerIds;

    public BasicLobby(int desiredPlayerCount, IRegistrator iRegistrator) {
        this.desiredPlayerCount = desiredPlayerCount;
        this.iRegistrator = iRegistrator;
        playerIds=new int[desiredPlayerCount];
    }


    @Override
    public int getOpenPlayerCount() {
        return openPlayerCount;
    }

    @Override
    public void addPlayer(int playerID) {

    }

    @Override
    public IRegistrator getIregistrator() {
        return iRegistrator;
    }


}
