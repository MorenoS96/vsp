package tron.model.base.persistenz;

import java.util.List;

public class Board {
    int height,width;
    List<BoardCell> cells;

    public Board(int height, int width, List<BoardCell> cells) {
        this.height = height;
        this.width = width;
        this.cells = cells;
    }

    public Board initBoard(int height, int width, List<BoardCell> cells) { //TODO
        Board board = new Board(height,width,cells);
        return board;
    }

}
