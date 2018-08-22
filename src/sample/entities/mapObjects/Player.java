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
        things = new ArrayList<>();
        weapons =new ArrayList<>();
        this.id = id;
        this.withTorch = false;
        this.seeSize = Constants.playerSeeSize;
        this.texture1 = new Image("/resources/textures/player1.png");
        this.texturePistol = new Image("/resources/textures/playerPistol1.png");
        this.textureBomb = new Image("/resources/textures/playerBomb1.png");
        this.texture1Torch = new Image("/resources/textures/player1torch.png");
        this.texturePistolTorch = new Image("/resources/textures/playerPistol1torch.png");
        this.textureBombTorch = new Image("/resources/textures/playerBomb1torch.png");
        this.objectTexture = texture1;
        lastShotTime = LocalTime.now();
    }

    private ArrayList<IWeapon> weapons;
    private IWeapon tempWeapon = null;
    private int id;
    private int seeSize;
    private boolean withTorch;


    private Image texture1;
    private Image texturePistol;
    private Image textureBomb;
    private Image texture1Torch;
    private Image texturePistolTorch;
    private Image textureBombTorch;

    private LocalTime lastShotTime;

    private ArrayList<Thing> things;


    public void takeTorch(){
        if(!withTorch){
            this.withTorch = true;
            this.seeSize += Constants.torchEffect;
            if(tempWeapon == null){
                this.objectTexture = this.texture1Torch;
            }
            else if(tempWeapon instanceof Pistol){
                this.objectTexture = this.texturePistolTorch;
            }
            else if(tempWeapon instanceof Bomb){
                this.objectTexture = this.textureBombTorch;
            }
        }
    }

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
            if(withTorch){
                this.objectTexture = this.texture1Torch;
            }
            else {
                this.objectTexture = this.texture1;
            }
        }
        else if(this.tempWeapon instanceof Pistol){
            if(withTorch){
                this.objectTexture = this.texturePistolTorch;
            }
            else {
                this.objectTexture = this.texturePistol;
            }
        }
        else if(this.tempWeapon instanceof Bomb){
            if(withTorch){
                this.objectTexture = this.textureBombTorch;
            }
            else {
                this.objectTexture = this.textureBomb;
            }
        }
    }


    public LocalTime getLastShotTime() {
        return lastShotTime;
    }


    public void setHandWithoutWeapon(){
        this.tempWeapon = null;
        if(this.withTorch){
            this.objectTexture = this.texture1Torch;
        }
        else{
            this.objectTexture = this.texture1;
        }
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

    public boolean hasTorch() {
        return withTorch;
    }

    @Override
    public ArrayList<Thing> getThings() {
        return this.things;
    }
}
