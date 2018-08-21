package sample.entities.mapObjects;

import javafx.scene.image.Image;
import sample.enums.Directions;

public abstract class GameObject {

    public GameObject(int x, int y, double speed, int hp){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = Directions.Up;
        this.directionOfMoving = Directions.Up;
        this.delta = 0;
        this.enabled = true;
        this.hp = hp;
        this.maxHp = hp;
    }

    protected int x;
    protected int y;
    protected double speed;
    protected Directions direction;
    protected Directions directionOfMoving;
    protected double delta;
    protected boolean enabled;
    protected int hp;
    protected int maxHp;

    public int getX() {
        return x;
    }

    public void setX(int x) throws Exception {
        if(x < 0){
            throw new Exception();
        }
        else{
            this.x = x;
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) throws Exception {
        if(y < 0){
            throw new Exception();
        }
        else{
            this.y = y;
        }
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) throws Exception {
        if(speed < 0){
            throw new Exception();
        }
        else{
            this.speed = speed;
        }
    }

    public Directions getDirection() {
        return direction;
    }

    public void setDirection(Directions direction) throws Exception {
        if(direction == null){
            throw new Exception();
        }
        else{
            this.direction = direction;
        }
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) throws Exception {
        if(delta > 1 || delta < 0){
            throw new Exception();
        }
        else{
            this.delta = delta;
        }
    }

    public Directions getDirectionOfMoving() {
        return directionOfMoving;
    }

    public void setDirectionOfMoving(Directions directionOfMoving) throws Exception {
        if(directionOfMoving == null){
            throw new Exception();
        }
        else{
            this.directionOfMoving = directionOfMoving;
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }



    protected Image objectTexture;

    public Image getObjectTexture() {
        return objectTexture;
    }

    public void setObjectTexture(Image objectTexture) {
        if(objectTexture!= null){
            this.objectTexture = objectTexture;
        }
    }


    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }
}
