package tron.model.base.gamelogic.impl;

import javafx.application.Platform;
import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IControllerModel;
import tron.lobby.impl.Registrator;
import tron.model.base.gamelogic.interfaces.IGameLogic;
import tron.model.base.inputhandler.interfaces.IInputHandler;
import tron.model.base.persistenz.Board;
import tron.model.base.persistenz.BoardCell;
import tron.model.base.persistenz.Player;
import tron.view.interfaces.IViewModel;

import java.util.*;


// Wird noch IGameLogic implementieren
public class GameLogic implements IGameLogic, IInputHandler, Runnable {

    boolean onePlayerRemaining = false; // Kommt noch was zu
    int playerCount;
    List<Player> players;
    public Board board;

    public IControllerModel iControllerModel;

    public IViewModel iViewModel;

    Thread gameThread;

    public GameLogic(IControllerModel iControllerModel, IViewModel iViewModel) {
        this.board = new Board(HEIGHT, WIDTH);
        playerCount = PLAYER_COUNT;
        //playerCount ok in Controller oder Model?

        this.iControllerModel = iControllerModel;
        this.iViewModel = iViewModel;


    }

    @Override
    public void startGameThread() {
        players = initPlayers();
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        // Hier wird geupdated und dargestellt
        // das ganze wird FPS times per second gemacht

        double updateIntervall = 1000000000.0 / VELOCITYOFPLAYERS; // (FPS)
        double nextUpdateTime = System.nanoTime() + updateIntervall;

        while (gameThread != null) {
            //System.out.println("GameThread is running");

            try {
                double remainingTimeUpdate = (nextUpdateTime - System.nanoTime()) / 1000000; // in ms nicht ns

                if (remainingTimeUpdate < 0) { // Wenn die Aktionen zu lang gedauert haben, wird sofort weiter gemacht
                    remainingTimeUpdate = 0;
                }
                Thread.sleep((long) remainingTimeUpdate);
                nextUpdateTime += updateIntervall;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            gametick();
            List<Player> playersToPaint = players.stream().filter(Player::isAlive).toList();
            iViewModel.displayBoard(playersToPaint);

            //  System.out.println(board.toStringTest());
        }
    }

    @Override
    public void gametick() {
        // Was in einem Tick passieren soll
        moveEveryPlayer(iControllerModel.getInputForCurrentCycle(), board); //iControllerModel.getInputForCurrentCycle()
        //iViewModel.displayBoard(players);
        long playersAlive = players.stream().filter(Player::isAlive).count();

        if (playersAlive <= 1) {
            onePlayerRemaining = true;
            List<Player> winningPlayer = players.stream().filter(Player::isAlive).toList();
            if (winningPlayer.size() == 1) {
                System.out.println("Winner is " + winningPlayer.get(0).getId());
                Platform.runLater(() -> {
                    iViewModel.displayLastView(winningPlayer.get(0));
                });
            } else {
                System.out.println("Beide Spieler sind gleichzeitig gecrasht."); // lastStandingPlayer ist 0
                Platform.runLater(() -> {
                    iViewModel.displayLastView(null);
                });
            }
            //System.out.println(board.toStringTest());
            // iControllerModel.endGame(); //TODO einkommentieren wenn drin
            gameThread.stop();
        }

    }

    @Override
    public List<Player> initPlayers() {
        List<Player> players = new ArrayList<>();
        int[] startPositionIds = getPlayerStartingPositions();

        //System.out.println(board.toStringTest());

        for (int i = 1; i <= playerCount; i++) {
            List<BoardCell> paintedCells = new ArrayList<>(); // Könnte hier raus

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

    /**
     * Die Berechnung soll so funktionieren, dass wir die Spieleranzahl+2 durch die Boardlänge (x Anzahl) teilen,
     * und dann die mittleren Positionen als Startkoordinaten für die Spieler nutzen.
     *
     * @return Alle XWerte bzw Ids wo gestartet werden soll
     */
    @Override
    public int[] getPlayerStartingPositions() {
        int[] xWerte = new int[playerCount];
        int possibleXCoord = WIDTH / (playerCount + 2);
        int j = 0;
        for (int i = 2; i < playerCount + 2; i++) {
            int x = i * possibleXCoord;
            xWerte[j++] = x;
        }
        return xWerte;
    }

    @Override
    public void moveEveryPlayer(char[] allInputs, Board board) {

        List<Player> playersToKill = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            Player currentPlayer = players.get(i);
            if (currentPlayer.isAlive()) { // Wenn currentCell von einem Spieler auf CurrentCell eines anderen ist, beide dead
                currentPlayer.move(allInputs[i], board);
            }
            for (int j = 0; j < playerCount; j++) {
                for (int k = j + 1; k < playerCount; k++) {
                    if (players.get(j).getCurrentCell().getId() == players.get(k).getCurrentCell().getId()) {
                        playersToKill.add(players.get(j));
                        players.get(j).setAlive(false);
                        playersToKill.add(players.get(k));
                        players.get(k).setAlive(false);
                    }
                }
            }
            if (!currentPlayer.isAlive()) {
                playersToKill.add(currentPlayer);
            } // Hier könnte man auf Anzahl noch lebender Spieler leicht nachfragen
        }
        killPlayers(playersToKill);
    }

    @Override
    public void killPlayers(List<Player> players) {
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
        IViewModel iViewModel = null;
        GameLogic gameLogic = new GameLogic(iControllerModel, iViewModel);
        gameLogic.startGameThread();
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
}
