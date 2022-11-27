package tron.model.base.gamelogic.impl;

import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IControllerModel;
import tron.model.base.inputhandler.interfaces.IInputHandler;
import tron.model.base.persistenz.Board;
import tron.model.base.persistenz.BoardCell;
import tron.model.base.persistenz.Player;
import tron.registrator.impl.Registrator;
import tron.view.interfaces.IViewModel;

import java.util.*;
import java.util.stream.Collectors;


// Wird noch IGameLogic implementieren
public class GameLogic implements IInputHandler, Runnable {

    boolean onePlayerRemaining = false; // Kommt noch was zu

    List<Player> players;

    public Board board;

    public IControllerModel iControllerModel;

    public IViewModel iViewModel;

    Thread gameThread;

    //TODO vermutlich mind 10er tickspeed und eher width und height bei 20+

    public GameLogic(IControllerModel iControllerModel, IViewModel iViewModel) {
        this.board = new Board(HEIGHT, WIDTH);
        players = initPlayers(); //playerCount ok in Controller oder Model?

        this.iControllerModel = iControllerModel;
        this.iViewModel = iViewModel;

        //startGameThread();

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        // Hier wird geupdated und dargestellt
        // das ganze wird FPS times per second gemacht

        double updateIntervall = 1000000000.0 / VELOCITYOFPLAYERS; // (FPS)
        double nextUpdateTime = System.nanoTime() + updateIntervall;

        while (!gameThread.isInterrupted()) {
            //System.out.println("GameThread is running");

            gametick();
            System.out.println(board);

            //iViewModel.displayBoard(board, players); //TODO einkommentieren wenn view soweit ist

            try {
                double remainingTimeUpdate = (nextUpdateTime - System.nanoTime()) / 1000000; // in ms nicht ns

                if (remainingTimeUpdate < 0) { // Wenn die Aktionen zu lang gedauert haben, wird sofort weiter gemacht
                    remainingTimeUpdate = 0;
                }
                Thread.sleep((long) remainingTimeUpdate); //TODO noch nicht richtig
                nextUpdateTime += updateIntervall;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        //Delta Version testen ob besser
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
//                    delta--;
//            }
//        }

    }

    public void gametick() {
        // Was in einem Tick passieren soll
        moveEveryPlayer(iControllerModel.getInputForCurrentCycle(), board);
        long playersAlive = players.stream().filter(Player -> Player.isAlive() == true).count(); //TODO evtl simplify

        if (playersAlive <= 1) {
            onePlayerRemaining = true;
            // iControllerModel.endGame(); //TODO einkommentieren wenn drin
            List<Player> winningPlayer = players.stream().filter(Player::isAlive).collect(Collectors.toList());
            Player lastStandingPlayer; //TODO den müssen wir noch mitgeben
            if (winningPlayer.size() == 1) {
                lastStandingPlayer = winningPlayer.get(0);
            } else {
                lastStandingPlayer = null;
            }
            System.out.println("Winner is " + lastStandingPlayer.getId());
            gameThread.interrupt();
            //iViewModel.displayView(ViewEnum.VIEW4.getViewId());
        }

    }

    public List<Player> initPlayers() {
        List<Player> players = new ArrayList<>();
        int[] startPositionIds = getPlayerStartingPositions();

        for (int i = 1; i <= PLAYER_COUNT; i++) {
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

            Player player = new Player(i, i, board.getCellById(startPositionIds[i - 1]), paintedCells, moveUp, moveDown, moveLeft, moveRight, true);

            //System.out.println(player.getCurrentCell());
            player.setCurrentCellColor(i);
            player.getPaintedCells().add(player.getCurrentCell());
            players.add(player);
        }
        return players;
    }

    // Angepasst.

    /**
     * Die Berechnung soll so funktionieren, dass wir die Spieleranzahl+2 durch die Boardlänge (x Anzahl) teilen,
     * und dann die mittleren Positionen als Startkoordinaten für die Spieler nutzen.
     *
     * @return Alle XWerte bzw Ids wo gestartet werden soll
     */
    public int[] getPlayerStartingPositions() {
        int[] xWerte = new int[PLAYER_COUNT];
        int possibleXCoord = WIDTH / (PLAYER_COUNT + 2);
        int j = 0;
        for (int i = 2; i < PLAYER_COUNT + 2; i++) {
            int x = i * possibleXCoord;
            xWerte[j++] = x;
        }
        return xWerte;
    }

    public void moveEveryPlayer(char[] allInputs, Board board) { //TODO Test
        List<Player> playersToKill = new ArrayList<>();
        for (int i = 0; i < PLAYER_COUNT; i++) {
            Player currentPlayer = players.get(i);
            if (currentPlayer.isAlive()) {
                currentPlayer.move(allInputs[i], board);
            }
            if (!currentPlayer.isAlive()) {
                playersToKill.add(currentPlayer);
            } // Hier könnte man auf Anzahl noch lebender Spieler leicht nachfragen
        }
        killPlayers(playersToKill);
    }

    public void killPlayers(List<Player> players) { //TODO Test
        for (Player player : players) {
            player.playerDies();
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public static void main(String[] args) {
        Registrator registrator = new Registrator();
        IControllerModel iControllerModel = new BasicController(registrator);
        IViewModel iViewModel = null; // Später
        //new GameLogic(iControllerModel,iViewModel);
    }
}
