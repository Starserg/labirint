package sample.DAL;

import sample.Constants;
import sample.entities.IWeapon;
import sample.entities.Map;
import sample.entities.mapObjects.Box;
import sample.entities.mapObjects.Monster;
import sample.entities.things.Bomb;
import sample.entities.things.Pistol;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class MapSaver {



    public static boolean saveGame(Map map, String name){
        try {
            if(name == null || name.replaceAll(" ", "").length() == 0){
                throw new Exception();
            }
            //TODO: replace absolute path!!!
            File saveFile = new File("C:\\Users\\starserver\\IdeaProjects\\labirint\\src\\resources\\savegames\\" + name + ".txt");
            if(saveFile.createNewFile()){
                BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile, true));
                writer.write(map.getSpaces().length + " " + map.getSpaces()[0].length + System.lineSeparator());
                for(int i = 0; i < map.getSpaces().length; i++){
                    for(int j = 0; j < map.getSpaces()[i].length; j++){
                        for(int k = 0; k < map.getSpaces()[i][j].getWalls().length; k++){
                            if(map.getSpaces()[i][j].getWalls()[k]){
                                writer.write("1");
                            }
                            else{
                                writer.write("0");
                            }
                        }
                    }
                    writer.write(System.lineSeparator());
                }
                writer.write(map.getPlayerById(Constants.playerId).getX() + " " +  map.getPlayerById(Constants.playerId).getY() + System.lineSeparator());
                writer.write(map.getPlayerById(Constants.playerId).getWeapons().size() + System.lineSeparator());
                if(map.getPlayerById(Constants.playerId).getWeapons().size()>0){
                    for(IWeapon weapon : map.getPlayerById(Constants.playerId).getWeapons()){
                        if(weapon instanceof Pistol){
                            writer.write("pistol" + System.lineSeparator());
                        }
                        else if(weapon instanceof Bomb){
                            writer.write("bomb" + System.lineSeparator());
                        }
                    }
                }
                writer.write((map.getGameObjects().size()-1) + System.lineSeparator());
                for (int i = 0; i < map.getGameObjects().size(); i++){
                    if(map.getGameObjects().get(i) instanceof Monster){
                        writer.write("m " + map.getGameObjects().get(i).getX() + " " + map.getGameObjects().get(i).getY() + System.lineSeparator());
                    }
                    else if(map.getGameObjects().get(i) instanceof Box){
                        writer.write("b " + map.getGameObjects().get(i).getX() + " " + map.getGameObjects().get(i).getY() + System.lineSeparator());
                    }
                }
                writer.flush();
                writer.close();
                return true;
            }
            else{
                return  false;
            }

        }
        catch (Exception e){
            return false;
        }
    }



}
