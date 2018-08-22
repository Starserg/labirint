package sample.entities;

import sample.logic.GameLogic;

public class Game {

    public Game(int mapWidth, int mapHeight){
        gameLogic = new GameLogic(mapWidth, mapHeight);
    }

    public Game(Map loadedMap){
        gameLogic = new GameLogic(loadedMap);
    }

    private GameLogic gameLogic;

    public Map getGameMap() {
        return gameLogic.getMap();
    }

    public void startGame(){
        gameLogic.startGame();
    }


    public void doCommand(Command cmd) throws Exception {
        gameLogic.doCommand(cmd);
    }


    public int getLogicFPS(){
        return gameLogic.getFps();
    }

    public void setPause(boolean pause){
        gameLogic.setPause(pause);
    }


    public boolean isEndGame(){
        return gameLogic.isEndGame();
    }
}
