import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameWindow extends JPanel
{
    private LogInScreen logInScreen;
    private GameOverPanel gameOverPanel;
    
    private UserList userList;
    private User currentUser;
    
    private boolean startGame;
    
    private boolean isLoggedOrSignedIn;
    
    private GamePanel gamePanel; // recent addition
    
    public GameWindow(GamePanel gP)
    {
        gamePanel = gP; // recent addition
        
        logInScreen = new LogInScreen(this);
        gameOverPanel = new GameOverPanel(this);
        
        userList = new UserList(gamePanel);
        
        setLayout(null); // necessary???
        
        logInScreen.setBounds(0, 0, 400, 500); // relative to gameWindow, see GamePanel constructor
        add(logInScreen);
        logInScreen.setVisible(true);
        
        gameOverPanel.setBounds(0, 0, 500, 700);
        add(gameOverPanel);
        gameOverPanel.setVisible(false);
        
        startGame = false;
    }
    public GamePanel getGamePanel() // recent addition
    {
        return gamePanel;
    }
    
    public void save(User newUser) // adds the newUser (0 highScore) to the UserList
    {
        userList.addUser(newUser);
        currentUser = newUser;
    }
    public void save(int score) // saves new score to the UserList
    {
        if(currentUser != null)
        {
            currentUser.addGame(score);
            userList.maybeOrderList(currentUser);
        }
    }
    public void save() // writes to file
    {
        userList.save();
    }
    
    public User logIn(String name)
    {
        return userList.getUser(name);
    }
    public void setUser(User user)
    {
        currentUser = user;
    }
    
    public ArrayList<User> getScores()
    {
        return userList.getScoreList();
    }
    public User getUser()
    {
        return currentUser;
    }
    public User getNewUser(String name)
    {
        return userList.getUser(name);
    }
    
    public boolean getStartGame()
    {
        return startGame;
    }
    public void gameStarted()
    {
        startGame = false;
    }
    public void setVisiblePanel(String panelName)
    {
        switch (panelName) {
            case "play":
                gamePanel.getSidePanel().setIsACheater(false);
                gamePanel.getSidePanel().setScoreLabel(0); // is this repetitive???
                gamePanel.getSidePanel().setLevelLabel(1);
                gamePanel.getGameManager().setLives(3);
                gamePanel.getBoard().setBoardNum(1);
                gamePanel.getBoard().newLevel(1, 0);
                gameOverPanel.setVisible(false);
                logInScreen.setVisible(false);
                startGame = true;
                setVisible(false);
                break;
            case "logout":
                gamePanel.getSidePanel().setIsACheater(false);
                gamePanel.getSidePanel().setScoreLabel(0); // is this repetitive???
                gamePanel.getSidePanel().setLevelLabel(1);
                gamePanel.getGameManager().setLives(3);
                gamePanel.getBoard().setBoardNum(1);
                gamePanel.getBoard().newLevel(1, 0);
                gameOverPanel.setVisible(false);
                logInScreen.clear();
                logInScreen.setVisible(true);
                break;
            case "gameover":
                gameOverPanel.setVisible(true);
                logInScreen.setVisible(false);
                setVisible(true);
                // no save necessary here b/c saves on close (see Frame)
                break;
            default:
                break;
        }
    }
    public boolean getIsLoggedOrSignedIn()
    {
        return logInScreen.getIsLoggedOrSignedIn();
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    }
}
