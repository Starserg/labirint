package sample.presentation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import sample.entities.Map;
import sample.entities.Space;
import sample.entities.mapObjects.Player;

import java.util.ArrayList;

public class Drawer {

    public Drawer(){

    }


    private static Image wallImage = new Image("/resources/textures/wall1.png");

    private static int singleSize = 50;


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
        if(space.getObject()!= null){
            drawSpaceObjectOnFrame(frame, space, x, y, singleSize);
        }
    }


    private static void drawSpaceObjectOnFrame(ArrayList<ImageView> frame, Space space, int x, int y, int singleSize){
        ImageView objectImageView = new ImageView(space.getObject().getObjectTexture());
        objectImageView.setFitWidth(singleSize);
        objectImageView.setFitHeight(singleSize);
        objectImageView.setX(x);
        objectImageView.setY(y);
        frame.add(objectImageView);
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
    public static ArrayList<ImageView> getGameFrameUpd(Map map, int playerId, int windowWidth, int windowHeight) throws Exception {
        Player player = null;
        for(int i = 0; i < map.getPlayers().size(); i++){
            if(map.getPlayers().get(i).getId() == playerId){
                player = map.getPlayers().get(i);
                break;
            }
        }
        if(player == null){
            throw new Exception();
        }

        ArrayList frame = new ArrayList();
        for(int i = player.getX()-player.getSeeSize(); i < player.getX()+player.getSeeSize(); i++){
            for(int j = player.getY()-player.getSeeSize(); j < player.getY()+player.getSeeSize(); j++){
                if(i< 0 || j< 0 || i >= map.getSpaces().length || j >= map.getSpaces()[0].length){
                    continue;
                }
                if(map.caPlayerSeeSpace(player,  map.getSpaces()[i][j])){
                    drawSpaceOnFrame(frame, map.getSpaces()[i][j], singleSize*(i-player.getX()+player.getSeeSize()), singleSize*(j-player.getY()+player.getSeeSize()), singleSize);
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
