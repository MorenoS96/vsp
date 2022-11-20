package tron.model.base.gamelogic;

import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IController;
import tron.controller.interfaces.IControllerModel;
import tron.model.base.persistenz.Board;
import tron.model.base.persistenz.BoardCell;
import tron.model.base.persistenz.Coordinate;
import tron.model.base.persistenz.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameLogic {

    boolean onePlayerRemaining = false;

    static int playerCount;

    List<Player> players;

    IControllerModel iControllerModel;
    static Map<String,String>  config;


    public GameLogic(IControllerModel iControllerModel) {
        this.iControllerModel = iControllerModel;
        config = iControllerModel.getConfig();
        playerCount = iControllerModel.getPlayerCount();
        players = initPlayers(playerCount); //playerCount ok in Controller oder Model?

    }

    public List<Player> initPlayers(int playerCount) {
        List<Player> players = new ArrayList<>();
        int horizontalRasterPoints = Integer.parseInt(config.get("horizontalRasterPoints"));
        Coordinate[] startingPositions = getPlayerStartingPositions(horizontalRasterPoints);

        for (int i = 0; i < playerCount; i++) {
            // id der Zelle soll == position der Array entsprechen
            String playerColor = getPlayerColor(i);
            int yCoord = Integer.parseInt(config.get("verticalRasterPoints"));
            int boardCellId = (yCoord-1) * horizontalRasterPoints + startingPositions[i].getX()+1;
            BoardCell staringPosition = new BoardCell(startingPositions[i].getX(),startingPositions[i].getY(),boardCellId,playerColor);
            List<BoardCell> paintedCells = new ArrayList<>();

            //
            String moveUpKey = "player" + i+1 + "MoveUp";
            String moveUpString = config.get(moveUpKey);
            char moveUp = moveUpString.charAt(0);

            String moveDownKey = "player" + i+1 + "MoveDown";
            String moveDownString = config.get(moveDownKey);
            char moveDown = moveDownString.charAt(0);

            String moveLeftKey = "player" + i+1 + "MoveLeft";
            String moveLeftString = config.get(moveLeftKey);
            char moveLeft = moveLeftString.charAt(0);

            String moveRightKey = "player" + i+1 + "MoveRight";
            String moveRightString = config.get(moveRightKey);
            char moveRight = moveRightString.charAt(0);

            Player player = new Player(i, playerColor,staringPosition , paintedCells,moveUp,moveDown,moveLeft,moveRight);
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

    /**
     * Die Berechnung soll so funktionieren, dass wir die Spieleranzahl+2 durch die Boardlänge (x Anzahl) teilen,
     * und dann die mittleren Positionen als Startkoordianten für die Spieler nutzen.
     * @param horizontalRasterPoints Wie viele x Positionen das Board hat
     * @return Alle Koordinaten wo Spieler starten können
     */
    public static Coordinate[] getPlayerStartingPositions(int horizontalRasterPoints) {
        Coordinate[] coordinates = new Coordinate[playerCount];
        int yCoord = Integer.parseInt(config.get("verticalRasterPoints"));
        int posibleXCoord = horizontalRasterPoints/(playerCount+2);

        for(int i=2;i<playerCount+2;i++) {
            int x = i * posibleXCoord;
            coordinates[i] = new Coordinate(x-1,yCoord-1); //Wenn unsere Cells mit 1 anfangen das -1 weg
        }
        return coordinates;
    }

    public static boolean checkCollision(int id, Board currentBoard) {
        return false;
    }

    public static boolean checkBoarder(int id) {
        return false;
    }

    public void moveEveryPlayer(char[] allInputs,Board board) {
        for(int i=0;i<playerCount;i++) {
            players.get(i).move(allInputs[i], board);
        }
    }
}
