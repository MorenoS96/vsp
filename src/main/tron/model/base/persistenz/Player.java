package tron.model.base.persistenz;

import tron.model.base.inputhandler.interfaces.IInputHandler;

import java.util.List;

public class Player implements IInputHandler {

    int id;

    int color;

    char currentDirection;

    BoardCell currentCell;

    private final List<BoardCell> paintedCells;

    char moveUp, moveDown, moveLeft, moveRight;

    boolean isAlive;

    public Player(int id, int color, BoardCell currentCell, List<BoardCell> paintedCells, char moveUp, char moveDown, char moveLeft, char moveRight, boolean isAlive) {
        this.id = id;
        this.color = color;
        this.currentCell = currentCell;
        this.paintedCells = paintedCells;
        this.currentDirection = moveDown;
        this.moveUp = moveUp;
        this.moveDown = moveDown;
        this.moveLeft = moveLeft;
        this.moveRight = moveRight;
        this.isAlive = isAlive;
    }

    /**
     * Setzt die current Position vom Spieler entsprechend der Richtung auf eine andere Zelle,
     * wenn eine Eingabe gemacht wird dann einfach über die if Kaskade sonst ist der char ' '
     * und es wird die Richtung genommen, die das letzte mal ausgewählt wurde oder der Standart Up.
     *
     * @param direction richtung
     * @param board board
     */
    public void move(char direction, Board board) { //TODO Tests
        BoardCell previousCell = currentCell;
        int idPreviousCell = previousCell.getId();

        // Checkt ob Spieler eine 180 Grad Drehung machen würde, wenn ja, wird die Richtung einfach beibehalten
        if (isMoveIncorrect(direction)) {
            direction = currentDirection;
        }

        if (direction == moveUp) {
            currentDirection = moveUp;
            int newCellId = idPreviousCell - WIDTH;
            if (checkBorder(currentCell.getId(), "moveUp") || checkCollision(board.getCellById(newCellId))) { // nochmal hübscher machen
                this.isAlive = false;
                return;
            }
            currentCell = board.getCellById(newCellId);
        } else if (direction == moveDown) {
            currentDirection = moveDown;
            int newCellId = idPreviousCell + WIDTH;
            if (checkBorder(currentCell.getId(), "moveDown") || checkCollision(board.getCellById(newCellId))) { // nochmal hübscher machen
                this.isAlive = false;
                return;
            }
            currentCell = board.getCellById(newCellId);
        } else if (direction == moveRight) {
            currentDirection = moveRight;
            int newCellId = idPreviousCell + 1;
            if (checkBorder(currentCell.getId(), "moveRight") || checkCollision(board.getCellById(newCellId))) { // nochmal hübscher machen
                this.isAlive = false;
                return;
            }
            currentCell = board.getCellById(newCellId);
        } else if (direction == moveLeft) {
            currentDirection = moveLeft;
            int newCellId = idPreviousCell - 1;
            if (checkBorder(currentCell.getId(), "moveLeft") || checkCollision(board.getCellById(newCellId))) { // nochmal hübscher machen
                this.isAlive = false;
                return;
            }
            currentCell = board.getCellById(newCellId);
        } else if (direction == ' ') {
            move(currentDirection, board);
            //TODO wie genau tötet man spieler
        } else {
            System.out.println("Eingabe nicht gemapt, letzter richtige Input wird genommen");
            move(currentDirection,board);
        }

        //Position färben und als gefärbt markieren
        currentCell.setColorId(this.getColor());
        paintedCells.add(currentCell);
    }

    public void playerDies() { // Muss keinen Spieler übergeben wegen this ? TODO Tests
        // Zellen werden alle wieder entfärbt
        for (BoardCell boardCell : this.paintedCells) {
            boardCell.setColorId(0);
        }

    }

    /**
     * Checkt, ob man eine 180 Grad Drehung machen möchte
     *
     * @return true wenn man sich Illegal bewegen möchte sonst false
     */
    public boolean isMoveIncorrect(Character InputDirection) { //TODO Tests
        if (InputDirection == moveUp && currentDirection == moveDown ||
                InputDirection == moveDown && currentDirection == moveUp ||
                InputDirection == moveRight && currentDirection == moveLeft ||
                InputDirection == moveLeft && currentDirection == moveRight
        ) {
            return true;
        }
        return false;
    }

    /**
     * Ist 0, wenn nicht gefärbt sonst sollte da eine Farbe drin stehen
     *
     * @param nextBoardCell Nächsten Zelle
     * @return true, wenn nächste Zelle schon gefärbt ist, false, wenn nicht
     */
    public static boolean checkCollision(BoardCell nextBoardCell) {
        return nextBoardCell.getColorId() != 0;
    } //TODO Tests

    /**
     * Wenn der nächste Block über das Raster hinausgeht, wird true ausgegeben sonst false.
     * Diesen check zuerst ausführen damit die anderen keine Zellen abfragen die nicht existieren dürfen.
     *
     * @param cellId    von welcher Zelle aus gecheckt werden soll
     * @param direction in welche Richtung man sich bewegt
     * @return true oder false
     */
    public static boolean checkBorder(int cellId, String direction) { //TODO Tests
        int nextCellId;
        boolean nextCellIsBorder = false;
        switch (direction) {
            case "moveUp" -> {
                nextCellId = cellId - WIDTH;
                nextCellIsBorder = nextCellId < 0; // Nächste ZellenId darf nicht kleiner als 0 sein
            }
            case "moveDown" -> {
                nextCellId = cellId + WIDTH;
                nextCellIsBorder = nextCellId > (WIDTH * HEIGHT - 1);  // Nächste ZellenId darf nicht größer als höchste ID sein
            }
            case "moveRight" ->
                    nextCellIsBorder = (Math.abs(cellId-WIDTH-1) % WIDTH == 0); // Wenn Zelle am rechten Rand, dann nicht nach rechts bewegen.
            case "moveLeft" ->
                    nextCellIsBorder = (cellId % WIDTH == 0); // Wenn Zelle ganz links, dann nicht nach links bewegen.
        }
        return nextCellIsBorder;
    }

    public void setCurrentCellColor(int color) {
        this.currentCell.setColorId(color);
        this.paintedCells.add(currentCell);
    }


    public int getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public BoardCell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(BoardCell currentCell) {
        this.currentCell = currentCell;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public List<BoardCell> getPaintedCells() {
        return paintedCells;
    }
}
