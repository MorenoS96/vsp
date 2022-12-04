package tron.model.base.gamelogic.impl;

import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IControllerModel;
import tron.lobby.impl.BasicLobby;
import tron.lobby.interfaces.ILobby;
import tron.lobby.interfaces.IRegistrator;
import tron.lobby.util.InterfaceType;
import tron.model.base.inputhandler.interfaces.IInputHandler;
import tron.model.base.persistenz.ViewEnum;
import tron.model.interfaces.IModelController;
;
import tron.view.interfaces.IViewModel;

public class ModelController implements IModelController, IInputHandler {

    IRegistrator registrator;
    IControllerModel iControllerModel;
    IViewModel iViewModel; // Später

    ILobby iLobby;

    public ModelController(IRegistrator registrator) {
        this.registrator = registrator;
        iControllerModel=(IControllerModel)registrator.getInterfaceOfType(InterfaceType.IControllerModel);
        iViewModel=(IViewModel)registrator.getInterfaceOfType(InterfaceType.IViewModel);
        //iLobby=(ILobby) registrator.getInterfaceOfType(InterfaceType.ILobby);
    }


    @Override
    public void startGame(int playerCount) {
        if(iViewModel==null){
            iViewModel=(IViewModel)registrator.getInterfaceOfType(InterfaceType.IViewModel);
        }
        if(iControllerModel==null){
            iControllerModel=(IControllerModel)registrator.getInterfaceOfType(InterfaceType.IControllerModel);
        }
        iViewModel.displayView(ViewEnum.VIEW3.getViewId());
        GameLogic gameLogic = new GameLogic(iControllerModel,iViewModel);
        gameLogic.startGameThread();
    }

    @Override
    public void startApplication() throws InterruptedException {
        if(iViewModel==null){
            iViewModel=(IViewModel)registrator.getInterfaceOfType(InterfaceType.IViewModel);
        }
        if(iControllerModel==null){
            iControllerModel=(IControllerModel)registrator.getInterfaceOfType(InterfaceType.IControllerModel);
        }
        iLobby = new BasicLobby(PLAYER_COUNT,registrator); //TODO ersetzen
        iViewModel.displayView(ViewEnum.VIEW1.getViewId());

        // Detecten das jemand geklickt hat
        iViewModel.displayView(ViewEnum.VIEW2.getViewId());

        wait(WAIT_TIME_MILISEC);

    }
}
