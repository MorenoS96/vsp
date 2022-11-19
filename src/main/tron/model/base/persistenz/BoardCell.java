package tron.model.base.persistenz;

public class BoardCell {
    int x,y;
    String color;

    public BoardCell(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
