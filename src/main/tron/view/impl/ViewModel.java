package tron.view.impl;

import javafx.stage.Stage;
import tron.model.base.persistenz.Player;
import tron.view.basicView.components.viewHandler.impl.ViewHandler;
import tron.view.interfaces.IViewModel;

import java.io.IOException;
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

    }
}
