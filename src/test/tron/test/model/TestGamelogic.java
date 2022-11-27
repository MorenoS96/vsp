package tron.test.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IControllerModel;
import tron.model.base.gamelogic.impl.GameLogic;
import tron.model.base.inputhandler.interfaces.IInputHandler;
import tron.model.base.persistenz.Player;
import tron.registrator.impl.Registrator;
import tron.view.interfaces.IViewModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGamelogic implements IInputHandler {

    Registrator registrator = new Registrator();
    IControllerModel iControllerModel = new BasicController(registrator);
    IViewModel iViewModel = null; // Später
    GameLogic gameLogic = new GameLogic(iControllerModel,iViewModel);

    @Test
    public void testGetPlayerStartingPositions(){
        int[] xWerte = gameLogic.getPlayerStartingPositions();
        for(int xWert:xWerte) {
            Assertions.assertTrue(xWert >= 0 && xWert <= gameLogic.board.width);
            System.out.println(xWert);
        }
    }

    @Test
    public void testInitPlayers(){
        List<Player> players = gameLogic.getPlayers();
        assertEquals(players.size(),PLAYER_COUNT);

        for(Player player:players) {
            assertEquals(player.getColor(),player.getId());
            System.out.println(player.getId());

            assertEquals(player.getPaintedCells().size(),1);

            assertEquals(player.getCurrentCell().getColorId(),player.getColor());
            assertEquals(0, player.getCurrentCell().getY());
        }
    }
}


