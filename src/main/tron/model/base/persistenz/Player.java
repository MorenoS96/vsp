package tron.model.base.persistenz;

import java.util.List;

public class Player {
    int id;
    String color;

    char currentDirection = 'U';
    BoardCell currentCell;
    List<BoardCell> paintedCells;

    public Player(int id, String color, BoardCell currentCell, List<BoardCell> paintedCells) {
        this.id = id;
        this.color = color;
        this.currentCell = currentCell;
        this.paintedCells = paintedCells;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BoardCell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(BoardCell currentCell) {
        this.currentCell = currentCell;
    }
}
