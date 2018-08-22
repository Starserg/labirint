package sample.entities.mapObjects;

import javafx.scene.image.Image;
import sample.Constants;
import sample.entities.IWeapon;
import sample.entities.things.Bomb;
import sample.entities.things.Pistol;
import sample.entities.things.Thing;

import java.time.LocalTime;
import java.util.ArrayList;

public class Player extends GameObject implements IThingsContainer{
    public Player(int x, int y, int id){
        super(x, y, Constants.playerSpeed, Constants.playerHp);
        weapons =new ArrayList<>();
        this.id = id;
        this.seeSize = Constants.playerSeeSize;
        this.texture1 = new Image("/resources/textures/player1.png");
        this.texturePistol = new Image("/resources/textures/playerPistol1.png");
        this.textureBomb = new Image("/resources/textures/playerBomb1.png");
        this.objectTexture = texture1;
        lastShotTime = LocalTime.now();
    }

    private ArrayList<IWeapon> weapons;
    private IWeapon tempWeapon = null;
    private int id;
    private int seeSize;

    private Image texture1;
    private Image texturePistol;
    private Image textureBomb;

    private LocalTime lastShotTime;


    public int getId() {
        return id;
    }

    public int getSeeSize(){
        return seeSize;
    }

    public IWeapon getTempWeapon() {
        return tempWeapon;
    }

    public ArrayList<IWeapon> getWeapons() {
        return weapons;
    }

    public void spinWeapon(){
        if(weapons.size() > 0){
            if(tempWeapon == null){
                tempWeapon = weapons.get(0);
            }
            else{
                for(int i = 0; i < weapons.size(); i++){
                    if(weapons.get(i).equals(tempWeapon)){
                        if(i + 1 < weapons.size()){
                            tempWeapon = weapons.get(i+1);
                            break;
                        }
                        else{
                            tempWeapon = null;
                            break;
                        }
                    }
                }
            }
        }
        setTextureWithRightWeapon();
    }


    private void setTextureWithRightWeapon(){
        if(this.tempWeapon == null){
            this.objectTexture = this.texture1;
        }
        else if(this.tempWeapon instanceof Pistol){
            this.objectTexture = this.texturePistol;
        }
        else if(this.tempWeapon instanceof Bomb){
            this.objectTexture = this.textureBomb;
        }
    }


    public LocalTime getLastShotTime() {
        return lastShotTime;
    }


    public void setLastShotTime(LocalTime lastShotTime) {
        this.lastShotTime = lastShotTime;
    }

    public int getCountOfBombs(){
        if(tempWeapon instanceof Bomb){
            return ((Bomb) tempWeapon).getCount();
        }
        else{
            return 0;
        }
    }
}
