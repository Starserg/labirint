package sample.entities.mapObjects;

import sample.Constants;
import sample.entities.IWeapon;
import sample.entities.things.Thing;

import java.util.ArrayList;

public class Player extends GameObject implements IThingsContainer{
    public Player(int x, int y){
        super(x, y, Constants.playerSpeed);
        weapons =new ArrayList<>();
    }

    private ArrayList<IWeapon> weapons;
    private IWeapon tempWeapon;
}
