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

    private Image playerImg;
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
        ImageView groundImageView = new ImageView(space.getGroundImage());
        groundImageView.setFitWidth(singleSize);
        groundImageView.setFitHeight(singleSize);
        groundImageView.setX(x);
        groundImageView.setY(y);
        frame.add(groundImageView);
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
