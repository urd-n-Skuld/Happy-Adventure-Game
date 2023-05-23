import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class HappyAdventuresGame extends GameEngine implements ActionListener {
    //------------------------------------------------------
    //Global generic variables
    //------------------------------------------------------
    int numCols, numRows;       //These values are initialised when the world map is loaded (See loadBlocks())
    static int blockSize = 25;
    public boolean showHitboxes, showGrid = false;
    String gameStates; // "MenuSystem", "PlayGame", "2Player"
    String csvFile = "images/WorldMaps/Vertical_world.csv";
    //String csvFile = "images/WorldMaps/Horisontal world.csv";
    // putting this here allows easier changes
    static boolean death, gameOver;
    public boolean softResetIsTrue;
    public static Image[] blockIMG =
            {
                    loadImage("images/Sprites/border.png"),//0
                    loadImage("images/Sprites/soil.png"),//1
                    loadImage("images/Sprites/grass.png"),//2
                    loadImage("images/Sprites/spikesUp25px.png"),//3
                    loadImage("images/Sprites/fire.png"),//4 to create the fire sprite
                    loadImage("images/Sprites/ladder25px.png"),//5
                    loadImage("images/Sprites/ladder25px.png"),//6 need safe zone
                    loadImage("images/Sprites/safezonesign.png"),//7 need checkpoint sprites
                    loadImage("images/Sprites/checkpointInactive.png"),//8 need checkpoint
                    loadImage("images/Sprites/door_yellow25x.png"),//9
                    loadImage("images/Sprites/door_blue25x.png"),//10
                    loadImage("images/Sprites/door_yellow25x.png"),//11
                    loadImage("images/Sprites/block.png"),//12 need door extender
                    loadImage("images/Sprites/key_red25px.png"),//13
                    loadImage("images/Sprites/key_blue25px.png"),//14
                    loadImage("images/Sprites/key_yellow25px.png"),//15
                    loadImage("images/Sprites/CANDY1_25PX.png"),//16
                    loadImage("images/Sprites/CANDY2_25PX.png"),//17
                    loadImage("images/Sprites/CANDY3_25PX.png"),//18
                    loadImage("images/Sprites/heart.png"),//19 need heart sprite
                    loadImage("images/Sprites/block.png"),//20 need gate
                    loadImage("images/Sprites/waterfallbottom.png"),//21 need waterfall sprite
                    loadImage("images/Sprites/waterfallmiddle.png"),//22 need waterfall sprite
                    loadImage("images/Sprites/waterfalltop.png"),//23 need waterfall sprite
                    loadImage("images/Sprites/enemy1.png"),//24
                    loadImage("images/Sprites/enemy2.png"),//25
                    loadImage("images/Sprites/enemy3.png"),//26
                    loadImage("images/Sprites/orange.png"),//27 use friends sprite
                    loadImage("images/Sprites/orange.png"),//28 use friends sprite
                    loadImage("images/Sprites/orange.png"),//29 use friends sprite
                    loadImage("images/Sprites/orange.png"),//30 need clock
                    loadImage("images/Sprites/happy.png"),//31
                    loadImage("images/Sprites/happy_ass.png"),//32 need bappy
                    loadImage("images/Sprites/block_float25x75.png"),//33
                    loadImage("images/Sprites/vinetop.png"),//34
                    loadImage("images/Sprites/vinemiddle.png"),//35
                    loadImage("images/Sprites/vinebottom.png"),//36
                    loadImage("images/Sprites/supersweet.png"),//37
                    loadImage("images/Sprites/disappearingBlock.png"),//38    might need a sprite for this one
                    loadImage("images/Sprites/spikeBottom.png"),//39 need bappy
                    loadImage("images/Sprites/spikeLeft.png"),//40 need bappy
                    loadImage("images/Sprites/spikeRight.png"),//41 need bappy
                    loadImage("images/Sprites/secretblock1.png"),//42 need bappy
            };


    //------------------------------------------------------
    //Create a new game object
    //------------------------------------------------------
    public static void main(String[] args) {
        HappyAdventuresGame gameObj = new HappyAdventuresGame();
        createGame(gameObj, 60);
    }

    int frameWidth = 750, frameHeight = 500;

    @Override
    public void init()
    {System.out.println("Init called");
        setWindowSize(frameWidth, frameHeight);

        gameStates = "MenuSystem";    //Change this to "MenuSystem" if you want to turn on game menus
        showHitboxes = false;
        death = false;
        gameOver = false;

        initAudio();// line 109
        initWorld(csvFile);// line 113 .... creates variables for grid class
        initCharacters();// line 145
        initGUI();// line 98
        initEnemies();// line 927

        super.mFrame.setSize(frameWidth, frameHeight);

    }

    //------------------------------------------------------
    //GUI objects, methods and initialisation
    //------------------------------------------------------
    GUIClass menuObj = new GUIClass();

    public void initGUI()
    {System.out.println("InitGui called");
        //System.out.println("HAG Line 96 " + mFrame.isVisible());
        menuObj.setupGUI(this, super.mFrame, super.mPanel, gameStates);
    }

    //------------------------------------------------------
    //Audio objects, methods and initialisation
    //------------------------------------------------------
    AudioClass audioObj = new AudioClass();

    public void initAudio()
    {System.out.println("InitAudio called");
        audioObj.setupAudio(this);
    }

    //------------------------------------------------------
    //Game World objects initialisation
    //------------------------------------------------------
    ArrayList<BlockClass> myblocks;
    ArrayList<GridClass> gridObj;
    public void initWorld(String csv)
    {
        //System.out.println("InitWorld called");
        loadCSV map1 = new loadCSV();
        Object[] resultingLists = map1.loadMap(csv, blockSize);
        myblocks = (ArrayList<BlockClass>) resultingLists[0];
        gridObj = (ArrayList<GridClass>) resultingLists[1];

        numCols = loadCSV.getCol();
        numRows = loadCSV.getRows();
        //System.out.println("numRows: " + numRows + " numCols: " + numCols);

        for (BlockClass block : myblocks)
        {
            //System.out.println("LoadCSV line 61 " +block.getPosX()+ " = x " + block.getPosY() + " = y and "+ block.getType() + " = type");
            int x = block.getPosX();
            int y = block.getPosY();
            int type = block.getType();
            block.setblockHitBox(x,y, blockSize,blockSize);
        }
        //System.out.println(" HAG Line 130 " +numRows+ " numRows " + numCols + " numCols");
        //initEnemyMax();

        for (int i = 0; i<gridObj.size() - 1; i++)
        {
            gridObj.get(i).getBlockType();
            //System.out.println("HAG 144 type: " + gridObj.get(i).getBlockType());
        }


    }

    //------------------------------------------------------
    //Grid objects, methods and initialisation
    //------------------------------------------------------


    //------------------------------------------------------
    //Player and other non-playable characters initialisation
    //------------------------------------------------------
    PlayerCharacterClass happyObj = new PlayerCharacterClass();
    ArrayList<EnemyClass> enemyObj = new ArrayList<>();
    ArrayList<FriendClass> friendObj = new ArrayList<>();
    static boolean idle, jump, hit;
    static boolean leftKey, rightKey, upKey, downKey, jumpKey;
    int startPosX, startPosY;

    public void initCharacters()
    {
        //System.out.println("InitCharacters called");
        for (int i = 0; i < gridObj.size()-1; i++)
        {
            //Happy
            if (gridObj.get(i).getBlockType() == 31)
            {
                //System.out.println("gridObj.size():" + gridObj.size() + " i " + i);
                //System.out.println("numCol " + numCols);
                startPosX = gridObj.get(i).getPosX();
                startPosY = gridObj.get(i).getPosY();
                happyObj.setSize(blockSize);
                happyObj.setType(gridObj.get(i).getBlockType());
                happyObj.setPosX(startPosX);
                happyObj.setPosY(startPosY);
                happyObj.setVelX(0);
                happyObj.setVelY(0);
                happyObj.setAccelX(500);
                happyObj.setAccelY(0);
                happyObj.setPlayerImage(this);
                happyObj.setPlayerScore(0);
                happyObj.setPlayerLife(3);
                happyObj.createHitBox(startPosX, startPosY, blockSize);
                happyObj.loadPlayerSprites(this);
                happyObj.setCellIndex(gridObj.get(i).getCellIndex());
            }
            else if (gridObj.get(i).getBlockType() == 24 || gridObj.get(i).getBlockType() == 25 || gridObj.get(i).getBlockType() == 26) //Enemies  
            {
                int enemyPosX = gridObj.get(i).getPosX(), enemyPosY = gridObj.get(i).getPosY();
                EnemyClass tempEnemy = new EnemyClass(enemyPosX, enemyPosY, gridObj.get(i).getBlockType(), i);

                int leftStop = gridObj.get(i).getPosX(), rightStop = gridObj.get(i).getPosX(); //Initialise
                for (int indx = i+1; gridObj.get(indx).getBlockType() == -1 || gridObj.get(indx+numCols).getBlockType() != -1; indx++)
                {
                    System.out.println("CycleRight");
                    if (gridObj.get(indx).getBlockType() != -1 || gridObj.get(indx+numCols).getBlockType() == -1)
                    {
                        System.out.println("i " + i + " indx " + indx + " type " + gridObj.get(indx).getBlockType() + " type " + gridObj.get(indx + numCols).getBlockType());
                        rightStop = gridObj.get(indx-1).getPosX();
                        break;
                    }
                }
                for (int indx = i-1; gridObj.get(indx).getBlockType() == -1 || gridObj.get(indx+numCols).getBlockType() != -1; indx--)
                {
                    System.out.println("CycleLeft");
                    if (gridObj.get(indx).getBlockType() != -1 || gridObj.get(indx+numCols).getBlockType() == -1)
                    {
                        leftStop = gridObj.get(indx+1).getPosX();
                        break;
                    }
                }
                //System.out.println("startPosX " + gridObj.get(i).getPosX() + " RightStop: " + rightStop + " LeftStop: " + leftStop);
                tempEnemy.setMaxLeft(leftStop, blockSize);
                tempEnemy.setMaxRight(rightStop, blockSize);
                tempEnemy.loadEnemySprites(this);
                enemyObj.add(tempEnemy);
            }
            else if (gridObj.get(i).getBlockType() == 27 || gridObj.get(i).getBlockType() == 28 || gridObj.get(i).getBlockType() == 29) //Friends
            {
                int friendPosX = gridObj.get(i).getPosX(), friendPosY = gridObj.get(i).getPosY();
                FriendClass tempFriend = new FriendClass(friendPosX, friendPosY, gridObj.get(i).getBlockType(), i);
                tempFriend.loadFriendSprites(this);
                friendObj.add(tempFriend);
            }
        }
            //Bappy Placeholder
            //if (gridObj.getBlockType(i) == 32) {}

            idle = isOnGround = canJump = true;
            leftKey = rightKey = upKey = downKey = jumpKey = jump = hit = false;
            isJumping = isFalling = isClimbing = collided = false;
    }

    //------------------------------------------------------
    //Initialising the blocks Arraylist by loading the worldmap csv
    //------------------------------------------------------
//    public void loadBlocks()
//    {
//        myblocks.clear();
//
//        String csvFile = "images/WorldMaps/Horisontal world.csv";
//        BufferedReader br = null;
//        String line = "";
//        String cvsSplitBy = ",";
//
//        try
//        {
//            br = new BufferedReader(new FileReader(csvFile));
//            int row = 0;
//
//            while ((line = br.readLine()) != null)
//            {
//                // Used to Split the line from CSV into columns we can use the tiled app to create different world by exporting the csv file
//                String[] columns = line.split(cvsSplitBy);      //reads in one line
//                int column = 0;
//
//                for (String col : columns)
//                {
//                    int x = column * blockSize;
//                    int y = row * blockSize;
//
//                    if(!Objects.equals(col, "-1"))     //We only want to create a block if it is not an empty space
//                    {
//                        BlockClass block = new BlockClass();
//                        block.setPosX(x);
//                        block.setPosY(y);
//                        block.setType(Integer.parseInt(col));
//
//                    /*
//                          Here are the different attributes for the blocks:    (Remember to update the blockAttributeArray[] in BlockClass if new attributes are added
//                          0 = dangerous - takes 1 life on contact
//                          1 = has block to left
//                          2 = has block to the right
//                          3 = moves
//                          4 = solid...cant pass through
//                          5 = adds life on contact
//                          6 = still exists
//                          7 = adds points
//                          8 = can climb up or down
//                          9 = saves current position for checkpoint
//                     */
//                        // The following sets the attributes for the block
//                        switch (col)
//                        {
//                            case "0":
//                            case "1":
//                            case "2":
//                                block.setAttributes(4, true);
//                                break;
//                            //case "24":
//                            //case "25":
//                            //case "26":
//                            //break;
//
//                            default:
//                                //
//                                break;
//                        }
//                        myblocks.add(block);
//                    }
//
//                    column++;
//                }
//                numCols = column;
//                row++;
//            }
//            numRows = row;
//        }
//        catch (IOException e) { e.printStackTrace(); System.out.println("Error loading the CSV file. Location: loadBlocks()");}
//        finally
//        {
//            if (br != null)
//            {
//                try { br.close(); }
//                catch (IOException e) { e.printStackTrace(); }
//            }
//        }
//    }

    //------------------------------------------------------
    //The game loop update function for updating the game attributes
    //------------------------------------------------------
    int happyIndex, happyIdle = 0, happyJump = 1, happyHit = 2;
    double setDt;

    @Override
    public void update(double dt) {//System.out.println("Update called");
        setDt = dt;
        if (!gameOver) {
            happyIndex = updateAnimationSpeed(15);
            updateHappy(dt);
            moveBlocks();
        }
    }

    //------------------------------------------------------
    //Updating movements and actions of Happy
    //------------------------------------------------------
    int posX, posY, happyBoxX, happyBoxY, size, life;
    static double velX, velY, accelX, accelY;
    //static Timer hitTimer;
    static boolean isOnGround, isJumping, isFalling, isClimbing, collided, canJump;

    public void updateHappy(double dt) {
    //System.out.println("HAG Line 326 Calling updateHappy function");
    //System.out.println("HAG Line 327 " + leftKey + " " + rightKey + " " + upKey + " " + downKey + " " + jumpKey);
    //System.out.println("HAG Line 328 " + collided + " " + isClimbing + " " + canJump + " " + isFalling);
    //System.out.println("HAG Line 329 " + happyObj.getPosX() + " " + happyObj.getPosY() + " " + happyObj.getVelX() + " " + happyObj.getVelY() + " "+ happyObj.getAccelX() + " " + happyObj.getAccelY());
    //System.out.println("HAG Line 330 " + happyObj.getHitBoxX() + " " + happyObj.getHitBoxY());

        posX = happyObj.getPosX();
        posY = happyObj.getPosY();
        velX = happyObj.getVelX();
        velY = happyObj.getVelY();
        accelX = happyObj.getAccelX();
        accelY = happyObj.getAccelY();
        happyBoxX = happyObj.getHitBoxX();
        happyBoxY = happyObj.getHitBoxY();

        if (!hit) {
            //Horisontal movement
            if (leftKey && !rightKey) {
                //Moving to the left
                velX -= accelX * dt;

                if (velX < -happyObj.maxSpeedX) {
                    velX = -happyObj.maxSpeedX;
                }
            } else if (rightKey && !leftKey) {
                //Moving to the right
                velX += accelX * dt;

                if (velX > happyObj.maxSpeedX) {
                    velX = happyObj.maxSpeedX;
                }
            } else {
                //Happy is not moving left or right, but velX is not 0
                if (velX > 0) {
                    //If happy has positive velocity, applying a negative stop force
                    velX -= happyObj.stopSpeedX * dt;

                    if (velX < 0) {
                        velX = 0;
                    }
                }
                //Happy is not moving left or right, but velX is not 0
                else if (velX < 0) {
                    //If happy has negative velocity, applying a positive stop force
                    velX += happyObj.stopSpeedX * dt;

                    if (velX > 0) {
                        velX = 0;
                    }
                }
            }

            //Update Happy's current position, and hitbox current pos
            happyObj.setVelX(velX);
            posX += velX * dt;
            happyObj.setPosX(posX);
            happyBoxX = posX;
            happyObj.setHitBoxXY(posX, posY);

            //Collision
            collisionCheck();

            //Vertical movement
            if (isClimbing) {
                //System.out.println("isClimbing: " + isClimbing);
                accelY = 0;

                if (upKey && !downKey) {   //if Happy is climbing up
                    velY = -200;
                } else if (downKey && !upKey) {   //if Happy is climbing down
                    velY = 200;
                }
            } else if (!canJump && !isOnGround && isJumping) {   //if Happy is jumping
                //System.out.println("isJumping: " + isJumping);
                accelY += happyObj.gravity;
                velY += accelY * dt;
                posY += velY * dt;
            } else if (!isJumping) {
                //if Happy is not in jumping or climbing, apply a vertical gravitational force
                //System.out.println("isJumping: " + isJumping + " isClimbing: " + isClimbing + " isOnGround: " + isOnGround);
                accelY += happyObj.gravity;
                velY = 200;
                velY += accelY * dt;
                posY += velY * dt;
            }
            //Update Happy's current position, and hitbox current pos
            happyObj.setAccelY(accelY);
            happyObj.setVelY(velY);
            happyObj.setPosY(posY);
            happyObj.setHitBoxXY(posX, posY);
        }
        //Collision
        collisionCheck();
    }

    //------------------------------------------------------
    //Updating animation speed for Happy's sprites
    //------------------------------------------------------
    private int aniCounter, aniIndex, aniSpeed = 4;

    public int updateAnimationSpeed(int frameAmount) {
        aniCounter++;
        if (aniCounter >= aniSpeed) {
            aniCounter = 0;
            aniIndex++;
            if (aniIndex >= frameAmount) {
                aniIndex = 0;
            }
        }
        return aniIndex;
    }

    /*private void initEnemyMax()
    {
        for (BlockClass block : myblocks)
        {
            if (block instanceof EnemyClass enemy)
            {
                int x = block.getPosX();
                int y = block.getPosY();
                int AI_X = x;
                int AI_Y = y + 1;
                System.out.println("hag line 422 x = " + x + " y = " + y);
                //System.out.println("hag line 423 x = "+ AI_X + " y = "+ AI_Y );
                //if block below has a block on the left & the block to sprites left doesn't exist or is not solid
                if ((gridObj[AI_X][AI_Y].blockAttributeArray[1]) && ((gridObj[AI_X - 1][y] == null) || (!gridObj[AI_X - 1][y].blockAttributeArray[4]))) {
                    while ((gridObj[AI_X][AI_Y].blockAttributeArray[1]) && ((gridObj[AI_X - 1][y] == null) || (!gridObj[AI_X - 1][y].blockAttributeArray[4]))) {
                        AI_X--;
                        System.out.println("hag line 423 x = " + AI_X + " y = " + AI_Y);
                    }
                }
                enemy.setLeftMax(AI_X);
                AI_X = x;
                // if block below has a block to the right & the block to sprites right doesn't exist or is not solid
                if ((gridObj[AI_X][AI_Y].blockAttributeArray[2]) && ((gridObj[x + 1][y] == null) || (!gridObj[x + 1][y].blockAttributeArray[4]))) {
                    while ((gridObj[AI_X][AI_Y].blockAttributeArray[2]) && ((gridObj[x + 1][y] == null) || (!gridObj[x + 1][y].blockAttributeArray[4]))) {
                        AI_X++;
                    }
                }
                enemy.setRightMax(AI_X);
                //gridObj[x][y]=null;
            }


        }
    }*/

    private void moveBlocks() {
        if ((posY < 0) || (posX < 0) || (posY > numRows*blockSize) || (posX > numCols*blockSize))
        {
            softResetIsTrue = true;
            softReset();
        }
        for (int i = 0; i < enemyObj.size(); i++)
        {
            enemyObj.get(i).Move();
            int enemyPosX = enemyObj.get(i).getPosX();
            int enemyPosY = enemyObj.get(i).getPosY();
            enemyObj.get(i).setEnemyHitBox(enemyPosX, enemyPosY, blockSize, blockSize);
            if (happyObj.hitBox.intersects(enemyObj.get(i).hitBox)) {
                hit = true;
                //softResetIsTrue = true;
            }
        }
        for (int i = 0; i < friendObj.size(); i++) {
            friendObj.get(i).Move();
            int friendPosX = friendObj.get(i).getPosX();
            int friendPosY = friendObj.get(i).getPosY();
            friendObj.get(i).setFriendHitBox(friendPosX, friendPosY, blockSize, blockSize);
            if (happyObj.hitBox.intersects(friendObj.get(i).hitBox)) {
                friendObj.get(i).friendSaved();
            }
        }
    }


    /*public void setGrid() {
        for (int i = 0; i < frameWidth / blockSize; i++) {
            changeColor(Color.magenta);
            drawLine(i * blockSize, 0, i * blockSize, frameHeight);
            drawLine(0, i * blockSize, frameWidth, i * blockSize);
        }
    }*/
    public void softReset() {
        life--;
        happyObj.setPlayerLife(life);
        if (life < 0) {
            gameOver = true;
            gameStates = "GameOver";
        } else {
            //Soft Reset only resets the character position and b
            death = false;
            softResetIsTrue = false;
            canJump = true;
            hit = false;
            gameOver = false;
            audioObj.playAudioRevive(this, audioObj.revive);
            happyObj.setPosX(startPosX);
            happyObj.setPosY(startPosY);
            happyObj.setVelX(0);
            happyObj.setVelY(0);
            happyObj.setAccelX(500);
            happyObj.setAccelY(0);
            happyObj.setHitBoxXY(startPosX, startPosY);
        }
        //if myblocks need to be cleared use the method below
        //myblocks.clear();

        //System.out.println("HAG line 507 Softreset occurred");
        //System.out.println("HAG line 508 " + happyObj.getPosX() + " " + happyObj.getPosY() + " " + happyObj.getVelX() + " " + happyObj.getVelY() + " "+ happyObj.getAccelX() + " " + happyObj.getAccelY());
        //System.out.println("HAG line 509 " + happyObj.getHitBoxX() + " " + happyObj.getHitBoxY());
    }

    //------------------------------------------------------
    //Game loop component to draw the graphics
    //------------------------------------------------------
    Image bg = loadImage("images/Sprites/bg.jpg");

    @Override
    public void paintComponent() {
        clearBackground(frameWidth, frameHeight);

        drawImage(bg, 0, 0, frameWidth, frameHeight);

        if (!gameOver) {
            drawBlocks();
        }
        else
        {
            menuObj.RetryMenuPanel.setVisible(true);
            menuObj.RTbuttonPanel.setVisible(true);
        }
        drawHitBoxes();
    }
    public void gameReset()
    {
        gameOver = false;
        gameStates = "PlayGame";
        happyObj.setPlayerLife(3);
    }
    //------------------------------------------------------
    //Game loop component to draw the graphics
    //------------------------------------------------------
    //We need to draw each block based on the type
    public void drawBlocks()
    {
        int minDrawPosX, minDrawPosY, maxDrawPosX, maxDrawPosY, type;
        int drawX = happyObj.getPosX() - (frameWidth / 2);
        int drawY = happyObj.getPosY() - (frameHeight / 2); //CHECK
        posX = happyObj.getPosX();
        posY = happyObj.getPosY();
        minDrawPosX = -blockSize;
        minDrawPosY = -blockSize;
        maxDrawPosX = frameWidth;
        maxDrawPosY = frameHeight; //(numRows * blockSize) - frameHeight
//System.out.println("posX: " + posX + " posY: " + posY + "drawX: " + drawX + " drawY: " + drawY);
//System.out.println(" minDrawPosX: " + minDrawPosX + " minDrawPosY: " + minDrawPosY +" maxDrawPosX: " + maxDrawPosX +" maxDrawPosY: " + maxDrawPosY);
        if (drawX < 0)
        {
            drawX = 0;
        }
        else if (drawX > (numCols * blockSize) - frameWidth)
        {
            drawX = (numCols * blockSize) - frameWidth;
        }
        if (drawY > (numRows * blockSize) - frameHeight) //CHECK
        {
            drawY = (numRows * blockSize) - frameHeight;
        }
        else if (drawY < 0)
        {
            drawY = 0;
        }

        for (int row = 0; row < numRows; row++)
        {
            for (int col = 0; col < numCols; col++)
            {
                int index = row * numCols + col;
                int x = (col * blockSize) - drawX;
                int y = (row * blockSize) - drawY;
                type = gridObj.get(index).getBlockType();

                //System.out.println("index: " + index + " row" + row + " col: " + col + " numCols: " + numCols + " numRows: " + numRows + " arraysize: " + gridObj.size() + " type: " + gridObj.get(index).getBlockType() );
                //if (type != -1 && type != 31 && type != 24 && type != 25 && type != 26 && type != 27 && type != 28 && type != 29)
                if (type != -1 && type != 31 && !(type >= 24 && type <= 29)) //Not air, happy, enemies, or friends
                {
                    if(x >= minDrawPosX && x <= maxDrawPosX && y >= minDrawPosY && y <= maxDrawPosY)
                    {
                        drawImage(blockIMG[type], x, y, blockSize, blockSize);
                        //System.out.println("index: " + index + " posX: " + gridObj.get(index).getPosX() + " Y: " + gridObj.get(index).getPosY() + " type: " + gridObj.get(index).getBlockType()+1);
                        //System.out.println("x: " + x + " y: " + y + " minDrawPosX: " + minDrawPosX + " minDrawPosY: " + minDrawPosY +" maxDrawPosX: " + maxDrawPosX +" maxDrawPosY: " + maxDrawPosY);
                    }
                }
            }
        }
        drawFriends(drawX, drawY);
        drawPlayer(drawX, drawY);
        drawEnemies(drawX, drawY);
    }


    //------------------------------------------------------
    //Game loop component to draw the playable character
    //------------------------------------------------------
    public void drawPlayer(int drawX, int drawY) {
        int happyImage, tempX, tempY, currentX, currentY;
        happyImage = happyIdle;
        happyBoxX = happyObj.getHitBoxX();
        happyBoxY = happyObj.getHitBoxY();
        size = happyObj.getSize();
        life = happyObj.getPlayerLife();
        Image[][] imageArray = happyObj.getImageArray();

        changeColor(Color.white);
        drawBoldText(50, 50, "life: " + life, "arial", 25);

        if (idle && !jump && !hit) {
            happyImage = happyIdle;
        } else if (jump && idle && !hit) {
            happyImage = happyJump;
        } else if ((jump && idle && hit) || (!jump && idle && hit)) {
            happyImage = happyHit;
        }

        //System.out.println("Start pos: " + startPosX + " " + startPosY + " pos: " + posX + " " + posY);
        //System.out.println("numRows: " + numRows);

        tempY = happyObj.getPosY() - drawY;
        currentX = frameWidth/2;
        currentY = tempY;

        if ((happyObj.getPosX() < frameWidth / 2 || happyObj.getPosX() > ((numCols - 15) * blockSize))
                && (happyObj.getPosY() < frameHeight / 2 || happyObj.getPosY() > ((numRows - 10) * blockSize)))
        {
            //NEED TO DO ONLY CORNERs
            if (happyObj.getPosX() < frameWidth / 2 && happyObj.getPosY() < frameHeight / 2) { //top-left corner
                //System.out.println("TOP-LEFT");
                drawImage(imageArray[happyImage][happyIndex], happyObj.getPosX(), happyObj.getPosY(), blockSize, blockSize);
                currentX = happyObj.getPosX();
                currentY = happyObj.getPosY();
            }
            else if (happyObj.getPosX() > ((numCols - 15) * blockSize))  //bottom-right OR top-right corner
            {
                //System.out.println("BOTTOM RIGHT or TOP RIGHT");
                tempX = happyObj.getPosX() - drawX;
                drawImage(imageArray[happyImage][happyIndex], tempX, tempY, blockSize, blockSize);
                currentX = tempX;
            }
            else if (happyObj.getPosY() > ((numRows-10)*blockSize)) //Bottom-left corner
            {
                //System.out.println("BOTTOM LEFT");
                drawImage(imageArray[happyImage][happyIndex], happyObj.getPosX(), tempY, blockSize, blockSize);
                currentX = happyObj.getPosX();
            }

        } else if (happyObj.getPosX() >= frameWidth / 2 && happyObj.getPosX() <= ((numCols - 15) * blockSize)) {
            if (happyObj.getPosY() <= ((numRows - 10) * blockSize) && !(happyObj.getPosY() <= frameHeight/2)) { //Middle
                //System.out.println("MIDDLE");
                drawImage(imageArray[happyImage][happyIndex], frameWidth / 2, frameHeight / 2, blockSize, blockSize);
                currentY = frameHeight/2;
            } else if (happyObj.getPosY() <= frameHeight/2) { //top
                //System.out.println("TOP");
                drawImage(imageArray[happyImage][happyIndex], frameWidth / 2, tempY, blockSize, blockSize);
            } else { //Bottom
                //System.out.println("BOTTOM");
                drawImage(imageArray[happyImage][happyIndex], frameWidth / 2, tempY, blockSize, blockSize);
            }
        } else if (happyObj.getPosY() >= frameHeight / 2 && happyObj.getPosY() <= ((numRows - 10) * blockSize)) {
            //System.out.println("LEFT AND RIGHT");
            tempX = happyObj.getPosX() - drawX;
            drawImage(imageArray[happyImage][happyIndex], tempX, frameHeight / 2, blockSize, blockSize);
            currentX = tempX;
            currentY = frameHeight/2;
        }

        if ((hit) && (life >= 0)) {
            life--;
        }

        changeColor(Color.white);
        drawBoldText(50, 50, "life: " + life, "arial", 25);
    }

    public void drawEnemies(int drawX, int drawY) {
        int enemyImage;
        for (int i = 0; i < enemyObj.size(); i++) {
            Image[][] enemyImageArray = enemyObj.get(i).getImageArray();
            enemyImage = i;
            int enemyPosX = enemyObj.get(i).getPosX() - drawX;
            int enemyPosY = enemyObj.get(i).getPosY() - drawY;
            drawImage(enemyImageArray[enemyImage][happyIndex], enemyPosX, enemyPosY, blockSize, blockSize);
System.out.println("i: " + i + " happyIndex: " + happyIndex + " enemyObjcreated: " + enemyObj.size());
        }
    }

    public void drawFriends(int drawX, int drawY) {
        int friendImage;
        for (int i = 0; i < friendObj.size(); i++) {
            //friend1 = 0, friend1Jump = 1, friend2 = 2, friend2Jump = 3, friend3 = 4, friend3Jump = 5, friend4 = 6, friend4Jump = 7;
            Image[][] friendImageArray = friendObj.get(i).getImageArray();
            if (jump) {
                friendImage = i*2+1;
            } else {
                friendImage = i*2;
            }
            int friendPosX = friendObj.get(i).getPosX() - drawX;
            int friendPosY = friendObj.get(i).getPosY() - drawY;
            drawImage(friendImageArray[friendImage][happyIndex], friendPosX, friendPosY, blockSize, blockSize);
        }
    }

    //------------------------------------------------------
    //Creating the sprite array from the spritesheet
    //------------------------------------------------------
    public void loadBlockImages() {
        try {
            //Placeholder for the image sprites to be loaded into an array
        } catch (Exception e) {
            System.out.println("HAG Line 600 Issues with loading images for Blocks. Error location: BlockClass.loadBlockImages");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent event) {
        super.keyPressed(event);
        if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            leftKey = true; /*System.out.println("Hag 611 leftkey: " + leftKey);*/
        }
        if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightKey = true; /*System.out.println("Hag 612 rightKey: " + rightKey);*/
        }
        if (event.getKeyCode() == KeyEvent.VK_DOWN) {
            downKey = true; /*System.out.println("Hag 613 downKey: " + downKey);*/
        }
        if (event.getKeyCode() == KeyEvent.VK_UP) {
            upKey = true; /*System.out.println("Hag 614 upKey: " + upKey);*/
        }
        if (event.getKeyCode() == KeyEvent.VK_SPACE) {
            jumpKey = true;
            jump = true;
            System.out.println("HAG Line 619 jumpKey: " + jumpKey + " " + isOnGround);

            if (jump) {
                if (canJump) {
                    audioObj.playAudioJump(this, audioObj.jumpFX);
                    isJumping = true;
                    canJump = false;
                    isOnGround = false;
                    accelY = 350;
                    velY = 200;
                    velY += accelY * setDt;
                    velY *= -1;
                    happyObj.setAccelY(accelY);
                    happyObj.setVelY(velY);
                    System.out.println("HAG line 635 " + happyObj.getVelY() + " " + happyObj.getAccelY());
                }

            }
        }
        if (event.getKeyCode() == KeyEvent.VK_H) {if (showHitboxes) showHitboxes = false; else showHitboxes = true;}
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            if(gameStates == "PlayGame")
            {
                if(menuObj.PauseMenuPanel.isVisible())
                {
                    menuObj.PauseMenuPanel.setVisible(false); menuObj.PAbuttonPanel.setVisible(false);
                }
                else
                {
                    menuObj.PauseMenuPanel.setVisible(true); menuObj.PAbuttonPanel.setVisible(true);
                }
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        super.keyReleased(event);
        if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            leftKey = false;/* System.out.println("leftkey: " + leftKey);*/
        }
        if (event.getKeyCode() == KeyEvent.VK_UP) {
            upKey = false;/* System.out.println("upKey: " + upKey);*/
        }
        if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightKey = false;/* System.out.println("rightKey: " + rightKey);*/
        }
        if (event.getKeyCode() == KeyEvent.VK_DOWN) {
            downKey = false;/* System.out.println("downKey: " + downKey);*/
        }
        if (event.getKeyCode() == KeyEvent.VK_SPACE) {
            jumpKey = jump = false;// System.out.println("HAG Line 652 jumpKey: " + jumpKey);
        }
    }


    public boolean collisionCheck()
    {
//System.out.println("collisioncheck called");

        int posX = happyObj.getPosX();
        int posY = happyObj.getPosY();
        int HBposX = happyObj.getHitBoxX();
        int HBposY = happyObj.getHitBoxY();
        double HBminX = happyObj.hitBox.getMinX();
        double HBminY = happyObj.hitBox.getMinY();
        double HBmaxX = happyObj.hitBox.getMaxX();
        double HBmaxY = happyObj.hitBox.getMaxY();
        int blockposX, blockposY;
        double blockminX, blockmaxX, blockMinY, blockMaxY;
        int collisionHappyPosX;
        int collisionHappyPosY;
        int collisionBoxPosX;
        int collisionBoxPosY;
        double velX = happyObj.getVelX();
        double velY = happyObj.getVelY();
        double collisionVelX;
        double collisionVelY;
        int hitBoxX = happyObj.getHitBoxX();
        int hitBoxY = happyObj.getHitBoxY();

//System.out.println("velX: " + velX + " velY: " + velY + "  posX: " + posX + " posY: " + posY);
//System.out.println("HBposX: " + HBposX + " HBposY: " + HBposY + " HBminX: " + HBminX + " HBminY: " + HBminY + " HBmaxX: " + HBmaxX + " HBmaxY: " + HBmaxY);

            //Collision with blocks that have a hitBox
        for (BlockClass block : myblocks)
        {
            //System.out.println("for block initiated");
            int type = block.getType();
            blockposX = block.getPosX(); blockposY = block.getPosY();
            blockminX = block.hitBox.getMinX(); blockmaxX = block.hitBox.getMaxX();
            blockMinY = block.hitBox.getMinY(); blockMaxY = block.hitBox.getMaxY();
            //System.out.println("blockposX: " + blockposX + " blockposY: " + blockposY);
            //System.out.println("blockminX: " + blockminX + " blockMinY: " + blockMinY + " blockmaxX: " + blockmaxX + " blockMaxY: " + blockMaxY);

            if ((type == 0) || (type == 1) || (type == 2)) {
            //System.out.println("blocktype called");

                if (happyObj.hitBox.intersects(block.hitBox)) {
                    //System.out.println("668 HAG Collision true, BlockType: " + block.getType() );
                    collided = true;

                    //Estimate position of actual collision
                    collisionHappyPosX = happyObj.getPosX();
                    collisionHappyPosY = happyObj.getPosY();
                    collisionBoxPosX = block.hitBox.x;
                    collisionBoxPosY = block.hitBox.y;
                    collisionVelX = happyObj.getVelX();
                    collisionVelY = happyObj.getVelY();

                    if (velY > 0 && happyObj.hitBox.getMaxY() >= block.hitBox.getMinY())
                    {
                        //Happy is falling down to the ground, his feet is going through the nearest block below
                        //System.out.println("691 HAG Condition 1 is true");
                        //System.out.println("velX: " + velX + " velY: " + velY + "  posX: " + posX + " posY: " + posY);
                        //System.out.println("collisionVelX: " + collisionVelX + " collisionVelY: " + collisionVelY + " ollisioinHappyPosX: " + collisionHappyPosX + " collisionHappyPosY: " + collisionHappyPosY);
                        //System.out.println("collisionboxX: " + collisionBoxPosX + " collisionboxY: " + collisionBoxPosY);
                        posY = (int) (block.hitBox.getMinY() - happyObj.hitBox.height);
                        velY = 0;
                        isJumping = false;
                        collided = false;
                        isOnGround = checkIsOnGround(posX, posY);
                        //System.out.println("isOnGround: " + isOnGround);

                        if (!isOnGround) { canJump = false; }
                        else { canJump = true; }
                    }
                    else if (velY < 0 && happyObj.hitBox.getMinY() <= block.hitBox.getMaxY())
                    {   //Happy is going upwards, his head is going through the block above
                        //System.out.println(" 701 HAG Condition 2 is true");
                        // System.out.println("velX: " + velX + " velY: " + velY + "  posX: " + posX + " posY: " + posY);
                        //System.out.println("collisionVelX: " + collisionVelX + " collisionVelY: " + collisionVelY + " ollisioinHappyPosX: " + collisionHappyPosX + " collisionHappyPosY: " + collisionHappyPosY);
                        //System.out.println("collisionboxX: " + collisionBoxPosX + " collisionboxY: " + collisionBoxPosY);
                        posY = (int) (block.hitBox.getMaxY() + 1);
                        velY *= -1;
                        isJumping = false;
                        collided = false;
                        isOnGround = checkIsOnGround(posX, posY);


                        if (!isOnGround) {
                            canJump = false;
                        } else {
                            canJump = true;
                        }
                    } else if (velX > 0 && happyObj.hitBox.getMaxX() >= block.hitBox.getMinX()) {   //Happy is moving to the right, his right side is going through the block on the right
                        //System.out.println("700 HAG Condition 3 is true");
                        //System.out.println("velX: " + velX + " velY: " + velY + "  posX: " + posX + " posY: " + posY);
                        //System.out.println("collisionVelX: " + collisionVelX + " collisionVelY: " + collisionVelY + " ollisioinHappyPosX: " + collisionHappyPosX + " collisionHappyPosY: " + collisionHappyPosY);
                        //System.out.println("collisionboxX: " + collisionBoxPosX + " collisionboxY: " + collisionBoxPosY);
                        velX = 0;
                        posX = (int) block.hitBox.getMinX() - 1 - blockSize;
                        collided = false;
                        isOnGround = checkIsOnGround(posX, posY);
                        if (!isOnGround) { canJump = false; }
                    }
                    else if (velX < 0 && happyObj.hitBox.getMinX() <= block.hitBox.getMaxX())
                    {
                    //Happy is moving to the left, his left side is going through the block on the left
                    //System.out.println("707 HAG Condition 4 is true");
                    //System.out.println("velX: " + velX + " velY: " + velY + "  posX: " + posX + " posY: " + posY);
                    //System.out.println("collisionVelX: " + collisionVelX + " collisionVelY: " + collisionVelY + " ollisioinHappyPosX: " + collisionHappyPosX + " collisionHappyPosY: " + collisionHappyPosY);
                    //System.out.println("collisionboxX: " + collisionBoxPosX + " collisionboxY: " + collisionBoxPosY);
                        velX = 0;
                        posX = (int) block.hitBox.getMaxX() + 1;
                        collided = false;
                        isOnGround = checkIsOnGround(posX, posY);

                        if (!isOnGround) { canJump = false; }
                        else { canJump = true; }
                    //System.out.println("isOnGround: " + isOnGround);
                    }
                    else if ((velX == 0) && (velY == 0))
                    {
                        System.out.println("714 HAG Condition 5 is true. Collision occurred with VelX = 0 and VelY = 0... hmmmmm.....");
                    //System.out.println("velX: " + velX + " velY: " + velY + "  posX: " + posX + " posY: " + posY);
                    //System.out.println("collisionVelX: " + collisionVelX + " collisionVelY: " + collisionVelY + " ollisioinHappyPosX: " + collisionHappyPosX + " collisionHappyPosY: " + collisionHappyPosY);
                    //System.out.println("collisionboxX: " + collisionBoxPosX + " collisionboxY: " + collisionBoxPosY);
                    //Adding some random VelX and VelY to force another collision -- really dumb hack
                        velX = -100;
                        velY = 100;
                    }
                }
                happyObj.setVelY(velY);
                happyObj.setVelX(velX);
                happyObj.setPosY(posY);
                happyObj.setPosX(posX);
                happyObj.setHitBoxXY(posX, posY);
            }
            //Collision for blocks that don't have hitboxes
            if ((type == 16) || (type == 17) || (type == 18)) {   //These are candies
                if ((((happyObj.hitBox.getMaxX() - 5 > block.getPosX() && happyObj.hitBox.getMaxX() - 5 < block.getPosX() + blockSize)) && (happyObj.hitBox.getMaxY() - 5 > block.getPosY() && happyObj.hitBox.getMaxY() - 5 < block.getPosY() + blockSize))
                        || ((((happyObj.hitBox.getMinX() + 5 > block.getPosX() && happyObj.hitBox.getMinX() + 5 < block.getPosX() + blockSize)) && (happyObj.hitBox.getMinY() + 5 > block.getPosY() && happyObj.hitBox.getMinY() + 5 < block.getPosY() + blockSize)))) {
                    audioObj.playAudioEatCandy(this, audioObj.eatCandy);
                    //candy score
                    happyObj.setPlayerScore((int) 4*((type)- 16)^2 + 1);
                    gridObj.get(block.getCellIndex()).setBlockType(-1);     //set the current block in the grid to -1, so that it can no longer be drawn on the screen
                    gridObj.get(block.getCellIndex()).setActiveInd(false);  //this block in the grid is no longer active
                    myblocks.remove(block);

                    break;
                }
            } else if (type == 8) {   //These are spikes
                if ((((happyObj.hitBox.getMaxX() - 5 > block.getPosX() && happyObj.hitBox.getMaxX() - 5 < block.getPosX() + blockSize)) && (happyObj.hitBox.getMaxY() - 5 > block.getPosY() && happyObj.hitBox.getMaxY() - 5 < block.getPosY() + blockSize))
                        || ((((happyObj.hitBox.getMinX() + 5 > block.getPosX() && happyObj.hitBox.getMinX() + 5 < block.getPosX() + blockSize)) && (happyObj.hitBox.getMinY() + 5 > block.getPosY() && happyObj.hitBox.getMinY() + 5 < block.getPosY() + blockSize)))) {
                    //hitDelay();
                }
            }   //These are ladders
            else if (type == 9) {
                if ((((happyObj.hitBox.getMaxX() - 10 > block.getPosX() && happyObj.hitBox.getMaxX() - 10 < block.getPosX() + blockSize)) && (happyObj.hitBox.getMaxY() - 5 > block.getPosX() && happyObj.hitBox.getMaxY() - 5 < block.getPosY() + blockSize))
                        || ((((happyObj.hitBox.getMinX() + 10 > block.getPosX() && happyObj.hitBox.getMinX() + 10 < block.getPosX() + blockSize)) && (happyObj.hitBox.getMinY() + 5 > block.getPosY() && happyObj.hitBox.getMinY() + 5 < block.getPosY() + blockSize)))) {
                    if (upKey) {
                        posX = block.getPosX();
                        posY = block.getPosY();
                        isClimbing = true;
                    }

                }

            }

            happyObj.setPosY(posY);
            happyObj.setPosX(posX);
            happyObj.setHitBoxXY(posX, posY);
        }
        return false;
    }

    public boolean checkIsOnGround(int x, int y) {//System.out.println("checkisonground called");
        for (BlockClass block : myblocks) {
            if ((x >= block.hitBox.getMinX()) && (x <= block.hitBox.getMaxX()) && (y + 26 >= block.hitBox.getMinY())) {
                return true;
            }
        }
        return false;
    }
    public void drawHitBoxes()
    {System.out.println("drawhitboxes called");
        if (showHitboxes)
        {
            //Draw Happy
            changeColor(Color.green);
            drawLine(happyObj.hitBox.getMinX(), happyObj.hitBox.getMinY(), happyObj.hitBox.getMaxX(), happyObj.hitBox.getMinY());
            drawLine(happyObj.hitBox.getMinX(), happyObj.hitBox.getMinY(), happyObj.hitBox.getMinX(), happyObj.hitBox.getMaxY());
            drawLine(happyObj.hitBox.getMaxX(), happyObj.hitBox.getMinY(), happyObj.hitBox.getMaxX(), happyObj.hitBox.getMaxY());
            drawLine(happyObj.hitBox.getMinX(), happyObj.hitBox.getMaxY(), happyObj.hitBox.getMaxX(), happyObj.hitBox.getMaxY());

            //Draw all other world objects
            for(BlockClass block : myblocks)
            {
                drawLine(block.hitBox.getMinX(), block.hitBox.getMinY(), block.hitBox.getMaxX(), block.hitBox.getMinY());
                drawLine(block.hitBox.getMinX(), block.hitBox.getMinY(), block.hitBox.getMinX(), block.hitBox.getMaxY());
                drawLine(block.hitBox.getMaxX(), block.hitBox.getMinY(), block.hitBox.getMaxX(), block.hitBox.getMaxY());
                drawLine(block.hitBox.getMinX(), block.hitBox.getMaxY(), block.hitBox.getMaxX(), block.hitBox.getMaxY());
            }
        }
    }
    public void initEnemies()  
    {

//        for(BlockClass block: myblocks)
//        {
//            if (block instanceof EnemyClass enemy)
//            {
//
//                int i = -1;
//                int index = enemy.getGridLoc();
//                boolean left = true;
//                boolean right = true;
//                while(left)
//                {
//                    if ((gridObj.get(index-i).getBlockType()==-1) &&((gridObj.get(index+numRows-i).getBlockType()==0) ||(gridObj.get(index+numRows-i).getBlockType()==1) ||(gridObj.get(index+numRows-i).getBlockType()==2))) {
//                        i--;
//                    }
//                    else { enemy.setMaxLeft(i, blockSize); left = false; }
//                }
//                i = 1;
//                while(right)
//                {
//                    if ((gridObj.get(index+i).getBlockType()==-1) &&((gridObj.get(index+numRows+i).getBlockType()==0) ||(gridObj.get(index+numRows+i).getBlockType()==1) ||(gridObj.get(index+numRows+i).getBlockType()==2))) {
//                        i++;
//                    }
//                    else { enemy.setMaxRight(i, blockSize); right = false; }
//                }
//
//            }
//        }
    }
}






