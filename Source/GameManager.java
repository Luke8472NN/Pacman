import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.*;
public class GameManager
{
    private Board board;
    
    private boolean playing = false; // both these necessary???
    private boolean gameOver = false;
    
    private int lives;
    private int level;
    private int score;
    
    private int count;
    
    private SidePanel sidePanel;
    
    public GameManager(Board b, SidePanel sP)
    {
        board = b;
        b.newLevel(1, 0);
        
        sidePanel = sP;
        
        lives = 3;
    }
    
    public void newGame()
    {
        lives = 3;
        level = 1;
        score = 0;
        sidePanel.setScoreLabel(0); // necessary???
        sidePanel.setLevelLabel(1);
        
        count = 0;
        
        board.setBoardNum(1);
        board.newLevel(1, 0);
        playing = true;
    }
    
    public void advance()
    {
        board.advance(count);
        count++;
        
        if(board.getPointsToAdd() > 0)
        {
            score += board.getPointsToAdd();
            sidePanel.setScoreLabel(score);
            board.setPointsToAdd(0);
        }
    }
    
    public void died()
    {
        if(lives > 0)
        {
            board.set(12, 26, null);
            
            lives--;
            board.newLevel(level, level);
            
            count = 0;
            
            board.setGhostSpeed(4); // necessary???
        }
        else
            endGame();
    }
    
    public void levelComplete()
    {
        count = 0; // oder matters why???
        
        board.set(12, 26, null);
        
        level++;
        sidePanel.setLevelLabel(level);
        
        if(lives < 3)
            lives++; // just because the boards are difficult
        
        board.setBoardNum(((level - 1)%5) + 1);
        board.clearBoard();
        board.newLevel(level, level - 1);
        
        board.setGhostSpeed(4);
    }
    
    public void endGame()
    {
        board.newLevel(level, level - 1);
        playing = false;
        gameOver = true;
    }
    public boolean getGameOver()
    {
        return gameOver;
    }
    public void gameEnded()
    {
        gameOver = false;
    }
    
    public boolean playerWin()
    {
        return board.playerWin();
    }
    public boolean isAlive()
    {
        return board.isAlive();
    }
    public boolean isPlaying()
    {
        return playing;
    }
    
    public int getLives()
    {
        return lives;
    }
    public int getLevel()
    {
        return level;
    }
    public int getScore()
    {
        return score;
    }
    public void setLives(int value) // ever used???
    {
        lives = value;
        sidePanel.repaint();
    }
    /*public void setLevel(int value)
    {
        level = value;
        sidePanel.setLevelLabel(value);
    }*/
    public void setScore(int value)
    {
        score = value;
        sidePanel.setScoreLabel(value);
    }
    
    /*public Board getBoard() // ever used???
    {
        return board;
    }*/
    
    public void setCount(int value)
    {
        count = value;
    }
}
