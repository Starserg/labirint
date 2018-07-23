package sample.logic;

import sample.Constants;
import sample.DAL.MapMaker;
import sample.entities.Command;
import sample.entities.Map;
import sample.entities.mapObjects.GameObject;

import java.util.Timer;
import java.util.TimerTask;

public class GameLogic {

    //TODO: handle exceptions
    public GameLogic(int width, int height){
        map = MapMaker.makeRandomMap(width, height);
        updateTimer = new Timer();
    }

    private Map map;
    private Timer updateTimer;

    public Map getMap(){
        return this.map;
    }

    public void startGame(){
        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    updateMap();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, Constants.updateTimerPeriod, Constants.updateTimerPeriod);
    }


    private void updateMap() throws Exception {
        for(GameObject o: map.getGameObjects()){
            if(!o.isEnabled()){
                moveObject(o);
            }
        }
    }

    public void doCommand(Command cmd){

    }


    private void moveObject(GameObject obj) throws Exception {
        double newDelta = obj.getDelta()+obj.getSpeed();
        if(newDelta>=1){
            obj.setDelta(0);
            setObjectToNewSpace(obj);
        }
        else{
            obj.setDelta(newDelta);
        }
    }

    private void setObjectToNewSpace(GameObject obj) throws Exception {
        map.getSpaces()[obj.getX()][obj.getY()].setObject(null);
        switch (obj.getDirectionOfMoving()){
            case Up:{
                obj.setY(obj.getY()-1);
            }
            break;
            case Right:{
                obj.setX(obj.getX()+1);
            }
            break;
            case Down:{
                obj.setY(obj.getY()+1);
            }
            break;
            case Left:{
                obj.setX(obj.getX()-1);
            }
            break;
        }
        obj.setEnabled(true);
    }

}
