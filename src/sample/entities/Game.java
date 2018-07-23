package sample.entities;

import sample.logic.GameLogic;

public class Game {

    public Game(int mapWidth, int mapHeight){
        gameLogic = new GameLogic(mapWidth, mapHeight);
    }

    private GameLogic gameLogic;

    public Map getGameMap() {
        return gameLogic.getMap();
    }

    public void startGame(){
        gameLogic.startGame();
    }

}
