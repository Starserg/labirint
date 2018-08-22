package sample.entities.mapObjects;

import javafx.scene.image.Image;

public class Treasury extends GameObject{
    public Treasury(int x, int y) {
        super(x, y, 0, 1000);
        this.objectTexture = new Image("/resources/textures/treasury1.png");
    }
}
