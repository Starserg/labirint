package sample.presentation;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import sample.entities.Map;
import sample.entities.mapObjects.Player;

public class Drawer {

    public Drawer(){

    }

    private Image playerImg;


    public static WritableImage getGameFrame(Map map, Player player) {
        Image img = new Image("/resources/textures/ground1.png");
        WritableImage answer = new WritableImage((int)img.getWidth(), (int)img.getHeight());
        for(int  i = 0; i < (int)img.getWidth(); i++){
            for(int j = 0; j < (int)img.getHeight(); j++){
                answer.getPixelWriter().setColor(i, j, img.getPixelReader().getColor(i, j));
            }
        }

        //TODO: set drawing logic
        return answer;
    }
}
