package tron.view.basicView.overlays;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tron.controller.impl.basicController.components.configHandler.interfaces.IGetConfig;
import tron.controller.interfaces.IConfig;
import tron.controller.interfaces.IControllerView;

import tron.lobby.interfaces.IRegistrator;
import tron.lobby.util.InterfaceType;
import tron.view.basicView.components.boardHandler.interfaces.IViewHandler;
import tron.view.basicView.components.viewHandler.impl.ViewUtility;

public class StartMenu extends VBox {
    private final Label labelReady;
    private final Button btnStart;
    private final Label labelPlayerCount;
    private final Button btnConfirm;
    private final TextField inputPlayerCount;
    private IConfig iConfig;

    public StartMenu(String stylesheet, IViewHandler view) {
        super(20.0);
        this.getStylesheets().add(stylesheet);
        this.setAlignment(Pos.CENTER);

        iConfig = (IConfig) view.getRegistrator().getInterfaceOfType(InterfaceType.IConfig);
        int defaultPlayerCount = Integer.parseInt(iConfig.getConfigVal("defaultPlayerCount"));

        labelReady = new Label("Ready?");

        //inputfield for desired playerCount
        labelPlayerCount = new Label("Player Count (default is " + defaultPlayerCount + " ): ");
        inputPlayerCount = new TextField ();
        HBox hb = new HBox();
        hb.getChildren().add(inputPlayerCount);
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);

        btnConfirm = new Button("Confrim");
        btnConfirm.setOnAction(event -> {
            view.pushInput("changePlayerCount", inputPlayerCount.getText());
        });

        btnStart = new Button("Start Game");
        btnStart.setOnAction(event -> {
            System.out.println("click!");
            view.pushClick("startGameButton");
            view.hideOverlays();
        });

        this.getChildren().add(labelPlayerCount);
        this.getChildren().add(hb);
        this.getChildren().add(btnConfirm);
        this.getChildren().add(labelReady);
        this.getChildren().add(btnStart);
    }
}
