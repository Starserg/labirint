package sample.logic;

import sample.Constants;
import sample.DAL.MapMaker;
import sample.entities.Command;
import sample.entities.Map;
import sample.entities.mapObjects.GameObject;
import sample.enums.Directions;

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

    public void doCommand(Command cmd) throws Exception {
        switch (cmd.getActivity()){
            case Go:{
                if(cmd.getObject().isEnabled() && canObjGo(cmd.getObject(), cmd.getDirection())){
                    cmd.getObject().setDirectionOfMoving(cmd.getDirection());
                    occupySpaceBeforeMoving(cmd.getObject(), cmd.getDirection());
                    cmd.getObject().setEnabled(false);
                }
            }
            break;

            case Turn:{
                if(cmd.getObject().isEnabled()){
                    cmd.getObject().setDirection(cmd.getDirection());
                }
            }
            break;


            //TODO: add other variants
        }
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

    private boolean canObjGo(GameObject obj, Directions dir){
        switch (dir){
            case Up:{
                return (obj.getY()>0 && !map.getSpaces()[obj.getX()][obj.getY()].getWalls()[0] && map.getSpaces()[obj.getX()][obj.getY()-1].getObject() == null);
            }
            case Right:{
                return (obj.getX()+1 < map.getSpaces().length && !map.getSpaces()[obj.getX()][obj.getY()].getWalls()[1] && map.getSpaces()[obj.getX()+1][obj.getY()].getObject() == null);
            }
            case Down:{
                return (obj.getY()+1 < map.getSpaces()[0].length && !map.getSpaces()[obj.getX()][obj.getY()].getWalls()[2] && map.getSpaces()[obj.getX()][obj.getY()+1].getObject() == null);
            }
            case Left:{
                return (obj.getX() > 0 && !map.getSpaces()[obj.getX()][obj.getY()].getWalls()[3] && map.getSpaces()[obj.getX()-1][obj.getY()].getObject() == null);
            }
        }
        return false;
    }

    private void occupySpaceBeforeMoving(GameObject obj, Directions dir){
        switch (dir){
            case Up:{
                map.getSpaces()[obj.getX()][obj.getY()-1].setObject(obj);
            }
            break;
            case Right:{
                map.getSpaces()[obj.getX()+1][obj.getY()].setObject(obj);
            }
            break;
            case Down:{
                map.getSpaces()[obj.getX()][obj.getY()+1].setObject(obj);
            }
            break;
            case Left:{
                map.getSpaces()[obj.getX()-1][obj.getY()].setObject(obj);
            }
            break;
        }
    }

}
