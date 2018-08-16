package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DAL.MapSaver;
import sample.Main;
import sample.entities.Map;

import java.io.IOException;

public class SaveGameWindowController {




    public static Map tempMap = null;


    @FXML
    Button saveButton;

    @FXML
    Button backButton;

    @FXML
    TextField nameTextField;


    public void saveGame(ActionEvent event){
        MapSaver.saveGame(tempMap, nameTextField.getText());
    }


    public void goBackToGame(ActionEvent event) throws IOException {
        Stage stage = Main.getStage();
        Parent saveGameSceneRoot = FXMLLoader.load(getClass().getResource("../presentation/gameWindow.fxml"));
        stage.setScene(new Scene(saveGameSceneRoot));
    }

}
