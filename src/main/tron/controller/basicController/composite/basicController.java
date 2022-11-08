package tron.controller.basicController.composite;

import tron.controller.basicController.components.configHandler.impl.configHandler;
import tron.controller.basicController.components.configHandler.interfaces.IGetConfig;
import tron.controller.interfaces.IController;

public class basicController implements IController {
    IGetConfig iGetConfig = new configHandler();

    @Override
    public String getConfigVal(String ConfigName) {
        return iGetConfig.getConfigVal(ConfigName);
    }

    @Override
    public void setConfigPath(String ConfigPath) {
        iGetConfig.setConfigPath(ConfigPath);
    }
}
