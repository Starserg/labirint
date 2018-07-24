package sample.entities.things;

import sample.entities.IWeapon;
import sample.enums.Directions;

public class Pistol extends Thing implements IWeapon{
    public Pistol(int x, int y) {
        super(x, y);
    }

    @Override
    public void attack(Directions direction) {

    }
}
