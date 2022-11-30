package tron.model.base.gamelogic.impl;

import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IControllerModel;
import tron.lobby.impl.Registrator;
import tron.model.base.persistenz.ViewEnum;
import tron.model.interfaces.IModelController;
import tron.view.interfaces.IViewModel;

public class ModelController implements IModelController {

    Registrator registrator = new Registrator();
    IControllerModel iControllerModel = new BasicController(registrator);
    IViewModel iViewModel = null; // Später

    @Override
    public void startGame(int playerCount) {
        iViewModel.displayView(ViewEnum.VIEW3.getViewId());
        GameLogic gameLogic = new GameLogic(iControllerModel,iViewModel);
        gameLogic.startGameThread();
    }
}
