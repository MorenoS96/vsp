package tron.model.interfaces;

import tron.model.base.persistenz.Board;

public interface IInputHandler {

    String getRequest();

    Board displayBoard();
}
