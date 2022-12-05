package tron.model.base.persistenz;

public enum ColorEnum { // Vllt intern benutzen und sonst 1-6 Ids geben
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

    public int getId() {
        return id;
    }

    public ColorEnum getPlayerColor(int id) { // throws Fehler? kann Julius übernehmen und für sein Color Enum benutzen
        switch (id) {
            case 1:
                return ColorEnum.BLUE;
            case 2:
                return ColorEnum.RED;
            case 3:
                return ColorEnum.GREEN;
            case 4:
                return ColorEnum.YELLOW;
            case 5:
                return ColorEnum.PURPEL;
            case 6:
                return ColorEnum.ORANGE;
            default:
                System.out.println("Keine passende Farbe gefunden bzw zu viele Spieler");
                return null;
        }
    }

}
