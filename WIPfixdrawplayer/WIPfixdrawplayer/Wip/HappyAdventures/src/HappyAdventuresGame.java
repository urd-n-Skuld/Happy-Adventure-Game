/**********************************************************************************************
 Happy's Adventures brought to you by:  The Good Gamers Team
 Lisinda Gericke (16448443)
 Luiz dos Santos (07246870)
 Mishke van Wyk (21002736)
 Willow Lunicke (22001021)
***********************************************************************************************/

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
    int numCols, numRows;       //These values are initialised when the world map is loaded (See loadBlocks())
    static int blockSize = 25, blockVelX = 50, blockVelY = 50;
    public boolean showHitboxes;
    Timer hitTimer = new Timer();
    String gameStates; // "MenuSystem", "PlayGame", "Paused"
    String csvFile = "images/WorldMaps/Worldmapfinal.csv";
    String secretCSV = "images/WorldMaps/secretBlocks.csv";
    static boolean death, gameOver, gamePause, firstSuperSweetEaten, firstKeyFound, firstFriendSaved, endGame;
    public boolean softResetIsTrue, directionUp;

    //volpy HUD variables
    int[] HUDtot = {0, 0, 0, 0, 0};
    int[] KEYtot = {0, 0, 0, 0, 0};
    int score = 0;

    //HUD IMAGES
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

    //Background Variables
    int cloudX1=100, cloudX2=25, cloudX3=220;
    int cloud1VelX = 35, cloud2VelX = 35, cloud3VelX = 35;

    //World images
    public static Image[] blockIMG =
            {
                    loadImage("images/Sprites/border.png"),//0
                    loadImage("images/Sprites/soil.png"),//1
                    loadImage("images/Sprites/grass.png"),//2
                    loadImage("images/Sprites/spikesUp25px.png"),//3
                    loadImage("images/Sprites/fire.png"),//4 to create the fire sprite
                    loadImage("images/Sprites/vine.png"),//5
                    loadImage("images/Sprites/houseBG.png"),//6 need safe zone HOUSE VOLPY
                    loadImage("images/Sprites/caveBottom.png"),//7 CAVE BOTTOM
                    loadImage("images/Sprites/caveTop.png"),//8 cave top
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
                    loadImage("images/Sprites/doorNoKey.png"),//20 need gate
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
                    loadImage("images/Sprites/caveTopRight.png"),//51 = volpy cave top right
                    loadImage("images/Sprites/arrowRight.png"),//52 Arrows
                    loadImage("images/Sprites/arrowLeft.png"),//53 Arrows
                    loadImage("images/Sprites/arrowUpRight.png"),//54 Arrows
                    loadImage("images/Sprites/arrowUpLeft.png"),//55 Arrows
                    loadImage("images/Sprites/arrowDownRight.png"),//56 Arrows
                    loadImage("images/Sprites/arrowDownLeft.png"),//57 Arrows
                    loadImage("images/Sprites/arrowUpandRight.png"),//58 Arrows
                    loadImage("images/Sprites/bothWays.png"),//59 Arrows
                    loadImage("images/Sprites/arrowUpRightLeft.png"),//60 Arrows
                    loadImage("images/Sprites/exitcheckpoint.png"),// 61 is mapped to the new safe house checkpoint
                    loadImage("images/Sprites/exitsign.png"),//62 is mapped to the new exit sign image
                    loadImage("images/Sprites/exitcheckpoint.png")// 63 is mapped to the new friend checkpoints
            };

    //------------------------------------------------------
    //Create a new game object
    //------------------------------------------------------
    public static void main(String[] args) {
        HappyAdventuresGame gameObj = new HappyAdventuresGame();
        createGame(gameObj, 60);
    }

    //------------------------------------------------------
    //Create a new game object
    //------------------------------------------------------
    int frameWidth = 750, frameHeight = 500;
    int superSweetsEaten, keysFound, noKeypopup, friendsSaved, reachEnd;
    @Override
    public void init()
    {
        hudBG = loadImage("images/Sprites/HUD_bg.png");
        setWindowSize(frameWidth, frameHeight);

        gameStates = "MenuSystem";
        showHitboxes = death = gameOver = endGame = false;
        gamePause = true;
        firstSuperSweetEaten = false;
        superSweetsEaten = 0;
        firstKeyFound = false;
        keysFound = 0;
        firstFriendSaved = false;
        friendsSaved = 0;
        noKeypopup = 0;
        reachEnd = 0;

        initAudio();// line 109
        initWorld(csvFile, secretCSV);// line 176 .... creates variables for grid class
        initCharacters();// line 145
        initGUI();// line 98

        super.mFrame.setSize(frameWidth, frameHeight);
    }

    //------------------------------------------------------
    //GUI objects, methods and initialisation
    //------------------------------------------------------
    GUIClass menuObj = new GUIClass();
    public void initGUI()
    {
        menuObj.setupGUI(this, super.mFrame, super.mPanel, gameStates);
    }

    //------------------------------------------------------
    //Audio objects, methods and initialisation
    //------------------------------------------------------
    AudioClass audioObj = new AudioClass();
    public void initAudio() {
        audioObj.setupAudio(this);
    }

    //------------------------------------------------------
    //Game World objects initialisation
    //------------------------------------------------------
    ArrayList<BlockClass> myblocks;
    ArrayList<GridClass> gridObj;
    ArrayList<GridClass> secretAreas;

    public void initWorld(String mapCSV, String secretCSV)  {

        loadCSV map1 = new loadCSV();
        Object[] resultingLists = map1.loadMap(mapCSV,blockSize);
        myblocks = (ArrayList<BlockClass>) resultingLists[0];
        gridObj = (ArrayList<GridClass>) resultingLists[1];

        Object[] secretBlocks = map1.loadSecretAreas(secretCSV, blockSize);
        secretAreas = (ArrayList<GridClass>) secretBlocks[0];

        numCols = loadCSV.getCol();
        numRows = loadCSV.getRows();

        for (BlockClass block : myblocks) {

            int x = block.getPosX();
            int y = block.getPosY();
            int type = block.getType();
            block.setblockHitBox(x, y, blockSize, blockSize);
            if ((type >= 13) && (type <= 15)) {
                KEYtot[4]++;
            } else if ((type == 4) || (type == 19)) {
                block.initBlockAnimSprites(this);
            } else if (type == 33) {
                block.setblockHitBox(block.getStartX(), block.getStartY(), 75, 25);
            } else if (type == 42) {
                block.setblockHitBox(block.getStartX(), block.getStartY(), 50, 25);
            }
        }
    }

    //------------------------------------------------------
    //Player and other non-playable characters initialisation
    //------------------------------------------------------
    PlayerCharacterClass happyObj = new PlayerCharacterClass();
    ArrayList<EnemyClass> enemyObj = new ArrayList<>();
    ArrayList<FriendClass> friendObj = new ArrayList<>();
    static boolean idle, jump, hit;
    static boolean leftKey, rightKey, upKey, downKey, jumpKey;
    int startPosX, startPosY;
    boolean[] keys = new boolean[3];
    public void initCharacters() {
        for (int i = 0; i < gridObj.size() - 1; i++) {
            //Happy
            if (gridObj.get(i).getBlockType() == 31) {
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
            //Enemies
            else if ((gridObj.get(i).getBlockType() >= 24 && gridObj.get(i).getBlockType() <= 26) || (gridObj.get(i).getBlockType() >= 43 && gridObj.get(i).getBlockType() <= 47))
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

                tempEnemy.setMaxLeft(leftStop, blockSize);
                tempEnemy.setMaxRight(rightStop, blockSize);
                tempEnemy.initEnemySprites(this);
                enemyObj.add(tempEnemy);
            }
            //Friendlies
            else if (gridObj.get(i).getBlockType() == 27 || gridObj.get(i).getBlockType() == 28 || gridObj.get(i).getBlockType() == 29) //Friends
            {
                for (BlockClass block: myblocks){
                    if (block instanceof FriendClass friend)
                    {
                        if (i==friend.getGridIndex()){
                            friend.initFriendSprites(this);
                            friendObj.add(friend);
                        }
                    }
                }
            }
        }

        idle = isOnGround = canJump = canMoveLeft = canMoveRight = true;
        leftKey = rightKey = upKey = downKey = jumpKey = jump = hit = false;
        isJumping = isFalling = isClimbing = collided = isFloating =  false;
    }

    //------------------------------------------------------
    //The game loop update function for updating the game attributes
    //------------------------------------------------------
    int happyIndex, happyIdle = 0, happyJump = 1, happyHit = 2;
    double setDt;
    @Override
    public void update(double dt) {

        setDt = dt;
        //Tutorial popups to display on first occurrence
        if(gamePause && superSweetsEaten == 1 && firstSuperSweetEaten)
        {
            gamePause = true;
            menuObj.SuperSweetTutorialMenuPanel.setVisible(true);
        }
        if(gamePause && keysFound == 1 && firstKeyFound)
        {
            gamePause = true;
            menuObj.foundKeyTutorialMenuPanel.setVisible(true);
        }
        if(gamePause && firstFriendSaved && friendsSaved == 0)
        {
            gamePause = true;
            menuObj.rescueFirstFriendTutorialMenuPanel.setVisible(true);
        }
        if(gamePause && endGame && reachEnd == 0)
        {
            menuObj.endScreen1MenuPanel.setVisible(true);
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
        if (!canJump && !isOnGround && isJumping) {
            //if Happy is jumping
            accelY += happyObj.gravity;
            velY += accelY * dt;
            posY += velY * dt;
        } else if (!isJumping) {
            //if Happy is not jumping or climbing, apply a vertical gravitational force
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

    //------------------------------------------------------
    //Method to convert enemies to friendlies
    //------------------------------------------------------
    private void moveBlocks() {
        ArrayList<EnemyClass> enemiesToRemove = new ArrayList<>();

        //Happy is reset when he falls outside of boundaries
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
            if (happyObj.hitBox.intersects(enemyClass.hitBox)) {
                //if happy has a supersweet then they are converted to friends and need to be removed from Enemyblocks arraylist
                if (HUDtot[4] > 0) {
                    System.out.println(enemyClass.getGridLoc());
                    enemiesToRemove.add(enemyClass); // Add enemy to the removal list
                }
                //if happy doesn't have a supersweet, he gets hurt
                else {
                    hitDelay();
                }
            }
        }

        // Removing the enemies that have been converted to friendlies, creating new friendly block in the friendly arraylist
        for (EnemyClass enemy : enemiesToRemove) {
            FriendClass friend;
            if ((enemy.getType() >= 24 && enemy.getType() <= 26)) {
                friend = new FriendClass(enemy.getPosX(), enemy.getPosY(), enemy.getType() + 3, enemy.getGridLoc());
            } else {
                friend = new FriendClass(enemy.getPosX(), enemy.getPosY(), enemy.getType() - 17, enemy.getGridLoc());
            }
            friend.initFriendSprites(this);
            friend.setFriendFollow();
            playAudio(audioObj.friendly);
            friendObj.add(friend);
            enemyObj.remove(enemy); // Remove enemies from the enemyObj list
            HUDtot[4]--;  //remove one supersweet
        }

        // friends will follow Happy after being converted
        int friendFollowDistance = 40;
        for (int i = 0; i < friendObj.size(); i++) {
            double distance = distance(happyObj.getPosX(), happyObj.getPosY(), friendObj.get(i).getPosX(), friendObj.get(i).getPosY());
            if ((friendObj.get(i).getFollow()) && (distance > friendFollowDistance + (i * blockSize))) {
                friendObj.get(i).Move(happyObj.getPosX(), happyObj.getPosY(), distance + (i * blockSize));
            }
        }
    }

    //------------------------------------------------------
    // Resetting settings when Happy dies
    //------------------------------------------------------
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
    }

    //------------------------------------------------------
    //Drawing clouds
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

    //------------------------------------------------------
    //Drawing background and clouds
    //------------------------------------------------------
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

    //------------------------------------------------------
    //Drawing HUD
    //------------------------------------------------------
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
        drawBoldText(590, 20, "Total Score: " + happyObj.getPlayerScore(), "arial", 15);
    }

    //------------------------------------------------------
    //Game loop component to draw objects of the game
    // ------------------------------------------------------
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

    //------------------------------------------------------
    //Reset the game after gameOver
    // ------------------------------------------------------
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
        int drawY = happyObj.getPosY() - (frameHeight / 2); //CHECK
        posX = happyObj.getPosX();
        posY = happyObj.getPosY();
        minDrawPosX = -blockSize;
        minDrawPosY = -blockSize;
        maxDrawPosX = frameWidth;
        maxDrawPosY = frameHeight;

        if (drawX < 0) {
            drawX = 0;
        } else if (drawX > (numCols * blockSize) - frameWidth) {
            drawX = (numCols * blockSize) - frameWidth;
        }
        if (drawY > (numRows * blockSize) - frameHeight)
        {
            drawY = (numRows * blockSize) - frameHeight;
        } else if (drawY < 0) {
            drawY = 0;
        }

        drawVisualAssets(drawX,drawY);

        //Drawing the world blocks, platforms
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int index = row * numCols + col;
                int x = (col * blockSize) - drawX;
                int y = (row * blockSize) - drawY;
                type = gridObj.get(index).getBlockType();

                if (type != -1 && type != 31 && !(type >= 24 && type <= 29) && (type != 4) && (type != 19)&& (type != 7) && (type != 8) && (type != 51)&& (type != 6) && (type != 33) && (type != 42) && !(type >= 43 && type <= 47) && !(type >= 52 && type <= 63)) //Not air, happy, enemies, friends, fire, hearts, or floating blocks
                {
                    if (x >= minDrawPosX && x <= maxDrawPosX && y >= minDrawPosY && y <= maxDrawPosY) {
                        drawImage(blockIMG[type], x, y, blockSize, blockSize);
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

        if (idle && !jump && !hit) {
            happyImage = happyIdle;
        } else if (jump && idle && !hit) {
            happyImage = happyJump;
        } else if ((jump && idle && hit) || (!jump && idle && hit)) {
            happyImage = happyHit;
        }

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
    }

    //------------------------------------------------------
    //Game loop component to draw the enemy characters
    //------------------------------------------------------
    public void drawEnemies(int drawX, int drawY) {
        int enemyImage = 0;
        for (int i = 0; i < enemyObj.size(); i++) {
            Image[][] enemyImageArray = enemyObj.get(i).getImageArray();
            if((enemyObj.get(i).getType() >= 24 && enemyObj.get(i).getType() <= 26) )
            {
                enemyImage = enemyObj.get(i).getType() - 24;
            }
            else if (enemyObj.get(i).getType() >= 43 && enemyObj.get(i).getType() <= 47)
            {
                enemyImage = enemyObj.get(i).getType() - 40;
            }
            int enemyPosX = enemyObj.get(i).getPosX() - drawX;
            int enemyPosY = enemyObj.get(i).getPosY() - drawY;
            drawImage(enemyImageArray[enemyImage][happyIndex], enemyPosX, enemyPosY, blockSize, blockSize);
        }
    }

    //------------------------------------------------------
    //Game loop component to draw the friendly characters
    //------------------------------------------------------
    public void drawFriends(int drawX, int drawY) {
        int friendImage;
        for (int i = 0; i < friendObj.size(); i++) {
            //friend1 = 0, friend1Jump = 1, friend2 = 2, friend2Jump = 3, friend3 = 4, friend3Jump = 5, friend4 = 6, friend4Jump = 7;
            Image[][] friendImageArray = friendObj.get(i).getImageArray();
            if (jump && friendObj.get(i).getSaved()) {
                //friendImage = i * 2 + 1;
                friendImage = (friendObj.get(i).getType() - 27) * 2 + 1;
            } else {
                //friendImage = i * 2;
                friendImage = (friendObj.get(i).getType() - 27) * 2;
            }
            //if (i > 3) { friendImage = 0; }
            if (friendImage < 0 || friendImage > 7) { friendImage = 0; } //Prevents array out of bounds exception
            int friendPosX = friendObj.get(i).getPosX() - drawX;
            int friendPosY = friendObj.get(i).getPosY() - drawY;
            drawImage(friendImageArray[friendImage][happyIndex], friendPosX, friendPosY, blockSize, blockSize);
        }
    }

    //------------------------------------------------------
    //Game loop component to draw the block animations
    //------------------------------------------------------
    public void drawBlockAnimations(int drawX, int drawY) {
        int blockImage = 0;
        int type, blockPosX, blockPosY;
        for (int i = 0; i < myblocks.size(); i++) {
            Image[][] blockAnimArray = myblocks.get(i).getImageArray();
            type = myblocks.get(i).getType();
            if((type == 4) || (type == 19))
            {
                if (type == 4)
                {//This is fire
                    blockImage = 0;
                }
                if(type == 19)
                {//This is heart
                    blockImage = 1;
                }
                blockPosX = myblocks.get(i).getPosX() - drawX;
                blockPosY = myblocks.get(i).getPosY() - drawY;
                drawImage(blockAnimArray[blockImage][happyIndex], blockPosX, blockPosY, blockSize, blockSize);
            }
        }
    }

    //------------------------------------------------------
    //Game loop component to draw the moving world blocks
    //------------------------------------------------------
    public void drawFloatingBlocks(int drawX, int drawY)
    {
        for (int i = 0; i < myblocks.size(); i++)
        {
            if(myblocks.get(i).getType() == 33)
            {
                int blockPosX = myblocks.get(i).getPosX() - drawX;
                int blockPosY = myblocks.get(i).getPosY() - drawY;
                drawImage(blockIMG[33], blockPosX, blockPosY, 75, 25);
            }
            if(myblocks.get(i).getType() == 42)
            {
                int blockPosX = myblocks.get(i).getPosX() - drawX;
                int blockPosY = myblocks.get(i).getPosY() - drawY;
                drawImage(blockIMG[42], blockPosX, blockPosY, 50, 25);
            }
        }
    }

    //------------------------------------------------------
    //Game loop component to draw the vined secret areas
    //------------------------------------------------------
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

    //------------------------------------------------------
    //Game loop component to draw the background caves, arrows etc.
    //------------------------------------------------------
    public void drawVisualAssets (int drawX, int drawY)
    {
        for (int i = 0; i < myblocks.size(); i++)
        {
            if(myblocks.get(i).getType() == 6)
            {
                int blockPosX = myblocks.get(i).getPosX() - drawX;
                int blockPosY = myblocks.get(i).getPosY() - drawY;
                drawImage(blockIMG[6], blockPosX, blockPosY, 525, 175);
            }
            if(myblocks.get(i).getType() == 7)
            {
                int blockPosX = myblocks.get(i).getPosX() - drawX;
                int blockPosY = myblocks.get(i).getPosY() - drawY;
                drawImage(blockIMG[7], blockPosX, blockPosY, 775, 225);
            }
            if(myblocks.get(i).getType() == 8)
            {
                int blockPosX = myblocks.get(i).getPosX() - drawX;
                int blockPosY = myblocks.get(i).getPosY() - drawY;
                drawImage(blockIMG[8], blockPosX, blockPosY, 600, 850);
            }
            if(myblocks.get(i).getType() == 51)
            {

                int blockPosX = myblocks.get(i).getPosX() - drawX;
                int blockPosY = myblocks.get(i).getPosY() - drawY;
                drawImage(blockIMG[51], blockPosX, blockPosY, 800, 250);
            }
            if(myblocks.get(i).getType() == 52)
            {

                int blockPosX = myblocks.get(i).getPosX() - drawX;
                int blockPosY = myblocks.get(i).getPosY() - drawY;
                drawImage(blockIMG[52], blockPosX, blockPosY, 50, 50);
            }
            if(myblocks.get(i).getType() == 53)
            {

                int blockPosX = myblocks.get(i).getPosX() - drawX;
                int blockPosY = myblocks.get(i).getPosY() - drawY;
                drawImage(blockIMG[53], blockPosX, blockPosY, 50, 50);
            }
            if(myblocks.get(i).getType() == 54)
            {

                int blockPosX = myblocks.get(i).getPosX() - drawX;
                int blockPosY = myblocks.get(i).getPosY() - drawY;
                drawImage(blockIMG[54], blockPosX, blockPosY, 50, 50);
            }
            if(myblocks.get(i).getType() == 55)
            {

                int blockPosX = myblocks.get(i).getPosX() - drawX;
                int blockPosY = myblocks.get(i).getPosY() - drawY;
                drawImage(blockIMG[55], blockPosX, blockPosY, 50, 50);
            }
            if(myblocks.get(i).getType() == 56)
            {

                int blockPosX = myblocks.get(i).getPosX() - drawX;
                int blockPosY = myblocks.get(i).getPosY() - drawY;
                drawImage(blockIMG[56], blockPosX, blockPosY, 50, 50);
            }
            if(myblocks.get(i).getType() == 57)
            {

                int blockPosX = myblocks.get(i).getPosX() - drawX;
                int blockPosY = myblocks.get(i).getPosY() - drawY;
                drawImage(blockIMG[56], blockPosX, blockPosY, 50, 50);
            }
            if(myblocks.get(i).getType() == 58)
            {

                int blockPosX = myblocks.get(i).getPosX() - drawX;
                int blockPosY = myblocks.get(i).getPosY() - drawY;
                drawImage(blockIMG[58], blockPosX, blockPosY, 50, 50);
            }
            if(myblocks.get(i).getType() == 59)
            {

                int blockPosX = myblocks.get(i).getPosX() - drawX;
                int blockPosY = myblocks.get(i).getPosY() - drawY;
                drawImage(blockIMG[59], blockPosX, blockPosY, 50, 50);
            }
            if(myblocks.get(i).getType() == 60)
            {

                int blockPosX = myblocks.get(i).getPosX() - drawX;
                int blockPosY = myblocks.get(i).getPosY() - drawY;
                drawImage(blockIMG[60], blockPosX, blockPosY, 50, 50);
            }
        }
    }

    //------------------------------------------------------
    //Creating the sprite array from the spritesheet
    //------------------------------------------------------
    /*public void loadBlockImages() {
        try {
            //Placeholder for the image sprites to be loaded into an array
        } catch (Exception e) {
            System.out.println("HAG Line 600 Issues with loading images for Blocks. Error location: BlockClass.loadBlockImages");
        }
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    //------------------------------------------------------
    // Keys pressed controls
    //------------------------------------------------------
    @Override
    public void keyPressed(KeyEvent event) {
        super.keyPressed(event);

        if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            if (canMoveLeft) {
                leftKey = true;
            }
        }
        if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (canMoveRight) {
                rightKey = true;
            }
        }
        if (event.getKeyCode() == KeyEvent.VK_DOWN) {
            downKey = true;
        }
        if (event.getKeyCode() == KeyEvent.VK_UP) {
            upKey = true;
        }
        if (event.getKeyCode() == KeyEvent.VK_SPACE) {
            jumpKey = true;
            jump = true;

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
                }

            }
        }
        if (event.getKeyCode() == KeyEvent.VK_H) {
            if (showHitboxes) showHitboxes = false;
            else showHitboxes = true;
        }
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            if (gameStates == "PlayGame")
            {
                if (menuObj.PauseMenuPanel.isVisible())
                {
                    unPauseGame();
                    menuObj.PauseMenuPanel.setVisible(false);
                    menuObj.PAbuttonPanel.setVisible(false);
                } else
                {
                    pauseGame();
                    menuObj.PauseMenuPanel.setVisible(true);
                    menuObj.PAbuttonPanel.setVisible(true);
                }
            }
            if (menuObj.CreditsMenuPanel.isVisible())
            {
                menuObj.CreditsMenuPanel.setVisible(false);
                menuObj.MainMenuPanel.setVisible(true);
                menuObj.MMbuttonPanel.setVisible(true);
            }
        }
        if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            if(menuObj.foundKeyTutorialMenuPanel.isVisible())
            {
                unPauseGame();
                menuObj.foundKeyTutorialMenuPanel.setVisible(false);
                keysFound = 2;
            }
            if(menuObj.SuperSweetTutorialMenuPanel.isVisible())
            {
                unPauseGame();
                menuObj.SuperSweetTutorialMenuPanel.setVisible(false);
                superSweetsEaten = 2;
            }
            if(menuObj.CreditsMenuPanel.isVisible())
            {
                menuObj.CreditsMenuPanel.setVisible(false);
                menuObj.MainMenuPanel.setVisible(true);
                menuObj.MMbuttonPanel.setVisible(true);
            }
            if(menuObj.noKeyTutorialMenuPanel.isVisible())
            {
                unPauseGame();
                menuObj.noKeyTutorialMenuPanel.setVisible(false);
            }
            if(menuObj.rescueFirstFriendTutorialMenuPanel.isVisible())
            {
                unPauseGame();
                friendsSaved++;
                menuObj.rescueFirstFriendTutorialMenuPanel.setVisible(false);
            }
            if(menuObj.endScreen2MenuPanel.isVisible())
            {
                menuObj.endScreen2MenuPanel.setVisible(false);
                menuObj.CreditsMenuPanel.setVisible(true);
            }
            if(menuObj.endScreen1MenuPanel.isVisible())
            {
                reachEnd++;
                menuObj.endScreen1MenuPanel.setVisible(false);
                playAudio(audioObj.endingSad);
                menuObj.endScreen2MenuPanel.setVisible(true);
            }
        }
    }

    //------------------------------------------------------
    // Keys released controls
    //------------------------------------------------------
    @Override
    public void keyReleased(KeyEvent event) {
        super.keyReleased(event);
        if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            leftKey = false;
        }
        if (event.getKeyCode() == KeyEvent.VK_UP) {
            upKey = false;
            if (isClimbing) {
                velY = 0;
                happyObj.setVelY(0);
            }
        }
        if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightKey = false;
        }
        if (event.getKeyCode() == KeyEvent.VK_DOWN) {
            downKey = false;
        }
        if (event.getKeyCode() == KeyEvent.VK_SPACE) {
            jumpKey = jump = false;
        }
    }

    //------------------------------------------------------
    // Main function for checking collisions with the different world blocks and characters
    //------------------------------------------------------
    public boolean collisionCheck(double dt) {
        ArrayList<FriendClass> friendliesToRemove = new ArrayList<>();
        int posX = happyObj.getPosX();
        int posY = happyObj.getPosY();
        double velX = happyObj.getVelX();
        double velY = happyObj.getVelY();

        life = happyObj.getPlayerLife();

        //Collision with blocks that have a hitBox
        for (BlockClass block : myblocks) {

            int type = block.getType();

            if (((type >= 0) && (type <= 2)) || ((type >= 9) && (type <= 12)) || (type == 33) || (type == 42) || (type == 48))
            {
                if (happyObj.hitBox.intersects(block.hitBox)) {

                    collided = true;
                    isClimbing = false;
                    isOnGround = true;

                    //------------------------------------------------------
                    //Floating block collision settings
                    if((type != 33) && (type != 42))
                    {
                        System.out.println("Collision with non floating blocks " + type);
                        isFloating = false;
                        System.out.println("is floating: " + isFloating);
                    }
                    if((type == 33) || (type == 42)){
                        System.out.println("Collision with floating blocks" + type);
                        isFloating = true;
                        System.out.println("is floating: " + isFloating);
                    }
                    if(isFloating && isOnGround)
                    {
                        if(type == 33)
                        {
                            posX += blockVelX * dt;
                            happyObj.setPosX(posX);
                        }
                        else if ((type == 42))
                        {
                            posY += blockVelY * dt;
                            happyObj.setPosY(posY);
                        }
                    }
                    //---------------------------------------------------
                    if (velY > 0 && happyObj.hitBox.getMaxY() >= block.hitBox.getMinY()) //Happy Top Collision
                    {
                        //Happy is falling down to the ground, his feet is going through the nearest block below
                        posY = (int) (block.hitBox.getMinY() - happyObj.hitBox.height);
                        velY = 0;
                        isJumping = false;
                        collided = false;
                        isOnGround = checkIsOnGround(posX, posY);

                        if (!isOnGround) {
                            canJump = false;
                        } else {
                            canJump = true;
                        }
                    } else if (velY < 0 && happyObj.hitBox.getMinY() <= block.hitBox.getMaxY()) //Happy Bottom Collision
                    {   //Happy is going upwards, his head is going through the block above
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
                    } else if (velX > 0 && happyObj.hitBox.getMaxX() >= block.hitBox.getMinX()) //Happy Right Side Collision
                    {   //Happy is moving to the right, his right side is going through the block on the right
                        if(isFloating && directionUp)
                        {
                            posX += velX * dt;
                        }
                        else
                        {
                            canMoveRight = false;
                            rightKey = false;
                            velX = 0;
                            posX = (int) block.hitBox.getMinX() - 1 - blockSize;
                            collided = false;
                            isOnGround = checkIsOnGround(posX, posY);
                            if (!isOnGround) {
                                canJump = false;
                            }
                        }

                    } else if (velX < 0 && happyObj.hitBox.getMinX() <= block.hitBox.getMaxX()) //Happy Left Side Collision
                    {
                        //Happy is moving to the left, his left side is going through the block on the left
                        if(isFloating && directionUp)
                        {
                            posX += velX * dt;
                        }
                        else
                        {
                            canMoveLeft = false;
                            leftKey = false;
                            velX = 0;
                            posX = (int) block.hitBox.getMaxX() + 1;
                            collided = false;
                            isOnGround = checkIsOnGround(posX, posY);

                            if (!isOnGround) {
                                canJump = false;
                            } else {
                                canJump = true;
                            }
                        }

                    } else if ((velX == 0) && (velY == 0)) {
                        //Do nothing
                    }

                    if ((!canMoveLeft && (rightKey || velX == 0))) {
                        canMoveLeft = true;
                    }
                    if ((!canMoveRight && (leftKey || velX == 0))) {
                        canMoveRight = true;
                    }
                }
                happyObj.setVelY(velY);
                happyObj.setVelX(velX);
                happyObj.setPosY(posY);
                happyObj.setPosX(posX);
                happyObj.setHitBoxXY(posX, posY);
            }
            //Collision for blocks that don't have hitboxes
            if ((type == 16) || (type == 17) || (type == 18) || (type == 19) || (type == 37))
            {   //These are candies and hearts
                if ((((happyObj.hitBox.getMaxX() - 5 > block.getPosX() && happyObj.hitBox.getMaxX() - 5 < block.getPosX() + blockSize)) && (happyObj.hitBox.getMaxY() - 5 > block.getPosY() && happyObj.hitBox.getMaxY() - 5 < block.getPosY() + blockSize))
                        || ((((happyObj.hitBox.getMinX() + 5 > block.getPosX() && happyObj.hitBox.getMinX() + 5 < block.getPosX() + blockSize)) && (happyObj.hitBox.getMinY() + 5 > block.getPosY() && happyObj.hitBox.getMinY() + 5 < block.getPosY() + blockSize)))) {

                    if (type == 19) {
                        //Add a life when collide with a heart
                        life++;
                        audioObj.playAudioExtraLife(this, audioObj.extraLife);
                        happyObj.setPlayerLife(life);
                        HUDtot[type - 19]++;
                        deleteBlock(block);
                    } else {
                        //Eating candies
                        audioObj.playAudioEatCandy(this, audioObj.eatCandy);
                        if(type != 37)
                        {
                            HUDtot[type - 15]++;
                        }
                        else
                        {
                            HUDtot[type - 33]++;
                            superSweetsEaten++;
                            playAudio(audioObj.sweetSound);
                            if(superSweetsEaten == 1)
                            {
                                firstSuperSweetEaten = true;
                                pauseGame();
                            }
                        }
                        //updating score when candies are eaten
                        if (type != 37) {
                            happyObj.setPlayerScore(4 * ((type) - 16) ^ 2 + 1);
                        }
                        deleteBlock(block);
                    }
                    break;
                }
            } else if ((type == 3) || (type == 4) || (type == 39) || (type == 40) || (type == 41))
            {   //These are spikes and fire, Happy gets hurt
                if ((((happyObj.hitBox.getMaxX() - 5 > block.getPosX() && happyObj.hitBox.getMaxX() - 5 < block.getPosX() + blockSize)) && (happyObj.hitBox.getMaxY() - 5 > block.getPosY() && happyObj.hitBox.getMaxY() - 5 < block.getPosY() + blockSize))
                        || ((((happyObj.hitBox.getMinX() + 5 > block.getPosX() && happyObj.hitBox.getMinX() + 5 < block.getPosX() + blockSize)) && (happyObj.hitBox.getMinY() + 5 > block.getPosY() && happyObj.hitBox.getMinY() + 5 < block.getPosY() + blockSize)))) {
                    hitDelay();
                }
            }   //These are ladders
            else if (type == 5) {
                if ((((happyObj.hitBox.getMaxX() - 10 > block.getPosX() && happyObj.hitBox.getMaxX() - 10 < block.getPosX() + blockSize)) && (happyObj.hitBox.getMaxY() - 5 > block.getPosX() && happyObj.hitBox.getMaxY() - 5 < block.getPosY() + blockSize))
                        || ((((happyObj.hitBox.getMinX() + 10 > block.getPosX() && happyObj.hitBox.getMinX() + 10 < block.getPosX() + blockSize)) && (happyObj.hitBox.getMinY() + 5 > block.getPosY() && happyObj.hitBox.getMinY() + 5 < block.getPosY() + blockSize)))) {
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
            } else if ((type >= 13) && (type <= 15)) {
                //keys , update HUD and key inventory when keys are collected
                if ((((happyObj.hitBox.getMaxX() - 5 > block.getPosX() && happyObj.hitBox.getMaxX() - 5 < block.getPosX() + blockSize)) && (happyObj.hitBox.getMaxY() - 5 > block.getPosY() && happyObj.hitBox.getMaxY() - 5 < block.getPosY() + blockSize))
                        || ((((happyObj.hitBox.getMinX() + 5 > block.getPosX() && happyObj.hitBox.getMinX() + 5 < block.getPosX() + blockSize)) && (happyObj.hitBox.getMinY() + 5 > block.getPosY() && happyObj.hitBox.getMinY() + 5 < block.getPosY() + blockSize)))) {
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
            } else if ((type >= 9) && (type <= 11))
            {// doors , only open the doors if the right key is in the inventory
                if (keys[type - 9] && (distance(happyObj.hitBox.getMinX(), happyObj.hitBox.getMinY(), block.getPosX(), block.getPosY()) < 28))
                {
                    KEYtot[type - 9]--;
                    if (KEYtot[type - 9] == 0) {
                        keys[type - 9] = false;
                    }
                    playAudio(audioObj.doorBell);
                    deleteBlock(block);
                    break;
                }
                //no key, but interacting with a door. will show a popup the first time this happens
                else if (!(keys[type - 9]) && (noKeypopup == 0) && (distance(happyObj.hitBox.getMinX(), happyObj.hitBox.getMinY(), block.getPosX(), block.getPosY()) < 28))
                {
                    pauseGame();
                    menuObj.noKeyTutorialMenuPanel.setVisible(true);
                    noKeypopup = 1;
                }
            }
            else if ((type >= 27) && (type <= 29))
            {// friends , when the friend behind a door is interacted with, they need to follow Happy
                if (distance(happyObj.hitBox.getMinX(), happyObj.hitBox.getMinY(), block.getPosX(), block.getPosY())<29)
                {
                    if (block instanceof FriendClass myfriend){
                        if(!myfriend.getFollow() && !myfriend.getSaved())
                        {
                            playAudio(audioObj.friendly);
                            if((friendsSaved == 0) && (!firstFriendSaved))
                            {
                                firstFriendSaved = true;
                                pauseGame();
                            }
                        }
                        myfriend.setFriendFollow();
                    }
                    break;
                }
            }
            else if (type == 61) //Safe zone checkpoint, friends will be deleted from friend arraylist once they are saved
            {
                for (FriendClass friend : friendObj) {
                    if (distance(friend.getPosX(), friend.getPosY(), block.getPosX(), block.getPosY()) < 20) {
                        friend.setSaved();
                        friendliesToRemove.add(friend);
                    }
                }
            }
            else if (type == 49) //Exit zone checkpoint, triggers endgame scene
            {
                if ((((happyObj.hitBox.getMaxX() - 5 > block.getPosX() && happyObj.hitBox.getMaxX() - 5 < block.getPosX() + blockSize)) && (happyObj.hitBox.getMaxY() - 5 > block.getPosY() && happyObj.hitBox.getMaxY() - 5 < block.getPosY() + blockSize))
                        || ((((happyObj.hitBox.getMinX() + 5 > block.getPosX() && happyObj.hitBox.getMinX() + 5 < block.getPosX() + blockSize)) && (happyObj.hitBox.getMinY() + 5 > block.getPosY() && happyObj.hitBox.getMinY() + 5 < block.getPosY() + blockSize))))
                {
                    endGame = true;
                    pauseGame();
                    playAudio(audioObj.endingClap);
                }
            }
        }
        //removing the friends that have been saved
        if(friendliesToRemove.size() > 0)
        {
            for(FriendClass friend : friendliesToRemove)
            {
                playAudio(audioObj.byebye);
                friendObj.remove(friend);
            }
        }

        return false;
    }

    //------------------------------------------------------
    // Removing the block from the block arraylist
    //------------------------------------------------------
    private void deleteBlock(BlockClass block) {
        gridObj.get(block.getCellIndex()).setBlockType(-1);
        gridObj.get(block.getCellIndex()).setActiveInd(false);
        myblocks.remove(block);
    }
    /*private void deleteFriend(FriendClass friend)
    {
        gridObj.get(friend.getCellIndex()).setBlockType(-1);
        gridObj.get(friend.getCellIndex()).setActiveInd(false);
        friendObj.remove(friend);
    }*/

   /* private void deleteEnemy(EnemyClass enemy) {
        gridObj.get(enemy.getCellIndex()).setBlockType(-1);
        gridObj.get(enemy.getCellIndex()).setActiveInd(false);
        enemyObj.remove(enemy);
    }*/

    //------------------------------------------------------
    //
    //------------------------------------------------------
    private void friendSaver(){
        for (FriendClass friendClass : friendObj)
        {
            friendClass.softreset();
        }
    }

    //-----------------------------------------------------
    //This function will give Happy a few seconds to pass through objects when get hurt
    //-----------------------------------------------------
    public void hitDelay() {

        if (!hit && life >= 0) {
            life = happyObj.getPlayerLife();
            life--;
            happyObj.setPlayerLife(life);
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

    //-----------------------------------------------------
    //This function will check if Happy is standing on a block
    //-----------------------------------------------------
    public boolean checkIsOnGround(int x, int y) {//System.out.println("checkisonground called");
        for (BlockClass block : myblocks) {
            if ((x >= block.hitBox.getMinX()) && (x <= block.hitBox.getMaxX()) && (y + 26 >= block.hitBox.getMinY())) {
                return true;
            }
        }
        return false;
    }

    //-----------------------------------------------------
    //This function will draw the hitboxes of the world... currently only Happy is working
    //-----------------------------------------------------
    public void drawHitBoxes() {//System.out.println("drawhitboxes called");
        if (showHitboxes) {
            //Draw Happy
            changeColor(Color.green);
            drawLine(happyObj.hitBox.getMinX(), happyObj.hitBox.getMinY(), happyObj.hitBox.getMaxX(), happyObj.hitBox.getMinY());
            drawLine(happyObj.hitBox.getMinX(), happyObj.hitBox.getMinY(), happyObj.hitBox.getMinX(), happyObj.hitBox.getMaxY());
            drawLine(happyObj.hitBox.getMaxX(), happyObj.hitBox.getMinY(), happyObj.hitBox.getMaxX(), happyObj.hitBox.getMaxY());
            drawLine(happyObj.hitBox.getMinX(), happyObj.hitBox.getMaxY(), happyObj.hitBox.getMaxX(), happyObj.hitBox.getMaxY());

            /*//Draw all other world objects
            for (BlockClass block : myblocks) {
                drawLine(block.hitBox.getMinX(), block.hitBox.getMinY(), block.hitBox.getMaxX(), block.hitBox.getMinY());
                drawLine(block.hitBox.getMinX(), block.hitBox.getMinY(), block.hitBox.getMinX(), block.hitBox.getMaxY());
                drawLine(block.hitBox.getMaxX(), block.hitBox.getMinY(), block.hitBox.getMaxX(), block.hitBox.getMaxY());
                drawLine(block.hitBox.getMinX(), block.hitBox.getMaxY(), block.hitBox.getMaxX(), block.hitBox.getMaxY());
            }*/
        }
    }

    //-----------------------------------------------------
    //This function controls the moving blocks, updates their position and sets max and min movement range
    //-----------------------------------------------------
    public void updateFloatingBlock(double dt)
    {
        for (int i = 0; i < myblocks.size(); i++)
        {
            BlockClass block = myblocks.get(i);
            int blockX = block.getPosX();
            int blockY = block.getPosY();
            int maxRight = block.getStartX() + 2*blockSize;    //Can only move 3 blocks to the right
            int maxLeft = block.getStartX() - 2*blockSize - 25;  //Can only move 3 blocks to the left
            int maxTop = block.getStartY() - 7*blockSize;
            int maxBottom = block.getStartY() + 7*blockSize - 25;

            if (block.getType() == 33)
            {//These are horisontal moving floating blocks
                block.hitBox.y = blockY;
                block.hitBox.x = blockX;
                blockX += blockVelX * dt;
                block.setPosX(blockX);
                if (blockX > maxRight)
                {//If right side of block reaches maxRight

                    blockX = maxRight-1;     //reposition so that it doesn't lose space
                    block.setPosX(blockX);
                    blockVelX *= -1;
                    block.hitBox.y = blockY;
                    block.hitBox.x = blockX;
                }
                if (blockX < maxLeft)
                {//If left side of the block reaches maxLeft
                    blockX = maxLeft +1;     //reposition so that it doesn't lose space
                    block.setPosX(blockX);
                    blockVelX *= -1;
                    block.hitBox.y = blockY;
                    block.hitBox.x = blockX;
                }
                block.hitBox.y = blockY;
                block.hitBox.x = blockX;
            }
            else if (block.getType() == 42)
            {//These are vertical moving floating blocks
                block.hitBox.y = blockY;
                block.hitBox.x = blockX;
                blockY += blockVelY * dt;
                block.setPosY(blockY);

                if (blockY < maxTop)
                {//If top side of block reaches maxtop
                    directionUp = false;
                    blockY = maxTop+1;     //reposition so that it doesn't lose space
                    block.setPosY(blockY);
                    blockVelY *= -1;
                    block.hitBox.y = blockY;
                    block.hitBox.x = blockX;
                }
                if (blockY > maxBottom)
                {//If bottom side of the block reaches maxLeft
                    directionUp = true;
                    blockY = maxBottom -1;     //reposition so that it doesn't lose space
                    block.setPosY(blockY);
                    blockVelY *= -1;
                    block.hitBox.y = blockY;
                    block.hitBox.x = blockX;
                }
                block.hitBox.y = blockY;
                block.hitBox.x = blockX;
            }
        }
    }
    public void pauseGame()
    {
        gamePause = true;
    }
    public void unPauseGame()
    {
        gamePause = false;
    }


}






