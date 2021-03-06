package sample.presentation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import sample.entities.Map;
import sample.entities.Space;
import sample.entities.mapObjects.Player;
import sample.enums.Directions;

import java.util.ArrayList;

public class Drawer {

    public Drawer(){

    }


    private static Image wallImageHorizontal = new Image("/resources/textures/wall1.png");
    private static Image wallImageVertical = new Image("/resources/textures/wall2.png");
    private static Image blackImage = new Image("/resources/textures/black1.png");

    private static int singleSize = 50;
    private static int drawingDx = 0;
    private static int drawingDy = 0;


    private static void drawSpaceOnFrame(ArrayList<ImageView> frame, Space space, int x, int y, int singleSize){
        drawSpaceGroundOnFrame(frame, space, x, y, singleSize);
        drawSpaceWallsOnFrame(frame, space, x, y, singleSize);
    }


    private static void drawSpaceObjectOnFrame(ArrayList<ImageView> frame, Space space, int x, int y, int singleSize){
        ImageView objectImageView = new ImageView(space.getObject().getObjectTexture());
        switch (space.getObject().getDirection()){
            case Up:{
                objectImageView.setRotate(0);
            }
            break;
            case Right:{
                objectImageView.setRotate(90);
            }
            break;
            case Down:{
                objectImageView.setRotate(180);
            }
            break;
            case Left:{
                objectImageView.setRotate(270);
            }
            break;

        }
        objectImageView.setFitWidth(singleSize);
        objectImageView.setFitHeight(singleSize);
        objectImageView.setX(x);
        objectImageView.setY(y);
        setDeltaToObject(objectImageView, space.getObject().getDirectionOfMoving(), space.getObject().getDelta());
        frame.add(objectImageView);
    }


    private static void setDeltaToObject(ImageView objectImageView, Directions direction, double delta){
        double dx = 0;
        double dy = 0;
        switch (direction){
            case Up:{
                dy = -delta*singleSize;
            }
            break;
            case Right:{
                dx = delta*singleSize;
            }
            break;
            case Down:{
                dy = delta*singleSize;
            }
            break;
            case Left:{
                dx = -delta*singleSize;
            }
            break;
        }
        objectImageView.setX(objectImageView.getX()+ (int)dx);
        objectImageView.setY(objectImageView.getY()+ (int)dy);
    }


    private static void drawSpaceGroundOnFrame(ArrayList<ImageView> frame, Space space, int x, int y, int singleSize){
        ImageView groundImageView = new ImageView(space.getGroundImage());
        groundImageView.setFitWidth(singleSize);
        groundImageView.setFitHeight(singleSize);
        groundImageView.setX(x);
        groundImageView.setY(y);
        frame.add(groundImageView);
    }


    private static void drawSpaceWallsOnFrame(ArrayList<ImageView> frame, Space space, int x, int y, int singleSize){
        ImageView wallImageView;
        for(int i = 0; i < space.getWalls().length; i++){

            if(i%2 == 0){
                wallImageView = new ImageView(wallImageHorizontal);
                wallImageView.setFitWidth(singleSize);
                wallImageView.setFitHeight(singleSize/8);
            }
            else{
                wallImageView = new ImageView(wallImageVertical);
                wallImageView.setFitWidth(singleSize/8);
                wallImageView.setFitHeight(singleSize);
            }

            if(i == 0 || i == 2 || i == 3){
                wallImageView.setX(x);
            }
            else{
                wallImageView.setX(x+singleSize*7/8);
            }
            if(i == 0 || i == 1 || i == 3){
                wallImageView.setY(y);
            }
            else{
                wallImageView.setY(y+(singleSize*7)/8);
            }


            if(space.getWalls()[i]){
                frame.add(wallImageView);

            }
        }


    }


    public static ArrayList<ImageView> getGameFrameUpd(Map map, Player player, int windowWidth, int windowHeight) throws Exception {

        singleSize = windowWidth/(player.getSeeSize()*2+1);
        if(singleSize > windowHeight/(player.getSeeSize()*2+1)){
            singleSize = windowHeight/(player.getSeeSize()*2+1);
        }

        int delta = (int)(player.getDelta()*singleSize);
        switch (player.getDirectionOfMoving()){
            case Up:{
                drawingDy = delta;
                drawingDx = 0;
            }
            break;
            case Right:{
                drawingDx = -delta;
                drawingDy = 0;
            }
            break;
            case Down:{
                drawingDy = -delta;
                drawingDx = 0;
            }
            break;
            case Left:{
                drawingDx = delta;
                drawingDy = 0;
            }
            break;
        }

        ArrayList frame = new ArrayList();
        for(int i = player.getX()-player.getSeeSize(); i <= player.getX()+player.getSeeSize(); i++){
            for(int j = player.getY()-player.getSeeSize(); j <= player.getY()+player.getSeeSize(); j++){
                if(i< 0 || j< 0 || i >= map.getSpaces().length || j >= map.getSpaces()[0].length){
                    continue;
                }
                if(map.canPlayerSeeSpace(player,  map.getSpaces()[i][j])){
                    drawSpaceOnFrame(frame, map.getSpaces()[i][j], singleSize*(i-player.getX()+player.getSeeSize())+drawingDx, singleSize*(j-player.getY()+player.getSeeSize())+drawingDy, singleSize);
                }
            }
        }




        for(int i = player.getX()-player.getSeeSize(); i <= player.getX()+player.getSeeSize(); i++){
            for(int j = player.getY()-player.getSeeSize(); j <= player.getY()+player.getSeeSize(); j++){
                if(i< 0 || j< 0 || i >= map.getSpaces().length || j >= map.getSpaces()[0].length){
                    continue;
                }

                if(map.getSpaces()[i][j].getObject()!= null && map.getSpaces()[i][j].getObject().getX() == map.getSpaces()[i][j].getX() &&map.getSpaces()[i][j].getObject().getY() == map.getSpaces()[i][j].getY()){
                    drawSpaceObjectOnFrame(frame, map.getSpaces()[i][j], singleSize*(i-player.getX()+player.getSeeSize())+drawingDx, singleSize*(j-player.getY()+player.getSeeSize())+drawingDy, singleSize);
                }

            }
        }


        for(int i = player.getX()-player.getSeeSize()-1; i <= player.getX()+player.getSeeSize()+1; i++){
            for(int j = player.getY()-player.getSeeSize()-1; j <= player.getY()+player.getSeeSize()+1; j++){
                if(i< 0 || j< 0 || i >= map.getSpaces().length || j >= map.getSpaces()[0].length){
                    continue;
                }
                if(!map.canPlayerSeeSpace(player,  map.getSpaces()[i][j])){
                    ImageView blackImageView = new ImageView(blackImage);
                    blackImageView.setFitWidth(singleSize);
                    blackImageView.setFitHeight(singleSize);
                    blackImageView.setX(singleSize*(i-player.getX()+player.getSeeSize())+drawingDx);
                    blackImageView.setY(singleSize*(j-player.getY()+player.getSeeSize())+drawingDy);
                    frame.add(blackImageView);
                }
            }
        }


        return frame;
    }


}
