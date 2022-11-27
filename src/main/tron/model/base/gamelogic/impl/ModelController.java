package tron.model.base.gamelogic.impl;

import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IControllerModel;
import tron.model.base.persistenz.ViewEnum;
import tron.model.interfaces.IModelController;
import tron.registrator.impl.Registrator;
import tron.view.interfaces.IViewModel;

public class ModelController implements IModelController {

    Registrator registrator = new Registrator();
    IControllerModel iControllerModel = new BasicController(registrator);
    IViewModel iViewModel = null; // Später


    @Override
    public void startGame(int playerCount) {
        iViewModel.displayView(ViewEnum.VIEW3.getViewId());
        new GameLogic(iControllerModel,iViewModel);
    }
}
