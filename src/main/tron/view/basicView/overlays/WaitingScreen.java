package tron.view.basicView.overlays;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tron.view.basicView.components.boardHandler.interfaces.IViewHandler;
import tron.view.basicView.components.viewHandler.impl.ViewUtility;

public class WaitingScreen extends VBox {

    private final Label labelWaitingProgress;
    private final ProgressBar progressBar;
    private final Double progress;
    private final Label labelPlayersReady;
    public WaitingScreen(String stylesheet, IViewHandler view) {
        super(20.0);
        this.getStylesheets().add(stylesheet);
        this.setAlignment(Pos.CENTER);

        labelWaitingProgress = new Label("waiting on players");
        progress = 0.1;
        progressBar = new ProgressBar(progress);
        //TODO dynamic x and y
        labelPlayersReady = new Label("x of y players are ready");

        //TODO progression of progressbar
        /*
        if (new player joins)
         progress += 1 - x/y players
         */

        this.getChildren().add(labelWaitingProgress);
        this.getChildren().add(progressBar);
        this.getChildren().add(labelPlayersReady);
    }
}
