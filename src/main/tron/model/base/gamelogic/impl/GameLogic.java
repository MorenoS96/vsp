package tron.model.base.gamelogic.impl;

import tron.controller.interfaces.IControllerModel;
import tron.model.base.inputhandler.interfaces.IInputHandler;
import tron.model.base.persistenz.Board;
import tron.model.base.persistenz.BoardCell;
import tron.model.base.persistenz.Player;
import tron.view.interfaces.IViewModel;

import java.util.ArrayList;
import java.util.List;


// Wird noch IGameLogic implementieren
public class GameLogic implements IInputHandler, Runnable {

    boolean onePlayerRemaining = false; // Kommt noch was zu

    List<Player> players;

    public Thread gameThread;

    public Board board;

    IControllerModel iControllerModel;

    IViewModel iViewModel;

    public GameLogic(IControllerModel iControllerModel, IViewModel iViewModel) {
        this.board = new Board(HEIGHT, WIDTH);
        players = initPlayers(); //playerCount ok in Controller oder Model?

        this.iControllerModel = iControllerModel;
        this.iViewModel = iViewModel;

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // Soll eine Runde simulieren
        long lastRound = System.nanoTime();
        double nsTicks = 1000000000 / 60.0;
        double delta = 0;
        while (true) { // Später noch ersetzten
            long now = System.nanoTime();
            long timePassed = now - lastRound;
            delta += timePassed / nsTicks;
            lastRound = now;
            if (delta >= 1) {
                moveEveryPlayer(iControllerModel.getInputForCurrentCycle(), board);
                iViewModel.displayBoard(players);
            }
        }
    }

    public List<Player> initPlayers() {
        List<Player> players = new ArrayList<>();
        int[] startPositionIds = getPlayerStartingPositions();

        for (int i = 0; i < PLAYER_COUNT; i++) {
            // id der Zelle soll == position der Array entsprechen
            String playerColor = getPlayerColor(i);

            List<BoardCell> paintedCells = new ArrayList<>(); // Könnte hier raus

            Player player = new Player(i, playerColor, board.getCellById(startPositionIds[i]), paintedCells);
            player.setCurrentCellColor();
            players.add(player);
        }
        return players;
    }


    public  String getPlayerColor(int id) {
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
                System.out.println("Keine passende Farbe gefunden bzw zu viele Spieler");
                return null;
        }
    }

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
            x += 1;
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
