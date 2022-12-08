package tron.view.interfaces;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import tron.model.base.persistenz.Board;
import tron.model.base.persistenz.Player;

import java.util.List;

public interface IViewModel {
    void displayView(int view);

    void displayLastView(Player player);

    void displayBoard(List<Player> players);
}
