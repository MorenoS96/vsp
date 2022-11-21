package tron.model.base.persistenz;

import tron.model.base.gamelogic.GameLogic;

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

    /**
     * Setzt die current Position vom Spieler entsprechend der Richtung auf eine andere Zelle,
     * wenn eine Eingabe gemacht wird dann einfach über die if Kaskade sonst ist der char ' '
     * und es wird die Richtung genommen, die das letzte mal ausgewählt wurde oder der Standart Up.
     * @param direction
     * @param board
     */
    public void move(char direction,Board board) { //TODO
        paintedCells.add(currentCell); // Alte Position als gefärbt markieren
        BoardCell previousCell = currentCell;
        if (direction==moveUp) {
            currentDirection = moveUp;
            // vermutlich checks hierhin
            // ob in die Richtung bewegen darf, ob gegen gefärbe zelle fährt, ob aus dem Board fährt
            int idPreviousCell = previousCell.getId();
            currentCell = board.getCellById(idPreviousCell + 10); // + WIDTH
        } else if (direction==moveDown) {
            currentDirection = moveDown;
            int idPreviousCell = previousCell.getId();
            currentCell = board.getCellById(idPreviousCell - 10); // + WIDTH
        } else if (direction==moveRight) {
            currentDirection = moveRight;
            int idPreviousCell = previousCell.getId();
            currentCell = board.getCellById(idPreviousCell + 1);
        } else if (direction == moveLeft) {
            currentDirection = moveLeft;
            int idPreviousCell = previousCell.getId();
            currentCell = board.getCellById(idPreviousCell - 1);
        } else if(direction == ' '){
            move(currentDirection,board);
        } else {
            // Do nothing
            System.out.println("Eingabe nicht gemapt");
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
