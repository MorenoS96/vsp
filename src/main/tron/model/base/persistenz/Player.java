package tron.model.base.persistenz;

import java.util.List;

public class Player {
    int id;
    String color;
    char currentDirection;
    BoardCell currentCell;
    private List<BoardCell> paintedCells;
    final char moveUp, moveDown, moveLeft, moveRight;

    public Player(int id, String color, BoardCell currentCell, List<BoardCell> paintedCells, char moveUp, char moveDown, char moveLeft, char moveRight) {
        this.id = id;
        this.color = color;
        this.currentCell = currentCell;
        this.paintedCells = paintedCells;
        this.currentDirection = moveUp;
        this.moveUp = moveUp;
        this.moveDown = moveDown;
        this.moveLeft = moveLeft;
        this.moveRight = moveRight;
    }

    public void move(char direction,Board board) { //TODO
        paintedCells.add(currentCell); // Alte Position als gefärbt markieren
        BoardCell previousCell = currentCell;
        if (direction==moveUp) {
            currentDirection = moveUp;
            // vermutlich checks
            currentCell = board.getCells()[0];
        } else if (direction==moveDown) {

        } else if (direction==moveRight) {

        } else if (direction == moveLeft) {

        } else {

        }


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
