import java.awt.*;
import java.util.TimerTask;

public class PlayerCharacterClass
{
    private final Image[][] ImageArray = new Image[8][15];
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
            return GameEngine.loadImage("images/Sprites/happy.png");
        }
        else if (type == 32)
        {
            //return gameObj.loadImage("images/Sprites/bappy.png");
        }
        return null;
    }
    public void loadPlayerSprites(HappyAdventuresGame gameObj)
    {
        Image spriteSheet = GameEngine.loadImage("images/Sprites/SPHappy.png");

        for (int x = 0; x < ImageArray.length; x++)
        {
            for (int y = 0; y < ImageArray[x].length; y++)
            {
                ImageArray[x][y] = GameEngine.subImage(spriteSheet, y * 50, x * 50, 50, 50);
            }
        }
    }


    public void createHitBox(int x, int y, int s)
    {
        hitBox = new Rectangle(x, y, s,s);
    }

}
