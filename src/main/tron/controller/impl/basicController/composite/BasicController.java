package tron.controller.impl.basicController.composite;

import tron.controller.impl.basicController.components.configHandler.impl.ConfigHandler;
import tron.controller.impl.basicController.components.configHandler.interfaces.IGetConfig;
import tron.controller.impl.basicController.components.tastaturHandler.impl.TastaturHandler;
import tron.controller.impl.basicController.components.tastaturHandler.interfaces.IGetInput;
import tron.controller.interfaces.IController;

public class BasicController implements IController {
    IGetConfig iGetConfig;
    IGetInput iGetInput;

    public BasicController() {
        this.iGetConfig = new ConfigHandler();
        this.iGetInput = new TastaturHandler(iGetConfig);
    }

    @Override
    public String getConfigVal(String ConfigName) {
        return iGetConfig.getConfigVal(ConfigName);
    }

    @Override
    public void setConfigPath(String ConfigPath) {
        iGetConfig.setConfigPath(ConfigPath);
    }

    @Override
    public String getInput() {
        return iGetInput.getInput();
    }
}
