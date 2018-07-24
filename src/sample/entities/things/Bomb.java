package sample.entities.things;

import sample.entities.IWeapon;
import sample.enums.Directions;

public class Bomb extends Thing implements IWeapon{
    public Bomb(int x, int y) {
        super(x, y);
    }


    @Override
    public void attack(Directions direction) {

    }
}
