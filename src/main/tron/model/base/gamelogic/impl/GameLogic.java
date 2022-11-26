package tron.model.base.gamelogic.impl;

import tron.controller.interfaces.IControllerModel;
import tron.model.base.inputhandler.interfaces.IInputHandler;
import tron.model.base.persistenz.Board;
import tron.model.base.persistenz.BoardCell;
import tron.model.base.persistenz.Player;
import tron.view.interfaces.IViewModel;

import java.util.*;


// Wird noch IGameLogic implementieren
public class GameLogic implements IInputHandler {

    boolean onePlayerRemaining = false; // Kommt noch was zu

    List<Player> players;

    public Thread gameThread;

    public Board board;

    IControllerModel iControllerModel;

    IViewModel iViewModel;

    int delay;
    public GameLogic(IControllerModel iControllerModel, IViewModel iViewModel) {
        this.board = new Board(HEIGHT, WIDTH);
        players = initPlayers(); //playerCount ok in Controller oder Model?

        this.iControllerModel = iControllerModel;
        this.iViewModel = iViewModel;
        Timer timer = new Timer();
        // 1/ticks sekunden
        // GameLoop drin

    }

//    @Override
//    public void run() {
//        // Soll eine Runde simulieren
//        long lastRound = System.nanoTime();
//        double nsTicks = 1000000000 / 60.0;
//        double delta = 0;
//        while (true) { // Später noch ersetzten
//            long now = System.nanoTime();
//            long timePassed = now - lastRound;
//            delta += timePassed / nsTicks;
//            lastRound = now;
//            if (delta >= 1) {
//                moveEveryPlayer(iControllerModel.getInputForCurrentCycle(), board);
//                iViewModel.displayBoard(board, players);
//            }
//        }
//    }

    public List<Player> initPlayers() {
        List<Player> players = new ArrayList<>();
        int[] startPositionIds = getPlayerStartingPositions();

        for (int i = 1; i <= PLAYER_COUNT; i++) {
            // id der Zelle soll == position der Array entsprechen
            String playerColor = getPlayerColor(i);

            List<BoardCell> paintedCells = new ArrayList<>(); // Könnte hier raus

            //System.out.println(board);

            //System.out.println(board.getCellById(startPositionIds[i-1]));

            String moveUpKey = "player" + i + "MoveUp";
            String moveUpString = config.get(moveUpKey);
            char moveUp = moveUpString.charAt(0);

            String moveDownKey = "player" + i + "MoveDown";
            String moveDownString = config.get(moveDownKey);
            char moveDown = moveDownString.charAt(0);

            String moveLeftKey = "player" + i + "MoveLeft";
            String moveLeftString = config.get(moveLeftKey);
            char moveLeft = moveLeftString.charAt(0);

            String moveRightKey = "player" + i + "MoveRight";
            String moveRightString = config.get(moveRightKey);
            char moveRight = moveRightString.charAt(0);

            Player player = new Player(i,playerColor,board.getCellById(startPositionIds[i-1]),paintedCells,moveUp,moveDown,moveLeft,moveRight,true);

            //System.out.println(player.getCurrentCell());
            player.setCurrentCellColor(playerColor);
            players.add(player);
        }
        return players;
    }

    public  String getPlayerColor(int id) { // throws Fehler?
        switch (id) {
            case 1:
                return "Blue";
            case 2:
                return "Red";
            case 3:
                return "Green";
            case 4:
                return "Yellow";
            case 5:
                return "Purple";
            case 6:
                return "Orange";
            default:
                System.out.println("Keine passende Farbe gefunden bzw zu viele Spieler");
                return null;
        }
    }

    // Angepasst.
    /**
     * Die Berechnung soll so funktionieren, dass wir die Spieleranzahl+2 durch die Boardlänge (x Anzahl) teilen,
     * und dann die mittleren Positionen als Startkoordianten für die Spieler nutzen.
     *
     * @return Alle XWerte bzw Ids wo gestartet werden soll
     */
    public static int[] getPlayerStartingPositions() {
        int[] xWerte = new int[PLAYER_COUNT];
        int posibleXCoord = WIDTH / (PLAYER_COUNT + 2);

        int j = 0;
        for (int i = 2; i < PLAYER_COUNT + 2; i++) {
            int x = i * posibleXCoord;
            //coordinates[i] = new Coordinate(x - 1, HEIGHT - 1); //Wenn unsere Cells mit 1 anfangen das -1 weg
            xWerte[j++] = x;
        }
        return xWerte;
    }

    public void moveEveryPlayer(char[] allInputs, Board board) {
        List<Player> playersToKill = new ArrayList<>();
        for (int i = 0; i < PLAYER_COUNT; i++) {
            players.get(i).move(allInputs[i], board);
            if (!players.get(i).isAlive()) {
                playersToKill.add(players.get(i));
            } // Hier könnte man auf Anzahl noch lebender Spieler leicht nachfragen
        }
        killPlayers(playersToKill);
    }

    public void killPlayers(List<Player> players) {
        for (Player player : players) {
            player.playerDies();
        }
    }

}
