package sample.entities;

import sample.entities.mapObjects.GameObject;
import sample.entities.mapObjects.Player;

import java.util.ArrayList;

public class Map {

    public Map(Space[][] spaces){
        this.spaces = spaces;
        this.gameObjects = new ArrayList<GameObject>();
        this.players = new ArrayList<>();
        putGameObjectsToList();
    }

    private Space[][] spaces;

    private ArrayList<GameObject> gameObjects;

    private ArrayList<Player> players;

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }


    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Space[][] getSpaces() {
        return spaces;
    }

    private void putGameObjectsToList(){
        for(int i = 0; i < spaces.length; i++){
            for(int j = 0; j < spaces[i].length; j++){
                if(spaces[i][j].getObject() != null){
                    gameObjects.add(spaces[i][j].getObject());
                    if(spaces[i][j].getObject() instanceof Player){
                        this.players.add((Player) spaces[i][j].getObject());
                    }
                }
            }
        }
    }


    public boolean caPlayerSeeSpace(Player player, Space space){
        //TODO: refactor!
        return true;
    }


    public Player getPlayerById(int playerId) throws Exception {
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getId() == playerId){
                return players.get(i);
            }
        }
        throw new Exception();
    }



}
