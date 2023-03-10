package tron.model.base.gamelogic.impl;

import javafx.application.Platform;
import tron.controller.interfaces.IControllerModel;
import tron.model.base.gamelogic.interfaces.IGameLogic;
import tron.model.base.inputhandler.interfaces.IInputHandler;
import tron.model.base.persistenz.Board;
import tron.model.base.persistenz.BoardCell;
import tron.model.base.persistenz.Player;
import tron.view.interfaces.IViewModel;

import java.util.*;

public class GameLogic implements IGameLogic, IInputHandler, Runnable {

    boolean onePlayerRemaining = false;
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
            System.out.println(Arrays.toString(allIdsLeft(playersToPaint)));
            //iViewModel.displayBoard(allIdsLeft(playersToPaint),allCurrentCellsLeft(playersToPaint),allPathsLeft(playersToPaint)); TODO einkommentieren wenn implementiert
            iViewModel.displayBoard(playersToPaint); //TODO rausnehmen irgendwann.
        }
    }

    @Override
    public void gametick() {
        // Was in einem Tick passieren soll
        moveEveryPlayer(iControllerModel.getInputForCurrentCycle(), board);
        long playersAlive = players.stream().filter(Player::isAlive).count();

        if (playersAlive <= 1) {
            onePlayerRemaining = true;
            List<Player> winningPlayer = players.stream().filter(Player::isAlive).toList();
            if (winningPlayer.size() == 1) {
                System.out.println("Winner is " + winningPlayer.get(0).getId());
                Platform.runLater(() -> iViewModel.displayLastView(winningPlayer.get(0)));
            } else {
                System.out.println("Beide Spieler sind gleichzeitig gecrasht."); // lastStandingPlayer ist 0
                Platform.runLater(() -> iViewModel.displayLastView(null));
            }
            cleanUp();
            iControllerModel.endGame();
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> iViewModel.displayView(1));
            gameThread.stop();
        }

    }

    @Override
    public List<Player> initPlayers() {
        List<Player> players = new ArrayList<>();
        int[] startPositionIds = getPlayerStartingPositions();

        for (int i = 1; i <= playerCount; i++) {
            List<BoardCell> paintedCells = new ArrayList<>();

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

            player.setCurrentCellColor(i);
            player.getPaintedCells().add(player.getCurrentCell());
            players.add(player);
        }
        return players;
    }

    /**
     * Die Berechnung soll so funktionieren, dass wir die Spieleranzahl+2 durch die Boardl??nge (x Anzahl) teilen,
     * und dann die mittleren Positionen als Startkoordinaten f??r die Spieler nutzen.
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
            }
        }
        killPlayers(playersToKill);
    }

    @Override
    public void killPlayers(List<Player> players) {
        for (Player player : players) {
            player.playerDies();
        }
    }

    public void cleanUp() {
        for (Player player : players) {
            player.setCurrentDirection(' ');
        }
    }

    /**
     * @return int[] mit allen IDs der Spieler die noch am Leben sind
     */
    public int[] allIdsLeft(List<Player> playersToPaint) {
        int[] idsLeft = new int[playersToPaint.size()];
        for (int i = 0; i < playersToPaint.size(); i++) {
            idsLeft[i] = playersToPaint.get(i).getId();
        }
        return idsLeft;
    }

    /**
     * @return Ein Array wo je ein Eintrag laos ein int[] f??r einen Spieler steht, dieses int[] hat immer nur x und y
     * Wert drin
     */
    public int[][] allCurrentCellsLeft(List<Player> playersToPaint) {
        int[][] currenCellsLeft = new int[playersToPaint.size()][2];
        for (int i = 0; i < playersToPaint.size(); i++) {
            int x = playersToPaint.get(i).getCurrentCell().getX();
            int y = playersToPaint.get(i).getCurrentCell().getY();
            currenCellsLeft[i] = new int[]{x, y};
        }
        return currenCellsLeft;
    }

    /**
     * @return Pro Spieler ein int[][], dadrin sind so viele int[] mit x und y Werten wie der Spieler schon Zellen
     * gef??rbt hat.
     */
    public int[][][] allPathsLeft(List<Player> playersToPaint) {
        int[][][] pathsLeft = new int[playersToPaint.size()][][];
        for (int i = 0; i < playersToPaint.size(); i++) {
            int[][] onePath = new int[playersToPaint.get(i).getPaintedCells().size()][2];

            for (int j = 0; j < playersToPaint.get(i).getPaintedCells().size(); j++) {
                int x = playersToPaint.get(i).getPaintedCells().get(j).getX();
                int y = playersToPaint.get(i).getPaintedCells().get(j).getY();
                onePath[j] = new int[]{x, y};
            }
            pathsLeft[i] = onePath;
        }
        return pathsLeft;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
}
