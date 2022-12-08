package tron.controller.impl.basicController.components.clickHandler.impl;
import tron.controller.impl.basicController.components.clickHandler.interfaces.IClick;
import tron.controller.impl.basicController.components.gameLoopManager.interfaces.IGameLoop;

public class ClickHandler implements IClick {
    private IGameLoop iGameLoop;
    public ClickHandler(IGameLoop iGameLoop) {
        this.iGameLoop=iGameLoop;
    }

    @Override
    public void pushClick(String elementIdentifier) {
        switch (elementIdentifier){
            case "startGameButton":
                iGameLoop.startGame();

        }
    }

    @Override
    public void pushInput(String elementIdentifier, String input) {
            switch (elementIdentifier){
                case "changePlayerCount":
                    System.out.println(input);
                    int newPlayerCount=Integer.valueOf(input);
                    if(newPlayerCount>=2 && newPlayerCount<=6){
                        iGameLoop.setPlayerCount(newPlayerCount);
                    }
            }
    }
}
