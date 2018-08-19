package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Constants;
import sample.Main;

import java.io.IOException;

public class StartMenuController {


    @FXML
    public void initialize(){
        SpinnerValueFactory<Integer> valueFactoryX = new SpinnerValueFactory.IntegerSpinnerValueFactory(Constants.minMapSize, Constants.maxMapSize, Constants.minMapSize);
        SpinnerValueFactory<Integer> valueFactoryY = new SpinnerValueFactory.IntegerSpinnerValueFactory(Constants.minMapSize, Constants.maxMapSize, Constants.minMapSize);
        spinnerX.setValueFactory(valueFactoryX);
        spinnerY.setValueFactory(valueFactoryY);
        spinnerX.setLayoutX(520);
        spinnerY.setLayoutX(520);
        spinnerX.setLayoutY(170);
        spinnerY.setLayoutY(220);
        mainPane.getChildren().add(spinnerX);
        mainPane.getChildren().add(spinnerY);
    }


    final Spinner<Integer> spinnerX = new Spinner<Integer>();
    final Spinner<Integer> spinnerY = new Spinner<Integer>();


    @FXML
    AnchorPane mainPane;

    @FXML
    Button newGameButton;

    @FXML
    Button loadGameButton;

    @FXML
    Button exitButton;

    @FXML
    Label label1;

    @FXML
    Label label2;


    public void startNewGame(ActionEvent event) throws IOException {
        GameWindowController.randomMapWidth = spinnerX.getValue();
        GameWindowController.randomMapHeight = spinnerY.getValue();
        Stage stage = Main.getStage();
        Parent gameSceneRoot = FXMLLoader.load(getClass().getResource("../presentation/gameWindow.fxml"));
        stage.setScene(new Scene(gameSceneRoot));
        gameSceneRoot.requestFocus();
    }

    public void goToLoadWindow(ActionEvent event) throws IOException {
        Stage stage = Main.getStage();
        Parent gameSceneRoot = FXMLLoader.load(getClass().getResource("../presentation/loadGameWindow.fxml"));
        stage.setScene(new Scene(gameSceneRoot));
    }



    public void exit(ActionEvent event){
        Main.getStage().close();
    }
}
