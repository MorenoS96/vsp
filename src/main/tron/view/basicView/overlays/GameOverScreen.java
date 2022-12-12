package tron.view.basicView.overlays;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
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
            Timeline timeline = new Timeline();
            counter = 5;
            labelCountdown = new Label();
            KeyFrame kf = new KeyFrame(Duration.seconds(0),
                    event -> {
                        labelCountdown.setText(String.valueOf(counter));
                        System.out.println(counter);
                        if (counter <= 0) {
                            timeline.stop();
                            System.out.println("timeline stopped");
                        }
                        counter--;
                    });
            timeline.getKeyFrames().addAll(kf, new KeyFrame(Duration.seconds(1)));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
            this.getChildren().add(labelWinner);
            this.getChildren().add(hb);
            this.getChildren().add(labelCountdown);
        } else {
            labelWinner = new Label("You crashed at the same time. It's a draw!");
            labelPLayer = null;
            playerColor = null;
            Timeline timeline = new Timeline();
            counter = 5;
            labelCountdown = new Label();
            KeyFrame kf = new KeyFrame(Duration.seconds(0),
                    event -> {
                        labelCountdown.setText(String.valueOf(counter));
                        System.out.println(counter);
                        if (counter <= 0) {
                            timeline.stop();
                            System.out.println("timeline stopped");
                        }
                        counter--;
                    });
            timeline.getKeyFrames().addAll(kf, new KeyFrame(Duration.seconds(1)));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            this.getChildren().add(labelWinner);
            this.getChildren().add(labelCountdown);
        }
    }
}
