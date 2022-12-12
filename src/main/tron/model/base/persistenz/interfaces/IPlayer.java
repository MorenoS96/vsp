package tron.model.base.persistenz.interfaces;

import tron.model.base.persistenz.Board;
import tron.model.base.persistenz.BoardCell;

import java.util.List;

public interface IPlayer {
    void move(char direction, Board board);

    void playerDies();

    void setCurrentCellColor(int color);

    void setCurrentCell(BoardCell nextCell);

    boolean isAlive();

    List<BoardCell> getPaintedCells();

    BoardCell getCurrentCell();
}
