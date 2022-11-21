package tron.model.base.persistenz;

import java.util.List;

public class Board {
    int height,width;
    BoardCell[] cells;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.cells = createCells(height,width);
    }
    public Board(int height, int width, BoardCell[] cells) {
        this.height = height;
        this.width = width;
        this.cells = cells;
    }

    public BoardCell[] createCells(int height,int width) {
        BoardCell[] cells = new BoardCell[height*width];
        int idCounter = 0;
        for(int i=1;i<=width;i++) {
            for(int j=1;j<=height;j++) {
                BoardCell boardCell = new BoardCell(i,j,idCounter++);
                cells[idCounter] = boardCell;
            }
        }
        return cells;
    }

    public BoardCell[] getCells() {
        return cells;
    }

    public BoardCell getCellById(int id) {
        return cells[id];
    }
}
