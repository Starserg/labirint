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


    private static Image wallImage = new Image("/resources/textures/wall1.png");

    private static int singleSize = 50;
    private static int drawingDx = 0;
    private static int drawingDy = 0;

    public static ArrayList<ImageView> getGameFrame(Map map, Player player){
        ArrayList frame = new ArrayList();
        for(int i = 0; i < map.getSpaces().length; i++){
            for(int j = 0; j < map.getSpaces()[i].length; j++){
                drawSpaceOnFrame(frame, map.getSpaces()[i][j], singleSize*i, singleSize*j, singleSize);
            }
        }
        return frame;
    }

    private static void drawSpaceOnFrame(ArrayList<ImageView> frame, Space space, int x, int y, int singleSize){
        drawSpaceGroundOnFrame(frame, space, x, y, singleSize);
        if(space.getObject()!= null && space.getObject().getX() == space.getX() &&space.getObject().getY() == space.getY()){
            drawSpaceObjectOnFrame(frame, space, x, y, singleSize);
        }
    }


    private static void drawSpaceObjectOnFrame(ArrayList<ImageView> frame, Space space, int x, int y, int singleSize){
        ImageView objectImageView = new ImageView(space.getObject().getObjectTexture());
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
        //TODO: refactor!!!
        ImageView wallImageView = new ImageView(space.getObject().getObjectTexture());
        wallImageView.setFitWidth(singleSize);
        wallImageView.setFitHeight(singleSize/8);
        wallImageView.setX(x);
        wallImageView.setY(y);
        for(int i = 0; i < space.getWalls().length; i++){

            wallImageView.setRotate(90);

            if(space.getWalls()[i]){
                frame.add(wallImageView);

            }
        }


    }

//TODO: continue it! it will be great method!
    public static ArrayList<ImageView> getGameFrameUpd(Map map, Player player, int windowWidth, int windowHeight) throws Exception {

        singleSize = windowWidth/(player.getSeeSize()*2+1);

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
                if(map.caPlayerSeeSpace(player,  map.getSpaces()[i][j])){
                    drawSpaceOnFrame(frame, map.getSpaces()[i][j], singleSize*(i-player.getX()+player.getSeeSize())+drawingDx, singleSize*(j-player.getY()+player.getSeeSize())+drawingDy, singleSize);
                }
            }
        }
        return frame;
    }


  /*  public static WritableImage getGameFrame(Map map, Player player) {
        //TODO: refactor, very bad code! (for testing)
        Image img = new Image("/resources/textures/ground1.png");
        WritableImage answer = new WritableImage((int)img.getWidth()*map.getSpaces().length, (int)img.getHeight()*map.getSpaces()[0].length);
        for(int i = 0; i < map.getSpaces().length; i++){
            for(int j = 0; j < map.getSpaces()[i].length; j++){
                drawImageOnFrame(answer, map.getSpaces()[i][j].getGroundImage(), i*(int)img.getWidth(), j*(int)img.getHeight());
            }
        }

        //TODO: set drawing logic
        return answer;
    }






    private static void drawImageOnFrame(WritableImage frame, Image img, int x, int y){
        for(int  i = 0; i < (int)img.getWidth(); i++){
            for(int j = 0; j < (int)img.getHeight(); j++){
                frame.getPixelWriter().setColor(i+x, j+y, img.getPixelReader().getColor(i, j));
            }
        }

    }
*/
}
