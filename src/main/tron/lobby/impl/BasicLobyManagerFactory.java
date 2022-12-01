package tron.lobby.impl;

import tron.lobby.interfaces.ILobbyManager;
import tron.lobby.interfaces.ILobbyManagerFactory;

public class BasicLobyManagerFactory implements ILobbyManagerFactory {

        static ILobbyManager iLobbyManager=null;
        @Override
        public ILobbyManager getInstance() {
            if (iLobbyManager == null) {
                iLobbyManager = new BasicLobbyManager();
            }
            return iLobbyManager;
        }


}
