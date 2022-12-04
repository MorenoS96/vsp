package tron.model.base.gamelogic.impl;

import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IControllerModel;
import tron.lobby.impl.Registrator;
import tron.model.base.inputhandler.interfaces.IInputHandler;
import tron.model.base.persistenz.Board;
import tron.model.base.persistenz.BoardCell;
import tron.model.base.persistenz.Player;
import tron.model.base.persistenz.ViewEnum;
import tron.view.interfaces.IViewModel;

import java.sql.SQLOutput;
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

    public GameLogic(IControllerModel iControllerModel, IViewModel iViewModel) {
        this.board = new Board(HEIGHT, WIDTH);
        players = initPlayers(); //playerCount ok in Controller oder Model?

        this.iControllerModel = iControllerModel;
        this.iViewModel = iViewModel;

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

        while (gameThread != null) {
            //System.out.println("GameThread is running");
            double startTime = System.nanoTime();



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

    public void gametick() {
        // Was in einem Tick passieren soll

        moveEveryPlayer(iControllerModel.getInputForCurrentCycle(), board); //iControllerModel.getInputForCurrentCycle()
        //iViewModel.displayBoard(players);
        long playersAlive = players.stream().filter(Player::isAlive).count();

        if (playersAlive <= 1) {
            onePlayerRemaining = true;
            List<Player> winningPlayer = players.stream().filter(Player::isAlive).toList();
            int lastStandingPlayer;
            if (winningPlayer.size() == 1) {
                lastStandingPlayer = winningPlayer.get(0).getId();
                System.out.println("Winner is " + lastStandingPlayer);

            } else {
                System.out.println("Beide Spieler sind gleichzeitig gecrasht.");
                lastStandingPlayer = 0;
            }
            //System.out.println(board.toStringTest());
            gameThread.stop();
            // iControllerModel.endGame(); //TODO einkommentieren wenn drin
            //iViewModel.displayLastView(lastStandingPlayer); //TODO einkommentieren wenn drin
        }

    }

    public List<Player> initPlayers() {
        List<Player> players = new ArrayList<>();
        int[] startPositionIds = getPlayerStartingPositions();

        //System.out.println(board.toStringTest());

        for (int i = 1; i <= PLAYER_COUNT; i++) {
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

    public void moveEveryPlayer(char[] allInputs, Board board) {

        List<Player> playersToKill = new ArrayList<>();
        for (int i = 0; i < PLAYER_COUNT; i++) {
            Player currentPlayer = players.get(i);
            if (currentPlayer.isAlive()) { // Wenn currentCell von einem Spieler auf CurrentCell eines anderen ist, beide dead
                currentPlayer.move(allInputs[i], board);
            }
            for(int j=0;j<PLAYER_COUNT;j++) {
                for(int k=j+1;k<PLAYER_COUNT;k++) {
                    if(players.get(j).getCurrentCell().getId()==players.get(k).getCurrentCell().getId()) {
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
        IViewModel iViewModel = null; // Später
        GameLogic gameLogic =  new GameLogic(iControllerModel,iViewModel);
        gameLogic.startGameThread();
    }
}
