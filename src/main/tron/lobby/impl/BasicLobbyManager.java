package tron.lobby.impl;

import tron.lobby.interfaces.ILobby;
import tron.lobby.interfaces.ILobbyManager;
import tron.lobby.interfaces.IRegistrator;

import java.util.HashMap;
import java.util.Map;

public class BasicLobbyManager implements ILobbyManager {
    static Map<Integer, ILobby> lobbies = new HashMap<>();

    BasicLobbyManager(){

    }
    @Override
    public ILobby joinLobby(int playerCount, int playerID) {
        ILobby toReturn = null;
        Map.Entry<Integer, ILobby> entry = lobbies.entrySet().stream()
                .filter(integerILobbyEntry -> integerILobbyEntry.getKey() == playerCount)
                .filter(integerILobbyEntry -> integerILobbyEntry.getValue().getOpenPlayerCount() < integerILobbyEntry.getKey())
                .findAny().orElse(null);
        if (entry != null) {
            toReturn = entry.getValue();
            toReturn.addPlayer(playerID);
        } else {
            IRegistrator iRegistrator = new Registrator();
            toReturn = new BasicLobby(playerCount, iRegistrator);
        }
        return toReturn;
    }


}
