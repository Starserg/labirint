package sample.entities.things;

import sample.entities.IWeapon;
import sample.enums.Directions;

public class Bomb extends Thing implements IWeapon{
    public Bomb(int x, int y, int count) {
        super(x, y);
        this.count = count;
    }


    @Override
    public void attack(Directions direction) {

    }

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
