
public class GridClass
{
    private int cellIndex;
    private int posX, posY, type;
    private boolean isActive;

    //set

    public void setCellIndex (int i) { cellIndex = i; }
    public void setPosX (int i) { posX = i; }
    public void setPosY (int i) { posY = i; }
    public void setBlockType (int i) { type = i; }
    public void setActiveInd (boolean i) { isActive = i; }

    //get

    public int getCellIndex() { return cellIndex; }
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public int getBlockType() { return type; }
    public boolean getActiveInd() { return isActive; }


}