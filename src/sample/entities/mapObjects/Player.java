package sample.entities.mapObjects;

import javafx.scene.image.Image;
import sample.Constants;
import sample.entities.IWeapon;
import sample.entities.things.Thing;

import java.util.ArrayList;

public class Player extends GameObject implements IThingsContainer{
    public Player(int x, int y, int id){
        super(x, y, Constants.playerSpeed);
        weapons =new ArrayList<>();
        this.id = id;
        this.seeSize = Constants.playerSeeSize;
        this.objectTexture = new Image("/resources/textures/player1.png");
    }

    private ArrayList<IWeapon> weapons;
    private IWeapon tempWeapon;
    private int id;
    private int seeSize;


    public int getId() {
        return id;
    }

    public int getSeeSize(){
        return seeSize;
    }
}
