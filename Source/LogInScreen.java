import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.*;

public class LogInScreen extends JPanel
{
    private GameWindow gameWindow;
    
    private JTextField userName;
    private JPasswordField password;
    
    private ImageIcon logIn;
    private ImageIcon signUp;
    private ImageIcon logInINV;
    private ImageIcon signUpINV;
    
    private JLabel logInLabel = new JLabel(logIn);
    private JLabel signUpLabel = new JLabel(signUp);
    private JLabel userNameLabel = new JLabel("Username:");
    private JLabel passwordLabel = new JLabel("Password:");
    
    private BufferedImage backgroundImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    
    private boolean isLoggedOrSignedIn = false;
    
    public LogInScreen(GameWindow gW)
    {
        gameWindow = gW;
        setLayout(null);
        
        userName = new JTextField();
        password = new JPasswordField();
        
        logIn = new ImageIcon("Images/Screens and Buttons/LogInButton.png");
        signUp = new ImageIcon("Images/Screens and Buttons/SignupButton.png");
        logInINV = new ImageIcon("Images/Screens and Buttons/LogInButtonInverse.png");
        signUpINV = new ImageIcon("Images/Screens and Buttons/SignupButtonInverse.png");
        try {
            backgroundImage = ImageIO.read(new File("Images/Screens and Buttons/LogInScreen.png"));
        } catch(IOException e) {e.printStackTrace();}
        
        logInLabel.setBounds(30, 270, 160, 60);
        signUpLabel.setBounds(200, 270, 160, 60);
        
        logInLabel.addMouseListener(new logInListener());
        signUpLabel.addMouseListener(new signUpListener());
        
        logInLabel.setIcon(logIn);
        signUpLabel.setIcon(signUp);
        
        userNameLabel.setBounds(46, 123, 134, 54);
        userNameLabel.setForeground(Color.WHITE);
        userName.setBounds(43, 155, 306, 30);
        
        passwordLabel.setBounds(46, 167, 134, 54);
        passwordLabel.setForeground(Color.WHITE);
        password.setBounds(43, 200, 306, 30);
        
        add(logInLabel);
        add(signUpLabel);
        add(userNameLabel);
        add(passwordLabel);
        add(userName);
        add(password);
        
        Dimension panelSize = new Dimension(400, 400); // is this correct???
        setPreferredSize(panelSize);
    }
    
    public void clear()
    {
        userName.setText("");
        password.setText("");
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null); // (0, 0) relative to this panel
    }
    
    private class logInListener implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
            String name = userName.getText();
            String pwd = new String(password.getPassword());
            User user = gameWindow.logIn(name);
            
            if(user == null)
            {
                JOptionPane.showMessageDialog(null, "User Not Found!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else if(!user.getPassword().equals(pwd))
            {
                JOptionPane.showMessageDialog(null, "Invalid Password!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                gameWindow.setUser(user);
                isLoggedOrSignedIn = true;
                //hasExtraSoundYet = false;
                gameWindow.setVisiblePanel("play");
            }
        }
        public void mouseEntered(MouseEvent e)
        { 
            logInLabel.setIcon(logInINV);
        }
        public void mouseExited(MouseEvent e)
        {  
            logInLabel.setIcon(logIn);
        }
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
    }
    private class signUpListener implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
            if(userName.getText().length() > 13)
            {
                JOptionPane.showMessageDialog(null, "Invalid UserName:  User name must not contain more than 13 characters!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else if(userName.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Invalid UserName:  User name must not be blank!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else if(userName.getText().contains(" "))
            {
                JOptionPane.showMessageDialog(null, "Invalid UserName:  User name cannot contain spaces!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String name = userName.getText();
                String pwd = new String(password.getPassword());
                User user = gameWindow.logIn(name);
                
                if(user != null)
                {
                    JOptionPane.showMessageDialog(null, "User with that name already exists!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    User newUser = new User(name, pwd, 0);
                    gameWindow.save(newUser);
                    isLoggedOrSignedIn = true;
                    gameWindow.getGamePanel().getSidePanel().setHasExtraSoundYet(false);
                    gameWindow.setVisiblePanel("play");
                }
            }
        }
        public void mouseEntered(MouseEvent e)
        {
            signUpLabel.setIcon(signUpINV);  
        }
        public void mouseExited(MouseEvent e)
        {
            signUpLabel.setIcon(signUp);     
        }
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
    }
    public boolean getIsLoggedOrSignedIn()
    {
        return isLoggedOrSignedIn;
    }
}
