package tron.model.base.persistenz;

enum ViewEnum {
    VIEW1("Startbildschirm"),
    VIEW2("Wartebildschirm"),
    VIEW3("Spielbildschirm"),
    VIEW4("Endbildschirm");
    private final String name;
    ViewEnum(String name) {
        this.name = name;
    }

    public String getName() {return name;}

}
