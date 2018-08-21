package sample.entities.mapObjects;

import javafx.scene.image.Image;
import sample.Constants;
import sample.entities.things.Thing;

import java.util.ArrayList;

public class Box extends GameObject implements IThingsContainer{


    public Box(int x, int y) {
        super(x, y, 0, Constants.boxHp);
        this.objectTexture = new Image("/resources/textures/box1.png");
    }


}
