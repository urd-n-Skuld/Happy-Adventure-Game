import java.awt.*;

public class EnemyClass extends BlockClass {
    private int leftTravelMax, rightTravelMax;
    private double VelX = 2;
    Rectangle hitBox;
    private int locX, locY, gridLoc;



    public EnemyClass(int x, int y, int type, int gridIndex) {
        super(x, y, type);
        this.locX = x * HappyAdventuresGame.blockSize;
        this.locY = y * HappyAdventuresGame.blockSize;
        this.gridLoc = gridIndex;
        this.setEnemyHitBox(x, y, HappyAdventuresGame.blockSize, HappyAdventuresGame.blockSize);
    }
    public void init(){

    }

    public void Move() {
        //System.out.println("EC line 25 "+ this.locX+ " "+ this.leftTravelMax );
        this.locX += (int) VelX;
        if ((this.locX >= leftTravelMax)||(this.locX <= rightTravelMax)) { VelX *= -1; }
        //System.out.println("EC line 34 "+ this.locX+ " "+ this.rightTravelMax );
    }


    public Rectangle getHitBox() { return this.hitBox; }
    public void setEnemyHitBox(int x, int y, int w, int h)
   {   //not hitbox properties set for these types: candy, fire, ladder, spike

            hitBox = new Rectangle(x, y, w, h);
    }
}