package sample.DAL;

import sample.entities.Map;

import java.io.File;

public class MapSaver {



    public static boolean saveGame(Map map, String name){
        try {
            File saveFile = new File("/resources/savegames/" + name + ".txt");

            //TODO: set save logic here
            return true;
        }
        catch (Exception e){
            return false;
        }
    }



}
