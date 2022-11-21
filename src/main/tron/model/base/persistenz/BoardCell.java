package tron.model.base.persistenz;

public class BoardCell {
    private final int x, y, id;
    String color = "";
    // id der Zelle soll == position der Array entsprechen

    public BoardCell(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public BoardCell(int x, int y, int id, String color) {
        this.x = x;
        this.y = y;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }
}
