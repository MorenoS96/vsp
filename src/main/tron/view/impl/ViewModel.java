package tron.view.impl;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tron.model.base.persistenz.Player;
import tron.registrator.interfaces.IRegistrator;
import tron.view.basicView.components.boardHandler.interfaces.IViewHandler;
import tron.view.basicView.components.viewHandler.impl.Coordinate;
import tron.view.basicView.components.viewHandler.impl.ViewHandler;
import tron.view.basicView.overlays.StartMenu;
import tron.view.interfaces.IViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewModel implements IViewModel {

    private IViewHandler view;
    private StartMenu startMenu;

    public ViewModel(Stage stage, IRegistrator iRegistrator) {

        try {
            this.view = new ViewHandler(iRegistrator);
        } catch (IOException e) {
            e.printStackTrace();
        }
        startMenu=new StartMenu("startMenu.css",view,iRegistrator);
        // configure and show stage
        stage.setTitle("TRON - Light Cycles");
        stage.setScene(this.view.getScene());
        stage.show();

    }

    @Override
    public void displayView(int view) {
        switch (view) {
            case 1:
                showStartMenu();
                break;
            case 2:
                showWaitScreen();
                break;
            case 3:
                showGame();
                break;
            case 4:
                showEndScreen();
        }

    }

    @Override
    public void displayBoard(List<Player> players) {
        for (Player player : players) {
            view.draw(getPlayerCoordinates(player), getPlayerColor(player.getColor()));
            view.highlightCell(new Coordinate(player.getCurrentCell().getX(), player.getCurrentCell().getY()), getPlayerColor(player.getColor()));
        }
    }

    public List<Coordinate> getPlayerCoordinates(Player player) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < player.getPaintedCells().size(); i++) {
            coordinates.add(0, new Coordinate(player.getPaintedCells().get(i).getX(), player.getPaintedCells().get(i).getY()));
        }
        coordinates.add(0, new Coordinate(player.getCurrentCell().getX(), player.getCurrentCell().getY()));
        return coordinates;
    }

    public Color getPlayerColor(int id) {
        switch (id) {
            case 1:
                return Color.BLUE;
            case 2:
                return Color.RED;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.YELLOW;
            case 5:
                return Color.PURPLE;
            case 6:
                return Color.ORANGE;
            default:
                System.out.println("Keine passende Farbe gefunden bzw zu viele Spieler");
                return null;
        }
    }

    public void showStartMenu() {
        view.hideOverlays();
        view.registerOverlay("startMenu", startMenu);
        view.showOverlay("startMenu");
    }

    public void showWaitScreen() {
        view.hideOverlays();
        view.registerOverlay("waitScreen", startMenu);
        view.showOverlay("waitScreen");
    }

    public void showGame() {
        view.hideOverlays();
    }

    public void showEndScreen() {
        view.hideOverlays();
        view.registerOverlay("endScreen", startMenu);
        view.showOverlay("endScreen");
    }

}
