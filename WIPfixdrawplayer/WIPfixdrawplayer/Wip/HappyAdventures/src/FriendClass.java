import java.awt.*;

public class FriendClass extends BlockClass {
    Rectangle hitBox;
    private int posX, posY, gridLoc;
    private double VelX;

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

    public void Move() {

    }
}
