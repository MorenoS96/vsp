package tron.model.interfaces.impl;

import tron.controller.interfaces.IControllerModel;
import tron.lobby.interfaces.IRegistrator;
import tron.lobby.util.InterfaceType;
import tron.model.base.gamelogic.impl.GameLogic;
import tron.model.base.inputhandler.interfaces.IInputHandler;
import tron.model.base.persistenz.ViewEnum;
import tron.model.interfaces.IModelController;
import tron.view.interfaces.IViewModel;

public class ModelController implements IModelController, IInputHandler {

    IRegistrator registrator;
    IControllerModel iControllerModel;
    IViewModel iViewModel;

    public ModelController(IRegistrator registrator) {
        this.registrator = registrator;
        iControllerModel = (IControllerModel) registrator.getInterfaceOfType(InterfaceType.IControllerModel);
        iViewModel = (IViewModel) registrator.getInterfaceOfType(InterfaceType.IViewModel);
    }


    @Override
    public void startGame(int playerCount) {
        if (iViewModel == null) {
            iViewModel = (IViewModel) registrator.getInterfaceOfType(InterfaceType.IViewModel);
        }
        if (iControllerModel == null) {
            iControllerModel = (IControllerModel) registrator.getInterfaceOfType(InterfaceType.IControllerModel);
        }
        iViewModel.displayView(ViewEnum.VIEW3.getViewId());
        GameLogic gameLogic = new GameLogic(iControllerModel, iViewModel);
        gameLogic.setPlayerCount(playerCount);
        gameLogic.startGameThread();
    }

    @Override
    public void startApplication() {
        if (iViewModel == null) {
            iViewModel = (IViewModel) registrator.getInterfaceOfType(InterfaceType.IViewModel);
        }
        if (iControllerModel == null) {
            iControllerModel = (IControllerModel) registrator.getInterfaceOfType(InterfaceType.IControllerModel);
        }
        registrator.registerComponent(InterfaceType.IControllerModel, registrator);
        registrator.registerComponent(InterfaceType.IViewModel, iViewModel);

        iViewModel.displayView(ViewEnum.VIEW1.getViewId());
    }
}
