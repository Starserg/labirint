package sample.DAL;

import sample.entities.Map;
import sample.entities.Space;

public class MapMaker {

    public static Map makeRandomMap(int width, int height){
        Space[][] spaces = new Space[width][height];
        //TODO: set randomize logic here
        return new Map(spaces);
    }

}
