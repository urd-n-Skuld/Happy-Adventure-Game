import java.awt.*;

public class FriendClass extends BlockClass {
    private Image[][] ImageArray = new Image[8][15];
    Image  friendIm;
    Rectangle hitBox;
    private int posX, posY, gridLoc;
    private double VelX;
    private boolean saved = false;

    public FriendClass(int x, int y, int type, int gridIndex) {
        super(x, y, type);
        this.posX = x;
        this.posY = y;
        this.gridLoc = gridIndex;
    }

    public void setFriendHitBox(int x, int y, int w, int h)
    {   //not hitbox properties set for these types: candy, fire, ladder, spike
        hitBox = new Rectangle(x, y, w, h);
    }
    public Rectangle getHitBox() { return this.hitBox; }
    public int getGridLoc(){ return gridLoc; }
    @Override public int getPosX() { return posX; }
    @Override public int getPosY() { return posY; }

    public void friendSaved() { saved = true; }
    public void Move() {
        if (saved) {
            
        }
    }

    public Image[][] getImageArray() { return ImageArray; }

    public void loadFriendSprites(HappyAdventuresGame gameObj)
    {
        Image spriteSheet = gameObj.loadImage("images/Sprites/SPHappFriends.png");

        for (int x = 0; x < ImageArray.length; x++)
        {
            for (int y = 0; y < ImageArray[x].length; y++)
            {
                ImageArray[x][y] = gameObj.subImage(spriteSheet, y * 50, x * 50, 50, 50);
            }
        }
    }
}
