package tron.lobby.interfaces;

public interface ILobby {


     int getOpenPlayerCount() ;
      void addPlayer(int playerID);
      IRegistrator getIregistrator();
}
