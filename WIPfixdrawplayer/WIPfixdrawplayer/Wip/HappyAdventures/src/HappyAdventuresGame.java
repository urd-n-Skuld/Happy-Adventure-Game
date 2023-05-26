import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HappyAdventuresGame extends GameEngine implements ActionListener {
    //------------------------------------------------------
    //Global generic variables
    //------------------------------------------------------
    int numCols, numRows, superSweetsEaten, keysFound;       //These values are initialised when the world map is loaded (See loadBlocks())
    static int blockSize = 25, blockVelX = 50, blockVelY = 50;
    public boolean showHitboxes, showGrid = false;
    Timer hitTimer = new Timer();
    String gameStates; // "MenuSystem", "PlayGame", "2Player", "Paused"
    String message;

    String csvFile = "images/WorldMaps/Worldmapfinal.csv";
    String secretCSV = "images/WorldMaps/secretBlocks.csv";
    //String csvFile = "images/WorldMaps/Horisontal world.csv";

    // putting this here allows easier changes
    static boolean death, gameOver, gamePause, firstSuperSweetEaten, firstKeyFound;
    public boolean softResetIsTrue;

    //volpy HUD variables
    int[] HUDtot = {0, 0, 0, 0, 0};
    int[] KEYtot = {0, 0, 0, 0, 0};
    int score = 0;
    //volpy HUD IMAGES
    Image hudBG;
    Image[] HUDimg = {
            loadImage("images/Sprites/emptyKey.png"),
            loadImage("images/Sprites/redKey.png"),
            loadImage("images/Sprites/blueKey.png"),
            loadImage("images/Sprites/yellowKey.png"),
            loadImage("images/Sprites/greenKey.png"),
            loadImage("images/Sprites/heart.png"),
            loadImage("images/Sprites/CANDY1_25PX.png"),
            loadImage("images/Sprites/CANDY2_25PX.png"),
            loadImage("images/Sprites/CANDY3_25PX.png"),
            loadImage("images/Sprites/supersweet.png")};

//volpy Background Variables

    int cloudX1=100, cloudX2=25, cloudX3=220;
    // int cloudVelX=32;
    int cloud1VelX = 35;
    int cloud2VelX = 35;
    int cloud3VelX = 35;

    public static Image[] blockIMG =
            {
                    loadImage("images/Sprites/border.png"),//0
                    loadImage("images/Sprites/soil.png"),//1
                    loadImage("images/Sprites/grass.png"),//2
                    loadImage("images/Sprites/spikesUp25px.png"),//3
                    loadImage("images/Sprites/fire.png"),//4 to create the fire sprite
                    loadImage("images/Sprites/vine.png"),//5
                    loadImage("images/Sprites/vine.png"),//6 need safe zone
                    loadImage("images/Sprites/safezonesign.png"),//7 need checkpoint sprites
                    loadImage("images/Sprites/checkpointInactive.png"),//8 need checkpoint
                    loadImage("images/Sprites/door_red25x.png"),//9
                    loadImage("images/Sprites/door_blue25x.png"),//10
                    loadImage("images/Sprites/door_yellow25x.png"),//11
                    loadImage("images/Sprites/doorNoKey.png"),//12 need door extender
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
                    loadImage("images/Sprites/vine.png"),//34
                    loadImage("images/Sprites/vine.png"),//35
                    loadImage("images/Sprites/vine.png"),//36
                    loadImage("images/Sprites/supersweet.png"),//37
                    loadImage("images/Sprites/disappearingBlock.png"),//38    might need a sprite for this one
                    loadImage("images/Sprites/spikeBottom.png"),//39
                    loadImage("images/Sprites/spikeLeft.png"),//40
                    loadImage("images/Sprites/spikeRight.png"),//41
                    loadImage("images/Sprites/block_float25x75.png"),//42
                    loadImage("images/Sprites/enemy4.png"),//43
                    loadImage("images/Sprites/enemy5.png"),//44
                    loadImage("images/Sprites/enemy6.png"),//45
                    loadImage("images/Sprites/enemy7.png"),//46
                    loadImage("images/Sprites/enemy8.png"),//47
                    loadImage("images/Sprites/blockBGPurple25px.png"),//48
                    loadImage("images/Sprites/exitcheckpoint.png"),//49
                    loadImage("images/Sprites/secretblock1.png"),//50
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
    public void init() {
        System.out.println("Init called");
        //volpy hud Images initialisation
        hudBG = loadImage("images/Sprites/HUD_bg.png");
        setWindowSize(frameWidth, frameHeight);

        gameStates = "MenuSystem";    //Change this to "MenuSystem" if you want to turn on game menus
        showHitboxes = death = gameOver = false;
        firstSuperSweetEaten = false;
        firstKeyFound = false;
        superSweetsEaten = 0;
        keysFound = 0;
        gamePause = true;

        initAudio();// line 109
        initWorld(csvFile, secretCSV);// line 176 .... creates variables for grid class
        initCharacters();// line 145
        initGUI();// line 98

        super.mFrame.setSize(frameWidth, frameHeight);
        super.mFrame.setVisible(true);
    }

    //------------------------------------------------------
    //GUI objects, methods and initialisation
    //------------------------------------------------------
    GUIClass menuObj = new GUIClass();

    public void initGUI() {
        System.out.println("InitGui called");
        //System.out.println("HAG Line 96 " + mFrame.isVisible());
        menuObj.setupGUI(this, super.mFrame, super.mPanel, gameStates);
    }

    //------------------------------------------------------
    //Audio objects, methods and initialisation
    //------------------------------------------------------
    AudioClass audioObj = new AudioClass();

    public void initAudio() {
        System.out.println("InitAudio called");
        audioObj.setupAudio(this);
    }

    public void drawHUD() {
        //HUD BACKGROUND
        drawImage(hudBG, 0, 0, 750, 28);
        changeColor(Color.white);
        int[] HUDItemIX = {28, 128, 179, 230, 280};
        int[] HUDTextTX = {53, 150, 195, 250, 305};
        HUDtot[0] = life;
        for (int i = 0; i < 5; i++) {
            drawImage(HUDimg[i + 5], HUDItemIX[i], 1, 25, 25);
            changeColor(Color.white);
            drawBoldText(HUDTextTX[i], 20, "x" + HUDtot[i], "arial", 15);
        }
        //EMPTY KEYS REPLACE KEYS WITH COLOUR
        int[] HUDKeyIX = {372, 408, 444};
        for (int i = 0; i < 3; i++) {// 3 kinds of keys atm
            int k = 0;
            if (keys[i]) {
                k = i + 1;
            }
            drawImage(HUDimg[k], HUDKeyIX[i], 0, 25, 25);
            changeColor(Color.white);
            drawBoldText(HUDKeyIX[i], 25, "x" + KEYtot[i], "arial", 11);
        }
        //TOTAL SCORE
        changeColor(Color.white);
        drawBoldText(629, 20, "Total Score: " + happyObj.getPlayerScore(), "arial", 15);
    }

    //------------------------------------------------------
    //Game World objects initialisation
    //------------------------------------------------------
    ArrayList<BlockClass> myBlocks;
    ArrayList<GridClass> gridObj;
    ArrayList<GridClass> secretAreas;

    public void initWorld(String mapCSV, String secretCSV) {
        //System.out.println("InitWorld called");
        loadCSV map1 = new loadCSV();

        Object[] resultingLists = map1.loadMap(mapCSV, blockSize);
        myBlocks = (ArrayList<BlockClass>) resultingLists[0];
        gridObj = (ArrayList<GridClass>) resultingLists[1];

        Object[] secretBlocks = map1.loadSecretAreas(secretCSV, blockSize);
        secretAreas = (ArrayList<GridClass>) secretBlocks[0];

        numCols = loadCSV.getCol();
        numRows = loadCSV.getRows();
        //System.out.println("numRows: " + numRows + " numCols: " + numCols);

        for (BlockClass block : myBlocks) {
            //System.out.println("LoadCSV line 61 " +block.getPosX()+ " = x " + block.getPosY() + " = y and "+ block.getType() + " = type");
            int x = block.getPosX();
            int y = block.getPosY();
            int type = block.getType();
            block.setblockHitBox(x, y, blockSize, blockSize);
            if ((type >= 13) && (type <= 15)) {
                KEYtot[4]++;
            }
            else if ((type == 4) || (type == 19))
            {
                block.initBlockAnimSprites(this);
            }
            else if (type == 33)
            {
                block.setblockHitBox(block.getStartX(), block.getStartY(), 75, 25);
            }
            else if (type == 42)
            {
                block.setblockHitBox(block.getStartX(), block.getStartY(), 50, 25);
            }
        }

        /*for (int i = 0; i < gridObj.size() - 1; i++) {
            gridObj.get(i).getBlockType();
            //System.out.println("HAG 144 type: " + gridObj.get(i).getBlockType());
        }*/

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
    boolean[] keys = new boolean[4];

    public void initCharacters() {
        //System.out.println("InitCharacters called");
        for (int i = 0; i < gridObj.size() - 1; i++) {
            //Happy
            if (gridObj.get(i).getBlockType() == 31) {
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
            } else if ((gridObj.get(i).getBlockType() >= 24 && gridObj.get(i).getBlockType() <= 26) || (gridObj.get(i).getBlockType() >= 43 && gridObj.get(i).getBlockType() <= 47)) //Enemies
            {
                int enemyPosX = gridObj.get(i).getPosX(), enemyPosY = gridObj.get(i).getPosY();
                EnemyClass tempEnemy = new EnemyClass(enemyPosX, enemyPosY, gridObj.get(i).getBlockType(), i);
                int leftStop = gridObj.get(i).getPosX(), rightStop = gridObj.get(i).getPosX(); //Initialise

                for (int indx = i + 1;
                     (gridObj.get(indx).getBlockType() == -1 || (gridObj.get(indx).getBlockType() >= 5 && gridObj.get(indx).getBlockType() <= 8) || (gridObj.get(indx).getBlockType() >= 16 && gridObj.get(indx).getBlockType() <= 19) || (gridObj.get(indx).getBlockType() >= 21 && gridObj.get(indx).getBlockType() <= 26) || (gridObj.get(indx).getBlockType() >= 34 && gridObj.get(indx).getBlockType() <= 37))
                             || (gridObj.get(indx + numCols).getBlockType() != -1 || !(gridObj.get(indx + numCols).getBlockType() >= 5 && gridObj.get(indx + numCols).getBlockType() <= 8) || !(gridObj.get(indx + numCols).getBlockType() >= 16 && gridObj.get(indx + numCols).getBlockType() <= 19) || !(gridObj.get(indx + numCols).getBlockType() >= 21 && gridObj.get(indx + numCols).getBlockType() <= 26) || !(gridObj.get(indx + numCols).getBlockType() >= 34 && gridObj.get(indx + numCols).getBlockType() <= 37));
                     indx++) {
                    if ((gridObj.get(indx).getBlockType() != -1 && !(gridObj.get(indx).getBlockType() >= 5 && gridObj.get(indx).getBlockType() <= 8) && !(gridObj.get(indx).getBlockType() >= 16 && gridObj.get(indx).getBlockType() <= 19) && !(gridObj.get(indx).getBlockType() >= 21 && gridObj.get(indx).getBlockType() <= 26) && !(gridObj.get(indx).getBlockType() >= 34 && gridObj.get(indx).getBlockType() <= 37))
                            || (gridObj.get(indx + numCols).getBlockType() == -1 || (gridObj.get(indx + numCols).getBlockType() >= 5 && gridObj.get(indx + numCols).getBlockType() <= 8) || (gridObj.get(indx + numCols).getBlockType() >= 16 && gridObj.get(indx + numCols).getBlockType() <= 19) || (gridObj.get(indx + numCols).getBlockType() >= 21 && gridObj.get(indx + numCols).getBlockType() <= 26) || (gridObj.get(indx + numCols).getBlockType() >= 34 && gridObj.get(indx + numCols).getBlockType() <= 37))) {
                        //System.out.println("i " + i + " indx " + indx + " type " + gridObj.get(indx).getBlockType() + " type " + gridObj.get(indx + numCols).getBlockType());
                        rightStop = gridObj.get(indx - 1).getPosX();
                        break;
                    }
                }
                for (int indx = i - 1;
                     (gridObj.get(indx).getBlockType() == -1 || (gridObj.get(indx).getBlockType() >= 5 && gridObj.get(indx).getBlockType() <= 8) || (gridObj.get(indx).getBlockType() >= 16 && gridObj.get(indx).getBlockType() <= 19) || (gridObj.get(indx).getBlockType() >= 21 && gridObj.get(indx).getBlockType() <= 26) || (gridObj.get(indx).getBlockType() >= 34 && gridObj.get(indx).getBlockType() <= 37))
                             || (gridObj.get(indx + numCols).getBlockType() != -1 || !(gridObj.get(indx + numCols).getBlockType() >= 5 && gridObj.get(indx + numCols).getBlockType() <= 8) || !(gridObj.get(indx + numCols).getBlockType() >= 16 && gridObj.get(indx + numCols).getBlockType() <= 19) || !(gridObj.get(indx + numCols).getBlockType() >= 21 && gridObj.get(indx + numCols).getBlockType() <= 26) || !(gridObj.get(indx + numCols).getBlockType() >= 34 && gridObj.get(indx + numCols).getBlockType() <= 37));
                     indx--) {
                    if ((gridObj.get(indx).getBlockType() != -1 && !(gridObj.get(indx).getBlockType() >= 5 && gridObj.get(indx).getBlockType() <= 8) && !(gridObj.get(indx).getBlockType() >= 16 && gridObj.get(indx).getBlockType() <= 19) && !(gridObj.get(indx).getBlockType() >= 21 && gridObj.get(indx).getBlockType() <= 26) && !(gridObj.get(indx).getBlockType() >= 34 && gridObj.get(indx).getBlockType() <= 37))
                            || (gridObj.get(indx + numCols).getBlockType() == -1 || (gridObj.get(indx + numCols).getBlockType() >= 5 && gridObj.get(indx + numCols).getBlockType() <= 8) || (gridObj.get(indx + numCols).getBlockType() >= 16 && gridObj.get(indx + numCols).getBlockType() <= 19) || (gridObj.get(indx + numCols).getBlockType() >= 21 && gridObj.get(indx + numCols).getBlockType() <= 26) || (gridObj.get(indx + numCols).getBlockType() >= 34 && gridObj.get(indx + numCols).getBlockType() <= 37))) {
                        leftStop = gridObj.get(indx + 1).getPosX();
                        break;
                    }
                }
                //System.out.println("startPosX " + gridObj.get(i).getPosX() + " RightStop: " + rightStop + " LeftStop: " + leftStop);
                tempEnemy.setMaxLeft(leftStop, blockSize);
                tempEnemy.setMaxRight(rightStop, blockSize);
                tempEnemy.initEnemySprites(this);
                enemyObj.add(tempEnemy);
            } else if (gridObj.get(i).getBlockType() == 27 || gridObj.get(i).getBlockType() == 28 || gridObj.get(i).getBlockType() == 29) //Friends
            {
                for (BlockClass block: myBlocks){
                    if (block instanceof FriendClass friend)
                    {
                        if (i==friend.getGridIndex()){
                            friend.initFriendSprites();
                            friendObj.add(friend);
                        }
                    }
                }
            }
        }
        //Bappy Placeholder
        //if (gridObj.getBlockType(i) == 32) {}

        idle = true; isOnGround = true; canJump = true; canMoveLeft = true; canMoveRight = true;
        leftKey = false; rightKey = false; upKey = false; downKey = false; jumpKey = false; jump = false; hit = false;
        isJumping = false; isFalling = false; isClimbing = false; isFloating =  false;
    }

    //------------------------------------------------------
    //The game loop update function for updating the game attributes
    //------------------------------------------------------
    int happyIndex, happyIdle = 0, happyJump = 1, happyHit = 2;
    double setDt;
    @Override
    public void update(double dt) {//System.out.println("Update called");

        setDt = dt;
        if(gamePause && superSweetsEaten == 1 && firstSuperSweetEaten)
        {
            gamePause = true;
            menuObj.SuperSweetTutorialMenuPanel.setVisible(true);
            menuObj.SuperSweetTutorialbuttonPanel.setVisible(true);
        }
        if(gamePause && keysFound == 1 && firstKeyFound)
        {
            gamePause = true;
            menuObj.foundKeyTutorialMenuPanel.setVisible(true);
        }
        if ((!gameOver) && (!gamePause))
        {
            happyIndex = updateAnimationSpeed(15);
            moveClouds(dt);
            updateHappy(dt);
            updateFloatingBlock(dt);
            moveBlocks();
        }
    }

    //------------------------------------------------------
    //Updating movements and actions of Happy
    //------------------------------------------------------
    int posX, posY, happyBoxX, happyBoxY, size, life;
    static double velX, velY, accelX, accelY;
    static boolean isOnGround, isJumping, isFalling, isClimbing, collided, canJump, canMoveLeft, canMoveRight, isFloating;

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
        life = happyObj.getPlayerLife();

        if (life <= 0) {
            gameOver = true;
            gameStates = "GameOver";
        }

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
        collisionCheck(dt);

        //Vertical movement
        if (isClimbing) {
            accelY = 0;

            if (upKey && !downKey) {   //if Happy is climbing up
                velY = -100;
            } else if (downKey && !upKey) {   //if Happy is climbing down
                velY = 100;
            }
            posY += velY * dt;
        }
        if (!canJump && !isOnGround && isJumping) {   //if Happy is jumping
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
        }else if(isFloating & jump)
        {
            velY = 200;
            velY += accelY * dt;
            posY += velY * dt;
        }
        //Update Happy's current position, and hitbox current pos
        happyObj.setAccelY(accelY);
        happyObj.setVelY(velY);
        happyObj.setPosY(posY);
        happyObj.setHitBoxXY(posX, posY);

        //Collision
        collisionCheck(dt);

    }

    //------------------------------------------------------
    //Updating animation speed for Happy's sprites
    //------------------------------------------------------
    private int aniCounter;
    private int aniIndex;
    private final int aniSpeed = 4;

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

    private void moveBlocks() {

        if ((posY < 0) || (posX < 0) || (posY > numRows * blockSize) || (posX > numCols * blockSize)) {
            life = happyObj.getPlayerLife();
            life--;
            happyObj.setPlayerLife(life);
            softResetIsTrue = true;
            softReset();
        }
        for (EnemyClass enemyClass : enemyObj) {
            enemyClass.Move();
            int enemyPosX = enemyClass.getPosX();
            int enemyPosY = enemyClass.getPosY();
            enemyClass.setEnemyHitBox(enemyPosX, enemyPosY, blockSize, blockSize);

            //Collision with enemies
            if (happyObj.hitBox.intersects(enemyClass.hitBox))
            {
                if(HUDtot[4] > 0)
                {
                    FriendClass friend = new FriendClass(enemyClass);
                    friendObj.add(friend);
                    enemyObj.remove(enemyClass);
                    HUDtot[4]--;
                    break;
                }
                else
                {
                    hitDelay();
                }
            }
        }

        //--------------------------------------------
        //Removing the enemies that have been converted to friendlies
//        for (EnemyClass enemy : enemiesToRemove) {
//            FriendClass friend = new FriendClass(enemy.getPosX(), enemy.getPosY(), enemy.getType()+3, enemy.getGridLoc());
//            friend.initFriendSprites(this);
//            friend.setFriendFollow();
//            friendObj.add(friend);
//            enemyObj.remove(enemy); // Remove enemies from the enemyObj list
//            HUDtot[4]--;
//        }

        int friendFollowDistance = 40;
        for (int i = 0; i < friendObj.size(); i++) {
            double distance = distance(happyObj.getPosX(), happyObj.getPosY(), friendObj.get(i).getPosX(), friendObj.get(i).getPosY());
            if ((friendObj.get(i).getFollow()) && (distance > friendFollowDistance + (i*blockSize))) {
                friendObj.get(i).Move(happyObj.getPosX(), happyObj.getPosY(), distance + (i*blockSize));
            }
        }

    }

    public void softReset() {
        life = happyObj.getPlayerLife();

        if (life < 0) {
            gameOver = true;
            gameStates = "GameOver";
            friendSaver();
        } else {
            //Soft Reset only resets the character position and b
            death = false;
            softResetIsTrue = false;
            canJump = true;
            canMoveLeft = true;
            canMoveRight = true;
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
            friendSaver();
        }
        //if myBlocks need to be cleared use the method below
        //myBlocks.clear();

        //System.out.println("HAG line 507 Softreset occurred");
        //System.out.println("HAG line 508 " + happyObj.getPosX() + " " + happyObj.getPosY() + " " + happyObj.getVelX() + " " + happyObj.getVelY() + " "+ happyObj.getAccelX() + " " + happyObj.getAccelY());
        //System.out.println("HAG line 509 " + happyObj.getHitBoxX() + " " + happyObj.getHitBoxY());
    }

    //------------------------------------------------------
    //Game loop component to draw the graphics
    //------------------------------------------------------
    Image bg = loadImage("images/Sprites/bgA.png");
    Image bg2 = loadImage("images/Sprites/bg5.png");
    Image cloud1 = loadImage("images/Sprites/clouds1.png");
    Image cloud2 = loadImage("images/Sprites/clouds2.png");
    Image cloud3 = loadImage("images/Sprites/clouds3.png");
    Image sun = loadImage("images/Sprites/sun.png");
    public void moveClouds(double dt) {
        cloudX1 += cloud1VelX * dt;
        cloudX2 += cloud2VelX * dt;
        cloudX3 += cloud3VelX * dt;
    }

    public void drawBackground() {

        drawImage(bg, 0, 0, frameWidth, frameHeight);
        drawImage(cloud1, cloudX1, 100, 150, 30);
        drawImage(cloud2, cloudX2, 30, 150, 30);
        drawImage(cloud3, cloudX3, 150, 150, 30);

        drawImage(bg2, 0, 0, frameWidth, frameHeight);
        if (cloudX1 >= frameWidth) {
            cloudX1 = 1;
        }
        if (cloudX2 >= frameWidth) {
            cloudX2 = -20;
        }
        if (cloudX3 >= frameWidth) {
            cloudX3 = -150;
        }
    }

    @Override
    public void paintComponent() {
        clearBackground(frameWidth, frameHeight);

        drawBackground();
        if (!gameOver) {
            drawBlocks();
        } else {
            menuObj.RetryMenuPanel.setVisible(true);
            menuObj.RTbuttonPanel.setVisible(true);
        }
        drawHitBoxes();
        drawHUD();
    }

    public void gameReset() {
        gameOver = false;
        hit = false;
        gameStates = "PlayGame";
        happyObj.setPlayerLife(3);
        death = false;
        softResetIsTrue = false;
        canJump = true;
        canMoveLeft = true;
        canMoveRight = true;
        happyObj.setPosX(startPosX);
        happyObj.setPosY(startPosY);
        happyObj.setVelX(0);
        happyObj.setVelY(0);
        happyObj.setAccelX(500);
        happyObj.setAccelY(0);
        happyObj.setHitBoxXY(startPosX, startPosY);
    }

    //------------------------------------------------------
    //Game loop component to draw the graphics
    //------------------------------------------------------
    //We need to draw each block based on the type
    public void drawBlocks() {
        int minDrawPosX, minDrawPosY, maxDrawPosX, maxDrawPosY, type;
        int drawX = happyObj.getPosX() - (frameWidth / 2);
        int drawY = happyObj.getPosY() - (frameHeight / 2);
        posX = happyObj.getPosX();
        posY = happyObj.getPosY();
        minDrawPosX = -blockSize;
        minDrawPosY = -blockSize;
        maxDrawPosX = frameWidth;
        maxDrawPosY = frameHeight;
        if (drawX < 0) { drawX = 0; }
        else if (drawX > (numCols * blockSize) - frameWidth)
        {
            drawX = (numCols * blockSize) - frameWidth;
        }
        if (drawY > (numRows * blockSize) - frameHeight) //CHECK
        {
            drawY = (numRows * blockSize) - frameHeight;
        }
        else if (drawY < 0) { drawY = 0; }

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int index = row * numCols + col;
                int x = (col * blockSize) - drawX;
                int y = (row * blockSize) - drawY;
                type = gridObj.get(index).getBlockType();

                //System.out.println("index: " + index + " row" + row + " col: " + col + " numCols: " + numCols + " numRows: " + numRows + " arraysize: " + gridObj.size() + " type: " + gridObj.get(index).getBlockType() );
                //if (type != -1 && type != 31 && type != 24 && type != 25 && type != 26 && type != 27 && type != 28 && type != 29)
                if (type != -1 && type != 31 && !(type >= 24 && type <= 29) && (type != 4) && (type != 19) && (type != 33) && (type != 42) && !(type >= 43 && type <= 47)) //Not air, happy, enemies, friends, fire, hearts, or floating blocks
                {
                    if (x >= minDrawPosX && x <= maxDrawPosX && y >= minDrawPosY && y <= maxDrawPosY) {
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
        drawFloatingBlocks(drawX, drawY);
        drawBlockAnimations(drawX, drawY);
        drawSecretAreas(drawX, drawY);
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

        //changeColor(Color.white);
        //drawBoldText(50, 50, "life: " + life, "arial", 25);

        if (idle && !jump && !hit) {
            happyImage = happyIdle;
        } else if (jump && idle && !hit) {
            happyImage = happyJump;
        } else if ((jump && idle) || (!jump && idle)) {
            happyImage = happyHit;
        }

        //System.out.println("Start pos: " + startPosX + " " + startPosY + " pos: " + posX + " " + posY);
        //System.out.println("numRows: " + numRows);

        tempY = happyObj.getPosY() - drawY;
        currentX = frameWidth / 2;
        currentY = tempY;

        if ((happyObj.getPosX() < frameWidth / 2 || happyObj.getPosX() > (frameWidth/blockSize/2)
                && (happyObj.getPosY() < frameHeight / 2 || happyObj.getPosY() > (frameHeight/blockSize/2)))) {
            //NEED TO DO ONLY CORNERs
            if (happyObj.getPosX() < frameWidth / 2 && happyObj.getPosY() < frameHeight / 2) { //top-left corner
                //System.out.println("TOP-LEFT");
                drawImage(imageArray[happyImage][happyIndex], happyObj.getPosX(), happyObj.getPosY(), blockSize, blockSize);
                currentX = happyObj.getPosX();
                currentY = happyObj.getPosY();
            } else if (happyObj.getPosX() > (frameWidth/blockSize/2))  //bottom-right OR top-right corner
            {
                //System.out.println("BOTTOM RIGHT or TOP RIGHT");
                tempX = happyObj.getPosX() - drawX;
                drawImage(imageArray[happyImage][happyIndex], tempX, tempY, blockSize, blockSize);
                currentX = tempX;
            } else if (happyObj.getPosY() > (frameHeight/blockSize/2)) //Bottom-left corner
            {
                //System.out.println("BOTTOM LEFT");
                drawImage(imageArray[happyImage][happyIndex], happyObj.getPosX(), tempY, blockSize, blockSize);
                currentX = happyObj.getPosX();
            }

        } else if (happyObj.getPosX() >= frameWidth / 2 && happyObj.getPosX() <= (frameWidth/blockSize/2)) {
            if (happyObj.getPosY() <= (frameHeight/blockSize/2) && !(happyObj.getPosY() <= frameHeight / 2)) { //Middle
                //System.out.println("MIDDLE");
                drawImage(imageArray[happyImage][happyIndex], frameWidth / 2, frameHeight / 2, blockSize, blockSize);
                currentY = frameHeight / 2;
            } else if (happyObj.getPosY() <= frameHeight / 2) { //top
                //System.out.println("TOP");
                drawImage(imageArray[happyImage][happyIndex], frameWidth / 2, tempY, blockSize, blockSize);
            } else { //Bottom
                //System.out.println("BOTTOM");
                drawImage(imageArray[happyImage][happyIndex], frameWidth / 2, tempY, blockSize, blockSize);
            }
        } else if (happyObj.getPosY() >= frameHeight / 2 && happyObj.getPosY() <= (frameHeight/blockSize/2)) {
            //System.out.println("LEFT AND RIGHT");
            tempX = happyObj.getPosX() - drawX;
            drawImage(imageArray[happyImage][happyIndex], tempX, frameHeight / 2, blockSize, blockSize);
            currentX = tempX;
            currentY = frameHeight / 2;
        }

//        changeColor(Color.white);
//        drawBoldText(50, 50, "life: " + life, "arial", 25);
    }

    public void drawEnemies(int drawX, int drawY) {
        int enemyImage = 0;
        for (EnemyClass enemyClass : enemyObj) {
            Image[][] enemyImageArray = enemyClass.getImageArray();
            if ((enemyClass.getType() >= 24 && enemyClass.getType() <= 26)) {
                enemyImage = enemyClass.getType() - 24;
            }
            //Additional enemies aren't drawing
            else if (enemyClass.getType() >= 43 && enemyClass.getType() <= 47) {
                enemyImage = enemyClass.getType() - 40;
            }
            int enemyPosX = enemyClass.getPosX() - drawX;
            int enemyPosY = enemyClass.getPosY() - drawY;
            //drawImage(enemyImageArray[enemyImage][happyIndex], enemyPosX, enemyPosY, blockSize, blockSize);
            drawImage(enemyImageArray[1][1], enemyPosX, enemyPosY, blockSize, blockSize);
        }
    }
    //friend1 = 0, friend1Jump = 1, friend2 = 2, friend2Jump = 3, friend3 = 4, friend3Jump = 5, friend4 = 6, friend4Jump = 7;
    public void drawFriends(int drawX, int drawY) {
        int friendImage;
        int i;
        for (i = 0; i < friendObj.size(); i++) {
            Image[][] friendImageArray = friendObj.get(i).getImageArray();
            if (jump && friendObj.get(i).getSaved()) {
                //friendImage = i * 2 + 1;
                friendImage = (friendObj.get(i).getType() - 27) * 2 + 1;
            } else {
                //friendImage = i * 2;
                friendImage = (friendObj.get(i).getType() - 27) * 2;
            }
            if (i > 3) { friendImage = 0; }
            int friendPosX = friendObj.get(i).getPosX() - drawX;
            int friendPosY = friendObj.get(i).getPosY() - drawY;
            //drawImage(friendImageArray[friendImage][happyIndex], friendPosX, friendPosY, blockSize, blockSize);
            drawImage(friendImageArray[1][1], friendPosX, friendPosY, blockSize, blockSize);
        }
    }
    public void drawBlockAnimations(int drawX, int drawY) {
        int blockImage = 0;
        int type, blockPosX, blockPosY;
        for (BlockClass myBlock : myBlocks) {
            Image[][] blockAnimArray = myBlock.getImageArray();
            type = myBlock.getType();
            if ((type == 4) || (type == 19)) {
                if (type == 4) {//This is fire
                    blockImage = 0;
                }
                if (type == 19) {//This is heart
                    blockImage = 1;
                }
                blockPosX = myBlock.getPosX() - drawX;
                blockPosY = myBlock.getPosY() - drawY;
                drawImage(blockAnimArray[blockImage][1], blockPosX, blockPosY, blockSize, blockSize);
            }
        }
    }

    public void drawFloatingBlocks(int drawX, int drawY)
    {
        for (BlockClass myBlock : myBlocks) {
            if (myBlock.getType() == 33) {
                int blockPosX = myBlock.getPosX() - drawX;
                int blockPosY = myBlock.getPosY() - drawY;
                drawImage(blockIMG[33], blockPosX, blockPosY, 75, 25);
            }
            if (myBlock.getType() == 42) {
                int blockPosX = myBlock.getPosX() - drawX;
                int blockPosY = myBlock.getPosY() - drawY;
                drawImage(blockIMG[42], blockPosX, blockPosY, 50, 25);
            }
        }
    }

    public void drawSecretAreas(int drawX, int drawY)
    {
        for (GridClass secretArea : secretAreas) {
            if (secretArea.getBlockType() > 0) {
                int blockPosX = secretArea.getPosX() - drawX;
                int blockPosY = secretArea.getPosY() - drawY;
                drawImage(blockIMG[50], blockPosX, blockPosY, 25, 25);
            }
        }
    }
    // ------------------------------------------------------
    // Creating the sprite array from the spritesheet
    // ------------------------------------------------------


    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent event) {
        super.keyPressed(event);
        //Volpy pause

        if (event.getKeyCode() == KeyEvent.VK_LEFT) { leftKey = true; }
        if (event.getKeyCode() == KeyEvent.VK_RIGHT)  { rightKey = true; }
        if (event.getKeyCode() == KeyEvent.VK_DOWN) { downKey = true; }
        if (event.getKeyCode() == KeyEvent.VK_UP) { upKey = true; }
        if (event.getKeyCode() == KeyEvent.VK_SPACE) {
            jumpKey = true;
            jump = true;

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
                    //System.out.println("HAG line 635 " + happyObj.getVelY() + " " + happyObj.getAccelY());
                }


        }
        if (event.getKeyCode() == KeyEvent.VK_H) {
            showHitboxes = !showHitboxes;
        }
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (gameStates.equals("PlayGame")) {
                if (menuObj.PauseMenuPanel.isVisible()) {
                    menuObj.PauseMenuPanel.setVisible(false);
                    menuObj.PAbuttonPanel.setVisible(false);
                } else {
                    menuObj.PauseMenuPanel.setVisible(true);
                    menuObj.PAbuttonPanel.setVisible(true);
                }
            }

        }
        if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            if(menuObj.foundKeyTutorialMenuPanel.isVisible())
            {
                unPauseGame();
                menuObj.foundKeyTutorialMenuPanel.setVisible(false);
                keysFound = 2;
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
            upKey = false;
            if (isClimbing) { velY = 0; happyObj.setVelY(0); }
        }
        if (event.getKeyCode() == KeyEvent.VK_RIGHT) { rightKey = false; }
        if (event.getKeyCode() == KeyEvent.VK_DOWN) { downKey = false; }
        if (event.getKeyCode() == KeyEvent.VK_SPACE) { jumpKey = jump = false; }
    }


    public void collisionCheck(double dt)
    {
        int posX = happyObj.getPosX();
        int posY = happyObj.getPosY();

        double[] HHB = {happyObj.hitBox.getMinX(), happyObj.hitBox.getMinY(), happyObj.hitBox.getMaxX(), happyObj.hitBox.getMaxY()};

        double velX = happyObj.getVelX();
        double velY = happyObj.getVelY();

        for (BlockClass block : myBlocks) {
            int type = block.getType();

            if (isBlock(type) || isDoor(type) || isFloat(type) || (type == 48)) {
                double[] BHB = {block.hitBox.getMinX(), block.hitBox.getMinY(), block.hitBox.getMaxX(), block.hitBox.getMaxY()};

                if (happyObj.hitBox.intersects(block.hitBox)) {
                    isClimbing = false;
                    isOnGround = true;
                    isFloating = isFloat(type);
                    if (isFloat(type)) {
                        if (type == 33) {
                            posX += blockVelX * dt;
                            happyObj.setPosX(posX);
                        } else {
                            posY += blockVelY * dt;
                            happyObj.setPosY(posY);
                        }
                    }
                    if (velY > 0 && HHB[3] >= BHB[1]) {// going down HBB lower than bt
                        happyObj.setPosY((int) BHB[1] - happyObj.hitBox.height);
                        happyObj.setVelY(0);
                        isJumping = false;
                        canJump = checkIsOnGround();
                    } else if (velY < 0 && HHB[1] <= BHB[3]) {// going up HTB higher than BB
                        happyObj.setPosY((int) BHB[3] + 1);
                        happyObj.setVelY(0);
                        isJumping = false;
                        canJump = checkIsOnGround();
                    } else if (velX > 0 && HHB[2] >= BHB[0]) {// going right HR more right than block left
                        happyObj.setVelX(0);
                        happyObj.setPosX((int) BHB[0] - blockSize - 1);
                        isJumping = false;
                        canJump = checkIsOnGround();
                        canMoveRight = false;
                        rightKey = false;
                    } else if (velX < 0 && HHB[0] <= BHB[2]) {// going left HL more left than block right
                        happyObj.setVelX(0);
                        happyObj.setPosX((int) BHB[2] + 1);
                        isJumping = false;
                        canJump = checkIsOnGround();
                        canMoveLeft = false;
                        leftKey = false;
                    }
                }
            }
            if (contact(block, HHB))
            {
                if (isHeart(type))
                {
                    life++;
                    audioObj.playAudioExtraLife(this, audioObj.extraLife);
                    happyObj.setPlayerLife(life);
                    HUDtot[type - 19]++;
                    deleteBlock(block);
                    break;
                }
                else if (isCandy(type))
                {
                    audioObj.playAudioEatCandy(this, audioObj.eatCandy);
                    if (type != 37) { HUDtot[type - 15]++; }
                    else
                    {
                        HUDtot[type - 33]++;
                        superSweetsEaten++;
                        playAudio(audioObj.sweetSound);
                        if (superSweetsEaten == 1) {
                            firstSuperSweetEaten = true;
                            pauseGame();
                        }
                    }
                    happyObj.setPlayerScore(4 * ((type) - 16) ^ 2 + 1);
                    deleteBlock(block);
                    break;
                }
                else if (isDeadly(type))  { hitDelay(); }  //These are spikes and fire
                else if (type == 5) //These are ladders
                {
                    if ((upKey) || (downKey)) {
                        posX = block.getPosX();
                        posY = block.getPosY();
                        isClimbing = true;
                        happyObj.setAccelY(0);
                        happyObj.setPosX(posX);
                        happyObj.setPosY(posY);
                        happyObj.setHitBoxXY(posX, posY);
                    }
                }
                else if (isKey(type))
                {
                    keys[type - 13] = true;
                    KEYtot[type - 13]++;
                    KEYtot[3]++;
                    keysFound++;
                    if(keysFound == 1)
                    {
                        firstKeyFound = true;
                        pauseGame();
                    }

                    playAudio(audioObj.KeySound);
                    deleteBlock(block);
                    break;
                }
                else if (isFriend(type))
                {
                    if (distance(HHB[0], HHB[1], block.getPosX(), block.getPosY()) < 29)
                    {
                        if (block instanceof FriendClass myFriend) { myFriend.setFriendFollow(); }
                    }
                }
            }// end contact
            else if (isDoor(type)) {// doors
                if (keys[type - 9] && (distance(HHB[0], happyObj.hitBox.getMinY(), block.getPosX(), block.getPosY()) < 28))
                {
                    KEYtot[type - 9]--;
                    if (KEYtot[type - 9] == 0) {
                        keys[type - 9] = false;
                        message = "You need to find the right key...";
                    }
                    playAudio(audioObj.doorBell);
                    deleteBlock(block);
                    break;
                }
            }
            else if (type == 7)
            {
                for (FriendClass friend : friendObj)
                {
                    if (distance(friend.getPosX(), friend.getPosY(), block.getPosX(), block.getPosY()) < 50)
                    {
                        deleteBlock(friend);
                        break;
                    }
                }
            }
        }// end blocks loop

    }
    private boolean contact(BlockClass block, double[] HHB){
        double[] BP = new double[4]; // Block Position
        BP[0] = block.getPosX(); BP[1] = block.getPosY();
        BP[2] = BP[0] + blockSize; BP[3] = BP[1] + blockSize;
        if (((HHB[0] + 5 > BP[0]) && (HHB[0] + 5 < BP[2])) && ((HHB[1] + 5 > BP[1]) && (HHB[1] + 5 < BP[3]))) { return true; }
        return (((HHB[2] - 5 > BP[0]) && (HHB[2] - 5 < BP[2])) && ((HHB[3] - 5 > BP[1]) && (HHB[3] - 5 < BP[3])));
    }


    private boolean isCandy(int type) { return ((type >= 16 && type <= 18) || (type == 37)); }
    private boolean isHeart(int type) { return type == 19; }
    private boolean isBlock(int type) { return type >= 0 && type <= 2; }
    private boolean isDoor(int type) { return type >= 9 && type <= 12; }
    private boolean isFloat(int type) { return type == 33 || type == 42; }
    private boolean isKey(int type) { return type >= 13 && type <= 15; }
    private boolean isFriend(int type) { return type >= 27 && type <= 29; }
    private boolean isDeadly(int type) { return type >= 3 && type <= 4 || type == 39 || type == 40 || type == 41; }

    private void deleteBlock(BlockClass block) {
        gridObj.get(block.getCellIndex()).setBlockType(-1);
        gridObj.get(block.getCellIndex()).setActiveInd(false);
        myBlocks.remove(block);
    }
    private void deleteBlock(FriendClass friend) {
        gridObj.get(friend.getCellIndex()).setBlockType(-1);
        gridObj.get(friend.getCellIndex()).setActiveInd(false);
        friendObj.remove(friend);
        myBlocks.remove(friend);
    }
    private void friendSaver(){
        for (FriendClass friendClass : friendObj)
        {
            friendClass.softreset();
        }

    }

    //-----------------------------------------------------
    //This function will give Happy a few seconds to pass through objects when get hurt
    public void hitDelay() {

        if (!hit && happyObj.getPlayerLife() >= 0)
        {
            happyObj.setPlayerLife(happyObj.getPlayerLife()-1);
            audioObj.playAudioWasHit(this, audioObj.wasHit);
        }
        hit = true;
        hitTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                hit = false;
            }

        }, 2000);
    }

    public boolean checkIsOnGround() {
        for (BlockClass block : myBlocks) {
            if (block.hitBox != null && ((happyObj.hitBox.getMinX() >= block.hitBox.getMinX()) &&
                    (happyObj.hitBox.getMinX() <= block.hitBox.getMaxX()) && (happyObj.hitBox.getMaxX() + 1 >= block.hitBox.getMinY()))) {
                return true;
            }
        }
        return false;
    }

    public void drawHitBoxes() {
        if (showHitboxes) {
            //Draw Happy
            changeColor(Color.green);
            drawsquare(happyObj.hitBox.getMinX(), happyObj.hitBox.getMinY(), happyObj.hitBox.getMaxX(), happyObj.hitBox.getMinY());
            for (BlockClass block : myBlocks) {
                drawsquare(block.hitBox.getMinX(), block.hitBox.getMinY(), block.hitBox.getMaxX(), block.hitBox.getMinY());
            }
        }
    }
    private void drawsquare(double x1, double y1, double x2, double y2){
        drawLine(x1, y1, x2, y1);
        drawLine(x1, y1, x1, y2);
        drawLine(x2, y1, x2, y2);
        drawLine(x1, y2, x2, y2);
    }

    public void updateFloatingBlock(double dt)
    {
        for (BlockClass block : myBlocks) {
            if ((block.getType() == 42)||(block.getType() ==33))
            {
                int blockX = block.getPosX();
                int blockY = block.getPosY();
                int maxRight = block.getStartX() + 2 * blockSize;    //Can only move 3 blocks to the right
                int maxLeft = block.getStartX() - 2 * blockSize - 25;  //Can only move 3 blocks to the left
                int maxTop = block.getStartY() - 7 * blockSize;
                int maxBottom = block.getStartY() + 7 * blockSize - 25;

                if (block.getType() == 33) {
                    block.hitBox.y = blockY;
                    block.hitBox.x = blockX;
                    blockX += blockVelX * dt;
                    block.setPosX(blockX);
                    if (blockX > maxRight) {
                        blockX = maxRight - 1;
                        block.setPosX(blockX);
                        blockVelX *= -1;
                        block.hitBox.y = blockY;
                        block.hitBox.x = blockX;
                    }
                    if (blockX < maxLeft) {
                        blockX = maxLeft + 1;
                        block.setPosX(blockX);
                        blockVelX *= -1;
                    }
                    block.hitBox.y = blockY;
                    block.hitBox.x = blockX;
                }
                else
                {
                    block.hitBox.y = blockY;
                    block.hitBox.x = blockX;
                    blockY += blockVelY * dt;
                    block.setPosY(blockY);

                    if (blockY < maxTop) {
                        blockY = maxTop + 1;
                        block.setPosY(blockY);
                        blockVelY *= -1;
                        block.hitBox.y = blockY;
                        block.hitBox.x = blockX;

                    }
                    if (blockY > maxBottom) {
                        blockY = maxBottom - 1;
                        block.setPosY(blockY);
                        blockVelY *= -1;
                    }
                    block.hitBox.y = blockY;
                    block.hitBox.x = blockX;
                    }
            }

        }
    }
    public void pauseGame() { gamePause = true; }
    public void unPauseGame() { gamePause = false; }
}






