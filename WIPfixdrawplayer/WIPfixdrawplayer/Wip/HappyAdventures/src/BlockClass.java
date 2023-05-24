

import java.awt.*;

public class BlockClass{
    Rectangle hitBox;
    private int posX, posY, type, cellIndex;

    // init

    public BlockClass(int x, int y, int type) { this.posX = x; this.posY = y; this.type = type; }

    //set

    public void setPosX(int x) { posX = x; }
    public void setPosY(int y) { posY = y; }
    public void setType(int t) { type = t; }
    public void setCellIndex(int i) { cellIndex = i; }
    public void setblockHitBox(int x, int y, int w, int h)
    {   //not hitbox properties set for these types: candy, fire, ladder, spike, keys
        if (((type >= 3) && (type <= 5))||((type >= 13) && (type <= 18))||((type >= 27) && (type <= 29)))
        {
            hitBox = new Rectangle(0, 0, 0, 0);
        }
        else { hitBox = new Rectangle(x, y, w, h); }
    }

    // Get

    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public int getType() { return type; }
    public int getCellIndex() { return cellIndex; }

}

