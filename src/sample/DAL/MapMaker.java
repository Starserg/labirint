package sample.DAL;

import javafx.scene.image.Image;
import sample.Constants;
import sample.entities.Map;
import sample.entities.Space;
import sample.entities.mapObjects.Box;
import sample.entities.mapObjects.Monster;
import sample.entities.mapObjects.Player;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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
        setRandomMonsters(spaces, spaces.length*spaces[0].length/30);
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

    private static void setRandomMonsters(Space[][] spaces, int countOfMonsters){
        Random random = new Random();
        int randomX;
        int randomY;
        for(int i = 0; i < countOfMonsters; i++){
            randomX = random.nextInt(spaces.length-1);
            randomY = random.nextInt(spaces[0].length - 1);
            if(spaces[randomX][randomY].getObject() == null){
                spaces[randomX][randomY].setObject(new Monster(randomX, randomY));
            }
            else{
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

    public static Map loadMap(String name){
        Map answer;
        String[] tempStringSplit;
        String tempString;

        try{
            //TODO: replace absolute path!!!
            FileInputStream inputStream = new FileInputStream("C:\\Users\\starserver\\IdeaProjects\\labirint\\src\\resources\\savegames\\" + name + ".txt");
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                tempStringSplit = reader.readLine().split(" ");

                Space[][] spaces = new Space[Integer.parseInt(tempStringSplit[0])][Integer.parseInt(tempStringSplit[1])];
                for (int i = 0; i < spaces.length; i++) {
                    tempString = reader.readLine();
                    for (int j = 0; j < spaces[0].length; j++) {
                        spaces[i][j] = new Space(i, j);
                        for (int k = 0; k < 4; k++) {
                            if (tempString.charAt(j * 4 + k) == '1') {
                                spaces[i][j].getWalls()[k] = true;
                            }
                        }
                    }
                }
                setGroundImagesToSpaces(spaces);
                tempStringSplit = reader.readLine().split(" ");
                Player player = new Player(Integer.parseInt(tempStringSplit[0]), Integer.parseInt(tempStringSplit[1]), Constants.playerId);
                spaces[player.getX()][player.getY()].setObject(player);
                int countOfObjects = Integer.parseInt(reader.readLine());
                for(int i = 0; i < countOfObjects; i++){
                    tempStringSplit = reader.readLine().split(" ");
                    if(tempStringSplit[0].equals("m")){
                        spaces[Integer.parseInt(tempStringSplit[1])][Integer.parseInt(tempStringSplit[2])].setObject(new Monster(Integer.parseInt(tempStringSplit[1]), Integer.parseInt(tempStringSplit[2])));
                    }
                    else if(tempStringSplit[0].equals("b")){
                        spaces[Integer.parseInt(tempStringSplit[1])][Integer.parseInt(tempStringSplit[2])].setObject(new Box(Integer.parseInt(tempStringSplit[1]), Integer.parseInt(tempStringSplit[2])));
                    }
                }
                //TODO: set loading logic here

                answer = new Map(spaces);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }

        return answer;
    }



}
