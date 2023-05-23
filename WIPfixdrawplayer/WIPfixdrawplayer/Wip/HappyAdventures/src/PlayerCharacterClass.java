import java.awt.*;
import java.util.TimerTask;

public class PlayerCharacterClass
{
    private Image[][] ImageArray = new Image[8][15];
    Image playerIm;
    private int posX, posY, size, score, type, cellIndex;
    private double velX, velY;
    private double accelX, accelY;
    public final double gravity = 9.8;
    public final int maxSpeedX = 200, stopSpeedX = 800;
    Rectangle hitBox;
    private int life;

    public void setSize(int s) { size = s; }
    public void setType(int t) { type = t; }
    public void setPosX(int x) { posX = x; }
    public void setPosY(int y) { posY = y; }
    public void setVelX (double vx) { velX = vx; }
    public void setVelY (double vy) { velY = vy; }
    public void setAccelX (double ax) { accelX = ax; }
    public void setAccelY (double ay) { accelY = ay; }
    public void setPlayerImage (HappyAdventuresGame gameObj) { playerIm = getPlayerImage(gameObj); }
    public void setPlayerScore(int val) { score += val; }
    public void setPlayerLife(int l) { life = l; }
    public void setHitBoxXY(int x, int y) { hitBox.x = x; hitBox.y = y; }
    public void setCellIndex(int i) { cellIndex = i; }
    public int getSize () { return size; }
    public int getType () { return type; }
    public int getPosX () { return posX; }
    public int getPosY () { return posY; }
    public double getVelX () { return velX; }
    public double getVelY () { return velY; }
    public double getAccelX () { return accelX; }
    public double getAccelY () { return accelY; }
    public int getPlayerScore () { return score; }
    public int getPlayerLife () { return life; }
    public int getHitBoxX() { return hitBox.x; }
    public int getHitBoxY() { return hitBox.y; }
    public Image[][] getImageArray() { return ImageArray; }
    public int getCellIndex() { return cellIndex; }
    private Image getPlayerImage(HappyAdventuresGame gameObj)
    {
        if (type == 31)
        {
            return gameObj.loadImage("images/Sprites/happy.png");
        }
        else if (type == 32)
        {
            //return gameObj.loadImage("images/Sprites/bappy.png");
        }
        return null;
    }
    public void loadPlayerSprites(HappyAdventuresGame gameObj)
    {
        Image spriteSheet = gameObj.loadImage("images/Sprites/SPHappy.png");

        for (int x = 0; x < ImageArray.length; x++)
        {
            for (int y = 0; y < ImageArray[x].length; y++)
            {
                ImageArray[x][y] = gameObj.subImage(spriteSheet, y * 50, x * 50, 50, 50);
            }
        }
    }
    public void addLife() { life++; }
    /*public static void loseLife()
    {
        hit = true;
        life--;
        System.out.println("enemy collision detected");
        hitTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                hit = false;
            }
        }, 2000);
        System.out.println("hit is "+hit);
    }*/
    public void addScore(int i) { score += i; }
    public void createHitBox(int x, int y, int s)
    {
        hitBox = new Rectangle(x, y, s,s);
    }
    public void checkIfTrue()
    {
        //DEBUG CHECK IF playerSettings is listening keys from SimpleGame
        //use this if you do any change to the character movement keys or linked methods ex: test the right key
        // System.out.println("key right " + keyRight);
    }


    /*public static int[] getLoc(){
        return new int[]{posX, posY};
    }*/
    /*public static double[] getVel(){
        return new double[]{velX, velY};
    }*/
    /*public static void setLoc(int loc, int i){
        if (i==0) { posX = i; }
        else { posY = i; }
    }*/
    /*public static void setVel(double vel, int i){
        if (i==0) { velX = vel; }
        else { velY = vel; }
    }*/
    /*public static void setHitBox(int loc, int i){
        if (i==0) { hitBox.x = loc; }
        else { hitBox.y = loc; }
    }*/
    /*public static int[] getHitBox(){
        return new int[]{hitBox.x, hitBox.y};

    }*/



    /*public static boolean getIsOnGround(){ return isOnGround; }
    public static boolean getJump(){ return jump; }
    public static void setJump(boolean bool){
        jump = bool;
    }

    public static void setIsOnGround(boolean bool){
        isOnGround = bool;
    }*/



}
