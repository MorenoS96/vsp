package tron.model.base.persistenz;

public enum ViewEnum { // Keine View, sondern IDs für Views
    VIEW1(1), // Startbildschirm
    VIEW2(2), // Wartebildschirm
    VIEW3(3), // Spielbildschirm
    VIEW4(4); // Endbildschirm
    private final int viewId;
    ViewEnum(int viewId) {
        this.viewId = viewId;
    }

    public int getViewId() {return viewId;}

}
