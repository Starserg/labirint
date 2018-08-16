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
import sample.Main;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class LoadGameWindowController {


    @FXML
    public void initialize() {
        File directory = new File("/resources/savegames");
        String[] files = directory.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        });

        ObservableList list = FXCollections.observableArrayList();
        if(files!= null){
            for(int i =0; i < files.length; i++){
                list.add(files[i]);
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




    public void loadGame(ActionEvent event){

    }


    public void goBackToMenu(ActionEvent event) throws IOException {
        Stage stage = Main.getStage();
        Parent menuSceneRoot = FXMLLoader.load(getClass().getResource("../presentation/startMenu.fxml"));
        stage.setScene(new Scene(menuSceneRoot));
    }

}
