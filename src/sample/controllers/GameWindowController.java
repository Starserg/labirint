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
import javafx.stage.Window;
import sample.Constants;
import sample.Main;
import sample.entities.Command;
import sample.entities.Game;
import sample.entities.mapObjects.Player;
import sample.enums.Activities;
import sample.enums.Directions;
import sample.presentation.Drawer;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class GameWindowController {

    private Game game;


    @FXML
    public void initialize() {
        //TODO: refactor it!
        this.game = new Game(100, 100);
        playerId = Constants.playerId;
        try {
            player = game.getGameMap().getPlayerById(playerId);
        }
        catch (Exception e){
            updatePictureTimer.cancel();
            Stage stage = Main.getStage();
            Parent menuSceneRoot = null;
            try {
                menuSceneRoot = FXMLLoader.load(getClass().getResource("../presentation/startMenu.fxml"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            stage.setScene(new Scene(menuSceneRoot));
        }
        updatePictureTimer = new Timer();
        startTimer = new Timer();
        startTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                startDrawingGame();
                game.startGame();
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

    private int playerId;
    private Player player;

    private int frameCounter = 0;
    private LocalTime lastFrameCountTime;


    public void exitFromGame(ActionEvent event) throws IOException {
        updatePictureTimer.cancel();
        Stage stage = Main.getStage();
        Parent menuSceneRoot = FXMLLoader.load(getClass().getResource("../presentation/startMenu.fxml"));
        stage.setScene(new Scene(menuSceneRoot));
    }

    @FXML
    public void keyPressed(KeyEvent event) throws Exception {

        if(event.getCode().toString().equals("W")){
            game.doCommand(new Command(Activities.Go, Directions.Up, player));
        }
        else if(event.getCode().toString().equals("D")){
            game.doCommand(new Command(Activities.Go, Directions.Right, player));
        }
        else if(event.getCode().toString().equals("S")){
            game.doCommand(new Command(Activities.Go, Directions.Down, player));
        }
        else if(event.getCode().toString().equals("A")){
            game.doCommand(new Command(Activities.Go, Directions.Left, player));
        }

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
      //  ArrayList<ImageView> frame = Drawer.getGameFrame(game.getGameMap(), null); //TODO: set player
        ArrayList<ImageView> frame = null;
        try {
            frame = Drawer.getGameFrameUpd(game.getGameMap(), player, (int)gameContentPane.getPrefWidth(), (int)gameContentPane.getPrefHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
        gameContentPane.getChildren().clear();
        for (ImageView view: frame) {
            gameContentPane.getChildren().add(view);
        }
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
