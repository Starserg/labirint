package sample.entities.things;

public abstract class Thing {
    public Thing(int x, int y){
        this.x = x;
        this.y = y;
    }

    protected int x;
    protected int y;

    public int getX() {
        return x;
    }

    public void setX(int x) throws Exception {
        if(x < 0){
            throw new Exception();
        }
        else{
            this.x = x;
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) throws Exception {
        if(y < 0){
            throw new Exception();
        }
        else{
            this.y = y;
        }
    }


}
