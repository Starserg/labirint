package sample.entities;

import sample.entities.mapObjects.GameObject;
import sample.enums.Activities;
import sample.enums.Directions;

public class Command {

    public Command(Activities activity, Directions direction, GameObject object){
        this.activity = activity;
        this.direction = direction;
        this.object = object;
    }

    private Activities activity;
    private Directions direction;
    private GameObject object;

    public Activities getActivity() {
        return activity;
    }

    public Directions getDirection() {
        return direction;
    }

    public GameObject getObject() {
        return object;
    }
}
