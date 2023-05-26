import java.awt.*;

public class FriendClass extends BlockClass {
    Rectangle hitBox;
    private boolean follow, saved;
    private int posX, posY;
    private final int  StartX, StartY, gridLoc;
    private final Image[][] ImageArray = new Image[8][15];




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
    public FriendClass(EnemyClass enemy) {
        super(enemy.getPosX(), enemy.getPosY(), enemy.getType()+3);
        this.posX = enemy.getPosX();
        this.posY = enemy.getPosY();
        this.StartX = this.posX;
        this.StartY = this.posY;
        this.gridLoc = enemy.getGridLoc();
        this.follow = true;
        initFriendSprites();
    }


    public void initFriendSprites()
    {
        Image spriteSheet = GameEngine.loadImage("images/Sprites/SPHappFriends.png");
        for (int x = 0; x < ImageArray.length; x++)
        {
            for (int y = 0; y < ImageArray[x].length; y++)
            {
                ImageArray[x][y] = GameEngine.subImage(spriteSheet, y * 50, x * 50, 50, 50);
            }
        }
    }

    // set

    public void setFriendFollow() { if(!saved) {this.follow = true;} }
    @Override
    public void setblockHitBox(int x, int y, int w, int h)
    {
            hitBox = new Rectangle(0, 0, 0, 0);
    }
    public void setSaved(){
        if(!saved) {
            this.follow = false;

            this.saved = true;
        }

    }

    //get


    @Override public int getPosX() { return posX; }
    @Override public int getPosY() { return posY; }
    public boolean getSaved() { return this.saved; }
    public int getGridIndex() { return this.gridLoc; }
    public boolean getFollow() { return this.follow; }
    public Image[][] getImageArray() { return ImageArray; }

    // class specific


    public void Move(double HappyX, double HappyY, double distance) {
        this.posX += ((HappyX - this.posX) / distance) * (distance/5);
        this.posY += ((HappyY - this.posY) / distance) * (distance/5);
    }
    public void softreset() {
        if (!saved)
        {
        this.follow = false;
        this.posX = StartX;
        this.posY = StartY;
        }
    }
}
