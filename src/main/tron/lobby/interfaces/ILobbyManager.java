package tron.lobby.interfaces;

import tron.controller.impl.basicController.components.factory.interfaces.IFactory;

public interface ILobbyManager {
    /*
 let's players join into lobby,
 a lobby manages it's model, view and controller
 there can be multiple lobbies each lobby has it's own model, view and controller
 */
    ILobby joinLobby(int playerCount,int playerID);

}
