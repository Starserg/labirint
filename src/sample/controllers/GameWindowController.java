package sample.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Constants;
import sample.Main;
import sample.entities.Game;
import sample.presentation.Drawer;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;


public class GameWindowController {

    private Game game;


    @FXML
    public void initialize() {
        //TODO: refactor it!
        this.game = new Game(10, 10);
        updatePictureTimer = new Timer();
        startTimer = new Timer();
        startTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                startDrawingGame();
            }
        }, 500);
    }



    @FXML
    AnchorPane gameContentPane;

    @FXML
    AnchorPane gameMenuPane;


    @FXML
    Label FPSLabel;

    @FXML
    Label logicFPSLabel;

    @FXML
    Button exitButton;

    private Timer startTimer;
    private Timer updatePictureTimer;

    private int frameCounter = 0;
    private LocalTime lastFrameCountTime;


    public void exitFromGame(ActionEvent event) throws IOException {
        updatePictureTimer.cancel();
        Stage stage = Main.getStage();
        Parent menuSceneRoot = FXMLLoader.load(getClass().getResource("../presentation/startMenu.fxml"));
        stage.setScene(new Scene(menuSceneRoot));
    }

    @FXML
    public void keyPressed(KeyEvent event){
        //TODO: handle it
    }


    private void startDrawingGame(){
        lastFrameCountTime = LocalTime.now();
        updatePictureTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        drawGameContent();
                    }
                });

            }
        }, Constants.updateTimerPeriod, Constants.updateTimerPeriod);
    }

    private void drawGameContent(){
        ImageView view = new ImageView();
        view.setImage(Drawer.getGameFrame(game.getGameMap(), null)); //TODO: set player
        gameContentPane.getChildren().clear();
        gameContentPane.getChildren().add(view);
        if(LocalTime.now().isAfter(lastFrameCountTime.plusSeconds(1))){
            lastFrameCountTime = LocalTime.now();
            FPSLabel.setText(String.valueOf(frameCounter));
            frameCounter = 0;
        }
        else{
            frameCounter++;
        }
    }

}
