package sample.logic;

import sample.Constants;
import sample.DAL.MapMaker;
import sample.entities.Command;
import sample.entities.IWeapon;
import sample.entities.Map;
import sample.entities.mapObjects.GameObject;
import sample.entities.mapObjects.Monster;
import sample.entities.mapObjects.Player;
import sample.entities.things.Thing;
import sample.enums.Activities;
import sample.enums.Directions;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameLogic {

    //TODO: handle exceptions
    public GameLogic(int width, int height){
        map = MapMaker.makeRandomMap(width, height);
        pause = false;
        updateTimer = new Timer();
        logicRandom = new Random();
        fpsCounter = 0;
        fps = 0;
        lastFPSCount = LocalTime.now();
        removeList = new ArrayList<>();
    }

    public GameLogic(Map loadedMap){
        map = loadedMap;
        pause = false;
        updateTimer = new Timer();
        logicRandom = new Random();
        fpsCounter = 0;
        fps = 0;
        lastFPSCount = LocalTime.now();
        removeList = new ArrayList<>();
    }


    private Map map;
    private Timer updateTimer;
    private Random logicRandom;
    private int fpsCounter;
    private int fps;
    private LocalTime lastFPSCount;
    private boolean pause;
    private ArrayList<GameObject> removeList;

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
        if(!pause) {
            randomMoveMonsters();
            for (GameObject o : map.getGameObjects()) {
                if (!o.isEnabled()) {
                    moveObject(o);
                }
            }
            checkObjectsHp();
            if (LocalTime.now().isAfter(lastFPSCount.plusSeconds(1))) {
                lastFPSCount = LocalTime.now();
                fps = fpsCounter;
                fpsCounter = 0;
            } else {
                fpsCounter++;
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
            case SpinWeapon:{
                if(cmd.getObject() instanceof Player){
                    ((Player)cmd.getObject()).spinWeapon();
                }
            }


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
        if(obj instanceof Player){
            for(IWeapon weapon: ((Player) obj).getWeapons()){
                if(weapon instanceof Thing){
                    ((Thing)weapon).setX(obj.getX());
                    ((Thing)weapon).setY(obj.getY());
                }
            }
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


    private void randomMoveMonsters() throws Exception {
        for(GameObject object: map.getGameObjects()){
            if(object instanceof Monster && object.isEnabled()){
                doCommand(new Command(Activities.Go, getRandomDirection(), object));
            }
        }
    }


    private Directions getRandomDirection(){
        int direction = logicRandom.nextInt(4);
        switch (direction){
            case 0:{
                return Directions.Up;
            }
            case 1:{
                return Directions.Right;
            }
            case 2:{
                return Directions.Down;
            }
            case 3:{
                return Directions.Left;
            }
        }
        return Directions.Up;
    }


    public void setPause(boolean pause){
        this.pause = pause;
    }


    public int getFps(){
        return this.fps;
    }

    private void checkObjectsHp(){
        removeList.clear();
        for(GameObject object: map.getGameObjects()){
            if(object.getHp() <= 0){
                removeList.add(object);
            }
        }
        for(GameObject object: removeList){
            map.getGameObjects().remove(object);
            for(int i = object.getX()-1; i <= object.getX()+1; i++){
                for(int j = object.getY()-1; j <= object.getY()+1; j++){
                    if(i >= 0 && j >= 0 && i < map.getSpaces().length && j < map.getSpaces()[0].length){
                        if(map.getSpaces()[i][j].getObject().equals(object)){
                            map.getSpaces()[i][j].setObject(null);
                        }
                    }
                }
            }
        }
    }

}
