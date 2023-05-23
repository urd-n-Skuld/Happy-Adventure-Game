import java.awt.*;
import java.util.ArrayList;

public class EnemyClass extends BlockClass {
    private int leftTravelMax, rightTravelMax;
    private double VelX = 2;
    Rectangle hitBox;
    private int locX, locY, gridLoc;

    public void setMaxLeft(int max, int blockSize){ this.leftTravelMax = max; }
    public void setMaxRight(int max, int blockSize){ this.rightTravelMax = max; }
    public EnemyClass(int x, int y, int type, int gridIndex) {
        super(x, y, type);
        this.locX = x;
        this.locY = y;
        this.gridLoc = gridIndex;

    }

    public void Move() {
        //System.out.println("EC line 25 "+ this.locX+ " "+ this.leftTravelMax );
        this.locX += (int) VelX;
        if ((this.locX <= leftTravelMax)||(this.locX >= rightTravelMax)) { VelX *= -1; }
        //System.out.println("EC line 34 "+ this.locX+ " "+ this.rightTravelMax );
    }

public int getGridLoc(){ return gridLoc; }
    public Rectangle getHitBox() { return this.hitBox; }
    public void setEnemyHitBox(int x, int y, int w, int h)
   {   //not hitbox properties set for these types: candy, fire, ladder, spike

            hitBox = new Rectangle(x, y, w, h);
    }
    @Override public int getPosX() {
        return locX;
    }

    @Override public int getPosY() {
        return locY;
    }
}