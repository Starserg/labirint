package sample.DAL;

import javafx.scene.image.Image;
import sample.Constants;
import sample.entities.Map;
import sample.entities.Space;
import sample.entities.mapObjects.Player;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MapMaker {

    public static Map makeRandomMap(int width, int height){
        Space[][] spaces = new Space[width][height];
        for(int i =0; i < spaces.length; i++){
            for(int j = 0; j < spaces[i].length; j++){
                spaces[i][j] = new Space(i, j);
            }
        }
        setGroundImagesToSpaces(spaces);

        //TODO: very BAD CODE!!!!
        Player player1 = new Player(0, 0, Constants.playerId);
        spaces[0][0].setObject(player1);
        setRandomWalls(spaces, spaces.length*spaces[0].length*5/6);
        //TODO: set randomize logic here
        return new Map(spaces);
    }


    private static void setGroundImagesToSpaces(Space[][] spaces){
        Image[] grounds = new Image[Constants.variantsOfGround];
        for(int i = 0; i < grounds.length; i++){
            grounds[i] = new Image("/resources/textures/ground" + (i+1)+".png");
        }
        Random random = new Random();
        for(int i = 0; i < spaces.length; i++){
            for(int j = 0; j < spaces[i].length; j++){
                spaces[i][j].setGroundImage(grounds[random.nextInt(grounds.length)]);
            }
        }
    }


    private static void setRandomWalls(Space[][] spaces, int countOfWalls){
        Random random = new Random();
        for(int i = 0; i < countOfWalls; i++){
            if(!setWall(spaces, random.nextInt(spaces.length-1), random.nextInt(spaces[0].length-1), random.nextInt(4))){
                i--;
            }
        }
    }


    private static boolean setWall(Space[][] spaces, int x, int y, int direction){
        if(direction < 0 || direction > 3 || x < 0 || y < 0 || x >= spaces.length || y >= spaces[0].length){
            return false;
        }

        if(spaces[x][y].getWalls()[direction]){
            return false;
        }

        spaces[x][y].getWalls()[direction] = true;

        switch (direction){
            case 0:{
                if(y > 0){
                    spaces[x][y-1].getWalls()[2] = true;
                }
            }
            break;
            case 1:{
                if(x < spaces.length-1){
                    spaces[x+1][y].getWalls()[3] = true;
                }
            }
            break;
            case 2:{
                if(y < spaces[0].length-1){
                    spaces[x][y+1].getWalls()[0] = true;
                }
            }
            break;
            case 3:{
                if(x > 0){
                    spaces[x-1][y].getWalls()[1] = true;
                }
            }
            break;
        }

        return true;
    }



}
