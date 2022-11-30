package tron.view.impl;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tron.model.base.persistenz.Player;
import tron.view.basicView.components.viewHandler.impl.Coordinate;
import tron.view.basicView.components.viewHandler.impl.ViewHandler;
import tron.view.interfaces.IViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewModel implements IViewModel {

    private ViewHandler view;
    public ViewModel(Stage stage) {

        try {
            view = new ViewHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // configure and show stage
        stage.setTitle("TRON - Light Cycles");
        stage.setScene(view.getScene());
        stage.show();
    }

    @Override
    public void displayView(int view) {

    }

    @Override
    public void displayBoard(List<Player> players) {
        for (Player player : players) {
            view.draw(getPlayerCoordinates(player), getPlayerColor(player.getColor()));
            view.highlightCell(new Coordinate(player.getCurrentCell().getX(), player.getCurrentCell().getY()));
        }
    }

    public List<Coordinate> getPlayerCoordinates(Player player) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < player.getPaintedCells().size(); i++) {
            coordinates.add(new Coordinate(player.getPaintedCells().get(i).getX(), player.getPaintedCells().get(i).getY()));
        }
        coordinates.add(new Coordinate(player.getCurrentCell().getX(), player.getCurrentCell().getY()));
        return coordinates;
    }
    public Color getPlayerColor(String id) {
        switch (id) {
            case "1":
                return Color.BLUE;
            case "2":
                return Color.RED;
            case "3":
                return Color.GREEN;
            case "4":
                return Color.YELLOW;
            case "5":
                return Color.PURPLE;
            case "6":
                return Color.ORANGE;
            default:
                System.out.println("Keine passende Farbe gefunden bzw zu viele Spieler");
                return null;
        }
    }

}
