package tron.model.base.persistenz;

import tron.model.base.inputhandler.interfaces.IInputHandler;

import java.util.List;

public class Player implements IInputHandler {

    int id;
    String color;
    char currentDirection;
    BoardCell currentCell;
    private List<BoardCell> paintedCells;
    char moveUp, moveDown, moveLeft, moveRight;

    boolean isAlive = false;


    public Player(int id, String color, BoardCell currentCell, List<BoardCell> paintedCells) {

        String moveUpKey = "player" + id + 1 + "MoveUp";
        String moveUpString = config.get(moveUpKey);
        char moveUp = moveUpString.charAt(0);

        String moveDownKey = "player" + id + 1 + "MoveDown";
        String moveDownString = config.get(moveDownKey);
        char moveDown = moveDownString.charAt(0);

        String moveLeftKey = "player" + id + 1 + "MoveLeft";
        String moveLeftString = config.get(moveLeftKey);
        char moveLeft = moveLeftString.charAt(0);

        String moveRightKey = "player" + id + 1 + "MoveRight";
        String moveRightString = config.get(moveRightKey);
        char moveRight = moveRightString.charAt(0);
        new Player(id,color,currentCell,paintedCells,moveUp,moveDown,moveLeft,moveRight,true);
    }

    public Player(int id, String color, BoardCell currentCell, List<BoardCell> paintedCells, char moveUp, char moveDown, char moveLeft, char moveRight,boolean isAlive) {
        this.id = id;
        this.color = color;
        this.currentCell = currentCell;
        this.paintedCells = paintedCells;
        this.currentDirection = moveUp;
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
            if (checkBorder(currentCell.getId(), "moveUp") || checkCollision(board.getCellById(newCellId))) { // nochmal hübscher machen
                playerDies();
                return;
            }
            currentCell = board.getCellById(idPreviousCell + WIDTH);
        } else if (direction == moveDown) {
            currentDirection = moveDown;
            int newCellId = idPreviousCell - WIDTH;
            if (checkBorder(currentCell.getId(), "moveDown") || checkCollision(board.getCellById(newCellId))) { // nochmal hübscher machen
                playerDies();
                return;
            }
            currentCell = board.getCellById(idPreviousCell - WIDTH);
        } else if (direction == moveRight) {
            currentDirection = moveRight;
            int newCellId = idPreviousCell + 1;
            if (checkBorder(currentCell.getId(), "moveRight") || checkCollision(board.getCellById(newCellId))) { // nochmal hübscher machen
                playerDies();
                return;
            }
            currentCell = board.getCellById(idPreviousCell + 1);
        } else if (direction == moveLeft) {
            currentDirection = moveLeft;
            int newCellId = idPreviousCell - 1;
            if (checkBorder(currentCell.getId(), "moveLeft") || checkCollision(board.getCellById(newCellId))) { // nochmal hübscher machen
                playerDies();
                return;
            }
            currentCell = board.getCellById(idPreviousCell - 1);
        } else if (direction == ' ') {
            move(currentDirection, board);
            //TODO checks
        } else {
            // Do nothing
            System.out.println("Eingabe nicht gemapt");
        }
        currentCell.setColor(this.getColor());
    }

    public void playerDies() { // Muss keinen Spieler übergeben wegen this ?

        isAlive = false;
        // Zellen werden alle wieder entfärbt
        for(BoardCell boardCell: this.paintedCells) {
            boardCell.setColor(" ");
        }

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

    /**
     * Ist "" wenn nicht gefärbt sonst sollte da eine Farbe drin stehen
     *
     * @param nextBoardCell Nächsten Zelle
     * @return true, wenn nächste Zelle schon gefärbt ist, false, wenn nicht
     */
    public static boolean checkCollision(BoardCell nextBoardCell) {
        return !nextBoardCell.getColor().isEmpty();
    }

    //TODO alle bwewegungen danachen töten
    /**
     * Wenn der nächste Block über das Raster hinausgeht, wird true ausgegeben sonst false.
     * Diesen check zuerst ausführen damit die anderen keine Zellen abfragen die nicht existieren dürfen.
     *
     * @param cellId    von welcher Zelle aus gecheckt werden soll
     * @param direction in welche Richtung man sich bewegt
     * @return true oder false
     */
    public static boolean checkBorder(int cellId, String direction) {
        int nextCellId;
        boolean nextCellIsBorder = false;
        switch (direction) {
            case "moveUp":
                nextCellId = cellId + WIDTH;
                nextCellIsBorder = nextCellId <= (WIDTH * HEIGHT - 1); // Nächste ZellenId muss kleiner= sein als max Zellen ID
                break;
            case "moveDown":
                nextCellId = cellId - WIDTH;
                nextCellIsBorder = nextCellId >= 0; // Nächste ZellenId muss größer= sein als 0
                break;
            case "moveRight":
                nextCellIsBorder = (cellId + 1 % WIDTH == 0); // Wenn Zelle am rechten Rand, dann nicht nach rechts bewegen.
                break;
            case "moveLeft":
                nextCellIsBorder = (cellId % WIDTH == 0); // Wenn Zelle ganz links, dann nicht nach links bewegen.
                break;
        }
        return nextCellIsBorder;
    }

    public void setCurrentCellColor() {
        currentCell.setColor(this.getColor());
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

    public boolean isAlive() {
        return isAlive;
    }
}
