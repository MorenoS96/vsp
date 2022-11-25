package tron.model.base.persistenz;

enum ColorEnum {
    BLUE(1),
    RED(2),
    GREEN(3),
    YELLOW(4),
    PURPEL(5),
    ORANGE(6);

    private final int id;
    ColorEnum(int id) {
        this.id = id;
    }

    public int getId() {return id;}

}
