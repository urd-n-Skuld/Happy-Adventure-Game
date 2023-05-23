

import java.awt.*;

public class BlockClass
{
    //------------------------------------------------------
    //Declaring the variables
    //------------------------------------------------------
    Rectangle hitBox;

    private int posX;   //Don't make these static!!!
    private int posY;   //Don't make these static!!!
    private int type;   //Don't make these static!!!
    private int cellIndex;
    boolean[] blockAttributeArray = new boolean[11];        //The more attributes are added, this will need to increase!

    public BlockClass(int x, int y, int type) {

        this.posX = x;
        this.posY = y;
        this.type = type;
    }

    // 0 = dangerous
    // 1 = has block to left
    // 2 = has block to the right
    // 3 = moves
    // 4 = solid...cant pass through
    // 5 = adds life on contact
    // 6 = used already
    // 7 = adds points
    // 8 = ladder
    // 9 = has image
    // 10= no hitbox
    //------------------------------------------------------
    //Declaring the methods for the class
    //------------------------------------------------------
    public void setPosX(int x) {
        posX = x;
    }
    public void setPosY(int y) {
        posY = y;
    }
    public void setType(int t) {
        type = t;
    }
    public void setCellIndex(int i) { cellIndex = i; }

    public void setAttributes(int index, boolean attributeFlag) {
        this.blockAttributeArray[index] = attributeFlag;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getType() {
        return type;
    }
    public int getCellIndex() { return cellIndex; }

//    protected void makeHitBox(int blockSize)
//    // allows for different spacing on the ends of blocks
//    // untested
//    {
//        if (this.blockAttributeArray[1] && this.blockAttributeArray[2]) {
//            this.hitBox = new Rectangle(this.posX, this.posY, blockSize, blockSize);
//        } else if (this.blockAttributeArray[1])// block on left -3 from the right
//        {
//            this.hitBox = new Rectangle(this.posX, this.posY, blockSize - 3, blockSize);
//        } else if (this.blockAttributeArray[2])// block to the right.... hitbox - 3 on the left
//        {
//            this.hitBox = new Rectangle(this.posX + 3, this.posY, blockSize - 3, blockSize);
//        } else// no blocks on either side so both sides lose 3
//        {
//            this.hitBox = new Rectangle(this.posX + 3, this.posY, blockSize - 6, blockSize);
//        }
//    }
    public Rectangle getHitBox() {
        return this.hitBox;
    }
    public void setblockHitBox(int x, int y, int w, int h)
    {   //not hitbox properties set for these types: candy, fire, ladder, spike, keys
        if (((type >= 3) && (type <= 5))||((type >= 13) && (type <= 18)))
        {
            hitBox = new Rectangle(0, 0, 0, 0);
        }
        else
        {
            hitBox = new Rectangle(x, y, w, h);
        }
    }

}

