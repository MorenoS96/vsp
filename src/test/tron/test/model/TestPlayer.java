package tron.test.model;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IControllerModel;
import tron.model.base.gamelogic.impl.GameLogic;
import tron.model.base.inputhandler.interfaces.IInputHandler;
import tron.model.base.persistenz.Player;
import tron.registrator.impl.Registrator;
import tron.view.interfaces.IViewModel;

import java.util.concurrent.ThreadLocalRandom;

public class TestPlayer implements IInputHandler {

    Registrator registrator = new Registrator();
    IControllerModel iControllerModel = new BasicController(registrator);
    IViewModel iViewModel = null; // Später

    GameLogic gameLogic = new GameLogic(iControllerModel,iViewModel);

    @Test
    public void testSetCurentCell() {
        Player player1 =  gameLogic.getPlayers().get(0);
        gameLogic.getPlayers().get(0).setCurrentCell(gameLogic.board.getCellById(1));
        Assertions.assertEquals(1, gameLogic.board.getCellById(1).getColorId());
        gameLogic.getPlayers().get(0).setCurrentCell(gameLogic.board.getCellById(99));
        Assertions.assertEquals(1, gameLogic.board.getCellById(99).getColorId());
        Assertions.assertEquals(3,gameLogic.getPlayers().get(0).getPaintedCells().size());

        // Wenn schon gefärbt ist sollte der Spieler sich nicht dahin bewegen können
        player1.setCurrentCell(gameLogic.board.getCellById(1));
        Assertions.assertEquals(player1.getCurrentCell(),gameLogic.board.getCellById(99));

    }

    @Test
    public void testCheckBorder() {

        Player player1 =  gameLogic.getPlayers().get(0); // Bei AnfangsPosition nur nicht nach oben bewegen
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(),"moveUp"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveDown"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveRight"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveLeft"));

        player1.setCurrentCell(gameLogic.board.getCellById(0)); // Ecke oben links
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(),"moveUp"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveDown"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveRight"));
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveLeft"));

        player1.setCurrentCell(gameLogic.board.getCellById(WIDTH-1)); // Ecke oben rechts
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(),"moveUp"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveDown"));
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveRight"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveLeft"));

        player1.setCurrentCell(gameLogic.board.getCellById((HEIGHT-1)*WIDTH)); // Ecke unten links
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(),"moveUp"));
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveDown"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveRight"));
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveLeft"));

        player1.setCurrentCell(gameLogic.board.getCellById(WIDTH*HEIGHT-1)); // Ecke unten rechts
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(),"moveUp"));
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveDown"));
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveRight"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveLeft"));

        int rndm1 = (HEIGHT-1)*WIDTH+randomIntBetween(1,WIDTH-2);
        player1.setCurrentCell(gameLogic.board.getCellById(rndm1));
        System.out.println(rndm1);// Unten am Board
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(),"moveUp"));
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveDown"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveRight"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveLeft"));

        int rndm2 = WIDTH*randomIntBetween(1,HEIGHT-2);
        player1.setCurrentCell(gameLogic.board.getCellById(rndm2));
        System.out.println(rndm2);// Links am Board
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(),"moveUp"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveDown"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveRight"));
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveLeft"));

        int rndm3 = (WIDTH-1) + WIDTH * randomIntBetween(1,HEIGHT-2);
        player1.setCurrentCell(gameLogic.board.getCellById(rndm3));
        System.out.println(rndm3);// Rechts am Board
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(),"moveUp"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveDown"));
        Assertions.assertTrue(player1.checkBorder(player1.getCurrentCell().getId(), "moveRight"));
        Assertions.assertFalse(player1.checkBorder(player1.getCurrentCell().getId(), "moveLeft"));

        // Darf sich in jede Richtung bewegen
        for(int i=0;i<20;i++) {

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
        Player player1 =  gameLogic.getPlayers().get(0);
        gameLogic.getPlayers().get(0).setCurrentCell(gameLogic.board.getCellById(1));
        Assertions.assertEquals(1, gameLogic.board.getCellById(1).getColorId());
        gameLogic.getPlayers().get(0).setCurrentCell(gameLogic.board.getCellById(99));
        Assertions.assertEquals(1, gameLogic.board.getCellById(99).getColorId());
        Assertions.assertEquals(3,gameLogic.getPlayers().get(0).getPaintedCells().size());

        // Wenn schon gefärbt ist sollte der Spieler sich nicht dahin bewegen können
        player1.setCurrentCell(gameLogic.board.getCellById(1));
        Assertions.assertEquals(player1.getCurrentCell(),gameLogic.board.getCellById(99));

    }



    public int randomIntBetween(int min,int max) {
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
