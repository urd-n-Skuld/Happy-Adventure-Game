import java.awt.*;

public class FriendClass extends BlockClass {

    private boolean saved;
    private int StartX, StartY, posX, posY, gridLoc;
    private Image[][] ImageArray = new Image[8][15];



    // init

    public FriendClass(int x, int y, int type, int gridIndex) {
        super(x, y, type);
        this.posX = x;
        this.posY = y;
        this.StartX = x;
        this.StartY = y;
        this.gridLoc = gridIndex;
        System.out.println(this.gridLoc+ " new friend");
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

    public void setFriendSaved() { this.saved = true; }

    //get
    public boolean getSaved() { return this.saved; }
    @Override public int getPosX() { return posX; }
    @Override public int getPosY() { return posY; }
    public int getGridIndex() {return this.gridLoc; }
    public Image[][] getImageArray() { return ImageArray; }

    // class specific


    public void Move(double HappyX, double HappyY, double distance) {
        this.posX += ((HappyX - this.posX) / distance) * (distance/5);
        this.posY += ((HappyY - this.posY) / distance) * (distance/5);
    }
    public void softreset(){
        this.saved = false;
        this.posX = StartX;
        this.posY = StartY;
    }
}
