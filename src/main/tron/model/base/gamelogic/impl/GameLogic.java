package tron.model.base.gamelogic.impl;

import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IControllerModel;
import tron.model.base.inputhandler.interfaces.IInputHandler;
import tron.model.base.persistenz.Board;
import tron.model.base.persistenz.BoardCell;
import tron.model.base.persistenz.Coordinate;
import tron.model.base.persistenz.Player;
import tron.view.interfaces.IViewModel;

import java.util.ArrayList;
import java.util.List;

// Wird noch IGameLogic implementieren
public class GameLogic implements IInputHandler, Runnable {

    boolean onePlayerRemaining = false;

    List<Player> players;

    public Thread gameThread;

    public Board board;

    IControllerModel iControllerModel;

    IViewModel iViewModel;

    public GameLogic(IControllerModel iControllerModel,IViewModel iViewModel) {
        players = initPlayers(); //playerCount ok in Controller oder Model?
        this.board = new Board(HEIGHT, WIDTH);
        this.iControllerModel = iControllerModel;
        this.iViewModel = iViewModel;
        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {
        // Soll eine Runde simulieren
        long lastRound = System.nanoTime();
        double nsTicks = 1000000000/60.0;
        double delta = 0;
        while (true)  { // Später noch ersetzten
            long now = System.nanoTime();
            long timePassed = now - lastRound;
            delta += timePassed/nsTicks;
            lastRound = now;
            if(delta >= 1) {
                moveEveryPlayer(iControllerModel.getInputForCurrentCycle(),board);
                iViewModel.displayBoard(board,players);
            }
        }
    }

    public List<Player> initPlayers() {
        List<Player> players = new ArrayList<>();
        Coordinate[] startingPositions = getPlayerStartingPositions(WIDTH);

        for (int i = 0; i < PLAYER_COUNT; i++) {
            // id der Zelle soll == position der Array entsprechen
            String playerColor = getPlayerColor(i);
            int boardCellId = (HEIGHT - 1) * WIDTH + startingPositions[i].getX() + 1;
            BoardCell staringPosition = new BoardCell(startingPositions[i].getX(), startingPositions[i].getY(), boardCellId, playerColor);
            List<BoardCell> paintedCells = new ArrayList<>();

            Player player = new Player(i, playerColor, staringPosition, paintedCells);
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
     *
     * @param horizontalRasterPoints Wie viele x Positionen das Board hat
     * @return Alle Koordinaten wo Spieler starten können
     */
    public static Coordinate[] getPlayerStartingPositions(int horizontalRasterPoints) {
        Coordinate[] coordinates = new Coordinate[PLAYER_COUNT];
        int posibleXCoord = horizontalRasterPoints / (PLAYER_COUNT + 2);

        for (int i = 2; i < PLAYER_COUNT + 2; i++) {
            int x = i * posibleXCoord;
            coordinates[i] = new Coordinate(x - 1, HEIGHT - 1); //Wenn unsere Cells mit 1 anfangen das -1 weg
        }
        return coordinates;
    }

    /**
     * Ist "" wenn nicht gefärbt sonst sollte da eine Farbe drin stehen
     *
     * @param nextBoardCell Nächsten Zelle
     * @return true, wenn nächste Zelle schon gefärbt ist, false, wenn nicht
     */
    public static boolean checkCollision(BoardCell nextBoardCell) {
        return !nextBoardCell.getColor().isEmpty();
    }

    /**
     * Wenn der nächste Block über das Raster hinausgeht, wird true ausgegeben sonst false.
     * Diesen check zuerst ausführen damit die anderen keine Zellen abfragen die nicht existieren dürfen.
     *
     * @param cellId    von welcher Zelle aus gecheckt werden soll
     * @param direction in welche Richtung man sich bewegt
     * @return true oder false
     */
    public static boolean checkBorder(int cellId, String direction) {
        int nextCellId;
        boolean nextCellIsBorder = false;
        switch (direction) {
            case "moveUp":
                nextCellId = cellId + WIDTH;
                nextCellIsBorder = nextCellId <= (WIDTH * HEIGHT - 1); // Nächste ZellenId muss kleiner= sein als max Zellen ID
                break;
            case "moveDown":
                nextCellId = cellId - WIDTH;
                nextCellIsBorder = nextCellId >= 0; // Nächste ZellenId muss größer= sein als 0
                break;
            case "moveRight":
                nextCellIsBorder = (cellId + 1 % WIDTH == 0); // Wenn Zelle am rechten Rand, dann nicht nach rechts bewegen.
                break;
            case "moveLeft":
                nextCellIsBorder = (cellId % WIDTH == 0); // Wenn Zelle ganz links, dann nicht nach links bewegen.
                break;
        }
        return nextCellIsBorder;
    }

    public void moveEveryPlayer(char[] allInputs, Board board) {
        for (int i = 0; i < PLAYER_COUNT; i++) {
            if (players.get(i).isAlive()) {
                players.get(i).move(allInputs[i], board);
            }
        }
    }
}
