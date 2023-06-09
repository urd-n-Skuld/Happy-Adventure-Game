public class AudioClass
{
    GameEngine.AudioClip bgMusic;
    GameEngine.AudioClip jumpFX;
    GameEngine.AudioClip revive;
    GameEngine.AudioClip eatCandy;
    GameEngine.AudioClip wasHit;
    GameEngine.AudioClip extraLife;
    GameEngine.AudioClip friendly;
    GameEngine.AudioClip KeySound;
    GameEngine.AudioClip sweetSound;
    GameEngine.AudioClip doorBell;
    GameEngine.AudioClip byebye;
    GameEngine.AudioClip endingClap;
    GameEngine.AudioClip endingSad;

    public void setupAudio(HappyAdventuresGame game)
    {
        bgMusic = game.loadAudio("audio/BGMusic.wav");
        game.startAudioLoop(bgMusic, -15f);
        jumpFX = game.loadAudio("audio/jump.wav");
        revive = game.loadAudio("audio/revive.wav");
        eatCandy = game.loadAudio("audio/candy.wav");
        wasHit = game.loadAudio("audio/Hit.wav");
        extraLife = game.loadAudio("audio/extraHeart.wav");
        friendly = game.loadAudio("audio/friendly.wav");
        KeySound = game.loadAudio("audio/door1.wav");
        sweetSound = game.loadAudio("audio/superSweet.wav");
        doorBell = game.loadAudio("audio/doorBell.wav");
        byebye = game.loadAudio("audio/byebye.wav");
        endingClap = game.loadAudio("audio/Ending.wav");
        endingSad = game.loadAudio("audio/orangeSadMusic.wav");
    }

    public void playAudioJump(HappyAdventuresGame game, GameEngine.AudioClip jump)
    {
        game.playAudio(jump,-10f);
    }
    public void playAudioRevive(HappyAdventuresGame game, GameEngine.AudioClip revive)
    {
        game.playAudio(revive,-10f);
    }
    public void playAudioEatCandy(HappyAdventuresGame game, GameEngine.AudioClip eatCandy) { game.playAudio(eatCandy,-10f); }
    public void playAudioWasHit(HappyAdventuresGame game, GameEngine.AudioClip wasHit) { game.playAudio(wasHit,-10f);}
    public void playAudioExtraLife(HappyAdventuresGame game, GameEngine.AudioClip extraLife) { game.playAudio(extraLife,-10f);}

}
