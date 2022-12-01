package tron.view.basicView.overlays;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tron.controller.interfaces.IControllerView;

import tron.view.basicView.components.boardHandler.interfaces.IViewHandler;
import tron.view.basicView.components.viewHandler.impl.ViewUtility;

public class StartMenu extends VBox {
    private final Label labelReady;
    private final Button btnStart;
    public IControllerView iControllerView;

    public StartMenu(String stylesheet, IViewHandler view) {
        super(20.0);
        this.getStylesheets().add(stylesheet);
        this.setAlignment(Pos.CENTER);

        labelReady = new Label("Ready?");
        labelReady.setStyle("-fx-text-fill: " + ViewUtility.getHexTriplet(Color.PAPAYAWHIP.brighter()) + ";");

        btnStart = new Button("Start Game");
        btnStart.setOnAction(event -> {
            System.out.println("click!");
            view.pushClick("startGameButton");
            view.hideOverlays();
        });

        this.getChildren().add(labelReady);
        this.getChildren().add(btnStart);
    }
}