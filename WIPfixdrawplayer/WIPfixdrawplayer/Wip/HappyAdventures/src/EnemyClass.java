public class EnemyClass extends BlockClass {
    private int leftTravelMax, rightTravelMax;
    private double VelX = 2;

    private int locX, locY;
///////////////////////////////////////////////////
    private int enemyindex;
    private static int i = 1;
//////////////////////////////////////////////////////
    public EnemyClass(int x, int y, int type) {
        super(x, y, type);
        this.locX = x * HappyAdventuresGame.blockSize;
        this.locY = y * HappyAdventuresGame.blockSize;
        // gets pulled out of grid after it is initialised... will need its own loc



       //////////////////////////////////////////////
        enemyindex = i;
        i++;
        /////////////////////////////////////////
    }

    public void Move() {
        //System.out.println("EC line 25 "+ this.locX+ " "+ this.leftTravelMax );
        this.locX += (int) VelX;
        if (this.locX >= leftTravelMax) {
            VelX *= -1;
        }
        if (this.locX <= rightTravelMax) {
            VelX *= -1;
        }
        //System.out.println("EC line 34 "+ this.locX+ " "+ this.rightTravelMax );
    }
/*   @Override
    public void setLeftMax(int lMax) {
        this.leftTravelMax = lMax * HappyAdventuresGame.blockSize;
    }
    @Override
    public void setRightMax(int rMax) {
        this.rightTravelMax = rMax * HappyAdventuresGame.blockSize;
    }
*/
}