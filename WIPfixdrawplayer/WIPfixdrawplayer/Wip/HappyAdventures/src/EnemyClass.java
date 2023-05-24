import java.awt.*;
import java.util.ArrayList;

public class EnemyClass extends BlockClass {

    Rectangle hitBox;
    private double VelX = 2;
    private int locX, locY, gridLoc;
    private int leftTravelMax, rightTravelMax;
    private Image[][] ImageArray = new Image[8][15];

    // init

    public EnemyClass(int x, int y, int type, int gridIndex) {
        super(x, y, type);
        this.locX = x;
        this.locY = y;
        this.gridLoc = gridIndex;
    }
    public void initEnemySprites(HappyAdventuresGame gameObj)
    {
        Image spriteSheet = gameObj.loadImage("images/Sprites/MonterSprites.png");

        for (int x = 0; x < ImageArray.length; x++)
        {
            for (int y = 0; y < ImageArray[x].length; y++)
            {
                ImageArray[x][y] = gameObj.subImage(spriteSheet, y * 50, x * 50, 50, 50);
            }
        }
    }

    //set

    public void setMaxLeft(int max, int blockSize){ this.leftTravelMax = max; }
    public void setMaxRight(int max, int blockSize){ this.rightTravelMax = max; }
    public void setEnemyHitBox(int x, int y, int w, int h)
    {   //not hitbox properties set for these types: candy, fire, ladder, spike
        hitBox = new Rectangle(x, y, w, h);
    }

    //get

    @Override public int getPosY() {
        return locY;
    }
    @Override public int getPosX() {
        return locX;
    }
    public Rectangle getHitBox() { return this.hitBox; }
    public Image[][] getImageArray() { return ImageArray; }
    public int getGridLoc() {
        return gridLoc;
    }

    // class specific

    public void Move() {
        this.locX += (int) VelX;
        if ((this.locX <= leftTravelMax)||(this.locX >= rightTravelMax)) { VelX *= -1; }
    }










}