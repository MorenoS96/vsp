package tron.model.base.inputhandler.interfaces;

import tron.controller.impl.basicController.composite.BasicController;
import tron.registrator.impl.Registrator;

import java.util.Map;

public interface IInputHandler {

    Map<String,String> config = new BasicController(new Registrator()).getConfig();

    int WIDTH =  Integer.parseInt(config.get("horizontalRasterPoints"));
    int HEIGHT =  Integer.parseInt(config.get("verticalRasterPoints"));
    int PLAYER_COUNT = Integer.parseInt(config.get("defaultPlayerCount"));

}
