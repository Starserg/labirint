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
                spaces[i][j] = new Space();
            }
        }
        setGroundImagesToSpaces(spaces);

        //TODO: very BAD CODE!!!!
        Player player1 = new Player(0, 0, Constants.playerId);
        spaces[0][0].setObject(player1);

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

}
