
public class GridClass
{
    private int cellIndex;
    private int posX, posY, type;
    private boolean isActive;
    boolean[] blockAttributeArray = new boolean[10];

    public void setCellIndex (int i) { cellIndex = i; }
    public void setPosX (int i) { posX = i; }
    public void setPosY (int i) { posY = i; }
    public void setBlockType (int i) { type = i; }
    public void setActiveInd (boolean i) { isActive = i; }
    public int getCellIndex() { return cellIndex; }
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public int getBlockType() { return type; }
    public boolean getActiveInd() { return isActive; }

    /*public void setActiveInd(boolean bool)
    {
        this.active = bool;
        setAttributes();
    }*/

    private void setAttributes()
    {
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
        switch (this.type)
        {
            case 0://  Platform block - border
            case 1://  Platform - soil
            case 2://  Platform - grass

                this.blockAttributeArray[4] = this.blockAttributeArray[9] = true;
                break;

            case 9://  door - red
            case 10:// door - blue
            case 11:// door - yellow

                this.blockAttributeArray[4] = this.blockAttributeArray[9] = true;
                break;

            case 5:// ladder
                this.blockAttributeArray[8] = this.blockAttributeArray[9] = true;
                break;

            case 3:// Spikes
            case 4:// fire

                this.blockAttributeArray[0] = this.blockAttributeArray[9] = true;
                break;

            case 19:// Heart
                //heart and candy can pass through, not solid
                this.blockAttributeArray[5] = this.blockAttributeArray[9] = true;
                break;

            case 16:// Candy 1
            case 17:// Candy 2
            case 18:// Candy 3
                this.blockAttributeArray[7] =  this.blockAttributeArray[9] = true;
                break;

            case 24:// Enemy 1
            case 25:// Enemy 2
            case 26:// Enemy 3

                this.blockAttributeArray[0] = this.blockAttributeArray[3] = this.blockAttributeArray[4] = this.blockAttributeArray[9] = true;
                break;

            case 27:// Friendly 1
            case 28:// Friendly 2
            case 29:// Friendly 3

                this.blockAttributeArray[3] = this.blockAttributeArray[9] = true;
                break;

            default:


                //case 30:// Clock/timer for player 2
                //case 31://Happy load
                //case 32://Bappy load

        }

    }

    public void setLeftMax(int aiX) {
    }

    public void setRightMax(int aiX) {
    }
}