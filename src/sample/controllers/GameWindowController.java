package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Main;
import sample.entities.Game;
import sample.presentation.Drawer;

import java.io.IOException;


public class GameWindowController {

    private Game game;


    @FXML
    public void initialize() {
        //TODO: refactor it!
        this.game = null;
    }



    @FXML
    AnchorPane gameContentPane;

    @FXML
    AnchorPane gameMenuPane;

    @FXML
    Button exitButton;


    public void exitFromGame(ActionEvent event) throws IOException {
        Stage stage = Main.getStage();
        Parent menuSceneRoot = FXMLLoader.load(getClass().getResource("../presentation/startMenu.fxml"));
        stage.setScene(new Scene(menuSceneRoot));
    }

    @FXML
    public void keyPressed(KeyEvent event){
        //TODO: handle it
    }


    public void drawGameContent(){
        Drawer.getGameFroud(game.getGameMap(), null); //TODO: set player
    }

}
