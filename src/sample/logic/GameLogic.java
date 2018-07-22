package sample.logic;

import sample.entities.Command;
import sample.entities.Map;
import sample.entities.mapObjects.GameObject;

public class GameLogic {

    private Map map;

    private void updateMap(){
        for(GameObject o: map.getGameObjects()){
            if(!o.isEnabled()){
                
            }
        }
    }

    public void doCommand(Command cmd){

    }

}
