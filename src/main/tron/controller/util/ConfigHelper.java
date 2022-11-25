package tron.controller.util;

public class ConfigHelper {
    private static String[] moveConfigNames=new String[]{
            "player1MoveRight","player1MoveLeft","player1MoveUp","player1MoveDown",
            "player2MoveRight","player2MoveLeft","player2MoveUp","player2MoveDown",
            "player3MoveRight","player3MoveLeft","player3MoveUp","player3MoveDown",
            "player4MoveRight","player4MoveLeft","player4MoveUp","player4MoveDown",
            "player5MoveRight","player5MoveLeft","player5MoveUp","player5MoveDown",
            "player6MoveRight","player6MoveLeft","player6MoveUp","player6MoveDown",
    };
    public static String[] getMoveConfigNames(){
        return moveConfigNames.clone();
    }
}
