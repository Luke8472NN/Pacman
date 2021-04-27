import javax.swing.*;
import java.awt.*;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable
{
    private Board board;
    private Thread aThread;
    private GameWindow gameWindow = new GameWindow(this);
    private GameManager gameManager;
    private boolean keepGoing = true;
    
    private boolean isGameStarted = false;
    private SidePanel sidePanel;
    
    private boolean isPaused = false;
    
    //private ChomperBeginningSound beginning = new ChomperBeginningSound();
    //private Thread beginningThread = new Thread(beginning);
    private Thread intermissionThread;
    private ChomperIntermissionSound intermission = new ChomperIntermissionSound();
    //private Thread intermissionThread = new Thread(intermission);
    //private ChomperDeathSound chomperDeath = new ChomperDeathSound();
    //private Thread deathThread = new Thread(chomperDeath);
    
    private boolean isIntermissionPlayedYet = false;
    private Timer pauseTimer;
    
    private long startTime; // only used to test
    
    private String cheatString;
    
    public GamePanel()
    {
        board = new Board(this);
        
        addKeyListener(new Keys());
        addMouseListener(new Mouse());
        
        aThread = new Thread(this);
        aThread.start();
        
        sidePanel = new SidePanel(this);
        sidePanel.setBounds(500, 0, 300, 700);
        
        gameManager = new GameManager(board, sidePanel);
        
        setLayout(null); // necessary???
        gameWindow.setBounds(50, 100, 400, 500);
        
        add(gameWindow);
        add(sidePanel);
        
        setBackground(Color.BLACK);
        
        intermissionThread = new Thread(intermission);
        
        cheatString = "";
    }
    
    public Board getBoard()
    {
        return board;
    }
    public SidePanel getSidePanel()
    {
        return sidePanel;
    }
    public GameManager getGameManager()
    {
        return gameManager;
    }
    public GameWindow getGameWindow()
    {
        return gameWindow;
    }
    public ArrayList<User> getUserList()
    {
        return gameWindow.getScores();
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        if(isGameStarted)
            board.draw(g);
        else
            board.drawWithoutGhosts(g); // changed to without Pacman
    }
    
    public void run()
    {
        while(keepGoing)
        {
            if(!gameWindow.getIsLoggedOrSignedIn())
            {
                try {
                    Thread.sleep(25);
                    board.setWallColorsAtTime(board.getBoardColorTimeCount() + 1);
                } catch (InterruptedException e) {Thread.currentThread().interrupt();}
            }
            else
            {
                if(gameWindow.getStartGame())
                {
                    ChomperBeginningSound beginning = new ChomperBeginningSound();
                    Thread beginningThread = new Thread(beginning);
                    beginningThread.start();
                    
                    try {
                        for(int i=0; i<4500/25; i++)
                        {
                            Thread.sleep(25);
                            board.setWallColorsAtTime(board.getBoardColorTimeCount() + 1);
                            repaint();
                        }
                    } catch(InterruptedException e) {Thread.currentThread().interrupt();}
                    
                    isGameStarted = true;
                    
                    gameManager.newGame();
                    gameWindow.gameStarted();
                    
                    requestFocusInWindow(); // this is necessary for keylistener to work with this Thread
                }
                if(gameManager.isAlive() && !gameManager.playerWin() && isGameStarted)
                {
                    if(!isPaused)
                    {
                        gameManager.advance();
                        board.getPacman().incrementTimesMoved();
                        board.incrementGhostTimesMoved();
                        try {
                            for(int i=0; i<25/25; i++)
                            {
                                Thread.sleep(25);
                                board.setWallColorsAtTime(board.getBoardColorTimeCount() + 1);
                                repaint();
                            }
                        } catch(InterruptedException e) {Thread.currentThread().interrupt();}
                    }
                    else
                    {                                                                            
                        try {
                            for(int i=0; i<25/25; i++)
                            {
                                Thread.sleep(25);
                                board.setWallColorsAtTime(board.getBoardColorTimeCount() + 1);
                                repaint();
                            }
                        } catch(InterruptedException e) {Thread.currentThread().interrupt();}
                        
                        if(!isIntermissionPlayedYet)
                        {
                            intermissionThread = new Thread(intermission);
                            intermissionThread.start();
                            isIntermissionPlayedYet = true;
                            
                            pauseTimer = new Timer(5200, new ActionListener() { // close enough guess of the intermission sound's length
                                public void actionPerformed(ActionEvent e)
                                {
                                    intermissionThread = new Thread(intermission);
                                    intermissionThread.start();
                                }
                            } );
                            pauseTimer.start();
                        }
                    }
                }
                else if(!gameManager.isAlive())
                {
                    ChomperDeathSound chomperDeath = new ChomperDeathSound();
                    Thread deathThread = new Thread(chomperDeath);
                    deathThread.start();
                    
                    board.getNegativeGhostTimer().stop(); // so ghosts don't flash in the 1500ms wait below for the sound
                    
                    try {
                        for(int i=0; i<1500/25; i++)
                        {
                            Thread.sleep(25);
                            board.setWallColorsAtTime(board.getBoardColorTimeCount() + 1);
                            repaint();
                        }
                    } catch(InterruptedException e) {Thread.currentThread().interrupt();}
                    
                    gameManager.died();
                }
                else if(gameManager.playerWin())
                {
                    gameManager.levelComplete();
                    
                    try {
                        for(int i=0; i<1000/25; i++)
                        {
                            Thread.sleep(25);
                            board.setWallColorsAtTime(board.getBoardColorTimeCount() + 1);
                            repaint();
                        }
                    } catch(InterruptedException e) {Thread.currentThread().interrupt();}
                    
                    ChomperBeginningSound beginning = new ChomperBeginningSound();
                    Thread beginningThread = new Thread(beginning);
                    beginningThread.start();
                    
                    try {
                        for(int i=0; i<4500/25; i++)
                        {
                            Thread.sleep(25);
                            board.setWallColorsAtTime(board.getBoardColorTimeCount() + 1);
                            repaint();
                        }
                    } catch(InterruptedException e) {Thread.currentThread().interrupt();}
                }
                if(gameManager.getGameOver())
                {
                    gameWindow.setVisiblePanel("gameover");
                    gameManager.gameEnded();
                    
                    isGameStarted = false;
                    
                    try {
                        Thread.sleep(25);
                        board.setWallColorsAtTime(board.getBoardColorTimeCount() + 1);
                        repaint();
                    } catch (InterruptedException e) {Thread.currentThread().interrupt();}
                }
                
                
                if(gameManager.getLevel() <= 25)
                {
                    try {
                        Thread.sleep(25 - gameManager.getLevel());
                    } catch(Exception e) {e.printStackTrace();}
                }
                // else no pause here
            }
            
            repaint();
        }
    }
    
    public void addNotify()
    {
        super.addNotify();
        requestFocus();
    }
    private class Keys implements KeyListener
    {
        public void keyPressed(KeyEvent e)
        {
            int keyCode = e.getKeyCode();
            
            if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W)
            {
                if('u' == board.getPacmanDirection() && board.getPacman().getX()%20 == 0 && board.getPacman().getY()%20 == 0)
                    board.setAdvanceCount(board.getAdvanceCount() + 1);
                board.setFuturePacmanDirection('u');
                
                /*if(board.getPacmanDirection() == 'u' || board.getPacmanDirection() == 'd')
                    board.setFuturePacmanDirection('u');
                else if(board.getPacmanDirection() == 'l')
                {
                    if(!(board.get(board.getPacman().getBoardX(), board.getPacman().getBoardY() - 1) instanceof Wall) ||
                       !(board.get(board.getPacman().getBoardX() - 1, board.getPacman().getBoardY() - 1) instanceof Wall))
                        board.setFuturePacmanDirection('u');
                }
                else if(board.getPacmanDirection() == 'r')
                {
                    if(!(board.get(board.getPacman().getBoardX(), board.getPacman().getBoardY() - 1) instanceof Wall) ||
                       !(board.get(board.getPacman().getBoardX() + 1, board.getPacman().getBoardY() - 1) instanceof Wall))
                        board.setFuturePacmanDirection('u');
                }*/
            }
            else if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S)
            {
                if('d' == board.getPacmanDirection()  && board.getPacman().getX()%20 == 0 )//&& (board.getPacman().getY()%20 == 0 || board.getPacman().getY()%20 == 15 ))//|| board.getPacman().getY()%20 == 10))
                    board.setAdvanceCount(board.getAdvanceCount() + 2);
                board.setFuturePacmanDirection('d');
                
                /*if(board.getPacmanDirection() == 'u' || board.getPacmanDirection() == 'd')
                    board.setFuturePacmanDirection('d');
                else if(board.getPacmanDirection() == 'l')
                {
                    if(!(board.get(board.getPacman().getBoardX(), board.getPacman().getBoardY() + 1) instanceof Wall) ||
                       !(board.get(board.getPacman().getBoardX() - 1, board.getPacman().getBoardY() + 1) instanceof Wall))
                        board.setFuturePacmanDirection('d');
                }
                else if(board.getPacmanDirection() == 'r')
                {
                    if(!(board.get(board.getPacman().getBoardX(), board.getPacman().getBoardY() + 1) instanceof Wall) ||
                       !(board.get(board.getPacman().getBoardX() + 1, board.getPacman().getBoardY() + 1) instanceof Wall))
                        board.setFuturePacmanDirection('d');
                }*/
            }
            else if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)
            {
                if('l' == board.getPacmanDirection() && board.getPacman().getX()%20 == 0 && board.getPacman().getY()%20 == 0)
                    board.setAdvanceCount(board.getAdvanceCount() + 1);
                board.setFuturePacmanDirection('l');
                
                /*if(board.getPacmanDirection() == 'l' || board.getPacmanDirection() == 'r')
                    board.setFuturePacmanDirection('l');
                else if(board.getPacmanDirection() == 'u')
                {
                    if(!(board.get(board.getPacman().getBoardX() - 1, board.getPacman().getBoardY()) instanceof Wall) ||
                       !(board.get(board.getPacman().getBoardX() - 1, board.getPacman().getBoardY() - 1) instanceof Wall))
                        board.setFuturePacmanDirection('l');
                }
                else if(board.getPacmanDirection() == 'd')
                {
                    if(!(board.get(board.getPacman().getBoardX() - 1, board.getPacman().getBoardY()) instanceof Wall) ||
                       !(board.get(board.getPacman().getBoardX() - 1, board.getPacman().getBoardY() + 1) instanceof Wall))
                        board.setFuturePacmanDirection('l');
                }*/
            }
            else if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D)
            {
                if('r' == board.getPacmanDirection() && board.getPacman().getY()%20 == 0 )//&& (board.getPacman().getX()%20 == 0 || board.getPacman().getX()%20 == 15 ))//|| board.getPacman().getY()%20 == 10))
                    board.setAdvanceCount(board.getAdvanceCount() + 2);
                board.setFuturePacmanDirection('r');
                
                /*if(board.getPacmanDirection() == 'l' || board.getPacmanDirection() == 'r')
                    board.setFuturePacmanDirection('r');
                else if(board.getPacmanDirection() == 'u')
                {
                    if(!(board.get(board.getPacman().getBoardX() + 1, board.getPacman().getBoardY()) instanceof Wall) ||
                       !(board.get(board.getPacman().getBoardX() + 1, board.getPacman().getBoardY() - 1) instanceof Wall))
                        board.setFuturePacmanDirection('r');
                }
                else if(board.getPacmanDirection() == 'd')
                {
                    if(!(board.get(board.getPacman().getBoardX() + 1, board.getPacman().getBoardY()) instanceof Wall) ||
                       !(board.get(board.getPacman().getBoardX() + 1, board.getPacman().getBoardY() + 1) instanceof Wall))
                        board.setFuturePacmanDirection('r');
                }*/
            }
            else if(keyCode == KeyEvent.VK_P)
            {
                if(isPaused)
                {
                    intermission.stopSound();
                    pauseTimer.stop();
                    isPaused = false;
                    board.restartNegativeGhostTimer(); // resart with 500ms delay each time best???
                }
                else
                {
                    isIntermissionPlayedYet = false;
                    isPaused = true;
                    board.getNegativeGhostTimer().stop();
                }
            }
            else if(keyCode >= 65 && keyCode <= 90 && keyCode != 80) // a to z no p (but wsad is also used...)
            {
                if(gameWindow.getIsLoggedOrSignedIn() && gameManager.isAlive() && !gameManager.playerWin() && isGameStarted)
                // all conditions here necessary???
                {
                    cheatString += e.getKeyChar();
                    checkStringForCheats(cheatString);
                }
            }
        }
        public void keyTyped(KeyEvent e)
        {
            
        }
        public void keyReleased(KeyEvent e)
        {
            
        }
    }
    public void checkStringForCheats(String string)
    {
        if(string.length() >= 4 && string.substring(string.length() - 4).equals("more"))
        {
            cheatString = "";
            gameManager.setScore(gameManager.getScore() + 1000);
            sidePanel.setIsACheater(true);
        }
        else if(string.length() >= 4 && string.substring(string.length() - 4).equals("next"))
        {
            cheatString = "";
            gameManager.levelComplete();
            sidePanel.setIsACheater(true);
            
            // maybe a variation of what's below???
            /*gameManager.setCount(0);
            
            board.setBoardNum(1);
            board.clearBoard();
            board.createBoard();
            gameManager.setScore(0);*/
        }
        else if(string.length() >= 4 && string.substring(string.length() - 4).equals("life"))
        {
            cheatString = "";
            if(gameManager.getLives() < 3)
            {
                gameManager.setLives(gameManager.getLives() + 1);
                sidePanel.setIsACheater(true);
            }
        }
    }
    
    private class Mouse implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX();
            int y = e.getY();
            
            if(x<510 || x>10+780 || y<215 || y>215+455) // outside of the JScrollPane // is this actually precise???
                addNotify();
        }
        public void mouseEntered(MouseEvent e)
        {
            
        }
        public void mouseExited(MouseEvent e)
        {
            
        }
        public void mousePressed(MouseEvent e)
        {
            
        }
        public void mouseReleased(MouseEvent e)
        {
            
        }
    }
}
