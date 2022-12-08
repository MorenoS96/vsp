package tron.view.basicView.overlays;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import tron.controller.interfaces.IConfig;
import tron.lobby.util.InterfaceType;
import tron.view.basicView.components.boardHandler.interfaces.IViewHandler;

public class WaitingScreen extends VBox {

    private final Label labelWaitingProgress;
    private final ProgressBar progressBar;
    private Double progress = 0.0;
    private final Label labelPlayersReady;
    private final IConfig iConfig;
    public WaitingScreen(String stylesheet, IViewHandler view) {
        super(20.0);
        this.getStylesheets().add(stylesheet);
        this.setAlignment(Pos.CENTER);

        iConfig = (IConfig) view.getRegistrator().getInterfaceOfType(InterfaceType.IConfig);
        labelWaitingProgress = new Label("waiting on players");
        //TODO dynamic x and y
        double y = Integer.parseInt(iConfig.getConfigVal("defaultPlayerCount"));
        double x = 1.0;
        labelPlayersReady = new Label("x of" + y + "players are ready");

        progress = x/y;
        System.out.println(progress);
        progressBar = new ProgressBar(progress);

        this.getChildren().add(labelWaitingProgress);
        this.getChildren().add(progressBar);
        this.getChildren().add(labelPlayersReady);
    }
}
