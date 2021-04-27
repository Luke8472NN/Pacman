import java.io.InputStream;
import java.io.FileInputStream;
import sun.audio.*;
import java.io.IOException;
/*import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;*/

public class ChomperBeginningSound implements Runnable
{
    InputStream input;
    AudioStream audio;
    
    public void run()
    {
        try {
            input = new FileInputStream("Audio Files/pacman_beginning.wav");
            audio = new AudioStream(input);
            AudioPlayer.player.start(audio);
        } catch(IOException e) {e.printStackTrace();}
    }
    
    /*File soundfile = new File("/Users/LukeAir/Downloads/Pacman9/Audio Files/pacman_beginning.wav");
    AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
    Clip clip = AudioSystem.getClip();
    clip.open(audioIn);
    clip.start();
    //clip.loop(0); // meaning no repeats
    //if(clip.isRunning()) clip.stop();*/
    
        /*String path = "";
        try {
            CodeSource codeSource = Frame.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            path = jarFile.getParentFile().getPath();
        } catch(Exception e) {e.printStackTrace();}
        path += "/Chomper/";*/
}
