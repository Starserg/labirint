package sample.DAL;

import javafx.scene.image.Image;
import sample.Constants;
import sample.entities.Map;
import sample.entities.Space;
import sample.entities.mapObjects.Box;
import sample.entities.mapObjects.Monster;
import sample.entities.mapObjects.Player;
import sample.entities.mapObjects.Treasury;
import sample.entities.things.Bomb;
import sample.entities.things.Pistol;
import sample.entities.things.Torch;


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

        Player player1 = new Player(Constants.startX, Constants.startY, Constants.playerId);
        spaces[0][0].setObject(player1);
        setRandomWalls(spaces, spaces.length*spaces[0].length*5/6);
        setRandomMonsters(spaces, spaces.length*spaces[0].length/50);
        setRandomBoxes(spaces, spaces.length*spaces[0].length/35);
        setRandomTreasury(spaces);
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


    private static void setRandomTreasury(Space[][] spaces){
        Random random = new Random();
        int x;
        int y;
        while (true){
            x = random.nextInt(spaces.length);
            y = random.nextInt(spaces[0].length);
            if(spaces[x][y].getObject() == null){
                spaces[x][y].setObject(new Treasury(x, y));
                spaces[x][y].getWalls()[0] = true;
                spaces[x][y].getWalls()[1] = true;
                spaces[x][y].getWalls()[2] = true;
                spaces[x][y].getWalls()[3] = true;
                if(x > 0){
                    spaces[x-1][y].getWalls()[1] = true;
                }
                if(y > 0){
                    spaces[x][y-1].getWalls()[2] = true;
                }
                if(x + 1 < spaces.length){
                    spaces[x+1][y].getWalls()[3] = true;
                }
                if(y + 1 < spaces[0].length){
                    spaces[x][y+1].getWalls()[0] = true;
                }
                break;
            }
        }
    }


    private static void setRandomBoxes(Space[][] spaces, int count){
        Random random = new Random();
        int randomX;
        int randomY;
        for(int i = 0; i < count; i++){
            randomX = random.nextInt(spaces.length-1);
            randomY = random.nextInt(spaces[0].length - 1);
            if(spaces[randomX][randomY].getObject() == null){
                Box box = new Box(randomX, randomY);
                if(random.nextInt(Constants.countOfVariantsToPistolInBox) == 0){
                    box.getThings().add(new Pistol(box.getX(), box.getY()));
                }
                if(random.nextInt(Constants.countOfVariantsToBombsInBox) == 0){
                    box.getThings().add(new Bomb(box.getX(), box.getY(), Constants.countOfBombsInBox));
                }
                if(random.nextInt(Constants.countOfVariantsToTorchInBox) == 0){
                    box.getThings().add(new Torch(box.getX(), box.getY()));
                }
                spaces[randomX][randomY].setObject(box);
            }
            else{
                i--;
            }
        }
    }

    private static void setRandomWalls(Space[][] spaces, int countOfWalls){
        Random random = new Random();
        for(int i = 0; i < countOfWalls; i++){
            if(!setWall(spaces, random.nextInt(spaces.length), random.nextInt(spaces[0].length), random.nextInt(5))){
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
                if(Boolean.parseBoolean(tempStringSplit[2])){
                    player.takeTorch();
                }
                int countOfWeapons = Integer.parseInt(reader.readLine());
                if(countOfWeapons > 0){
                    for(int i = 0 ; i< countOfWeapons; i++){
                        tempString = reader.readLine();
                        if(tempString.equals("pistol")){
                            player.getWeapons().add(new Pistol(player.getX(), player.getY()));
                        }
                        else if(tempString.equals("bomb")){
                            player.getWeapons().add(new Bomb(player.getX(), player.getY(), Integer.parseInt(reader.readLine())));
                        }
                    }
                }
                spaces[player.getX()][player.getY()].setObject(player);
                int countOfObjects = Integer.parseInt(reader.readLine());
                for(int i = 0; i < countOfObjects; i++){
                    tempStringSplit = reader.readLine().split(" ");
                    if(tempStringSplit[0].equals("m")){
                        spaces[Integer.parseInt(tempStringSplit[1])][Integer.parseInt(tempStringSplit[2])].setObject(new Monster(Integer.parseInt(tempStringSplit[1]), Integer.parseInt(tempStringSplit[2])));
                    }
                    else if(tempStringSplit[0].equals("b")){
                        Box box = new Box(Integer.parseInt(tempStringSplit[1]), Integer.parseInt(tempStringSplit[2]));
                        int thingsCount  = Integer.parseInt(reader.readLine());
                        for(int t = 0; t < thingsCount; t++){
                            tempStringSplit = reader.readLine().split(" ");
                            if(tempStringSplit[0].equals("pistol")){
                                box.getThings().add(new Pistol(box.getX(), box.getY()));
                            }
                            else if(tempStringSplit[0].equals("bomb")){
                                box.getThings().add(new Bomb(box.getX(), box.getY(), Integer.parseInt(tempStringSplit[1])));
                            }
                        }
                        spaces[box.getX()][box.getY()].setObject(box);
                    }
                    else if(tempStringSplit[0].equals("t")){
                        spaces[Integer.parseInt(tempStringSplit[1])][Integer.parseInt(tempStringSplit[2])].setObject(new Treasury(Integer.parseInt(tempStringSplit[1]), Integer.parseInt(tempStringSplit[2])));
                    }
                }


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
