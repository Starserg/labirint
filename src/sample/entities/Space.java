package sample.entities;

import javafx.scene.image.Image;
import sample.entities.mapObjects.GameObject;
import sample.entities.things.Thing;

import java.util.ArrayList;

public class Space {

    public Space(){
        this.walls = new boolean[4];
        for (int i = 0; i < this.walls.length; i++){
            this.walls[i] = false;
        }
    }

    private boolean[] walls;

    private GameObject object;
    private ArrayList<Thing> things;

    public boolean[] getWalls() {
        return walls;
    }

    public GameObject getObject() {
        return object;
    }

    public void setObject(GameObject object) {
        this.object = object;
    }



    //for drawing (experiment)

    private Image groundImage;

    public Image getGroundImage() {
        return groundImage;
    }

    public void setGroundImage(Image groundImage) {
        if(groundImage != null){
            this.groundImage = groundImage;
        }
    }
}
