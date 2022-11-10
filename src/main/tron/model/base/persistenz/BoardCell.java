package tron.model.base.persistenz;

public class BoardCell {
    int id;
    String color;

    public BoardCell(int id, String color) {
        this.id = id;
        this.color = color;
    }

    public void paintCell(String color) { //TODO

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
