package tron.test.model;

import org.junit.Test;
import org.junit.jupiter.api.*;
import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IControllerModel;
import tron.model.base.gamelogic.impl.GameLogic;
import tron.model.base.inputhandler.interfaces.IInputHandler;
import tron.model.base.persistenz.BoardCell;
import tron.model.base.persistenz.Player;
import tron.registrator.impl.Registrator;
import tron.view.interfaces.IViewModel;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestPlayer implements IInputHandler {

    Registrator registrator = new Registrator();
    IControllerModel iControllerModel = new BasicController(registrator);
    IViewModel iViewModel = null; // Später

    GameLogic gameLogic = new GameLogic(iControllerModel, null);

    public void cleanUp() {
        for(BoardCell boardCell: gameLogic.board.getCells()) {
            boardCell.setColorId(0);
        }
        for(int i=1;i<=PLAYER_COUNT;i++) {
            Player player = gameLogic.getPlayers().get(i-1);
            player.setCurrentDirection(player.getMoveDown());
            player.setAlive(true);

            player.setPaintedCells(new ArrayList<>());
//            player.setCurrentCell(gameLogic.board.getCellById(i-1));
        }
    }

    @Test
    public void testSetCurentCell() {
        Player player1 = gameLogic.getPlayers().get(0);
        gameLogic.getPlayers().get(0).setCurrentCell(gameLogic.board.getCellById(1));
        Assertions.assertEquals(1, gameLogic.board.getCellById(1).getColorId());
        gameLogic.getPlayers().get(0).setCurrentCell(gameLogic.board.getCellById(99));
        Assertions.assertEquals(1, gameLogic.board.getCellById(99).getColorId());
        Assertions.assertEquals(3, gameLogic.getPlayers().get(0).getPaintedCells().size());

        // Wenn schon gefärbt ist sollte der Spieler sich nicht dahin bewegen können
        player1.setCurrentCell(gameLogic.board.getCellById(1));
        Assertions.assertEquals(player1.getCurrentCell(), gameLogic.board.getCellById(99));

    }

    @Test
    public void testCheckBorder() {

        Player player1 = gameLogic.getPlayers().get(0); // Bei AnfangsPosition nur nicht nach oben bewegen
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveUp"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveDown"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveRight"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveLeft"));

        player1.setCurrentCell(gameLogic.board.getCellById(0)); // Ecke oben links
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveUp"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveDown"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveRight"));
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveLeft"));

        player1.setCurrentCell(gameLogic.board.getCellById(WIDTH - 1)); // Ecke oben rechts
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveUp"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveDown"));
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveRight"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveLeft"));

        player1.setCurrentCell(gameLogic.board.getCellById((HEIGHT - 1) * WIDTH)); // Ecke unten links
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveUp"));
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveDown"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveRight"));
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveLeft"));

        player1.setCurrentCell(gameLogic.board.getCellById(WIDTH * HEIGHT - 1)); // Ecke unten rechts
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveUp"));
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveDown"));
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveRight"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveLeft"));

        int rndm1 = (HEIGHT - 1) * WIDTH + randomIntBetween(1, WIDTH - 2);
        player1.setCurrentCell(gameLogic.board.getCellById(rndm1));
        System.out.println(rndm1);// Unten am Board
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveUp"));
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveDown"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveRight"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveLeft"));

        int rndm2 = WIDTH * randomIntBetween(1, HEIGHT - 2);
        player1.setCurrentCell(gameLogic.board.getCellById(rndm2));
        System.out.println(rndm2);// Links am Board
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveUp"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveDown"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveRight"));
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveLeft"));

        int rndm3 = (WIDTH - 1) + WIDTH * randomIntBetween(1, HEIGHT - 2);
        player1.setCurrentCell(gameLogic.board.getCellById(rndm3));
        System.out.println(rndm3);// Rechts am Board
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveUp"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveDown"));
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveRight"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveLeft"));

        // Darf sich in jede Richtung bewegen
        for (int i = 0; i < 20; i++) {

            int rndm4 = randomIntBetweenWithBorders(WIDTH, (HEIGHT - 1) * WIDTH);
            player1.setCurrentCell(gameLogic.board.getCellById(rndm4));
            //System.out.println(rndm4);
            Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveUp"));
            Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveDown"));
            Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveRight"));
            Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveLeft"));
        }
    }

    @Test
    public void testCheckCollision() {
        Player player1 = gameLogic.getPlayers().get(0);
        System.out.println(gameLogic.board.getCellById(player1.getCurrentCell().getId() + WIDTH));
        // nächste Zelle ungefärbt also keine Farb Collision
        Assertions.assertFalse(player1.checkCollision(gameLogic.board.getCellById(player1.getCurrentCell().getId() + WIDTH)));

        gameLogic.getPlayers().get(0).setCurrentCell(gameLogic.board.getCellById(player1.getCurrentCell().getId() + WIDTH));
        gameLogic.board.getCellById(player1.getCurrentCell().getId() + WIDTH).setColorId(2);
        // Player auf eine Zelle runtergesetzt
        System.out.println(gameLogic.board.getCellById(player1.getCurrentCell().getId() + WIDTH));
        Assertions.assertTrue(player1.checkCollision(gameLogic.board.getCellById(player1.getCurrentCell().getId() + WIDTH)));

    }

    @Test
    public void testIsMoveIncorrect() {
        Player player1 = gameLogic.getPlayers().get(0);
        // Sollte anfangs immer moveDown sein
        System.out.println(player1.getCurrentDirection());

        Assertions.assertTrue(player1.isMoveIncorrect(player1.getMoveUp()));
        Assertions.assertFalse(player1.isMoveIncorrect(player1.getMoveLeft()));
        Assertions.assertFalse(player1.isMoveIncorrect(player1.getMoveRight()));
        Assertions.assertFalse(player1.isMoveIncorrect(player1.getMoveDown()));

        player1.setCurrentDirection(player1.getMoveLeft());
        System.out.println(player1.getCurrentDirection());
        Assertions.assertFalse(player1.isMoveIncorrect(player1.getMoveUp()));
        Assertions.assertFalse(player1.isMoveIncorrect(player1.getMoveLeft()));
        Assertions.assertTrue(player1.isMoveIncorrect(player1.getMoveRight()));
        Assertions.assertFalse(player1.isMoveIncorrect(player1.getMoveDown()));

        player1.setCurrentDirection(player1.getMoveRight());
        System.out.println(player1.getCurrentDirection());
        Assertions.assertFalse(player1.isMoveIncorrect(player1.getMoveUp()));
        Assertions.assertTrue(player1.isMoveIncorrect(player1.getMoveLeft()));
        Assertions.assertFalse(player1.isMoveIncorrect(player1.getMoveRight()));
        Assertions.assertFalse(player1.isMoveIncorrect(player1.getMoveDown()));

        player1.setCurrentDirection(player1.getMoveUp());
        System.out.println(player1.getCurrentDirection());
        Assertions.assertFalse(player1.isMoveIncorrect(player1.getMoveUp()));
        Assertions.assertFalse(player1.isMoveIncorrect(player1.getMoveLeft()));
        Assertions.assertFalse(player1.isMoveIncorrect(player1.getMoveRight()));
        Assertions.assertTrue(player1.isMoveIncorrect(player1.getMoveDown()));
    }

    @Test
    public void testMove() {
        cleanUp();
        Player player1 = gameLogic.getPlayers().get(0);
        Player player2 = gameLogic.getPlayers().get(1);
        player1.setCurrentCell(gameLogic.board.getCellById(0));
        player2.setCurrentCell(gameLogic.board.getCellById(WIDTH-1));

        //Sollte trotz leerer eingabe nach unten
        player1.move(' ',gameLogic.board);
        Assertions.assertEquals(player1.getCurrentCell(),gameLogic.board.getCellById(WIDTH));

        //Sollte trotz leerer eingabe nach unten
        player1.move(' ',gameLogic.board);
        Assertions.assertEquals(player1.getCurrentCell(),gameLogic.board.getCellById(WIDTH*2));

        //Sollte trotz falscher Eingabe nach unten
        player1.move(player1.getMoveUp(),gameLogic.board);
        Assertions.assertEquals(player1.getCurrentCell(),gameLogic.board.getCellById(WIDTH*3));

        //Fährt aus dem Spielfeld
        player1.move(player1.getMoveLeft(),gameLogic.board);

        Assertions.assertEquals(4,player1.getPaintedCells().size());
        Assertions.assertFalse(player1.isAlive());

        // Nach unten fahren
        player2.move(player2.getMoveDown(),gameLogic.board);
        Assertions.assertEquals(player2.getCurrentCell(),gameLogic.board.getCellById(WIDTH*2 - 1));

        // Nach links fahren
        player2.move(player2.getMoveLeft(),gameLogic.board);
        Assertions.assertEquals(player2.getCurrentCell(),gameLogic.board.getCellById(WIDTH*2 - 2));

        // Nach links fahren ohne extra Eingabe
        player2.move(' ',gameLogic.board);
        Assertions.assertEquals(player2.getCurrentCell(),gameLogic.board.getCellById(WIDTH*2 - 3));

        // Nach oben fahren
        player2.move(player2.getMoveUp(),gameLogic.board);
        Assertions.assertEquals(player2.getCurrentCell(),gameLogic.board.getCellById(WIDTH - 3));

        // Nach rechts fahren
        player2.move(player2.getMoveRight(),gameLogic.board);
        Assertions.assertEquals(player2.getCurrentCell(),gameLogic.board.getCellById(WIDTH - 2));

        // Nochmal nach rechts gegen eine selbst gefärbte Zelle
        System.out.println(gameLogic.board.getCellById(WIDTH-1));
        player2.move(' ',gameLogic.board);
        Assertions.assertFalse(player2.isAlive());
        Assertions.assertEquals(6,player2.getPaintedCells().size());

    }





    public int randomIntBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public int randomIntBetweenWithBorders(int min, int max) {
        int rndmNumber = ThreadLocalRandom.current().nextInt(min, max + 1);
        if ((Math.abs(rndmNumber - (WIDTH - 1)) % WIDTH == 0) || (rndmNumber % WIDTH == 0)) {
            return randomIntBetweenWithBorders(min, max);
        } else {
            return rndmNumber;
        }
    }
}
