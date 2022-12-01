package tron.test.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IControllerModel;
import tron.lobby.impl.Registrator;
import tron.model.base.gamelogic.impl.GameLogic;
import tron.model.base.inputhandler.interfaces.IInputHandler;
import tron.model.base.persistenz.BoardCell;
import tron.model.base.persistenz.Player;
import tron.view.interfaces.IViewModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGamelogic implements IInputHandler {

    Registrator registrator = new Registrator();
    IControllerModel iControllerModel = new BasicController(registrator);
    IViewModel iViewModel = null; // Später
    GameLogic gameLogic = new GameLogic(iControllerModel, iViewModel);


    public void cleanUp() {
        for (BoardCell boardCell : gameLogic.board.getCells()) {
            boardCell.setColorId(0);
        }
        for (int i = 1; i <= PLAYER_COUNT; i++) {
            Player player = gameLogic.getPlayers().get(i - 1);
            player.setCurrentDirection(player.getMoveDown());
            player.setAlive(true);

            player.setPaintedCells(new ArrayList<>());
            player.setCurrentCell(gameLogic.board.getCellById(i - 1));
        }
    }


    @Test
    public void testGetPlayerStartingPositions() {
        int[] xWerte = gameLogic.getPlayerStartingPositions();
        for (int xWert : xWerte) {
            Assertions.assertTrue(xWert >= 0 && xWert <= gameLogic.board.width);
            System.out.println(xWert);
        }
    }

    @Test
    public void testInitPlayers() {
        List<Player> players = gameLogic.getPlayers();
        assertEquals(players.size(), PLAYER_COUNT);

        for (Player player : players) {
            assertEquals(player.getColor(), player.getId());
            System.out.println(player.getId());

            assertEquals(player.getPaintedCells().size(), 1);

            assertEquals(player.getCurrentCell().getColorId(), player.getColor());
            assertEquals(0, player.getCurrentCell().getY());
        }
    }

    @Test
    public void testKillPlayers() {
        cleanUp();
        List<Player> players = gameLogic.getPlayers();
        for (Player player : players) {
            for (int i = 0; i < 3; i++) {
                player.move(player.getMoveDown(), gameLogic.board);
            }
        }
        //System.out.println(gameLogic.board);
        List<Player> playersToKill = new ArrayList<>();
        playersToKill.add(players.get(0));
        Assertions.assertEquals(4, players.get(0).getPaintedCells().size());

        Player player = players.get(PLAYER_COUNT - 1);
        for (int i = 0; i < 3; i++) {
            player.move(player.getMoveRight(), gameLogic.board);
        }
        Assertions.assertEquals(7, player.getPaintedCells().size());
        //Player wurden je 3 und der letzte noch weitere 3 laufen gelassen, damit kommen sie auf je 4 und einer 7 gefärbte Felder

        //Insg also (3 * (PlayerCount-1)) + PlayerCount-1 + 7
        int paintedCellsInBoard = 0;
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            if (gameLogic.board.getCellById(i).getColorId() != 0) {
                paintedCellsInBoard++;
            }
        }
        Assertions.assertEquals(paintedCellsInBoard, (3 * (PLAYER_COUNT - 1) + PLAYER_COUNT - 1 + 7));

        // Erster Spieler wird getötet, seine Liste soll erhalten bleiben
        gameLogic.killPlayers(playersToKill);
        List<BoardCell> boardCells = new ArrayList<>();
        Assertions.assertEquals(4, players.get(0).getPaintedCells().size());

        paintedCellsInBoard = 0;
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            if (gameLogic.board.getCellById(i).getColorId() != 0) {
                paintedCellsInBoard++;
            }
        }
        Assertions.assertEquals(paintedCellsInBoard, (3 * (PLAYER_COUNT - 2) + PLAYER_COUNT - 2 + 7));

        //Alle Spieler sterben
        for (int i = 1; i < PLAYER_COUNT; i++) {
            playersToKill.add(players.get(i));
        }
        gameLogic.killPlayers(playersToKill);

        paintedCellsInBoard = 0;
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            if (gameLogic.board.getCellById(i).getColorId() != 0) {
                paintedCellsInBoard++;
            }
        }
        Assertions.assertEquals(paintedCellsInBoard, 0);

        for (int i=0;i<PLAYER_COUNT;i++) {
            if(i==PLAYER_COUNT-1) {
                Assertions.assertEquals(7, players.get(i).getPaintedCells().size());
                break;
            }
            Assertions.assertEquals(4, players.get(i).getPaintedCells().size());
        }

    }

    @Test
    public void testMoveEveryPlayer() {
        //Spieler bewegen sich wenn am leben, sonst bewegen sie sich nicht und haben kein pfad
        // Alle anderen dürfen aber keine Probleme damit haben sich zu bewegen.
        List<Player> players = gameLogic.getPlayers();
        char[] inputForCurrentCircleAllDown = new char[6];
        for (int i = 0; i < PLAYER_COUNT; i++) {
            inputForCurrentCircleAllDown[i] = players.get(i).getMoveDown();
        }

        char[] inputForCurrentCircle2 = new char[6];
        for (int i = 0; i < PLAYER_COUNT; i++) {
            inputForCurrentCircle2[i] = players.get(i).getMoveDown();
            if (i == PLAYER_COUNT - 1) {
                inputForCurrentCircle2[i] = players.get(i).getMoveLeft();
            }
        }
        for (int i = 0; i < 5; i++) {
            gameLogic.moveEveryPlayer(inputForCurrentCircleAllDown, gameLogic.board);
        }

        for (int i = 0; i < PLAYER_COUNT; i++) {
            Assertions.assertTrue(players.get(i).isAlive());
            Assertions.assertEquals(6, players.get(i).getPaintedCells().size());
        }

        if (WIDTH <= 10 && PLAYER_COUNT < 4) { // Der Test braucht ein relativ enges Spielfeld un wenige Spieler
            gameLogic.moveEveryPlayer(inputForCurrentCircle2, gameLogic.board);
            gameLogic.moveEveryPlayer(inputForCurrentCircle2, gameLogic.board);

            for (int i = 0; i < PLAYER_COUNT; i++) {
                if (i != PLAYER_COUNT - 1) {
                    Assertions.assertTrue(players.get(i).isAlive());
                    Assertions.assertEquals(8, players.get(i).getPaintedCells().size());
                } else {
                    System.out.println(i);
                    Assertions.assertFalse(players.get(i).isAlive());
                    Assertions.assertEquals(0, players.get(i).getPaintedCells().size());
                }
            }
            gameLogic.moveEveryPlayer(inputForCurrentCircle2, gameLogic.board);
            Assertions.assertEquals(0, boardGefaerbteFarben(PLAYER_COUNT));
            Assertions.assertEquals(9, boardGefaerbteFarben(1));

            // 2 Spieler fahren gegeneinander
            cleanUp();
            players.get(0).setCurrentCell(gameLogic.board.getCellById(WIDTH * 3 + 5));
            players.get(1).setCurrentCell(gameLogic.board.getCellById(WIDTH * 3 + 9));
            char[] inputForCurrentCircle3 = new char[6];
            for (int i = 0; i < PLAYER_COUNT; i++) {
                if (i == 0) {
                    inputForCurrentCircle3[i] = players.get(i).getMoveRight();
                } else if (i == 1) {
                    inputForCurrentCircle3[i] = players.get(i).getMoveLeft();
                } else {
                    inputForCurrentCircle3[i] = players.get(i).getMoveDown();
                }
            }

            gameLogic.moveEveryPlayer(inputForCurrentCircle3, gameLogic.board);
            gameLogic.moveEveryPlayer(inputForCurrentCircle3, gameLogic.board);
            for (int i = 0; i < PLAYER_COUNT; i++) {
                if (i == 0) {
                    Assertions.assertFalse(players.get(i).isAlive());
                } else if (i == 1) {
                    Assertions.assertFalse(players.get(i).isAlive());
                } else {
                    Assertions.assertTrue(players.get(i).isAlive());
                }
            }
            for (int i = 0; i < PLAYER_COUNT; i++) {
                if (i == 0) {
                    Assertions.assertEquals(0, players.get(i).getPaintedCells().size());
                    Assertions.assertEquals(0, boardGefaerbteFarben(i + 1));
                } else if (i == 1) {
                    Assertions.assertEquals(0, players.get(i).getPaintedCells().size());
                    Assertions.assertEquals(0, boardGefaerbteFarben(i + 1));
                } else {
                    Assertions.assertEquals(3, players.get(i).getPaintedCells().size());
                    Assertions.assertEquals(3, boardGefaerbteFarben(i + 1));
                }
            }
        }
    }

    public int boardGefaerbteFarben(int ColorId) {
        int paintedCellsInBoard = 0;
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            if (gameLogic.board.getCellById(i).getColorId() == ColorId) {
                paintedCellsInBoard++;
            }
        }
        return paintedCellsInBoard;
    }

}


