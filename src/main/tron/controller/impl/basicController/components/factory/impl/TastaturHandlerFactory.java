package tron.controller.impl.basicController.components.factory.impl;

import tron.controller.impl.basicController.components.configHandler.interfaces.IGetConfig;
import tron.controller.impl.basicController.components.factory.interfaces.IFactory;
import tron.controller.impl.basicController.components.tastaturHandler.impl.TastaturHandler;
import tron.controller.interfaces.IConfig;

public class TastaturHandlerFactory implements IFactory<TastaturHandler> {
    private TastaturHandler tastaturHandler= null;
    private IGetConfig iGetConfig=null;

    public TastaturHandlerFactory(IGetConfig iGetConfig) {
        this.iGetConfig = iGetConfig;
    }

    @Override
    public TastaturHandler getInstance() {
        if(tastaturHandler==null){
            tastaturHandler=new TastaturHandler(iGetConfig);
        }
        return tastaturHandler;
    }

    public void setiGetConfig(IGetConfig iGetConfig) {
        this.iGetConfig = iGetConfig;
    }
}
