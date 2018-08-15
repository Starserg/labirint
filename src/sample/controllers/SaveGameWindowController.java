package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.DAL.MapSaver;

public class SaveGameWindowController {




    @FXML
    Button saveButton;

    @FXML
    Button backButton;

    @FXML
    TextField nameTextField;


    public void saveGame(ActionEvent event){
       // MapSaver.saveGame();
    }


    public void goBackToGame(ActionEvent event){

    }

}
