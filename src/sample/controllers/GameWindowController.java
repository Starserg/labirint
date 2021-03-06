package sample.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Constants;
import sample.Main;
import sample.entities.Command;
import sample.entities.Game;
import sample.entities.Map;
import sample.entities.mapObjects.Player;
import sample.entities.things.Bomb;
import sample.entities.things.Pistol;
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
        menuPaneClone = gameMenuPane;
        if(randomGame) {
            this.game = new Game(randomMapWidth, randomMapHeight);
        }
        else {
            this.game = new Game(loadedMap);
        }
        playerId = Constants.playerId;
        try {
            player = game.getGameMap().getPlayerById(playerId);
        } catch (Exception e) {
            updatePictureTimer.cancel();
            randomGame = true;
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
    AnchorPane mainPane;

    @FXML
    AnchorPane gameContentPane;

    @FXML
    AnchorPane gameMenuPane;

    AnchorPane menuPaneClone;

    @FXML
    Label FPSLabel;

    @FXML
    Label logicFPSLabel;

    @FXML
    Button exitButton;

    @FXML
    ImageView tempThingImageView;

    @FXML
    Label bombsCountLabel;

    @FXML
    ImageView torchIcon;




    public static boolean randomGame = true;
    public static int randomMapWidth = Constants.minMapSize;
    public static int randomMapHeight = Constants.minMapSize;

    public static Map loadedMap;

    private Timer startTimer;
    private Timer updatePictureTimer;

    private int playerId;
    private Player player;

    private Image bombImage = new Image("/resources/textures/bomb1.png");
    private Image pistolImage = new Image("/resources/textures/pistol1.png");
    private Image handImage = new Image("/resources/textures/hand1.png");
    private Image torchImage = new Image("/resources/textures/torch1.png");

    private int frameCounter = 0;
    private LocalTime lastFrameCountTime;


    public void exitFromGame(ActionEvent event) throws IOException {
        randomGame = true;
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
        else if(event.getCode().toString().equals("UP")){
            game.doCommand(new Command(Activities.Turn, Directions.Up, player));
        }
        else if(event.getCode().toString().equals("RIGHT")){
            game.doCommand(new Command(Activities.Turn, Directions.Right, player));
        }
        else if(event.getCode().toString().equals("DOWN")){
            game.doCommand(new Command(Activities.Turn, Directions.Down, player));
        }
        else if(event.getCode().toString().equals("LEFT")){
            game.doCommand(new Command(Activities.Turn, Directions.Left, player));
        }
        else if(event.getCode().toString().equals("CONTROL")){
            game.doCommand(new Command(Activities.SpinWeapon, null, player));
        }
        else if(event.getCode().toString().equals("SPACE")){
            game.doCommand(new Command(Activities.Attack, player.getDirection(), player));
        }
        else if(event.getCode().toString().equals("F")){
            game.doCommand(new Command(Activities.Take, player.getDirection(), player));
        }

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

    private void drawGameContent() {
        if (game.isEndGame()) {
            try {
                exitFromGame(new ActionEvent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            //  mainPane.getChildren().remove(gameMenuPane);
            ArrayList<ImageView> frame = null;
            try {
                frame = Drawer.getGameFrameUpd(game.getGameMap(), player, (int) mainPane.getWidth(), (int) mainPane.getHeight() - Constants.gameWindowDy);
            } catch (Exception e) {
                e.printStackTrace();
            }
            gameContentPane.getChildren().clear();
            for (ImageView view : frame) {
                gameContentPane.getChildren().add(view);
            }
            if (LocalTime.now().isAfter(lastFrameCountTime.plusSeconds(1))) {
                lastFrameCountTime = LocalTime.now();
                FPSLabel.setText(String.valueOf(frameCounter));
                logicFPSLabel.setText(String.valueOf(game.getLogicFPS()));
                frameCounter = 0;
            } else {
                frameCounter++;
            }
            if (player.getTempWeapon() == null) {
                tempThingImageView.setImage(handImage);
                bombsCountLabel.setText("");
            } else if (player.getTempWeapon() instanceof Pistol) {
                tempThingImageView.setImage(pistolImage);
                bombsCountLabel.setText("");
            } else if ((player.getTempWeapon() instanceof Bomb)) {
                tempThingImageView.setImage(bombImage);
                bombsCountLabel.setText(player.getCountOfBombs() + "");
            }
            if (player.hasTorch()) {
                torchIcon.setImage(torchImage);
            }
            gameMenuPane = menuPaneClone;
            //mainPane.getChildren().add(gameMenuPane);
        }

    }

    public void openSaveWindow(ActionEvent event) throws IOException {
        game.setPause(true);
        SaveGameWindowController.tempMap = game.getGameMap();
        loadedMap = game.getGameMap();
        randomGame = false;
        Stage stage = Main.getStage();
        Parent saveGameSceneRoot = FXMLLoader.load(getClass().getResource("../presentation/saveGameWindow.fxml"));
        stage.setScene(new Scene(saveGameSceneRoot));
    }

}
