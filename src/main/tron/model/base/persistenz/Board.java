package tron.model.base.persistenz;

import java.util.List;

public class Board {
    int height,width;
    BoardCell[] cells;

    public Board(int height, int width, BoardCell[] cells) {
        this.height = height;
        this.width = width;
        this.cells = cells;
    }

    public Board initBoard(int height, int width,BoardCell[] cells) { //TODO
        return new Board(height,width,cells);
    }

    public BoardCell[] getCells() {
        return cells;
    }
}
