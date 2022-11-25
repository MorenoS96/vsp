package tron.model.base.persistenz;

public class Board {
    int height,width;
    BoardCell[] cells;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.cells = createCells(height,width);
    }

    // Ids gehen von 0 bis WIDHT*HEIGTH-1 aber x und y fangen bei 1 an und gehen bis einschließlich HEIGHT oder WIDTH
    public BoardCell[] createCells(int height,int width) {
        BoardCell[] cells = new BoardCell[height*width];
        int idCounter = 0;
        for(int y=0;y<height;y++) {
            for(int x=0;x<width;x++) {
                BoardCell boardCell = new BoardCell(x,y,idCounter,"");
                cells[idCounter] = boardCell;
                idCounter++;
            }
        }
        return cells;
    }

    public BoardCell getCellById(int id) {
        return cells[id];
    }
}
