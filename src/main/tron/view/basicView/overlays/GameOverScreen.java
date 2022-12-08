package tron.view.basicView.overlays;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import tron.model.base.persistenz.Player;
import tron.view.basicView.components.boardHandler.interfaces.IViewHandler;

import java.util.concurrent.CountDownLatch;

public class GameOverScreen extends VBox {
    private final Label labelWinner;
    private final Label labelCountdown;
    private final Label labelPLayer;
    private final Rectangle playerColor;
    private Integer counter;

    public GameOverScreen(String stylesheet, IViewHandler view, Player winner, Color color) {
        super(20.0);
        this.getStylesheets().add(stylesheet);
        this.setAlignment(Pos.CENTER);

        labelWinner = new Label("The winner is: ");
        HBox hb = new HBox();
        labelPLayer = new Label("Player " + winner.getId() + " ");
        playerColor = new Rectangle(20, 20, 20, 20);
        //TODO dynamisches Laden der Daten
        playerColor.setFill(color);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().add(labelPLayer);
        hb.getChildren().add(playerColor);
        //TODO counter fixen
        counter = 5;
        while (counter > 0) {counter--;}
        labelCountdown = new Label("Countdown for new Round: " + counter);

        this.getChildren().add(labelWinner);
        this.getChildren().add(hb);
        this.getChildren().add(labelCountdown);
    }
}
