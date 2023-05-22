public class AudioClass
{
    GameEngine.AudioClip bgMusic;
    GameEngine.AudioClip jumpFX;
    GameEngine.AudioClip revive;
    GameEngine.AudioClip eatCandy;


    public void setupAudio(HappyAdventuresGame game)
    {
        game.startAudioLoop(bgMusic);
        jumpFX = game.loadAudio("audio/jump.wav");
        revive = game.loadAudio("audio/revive.wav");
        eatCandy = game.loadAudio("audio/candy.wav");
    }

    public void playAudioJump(HappyAdventuresGame game, GameEngine.AudioClip jump)
    {
        game.playAudio(jump,-10f);
    }
    public void playAudioRevive(HappyAdventuresGame game, GameEngine.AudioClip revive)
    {
        game.playAudio(revive,-10f);
    }
    public void playAudioEatCandy(HappyAdventuresGame game, GameEngine.AudioClip revive)
    {
        game.playAudio(revive,-10f);
    }
}
