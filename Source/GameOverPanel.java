import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.*;

public class GameOverPanel extends JPanel
{
    private GameWindow gameWindow;
    
    private ImageIcon playAgain;
    private ImageIcon logOut;
    private ImageIcon playAgainINV;
    private ImageIcon logOutINV;
        
    private JLabel playAgainLabel = new JLabel(playAgain);
    private JLabel logOutLabel = new JLabel(logOut);
    
    private BufferedImage background = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    
    public GameOverPanel(GameWindow gW)
    {
        gameWindow = gW;
        
        playAgain = new ImageIcon("Images/Screens and Buttons/PlayAgainButton.png");
        logOut = new ImageIcon("Images/Screens and Buttons/LogOutButton.png");
        playAgainINV = new ImageIcon("Images/Screens and Buttons/PlayAgainButtonInverse.png");
        logOutINV = new ImageIcon("Images/Screens and Buttons/LogOutButtonInverse.png");
        try {
            background = ImageIO.read(new File("Images/Screens and Buttons/GameOverScreen.png"));
        } catch(IOException e) {e.printStackTrace();}
        
        playAgainLabel.addMouseListener(new PlayAgainListener());
        logOutLabel.addMouseListener(new LogOutListener());
        
        logOutLabel.setIcon(logOut);
        playAgainLabel.setIcon(playAgain);
        
        setLayout(null);
        
        // same size & location as LogInScreen's logInLabel & signUpLabel
        playAgainLabel.setBounds(115-75, 150, 310, 60);
        logOutLabel.setBounds(115-46, 270, 252, 60);
        
        add(playAgainLabel);
        add(logOutLabel);

        Dimension panelSize = new Dimension(400, 500);
        setPreferredSize(panelSize);
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null); // (0, 0) relative to this panel
        
        // b/c I didn't know how to add the borders in photoshop...
        Color borderYellow = new Color(238, 228, 12);
        g.setColor(borderYellow);
        g.fillRect(0, 0, 5, 500);
        g.fillRect(0, 0, 400, 5);
        g.fillRect(395, 0, 5, 500);
        g.fillRect(0, 495, 400, 5);
    }
    
    private class LogOutListener implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
            gameWindow.setVisiblePanel("logout");
        }
        public void mouseEntered(MouseEvent e)
        {
            logOutLabel.setIcon(logOutINV); 
        }
        public void mouseExited(MouseEvent e)
        {
            logOutLabel.setIcon(logOut);
        }
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
    }
    
    private class PlayAgainListener implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
            gameWindow.setVisiblePanel("play");
        }
        public void mouseEntered(MouseEvent e)
        {
            playAgainLabel.setIcon(playAgainINV); 
        }
        public void mouseExited(MouseEvent e)
        {
            playAgainLabel.setIcon(playAgain);
        }
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
    }
}
