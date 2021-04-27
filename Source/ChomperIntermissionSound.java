import java.io.InputStream;
import java.io.FileInputStream;
import sun.audio.*;
import java.io.IOException;

public class ChomperIntermissionSound implements Runnable
{
    InputStream input;
    AudioStream audio;
    
    public void run()
    {
        try {
            input = new FileInputStream("Audio Files/pacman_intermission.wav");
            audio = new AudioStream(input);
            AudioPlayer.player.start(audio);
        } catch(IOException e) {e.printStackTrace();}
    }
    
    public void stopSound()
    {
        AudioPlayer.player.stop(audio);
    }
}
