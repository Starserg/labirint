package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.entities.Game;

public class Main extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent menuSceneRoot = FXMLLoader.load(getClass().getResource("presentation/startMenu.fxml"));
        primaryStage.setTitle("Labyrinth");
        primaryStage.setScene(new Scene(menuSceneRoot));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    public static Stage getStage(){
        return stage;
    }

}
