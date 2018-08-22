package sample.entities.mapObjects;

import javafx.scene.image.Image;
import sample.Constants;

public class Monster extends GameObject {

    public Monster(int x, int y) {
        super(x, y, Constants.monsterSpeed, Constants.monsterHp);
        this.objectTexture = new Image("/resources/textures/spider1.png");
    }





}
