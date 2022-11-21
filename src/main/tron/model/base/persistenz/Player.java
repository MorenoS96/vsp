package tron.model.base.persistenz;

import tron.model.base.gamelogic.impl.GameLogic;
import tron.model.base.inputhandler.interfaces.IInputHandler;

import java.util.List;

public class Player implements IInputHandler {

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
     *
     * @param direction
     * @param board
     */
    public void move(char direction, Board board) {
        paintedCells.add(currentCell); // Alte Position als gefärbt markieren
        BoardCell previousCell = currentCell;
        int idPreviousCell = previousCell.getId();

        // Checkt ob Spieler eine 180 Grad Drehung machen würde, wenn ja, wird die Richtung einfach beibehalten
        if(moveCorrect(direction)) {
            direction = currentDirection;
        }

        if (direction == moveUp) {
            currentDirection = moveUp;
            int newCellId = idPreviousCell + WIDTH;
            if (GameLogic.checkBorder(currentCell.getId(), "moveUp") || GameLogic.checkCollision(board.getCellById(newCellId))) { // nochmal hübscher machen
                playerDies();
            }
            currentCell = board.getCellById(idPreviousCell + WIDTH);
        } else if (direction == moveDown) {
            currentDirection = moveDown;
            int newCellId = idPreviousCell - WIDTH;
            if (GameLogic.checkBorder(currentCell.getId(), "moveDown") || GameLogic.checkCollision(board.getCellById(newCellId))) { // nochmal hübscher machen
                playerDies();
            }
            currentCell = board.getCellById(idPreviousCell - WIDTH);
        } else if (direction == moveRight) {
            currentDirection = moveRight;
            int newCellId = idPreviousCell + 1;
            if (GameLogic.checkBorder(currentCell.getId(), "moveRight") || GameLogic.checkCollision(board.getCellById(newCellId))) { // nochmal hübscher machen
                playerDies();
            }
            currentCell = board.getCellById(idPreviousCell + 1);
        } else if (direction == moveLeft) {
            currentDirection = moveLeft;
            int newCellId = idPreviousCell - 1;
            if (GameLogic.checkBorder(currentCell.getId(), "moveLeft") || GameLogic.checkCollision(board.getCellById(newCellId))) { // nochmal hübscher machen
                playerDies();
            }
            currentCell = board.getCellById(idPreviousCell - 1);
        } else if (direction == ' ') {
            move(currentDirection, board);
        } else {
            // Do nothing
            System.out.println("Eingabe nicht gemapt");
        }

    }

    public void playerDies() { // Muss keinen Spieler übergeben wegen this ?

    }

    /**
     * Checkt, ob man eine 180 Grad Drehung machen möchte
     *
     * @return true wenn man sich Illegal bewegen möchte sonst false
     */
    public  boolean moveCorrect(Character InputDirection) {
        if (InputDirection == moveUp && currentDirection == moveDown ||
            InputDirection == moveDown && currentDirection == moveUp ||
            InputDirection == moveRight && currentDirection == moveLeft ||
            InputDirection == moveLeft && currentDirection == moveRight
        ) {
            return true;
        }
        return false;
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
