package sample.entities.mapObjects;

import javafx.scene.image.Image;
import sample.Constants;

import java.time.LocalTime;

public class Monster extends GameObject {

    public Monster(int x, int y) {
        super(x, y, Constants.monsterSpeed, Constants.monsterHp);
        this.objectTexture = new Image("/resources/textures/spider2.png");
        this.lastByte = LocalTime.now();
    }

    private LocalTime lastByte;

    public LocalTime getLastByte() {
        return lastByte;
    }

    public void setLastByte(LocalTime lastByte) {
        this.lastByte = lastByte;
    }
}
