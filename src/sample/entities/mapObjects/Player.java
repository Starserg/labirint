package sample.entities.mapObjects;

import sample.Constants;
import sample.entities.Weapon;

import java.util.ArrayList;

public class Player extends GameObject{
    public Player(int x, int y){
        super(x, y, Constants.playerSpeed);
        weapons =new ArrayList<>();
    }

    private ArrayList<Weapon> weapons;
    private Weapon tempWeapon;
}
