package tron.model.base.persistenz;

import java.util.Arrays;

public class Board {
    public int height, width;
    public BoardCell[] cells;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.cells = createCells(height, width);
    }

    // Ids gehen von 0 bis WIDHT*HEIGTH-1 aber x und y fangen bei 1 an und gehen bis einschließlich HEIGHT oder WIDTH
    // id der Zelle soll == position der Array entsprechen
    public BoardCell[] createCells(int height, int width) {
        BoardCell[] cells = new BoardCell[height * width];
        int idCounter = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[idCounter] = new BoardCell(x, y, idCounter, 0);
                idCounter++;
            }
        }
        return cells;
    }

    public BoardCell getCellById(int id) {
        return cells[id];
    }

    public BoardCell[] getCells() {
        return cells;
    }

    @Override
    public String toString() {
        return "Board{" +
                "height=" + height +
                ", width=" + width +
                ", cells=" + Arrays.toString(cells) +
                '}';
    }

    public String toStringTest() {
        int cellsId=0;
        String ausgabe = "";
        for(int i=0;i<this.height;i++) {
            String line = "";
            for(int j=0;j<this.width;j++) {
                line += this.cells[cellsId++].colorId + " ";
            }
            ausgabe += line + "\n";
        }
        return ausgabe;
    }
}
