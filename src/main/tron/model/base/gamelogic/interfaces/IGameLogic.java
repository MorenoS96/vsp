package tron.model.base.gamelogic.interfaces;

import tron.model.base.persistenz.Board;
import tron.model.base.persistenz.Player;

import java.util.List;

public interface IGameLogic {
    void startGameThread();

    void gametick();

    List<Player> initPlayers();

    int[] getPlayerStartingPositions();

    void moveEveryPlayer(char[] allInputs, Board board);

    void killPlayers(List<Player> players);

}
