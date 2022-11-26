package tron.test.model;

import org.junit.jupiter.api.Test;
import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IControllerModel;
import tron.model.base.gamelogic.impl.GameLogic;
import tron.model.base.inputhandler.interfaces.IInputHandler;
import tron.model.base.persistenz.Board;
import tron.model.base.persistenz.BoardCell;
import tron.registrator.impl.Registrator;
import tron.view.interfaces.IViewModel;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBoard implements IInputHandler {

    Registrator registrator = new Registrator();
    IControllerModel iControllerModel = new BasicController(registrator);
    IViewModel iViewModel = null; // Später
    GameLogic gameLogic = new GameLogic(iControllerModel,iViewModel);

    @Test
    public void testBoard(){
        Board board = gameLogic.board;
        System.out.println(board);

        for(BoardCell boardCell: board.getCells()) {
            assertTrue(boardCell.getId() >=0 && boardCell.getId() < WIDTH*HEIGHT);
            assertTrue(boardCell.getX() >=0 && boardCell.getX() <WIDTH);
            assertTrue(boardCell.getY() >=0 && boardCell.getY() <HEIGHT);
        }
    }

}
