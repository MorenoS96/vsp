package tron.view.basicView.overlays;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;
import tron.model.base.persistenz.Player;
import tron.view.basicView.components.boardHandler.interfaces.IViewHandler;

public class GameOverScreen extends VBox {
    private final Label labelWinner;
    private Label labelCountdown;
    private final Label labelPLayer;
    private final Rectangle playerColor;
    private Integer counter;

    public GameOverScreen(String stylesheet, IViewHandler view, Player winner, Color color) {
        super(20.0);
        this.getStylesheets().add(stylesheet);
        this.setAlignment(Pos.CENTER);

        if (winner != null) {
            labelWinner = new Label("The winner is: ");
            HBox hb = new HBox();
            labelPLayer = new Label("Player " + winner.getId() + " ");
            playerColor = new Rectangle(20, 20, 20, 20);
            playerColor.setFill(color);
            hb.setAlignment(Pos.CENTER);
            hb.getChildren().add(labelPLayer);
            hb.getChildren().add(playerColor);
            //TODO counter einkommentieren

            this.getChildren().add(labelWinner);
            this.getChildren().add(hb);
        } else {
            labelWinner = new Label("You crashed at the same time. It's a draw!");
            labelCountdown = null;
            labelPLayer = null;
            playerColor = null;

            this.getChildren().add(labelWinner);
        }

        counter = 5;
        labelCountdown = new Label("Countdown for new Round: " + counter);
        this.getChildren().add(labelCountdown);
        Platform.runLater(() -> {

            while (counter > 0) {
                try {
                    Thread.sleep((long) 1000);
                    System.out.println(System.currentTimeMillis());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                counter--;
            }
        });
    }
}
