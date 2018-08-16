package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import sample.DAL.MapMaker;
import sample.Main;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class LoadGameWindowController {


    @FXML
    public void initialize() {
        //TODO: replace absolute path!!!
        File directory = new File("C:\\Users\\starserver\\IdeaProjects\\labirint\\src\\resources\\savegames");
        String[] files = directory.list();

        ObservableList list = FXCollections.observableArrayList();
        if(files!= null){
            for(int i =0; i < files.length; i++){
                list.add(files[i].replace(".txt", ""));
            }
        }
        gamesList.setItems(list);
    }


    @FXML
    ListView<String> gamesList;

    @FXML
    Button loadButton;

    @FXML
    Button backButton;




    public void loadGame(ActionEvent event) throws IOException {
        if(gamesList.getSelectionModel().getSelectedItem() != null) {
            GameWindowController.loadedMap = MapMaker.loadMap(gamesList.getSelectionModel().getSelectedItem());
            GameWindowController.randomGame = false;
            Stage stage = Main.getStage();
            Parent gameSceneRoot = FXMLLoader.load(getClass().getResource("../presentation/gameWindow.fxml"));
            stage.setScene(new Scene(gameSceneRoot));
            gameSceneRoot.requestFocus();
        }
    }


    public void goBackToMenu(ActionEvent event) throws IOException {
        Stage stage = Main.getStage();
        Parent menuSceneRoot = FXMLLoader.load(getClass().getResource("../presentation/startMenu.fxml"));
        stage.setScene(new Scene(menuSceneRoot));
    }

}
