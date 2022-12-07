package tron.view.basicView.overlays;

import com.sun.javafx.scene.control.InputField;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tron.controller.interfaces.IControllerView;

import tron.view.basicView.components.boardHandler.interfaces.IViewHandler;
import tron.view.basicView.components.viewHandler.impl.ViewUtility;

public class StartMenu extends VBox {
    private final Label labelReady;
    private final Button btnStart;

    private final Label labelPlayerCount;
    private final TextField inputPlayerCount;
    public IControllerView iControllerView;

    public StartMenu(String stylesheet, IViewHandler view) {
        super(20.0);
        this.getStylesheets().add(stylesheet);
        this.setAlignment(Pos.CENTER);

        labelReady = new Label("Ready?");
        labelReady.setStyle("-fx-text-fill: " + ViewUtility.getHexTriplet(Color.PAPAYAWHIP.brighter()) + ";");

        //inputfield for desired playerCount
        labelPlayerCount = new Label("Player Count (default is 2): ");
        inputPlayerCount = new TextField ();
        HBox hb = new HBox();
        hb.getChildren().add(inputPlayerCount);
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);

        btnStart = new Button("Start Game");
        btnStart.setOnAction(event -> {
            System.out.println("click!");
            view.pushClick("startGameButton");
            view.pushInput("changePlayerCount", inputPlayerCount.getText());
            view.hideOverlays();
        });

        this.getChildren().add(labelReady);
        this.getChildren().add(btnStart);
        this.getChildren().add(labelPlayerCount);
        this.getChildren().add(hb);
    }
}
