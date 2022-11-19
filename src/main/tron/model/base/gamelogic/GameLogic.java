package tron.model.base.gamelogic;

import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IController;
import tron.controller.interfaces.IControllerModel;
import tron.model.base.persistenz.Board;
import tron.model.base.persistenz.BoardCell;
import tron.model.base.persistenz.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameLogic {

    boolean onePlayerRemaining = false;

    static int playerCount;

    List<Player> players;

    static BasicController basicController;

    static Map<String,String>  config = basicController.getConfig();


    public GameLogic() {
        playerCount = basicController.getPlayerCount(); // Wieso NullpointerExc
        players = initPlayers(playerCount);

    }

    public List<Player> initPlayers(int playerCount) {
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < playerCount; i++) {
            List<BoardCell> list = new ArrayList<>();
            Player player = new Player(i, getPlayerColor(i), getPlayerPosition(i), list);
            players.add(player);
        }
        return players;
    }

    public static String getPlayerColor(int id) {
        switch (id) {
            case 0:
                return "Blue";
            case 1:
                return "Red";
            case 2:
                return "Green";
            case 3:
                return "Yellow";
            case 4:
                return "Purple";
            case 5:
                return "Orange";
            default:
                System.out.println("Keine passende Farbe gefunden");
                return null;
        }
    }

    // Evtl später noch check, ob Spielfeld groß genug ist für die Anzahl an Spielern, nur nötig, wenn runtergestellt.
    public static BoardCell getPlayerPosition(int id) {
        String color = getPlayerColor(id);
        int FieldX = Integer.parseInt(config.get("horizontalRasterPoints"));
        int xPos = FieldX/playerCount;
        return new BoardCell(xPos,0,color);
    }

    public static boolean checkCollision(int id, Board currentBoard) {
        return false;
    }

    public static boolean checkBoarder(int id) {
        return false;
    }

    // Methode zur Ermittlung der Startposition
}
