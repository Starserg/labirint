package sample.logic;

import javafx.scene.image.Image;
import sample.Constants;
import sample.DAL.MapMaker;
import sample.entities.Command;
import sample.entities.IWeapon;
import sample.entities.Map;
import sample.entities.Space;
import sample.entities.mapObjects.Box;
import sample.entities.mapObjects.GameObject;
import sample.entities.mapObjects.Monster;
import sample.entities.mapObjects.Player;
import sample.entities.things.Bomb;
import sample.entities.things.Pistol;
import sample.entities.things.Thing;
import sample.entities.things.Torch;
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
            break;
            case Attack:{
                if(cmd.getObject() instanceof Player){
                    if(((Player)cmd.getObject()).getTempWeapon() instanceof Pistol){
                        tryShot((Player)cmd.getObject(), cmd.getDirection());
                    }
                    else if(((Player)cmd.getObject()).getTempWeapon() instanceof Bomb){
                        tryBlowUp((Player)cmd.getObject(), cmd.getDirection());
                    }
                }
                else if(cmd.getObject() instanceof Monster){

                }
            }
            break;
            case Take:{
                if(cmd.getObject() instanceof Player){
                    tryTake((Player)cmd.getObject(), cmd.getDirection());
                }
            }
            break;

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

    private boolean canBulletGo(Player player, Directions dir){
        switch (dir){
            case Up:{
                return (player.getY()>0 && !map.getSpaces()[player.getX()][player.getY()].getWalls()[0]);
            }
            case Right:{
                return (player.getX()+1 < map.getSpaces().length && !map.getSpaces()[player.getX()][player.getY()].getWalls()[1]);
            }
            case Down:{
                return (player.getY()+1 < map.getSpaces()[0].length && !map.getSpaces()[player.getX()][player.getY()].getWalls()[2]);
            }
            case Left:{
                return (player.getX() > 0 && !map.getSpaces()[player.getX()][player.getY()].getWalls()[3]);
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

    //TODO: check user!!!
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
                        if(map.getSpaces()[i][j].getObject() != null && map.getSpaces()[i][j].getObject().equals(object)){
                            map.getSpaces()[i][j].setObject(null);
                        }
                    }
                }
            }
        }
    }



    private void tryShot(Player player, Directions direction){
        if(player.getLastShotTime().plusSeconds(Constants.secondsToPistolRecharge).isBefore(LocalTime.now()) && canBulletGo(player, direction)){
            boolean breakCycle = false;
            int tempX = player.getX();
            int tempY = player.getY();
            while (true){
                switch (direction){
                    case Up:{
                        tempY--;
                        if(tempY < 0){
                            breakCycle = true;
                        }
                        else if(map.getSpaces()[tempX][tempY].getObject() != null){
                            damageObject(map.getSpaces()[tempX][tempY].getObject(), Constants.pistolDamage);
                            breakCycle = true;
                        }
                        else if(map.getSpaces()[tempX][tempY].getWalls()[0]){
                            breakCycle = true;
                        }
                    }
                    break;
                    case Right:{
                        tempX++;
                        if(tempX >= map.getSpaces().length){
                            breakCycle = true;
                        }
                        else if(map.getSpaces()[tempX][tempY].getObject() != null){
                            damageObject(map.getSpaces()[tempX][tempY].getObject(), Constants.pistolDamage);
                            breakCycle = true;
                        }
                        else if(map.getSpaces()[tempX][tempY].getWalls()[1]){
                            breakCycle = true;
                        }
                    }
                    break;
                    case Down:{
                        tempY++;
                        if(tempY >= map.getSpaces()[0].length){
                            breakCycle = true;
                        }
                        else if(map.getSpaces()[tempX][tempY].getObject() != null){
                            damageObject(map.getSpaces()[tempX][tempY].getObject(), Constants.pistolDamage);
                            breakCycle = true;
                        }
                        else if(map.getSpaces()[tempX][tempY].getWalls()[2]){
                            breakCycle = true;
                        }
                    }
                    break;
                    case Left:{
                        tempX--;
                        if(tempX < 0){
                            breakCycle = true;
                        }
                        else if(map.getSpaces()[tempX][tempY].getObject() != null){
                            breakCycle = true;
                            damageObject(map.getSpaces()[tempX][tempY].getObject(), Constants.pistolDamage);
                        }
                        else if(map.getSpaces()[tempX][tempY].getWalls()[3]){
                            breakCycle = true;
                        }
                    }
                    break;
                }
                if(breakCycle){
                    break;
                }
            }

            player.setLastShotTime(LocalTime.now());
        }
    }

    private void damageObject(GameObject object, int damage){
        object.setHp(object.getHp()-damage);
    }

    private void tryTake(Player player, Directions direction){
        switch (direction){
            case Up:{
                if(player.getY()> 0 && map.getSpaces()[player.getX()][player.getY()-1].getObject() instanceof Box){
                    takeAllFromBox(player, (Box)map.getSpaces()[player.getX()][player.getY()-1].getObject());
                }
            }
            break;
            case Right:{
                if(player.getX() + 1 < map.getSpaces().length && map.getSpaces()[player.getX()+1][player.getY()].getObject() instanceof Box){
                    takeAllFromBox(player, (Box)map.getSpaces()[player.getX()+1][player.getY()].getObject());
                }
            }
            break;
            case Down:{
                if(player.getY()+1 < map.getSpaces()[0].length && map.getSpaces()[player.getX()][player.getY()+1].getObject() instanceof Box){
                    takeAllFromBox(player, (Box)map.getSpaces()[player.getX()][player.getY()+1].getObject());
                }
            }
            break;
            case Left:{
                if(player.getX()> 0 && map.getSpaces()[player.getX()-1][player.getY()].getObject() instanceof Box){
                    takeAllFromBox(player, (Box)map.getSpaces()[player.getX()-1][player.getY()].getObject());
                }
            }
            break;
        }
    }

    private void takeWeaponFromBox(Player player, Box box){
        boolean letsTakeThisWeapon = false;
        for(Thing thing: box.getThings()){
            if(thing instanceof IWeapon) {
                letsTakeThisWeapon = true;
                for (IWeapon weapon : player.getWeapons()) {
                    if (thing instanceof Pistol && weapon instanceof Pistol) {
                        letsTakeThisWeapon = false;
                    }
                }
            }
            if(letsTakeThisWeapon){
                if(thing instanceof Pistol){
                    player.getWeapons().add(new Pistol(player.getX(), player.getY()));
                }
                else if(thing instanceof Bomb){
                    boolean wereBombs = false;
                    for(IWeapon weapon: player.getWeapons()){
                        if(weapon instanceof Bomb){
                            ((Bomb) weapon).setCount(((Bomb) weapon).getCount() + ((Bomb) thing).getCount());
                            wereBombs = true;
                            break;
                        }
                    }
                    if(!wereBombs){
                        player.getWeapons().add(new Bomb(player.getX(), player.getY(), ((Bomb) thing).getCount()));
                    }
                }
            }
            letsTakeThisWeapon = false;
        }

    }

    private void takeAllFromBox(Player player, Box box){
        takeWeaponFromBox(player, box);
        takeTorchFromBox(player, box);
        box.getThings().clear();
        box.setObjectTexture(new Image("/resources/textures/box2.png"));
    }

    private void takeTorchFromBox(Player player, Box box){
        for(Thing thing: box.getThings()){
            if(thing instanceof Torch){
                player.takeTorch();
                break;
            }
        }
    }


    private void  tryBlowUp(Player player, Directions direction){
        if(player.getTempWeapon() instanceof Bomb && ((Bomb) player.getTempWeapon()).getCount() > 0) {
            switch (direction) {
                case Up: {
                    if (map.getSpaces()[player.getX()][player.getY()].getWalls()[0] && player.getY() > 0) {
                        map.getSpaces()[player.getX()][player.getY()].getWalls()[0] = false;
                        map.getSpaces()[player.getX()][player.getY() - 1].getWalls()[2] = false;
                        ((Bomb) player.getTempWeapon()).setCount(((Bomb) player.getTempWeapon()).getCount()-1);
                    }
                }
                break;
                case Right: {
                    if (map.getSpaces()[player.getX()][player.getY()].getWalls()[1] && player.getX()+1 < map.getSpaces().length) {
                        map.getSpaces()[player.getX()][player.getY()].getWalls()[1] = false;
                        map.getSpaces()[player.getX()+1][player.getY()].getWalls()[3] = false;
                        ((Bomb) player.getTempWeapon()).setCount(((Bomb) player.getTempWeapon()).getCount()-1);
                    }
                }
                break;
                case Down: {
                    if (map.getSpaces()[player.getX()][player.getY()].getWalls()[2] && player.getY()+1 < map.getSpaces()[0].length) {
                        map.getSpaces()[player.getX()][player.getY()].getWalls()[2] = false;
                        map.getSpaces()[player.getX()][player.getY() + 1].getWalls()[0] = false;
                        ((Bomb) player.getTempWeapon()).setCount(((Bomb) player.getTempWeapon()).getCount()-1);
                    }
                }
                break;
                case Left: {
                    if (map.getSpaces()[player.getX()][player.getY()].getWalls()[3] && player.getX() > 0) {
                        map.getSpaces()[player.getX()][player.getY()].getWalls()[3] = false;
                        map.getSpaces()[player.getX()-1][player.getY()].getWalls()[1] = false;
                        ((Bomb) player.getTempWeapon()).setCount(((Bomb) player.getTempWeapon()).getCount()-1);
                    }
                }
                break;
            }
            if(((Bomb) player.getTempWeapon()).getCount() <= 0){
                player.getWeapons().remove(player.getTempWeapon());
                player.setHandWithoutWeapon();
            }
        }
    }

}
