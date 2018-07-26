package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

public class StartMenuController {


    @FXML
    AnchorPane mainPane;

    @FXML
    Button newGameButton;

    @FXML
    Button loadGameButton;

    @FXML
    Button exitButton;


    public void startNewGame(ActionEvent event) throws IOException {
        Stage stage = Main.getStage();
        Parent gameSceneRoot = FXMLLoader.load(getClass().getResource("../presentation/gameWindow.fxml"));
        stage.setScene(new Scene(gameSceneRoot));
        gameSceneRoot.requestFocus();
    }


    public void exit(ActionEvent event){
        Main.getStage().close();
    }
}
