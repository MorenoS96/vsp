package tron.model.base.persistenz;

public class BoardCell {
    private final int x, y, id;
    int colorId; // = 0
    // id der Zelle soll == position der Array entsprechen

    public BoardCell(int x, int y, int id, int color) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.colorId = color;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getColorId() {
        return colorId;
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

    @Override
    public String toString() {
        return "BoardCell{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                ", color='" + colorId + '\'' +
                '}';
    }
}
