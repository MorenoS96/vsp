package tron.controller.util;

public class ConfigHelper {
    private static String[] moveConfigNames=new String[]{
            "player1MoveRight","player1MoveLeft",
            "player2MoveRight","player2MoveLeft",
            "player3MoveRight","player3MoveLeft",
            "player4MoveRight","player4MoveLeft",
            "player5MoveRight","player5MoveLeft",
            "player6MoveRight","player6MoveLeft"
    };
    public static String[] getMoveConfigNames(){
        return moveConfigNames.clone();
    }
}
