package tron;

import javafx.application.Application;
import javafx.stage.Stage;


import tron.controller.impl.basicController.composite.BasicController;
import tron.controller.interfaces.IController;
import tron.controller.interfaces.IControllerModel;
import tron.model.base.gamelogic.impl.GameLogic;
import tron.model.base.gamelogic.impl.ModelController;
import tron.model.base.persistenz.ViewEnum;
import tron.model.interfaces.IModelController;
import tron.registrator.impl.Registrator;
import tron.registrator.interfaces.IRegistrator;
import tron.registrator.util.InterfaceType;
import tron.view.impl.ViewModel;
import tron.view.interfaces.IViewModel;

public class App extends Application {
    public static void main(String[] args) {


        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        IRegistrator iRegistrator=new Registrator();
        IModelController iModelController = new ModelController(iRegistrator);
        iRegistrator.registerComponent(InterfaceType.IModelController,iModelController);

        IController iController=new BasicController(iRegistrator);

        IViewModel viewModel=new ViewModel(stage,iRegistrator);
        iRegistrator.registerComponent(InterfaceType.IViewModel,viewModel);
        viewModel.displayView(ViewEnum.VIEW1.getViewId());
    }
}
