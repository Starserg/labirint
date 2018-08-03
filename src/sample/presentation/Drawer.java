package sample.presentation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import sample.entities.Map;
import sample.entities.mapObjects.Player;

public class Drawer {

    public Drawer(){

    }

    private Image playerImg;


    public static WritableImage getGameFrame(Map map, Player player) {
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

}
