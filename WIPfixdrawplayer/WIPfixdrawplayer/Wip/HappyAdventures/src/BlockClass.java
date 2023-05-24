

import java.awt.*;

public class BlockClass{
    Rectangle hitBox;
    private int posX, posY, type, cellIndex, startX, startY;
    private Image[][] blockAnimArray = new Image[2][15];
    // init

    public BlockClass(int x, int y, int type) { this.posX = x; this.posY = y; this.type = type; }

    //set

    public void setPosX(int x) { posX = x; }
    public void setPosY(int y) { posY = y; }
    public void setType(int t) { type = t; }
    public void setCellIndex(int i) { cellIndex = i; }
    public void setStartX (int x) { startX = x; }
    public void setStartY (int y) { startY = y; }
    public void setblockHitBox(int x, int y, int w, int h)
    {   //not hitbox properties set for these types: candy, fire, ladder, spike, keys

        if (((type >= 3) && (type <= 5))||((type >= 13) && (type <= 18))||((type >= 27) && (type <= 29))|| (type == 37))

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
    public Image[][] getImageArray() { return blockAnimArray; }
    public int getStartX () {return startX;}
    public int getStartY () { return startY;}
    public void initBlockAnimSprites(HappyAdventuresGame gameObj)
    {
        Image spriteSheet = gameObj.loadImage("images/Sprites/fire_heart_Sprites.png");

        for (int x = 0; x < blockAnimArray.length; x++)
        {
            for (int y = 0; y < blockAnimArray[x].length; y++)
            {
                blockAnimArray[x][y] = gameObj.subImage(spriteSheet, y * 50, x * 50, 50, 50);
            }
        }
    }

}

