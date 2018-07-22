package sample.entities;

import sample.entities.mapObjects.GameObject;

import java.util.ArrayList;

public class Map {

    public Map(Space[][] spaces){
        this.spaces = spaces;
        this.gameObjects = new ArrayList<GameObject>();
        putGameObjectsToList();
    }

    private Space[][] spaces;

    private ArrayList<GameObject> gameObjects;

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    private void putGameObjectsToList(){
        for(int i = 0; i < spaces.length; i++){
            for(int j = 0; j < spaces[i].length; j++){
                if(spaces[i][j].getObject() != null){
                    gameObjects.add(spaces[i][j].getObject());
                }
            }
        }
    }

}
