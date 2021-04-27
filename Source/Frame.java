// ~4000 lines total, 91.3%

// things for 2.0 or later...
    // zip file or encrypt user data
    // case insensitive check for vulgar usernames
    // typing next I couldn't get it to pause or play beginning sound
    // it is possible to type next/other cheats when beginning sound is played after the first playing
    // add arcade mode switch boolean cheat
    // remove or set as option/cheat all extra stuff
    // have death animation with decreasing fillArc angle (sound takes 1500ms)
    // change sounds from sun to javax.sound.sampled
    // find better sounds w/o clicking or make own sounds
    // (nvm leave it to make board5 reasonable) make ghost not stop on board5
        // & shouldn't be able to get blue ghost stuch at bottom portal
    // ghosts sometimes get stuck in cycles
    // animation in draws/paintComponents
    // compile to find out what unsafe or unchecked warning is
    // have colors shift going down the board instead of all of the board
    // display number of points at site of eating of something
// create icon (w/JSmooth???)

// things to research
    // protected variables
    // final anything
    // check out javax.sound.*;
    // learn other uses of System.nanoTime()
    // learn about focus... FocusListeners???
    // frame layouts
    // JSmooth wrapping, &c.
    // .jar, .exe, .class, .java
    // thread priority
    // key bindings
    // enumeration (the interface???)
    // https://docs.oracle.com/javase/tutorial/uiswing/components/list.html
    // command line & terminal
    // event-dispatching thread
    // exceptions (& writing them)
    // try-catch-finally blocks

// things already mostly completed???
    // individual negativeTimers, eyes, & negativeBlue...
    // (probably fine) changing to board1 has causes pause (necessary??? for switching boards with levels)
    // play beginningSound every level??? (yes... maybe)
    // too many files error/exception??? for chomp & death sounds
    // pacman went through walls... now stops for a few frames???
    // maximize window, what about top 2 bars???

import javax.swing.*;
import java.awt.Toolkit;
import java.awt.*;
import java.awt.event.*;
public class Frame
{
    private static GamePanel panel;
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Chomper");
        panel = new GamePanel();
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth());
        int ySize = ((int) tk.getScreenSize().getHeight());
        frame.setSize(xSize, ySize);
        
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        //frame.setSize(800, 700);
        
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(null);
        panel.setBounds((xSize/2)-400, 0/*((ySize-something)/2)-350*/, 800, 700);
        frame.add(panel);
        
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                panel.getGameWindow().save();
            }
        } );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        frame.setResizable(false);
    }
}
