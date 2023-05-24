import java.awt.*;

public class FriendClass extends BlockClass {

    Rectangle hitBox;
    private boolean saved = false;
    private int posX, posY, gridLoc;
    private Image[][] ImageArray = new Image[8][15];

    // init

    public FriendClass(int x, int y, int type, int gridIndex) {
        super(x, y, type);
        this.posX = x;
        this.posY = y;
        this.gridLoc = gridIndex;
    }
    public void initFriendSprites(HappyAdventuresGame gameObj)
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

    // set

    public void setFriendSaved() { saved = true; }
    public void setFriendHitBox(int x, int y, int w, int h)
    {   //not hitbox properties set for these types: candy, fire, ladder, spike
        hitBox = new Rectangle(x, y, w, h);
    }

    //get

    @Override public int getPosX() { return posX; }
    @Override public int getPosY() { return posY; }
    public Rectangle getHitBox() { return this.hitBox; }
    public Image[][] getImageArray() { return ImageArray; }

    // class specific

    public void Move() { if (saved) { } }


}
