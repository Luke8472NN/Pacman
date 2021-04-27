import java.awt.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.awt.event.ActionListener;
import javax.swing.Timer;
//import java.util.Timer; // wrong Timer class...
import java.awt.event.*;
public class Board
{
    private GameObject[][] board;
    private Pacman p;
    private RedGhost rG;
    private PinkGhost pG;
    private OrangeGhost oG;
    private BlueGhost bG;
    
    private int pointsToAdd = 0;
    
    private int advanceCount = 0;
    
    //private boolean isAlive = true; // correct initial setting??? // see method doesn't use this variable
    
    private int pacmanSpeed = 5;
    //private int ghostSpeed = 4;
    
    private int boardNum = 1;
    
    private int negativeGhostTimeCount = 0;
    private Timer negativeGhostTimer = new Timer(500, new TimerListener());
    
    private int boardColorTimeCount = 0;
    private Timer boardColorTimer = new Timer(50, new TimerListener2());
    
    private GamePanel gamePanel;
    
    private ChomperChompSound chomperChompSound = new ChomperChompSound();
    
    public Board(GamePanel gP)
    {
        gamePanel = gP;
        
        board = new GameObject[25][35];
        createBoard();
        
        boardColorTimer.start();
    }
    public void createBoard()
    {
        BufferedReader reader;
        String line;
        int lineCount = 0;
        try
        {
            reader = new BufferedReader(new FileReader("Text Files/Board" + Integer.toString(boardNum) + ".txt"));
            while((line=reader.readLine()) != null)
            {
                String[] boxes = line.split("\\s");
                for(int i = 0; i < boxes.length; i++)
                {
                    if(boxes[i].equals("r"))
                    {
                        rG = new RedGhost(20*i, 20*lineCount, 'l');
                        //board[i][lineCount] = rG;
                    }
                    else if(boxes[i].equals("b"))
                    {
                        bG = new BlueGhost(20*i, 20*lineCount-10, 'u');
                        //board[i][lineCount] = bG;
                    }
                    else if(boxes[i].equals("p"))
                    {
                        pG = new PinkGhost(20*i, 20*lineCount-10, 'u');
                        //board[i][lineCount] = pG;
                    }
                    else if(boxes[i].equals("o"))
                    {
                        oG = new OrangeGhost(20*i, 20*lineCount-10, 'u');
                        //board[i][lineCount] = oG;
                    }
                    else if(Integer.parseInt(boxes[i]) == 0)
                    {
                        Pellet pel = new Pellet(20*i, 20*lineCount, Color.WHITE);
                        board[i][lineCount] = pel;
                    }
                    else if(Integer.parseInt(boxes[i]) == 1)
                    {
                        Wall w = new Wall(20*i, 20*lineCount, Color.BLUE);
                        board[i][lineCount] = w;
                    }
                    else if(Integer.parseInt(boxes[i]) == 2) // open space
                    {
                        /*Wall w = new Wall(20*i, 20*lineCount, Color.ORANGE);
                        board[i][lineCount] = w;*/
                    }
                    else if(Integer.parseInt(boxes[i]) == 3)
                    {
                        p = new Pacman(20*i, 20*lineCount, Color.YELLOW, 'l');
                        //board[i][lineCount] = p;
                    }
                    else if(Integer.parseInt(boxes[i]) == 4)
                    {
                        LargePellet lPel = new LargePellet(20*i, 20*lineCount, Color.WHITE);
                        board[i][lineCount] = lPel;
                    }
                }
                
                lineCount++; 
            }
        }
        catch(IOException e) {}
    }
    public void clearBoard()
    {
        for(int i=0; i<25; i++)
        {
            for(int j=0; j<35; j++)
            {
                board[i][j] = null;
            }
        }
    }
    
    public void newLevel(int level, int prevLevel)
    {
        if(level == prevLevel)
        {
            BufferedReader reader;
            String line;
            int lineCount = 0;
            try
            {
                reader = new BufferedReader(new FileReader("Text Files/Board" + Integer.toString(boardNum) + ".txt"));
                while((line=reader.readLine()) != null)
                {
                    String[] boxes = line.split("\\s");
                    for(int i = 0; i < boxes.length; i++)
                    {
                        if(boxes[i].equals("r"))
                        {
                            rG = new RedGhost(20*i, 20*lineCount, 'l');
                            //board[i][lineCount] = rG;
                        }
                        else if(boxes[i].equals("b"))
                        {
                            bG = new BlueGhost(20*i, 20*lineCount-10, 'u');
                            //board[i][lineCount] = bG;
                        }
                        else if(boxes[i].equals("p"))
                        {
                            pG = new PinkGhost(20*i, 20*lineCount-10, 'u');
                            //board[i][lineCount] = pG;
                        }
                        else if(boxes[i].equals("o"))
                        {
                            oG = new OrangeGhost(20*i, 20*lineCount-10, 'u');
                            //board[i][lineCount] = oG;
                        }
                        
                        else if(Integer.parseInt(boxes[i]) == 3)
                        {
                            p = new Pacman(20*i, 20*lineCount, Color.YELLOW, 'l');
                            //board[i][lineCount] = p;
                        }
                    }
                    
                    lineCount++; 
                }
            }
            catch(IOException e) {}
        }
        else
        {
            createBoard(); // is this correct???
        }
    }
    public boolean isAlive()
    {
        double distFromRedGhost = Math.sqrt(Math.pow((p.getX()-rG.getX()), 2) + Math.pow((p.getY()-rG.getY()), 2));
        double distFromPinkGhost = Math.sqrt(Math.pow((p.getX()-pG.getX()), 2) + Math.pow((p.getY()-pG.getY()), 2));
        double distFromOrangeGhost = Math.sqrt(Math.pow((p.getX()-oG.getX()), 2) + Math.pow((p.getY()-oG.getY()), 2));
        double distFromBlueGhost = Math.sqrt(Math.pow((p.getX()-bG.getX()), 2) + Math.pow((p.getY()-bG.getY()), 2));
        
        if(distFromRedGhost < 20)
        {
            if(rG.getIsNegative())
            {
                rG.setIsNegative(false);
                rG.setIsBlueNegative(true);
                rG.setIsDead(true);
                rG.setGhostSpeed(4);
                addPointsForEatingGhost();
                
                ChomperEatGhostSound eatGhost = new ChomperEatGhostSound();
                Thread eatGhostThread = new Thread(eatGhost);
                eatGhostThread.start();
                
                negativeGhostTimer.stop();
                try {
                    for(int i=0; i<1000/25; i++) // correct sleep???, do the ghosts flash before dying???
                    {
                        Thread.sleep(25);
                        setWallColorsAtTime(getBoardColorTimeCount() + 1);
                        gamePanel.repaint();
                    }
                } catch(InterruptedException e) {Thread.currentThread().interrupt();}
                negativeGhostTimer = new Timer(500, new TimerListener());
                negativeGhostTimer.start();
            }
            else if(!rG.getIsDead())
            {
                return false;
            }
        }
        
        if(distFromPinkGhost < 20)
        {
            if(pG.getIsNegative())
            {
                pG.setIsNegative(false);
                pG.setIsBlueNegative(true);
                pG.setIsDead(true);
                pG.setGhostSpeed(4);
                addPointsForEatingGhost();
                
                ChomperEatGhostSound eatGhost = new ChomperEatGhostSound();
                Thread eatGhostThread = new Thread(eatGhost);
                eatGhostThread.start();
                
                negativeGhostTimer.stop();
                try {
                    for(int i=0; i<1000/25; i++) // correct sleep???, do the ghosts flash before dying???
                    {
                        Thread.sleep(25);
                        setWallColorsAtTime(getBoardColorTimeCount() + 1);
                        gamePanel.repaint();
                    }
                } catch(InterruptedException e) {Thread.currentThread().interrupt();}
                negativeGhostTimer = new Timer(500, new TimerListener());
                negativeGhostTimer.start();
            }
            else if(!pG.getIsDead())
            {
                return false;
            }
        }
        
        if(distFromOrangeGhost < 20)
        {
            if(oG.getIsNegative())
            {
                oG.setIsNegative(false);
                oG.setIsBlueNegative(true);
                oG.setIsDead(true);
                oG.setGhostSpeed(4);
                addPointsForEatingGhost();
                
                ChomperEatGhostSound eatGhost = new ChomperEatGhostSound();
                Thread eatGhostThread = new Thread(eatGhost);
                eatGhostThread.start();
                
                negativeGhostTimer.stop();
                try {
                    for(int i=0; i<1000/25; i++) // correct sleep???, do the ghosts flash before dying???
                    {
                        Thread.sleep(25);
                        setWallColorsAtTime(getBoardColorTimeCount() + 1);
                        gamePanel.repaint();
                    }
                } catch(InterruptedException e) {Thread.currentThread().interrupt();}
                negativeGhostTimer = new Timer(500, new TimerListener());
                negativeGhostTimer.start();
            }
            else if(!oG.getIsDead())
            {
                return false;
            }
        }
        
        if(distFromBlueGhost < 20)
        {
            if(bG.getIsNegative())
            {
                bG.setIsNegative(false);
                bG.setIsBlueNegative(true);
                bG.setIsDead(true);
                bG.setGhostSpeed(4);
                addPointsForEatingGhost();
                
                ChomperEatGhostSound eatGhost = new ChomperEatGhostSound();
                Thread eatGhostThread = new Thread(eatGhost);
                eatGhostThread.start();
                
                negativeGhostTimer.stop();
                try {
                    for(int i=0; i<1000/25; i++) // correct sleep???, do the ghosts flash before dying???
                    {
                        Thread.sleep(25);
                        setWallColorsAtTime(getBoardColorTimeCount() + 1);
                        gamePanel.repaint();
                    }
                } catch(InterruptedException e) {Thread.currentThread().interrupt();}
                negativeGhostTimer = new Timer(500, new TimerListener());
                negativeGhostTimer.start();
            }
            else if(!bG.getIsDead())
            {
                return false;
            }
        }
        
        return true;
    }
    public void addPointsForEatingGhost()
    {
        int negativeGhostsEaten = 0;
        if(rG.getIsDead())
            negativeGhostsEaten++;
        if(pG.getIsDead())
            negativeGhostsEaten++;
        if(oG.getIsDead())
            negativeGhostsEaten++;
        if(bG.getIsDead())
            negativeGhostsEaten++;
        gamePanel.getGameManager().setScore(gamePanel.getGameManager().getScore() + (int) (100*Math.pow(2, negativeGhostsEaten)));
    }
    public boolean playerWin()
    {
        int dotCount = 0;
        for(int i=0; i<25; i++)
        {
            for(int j=0; j<35; j++)
            {
                if(board[i][j] instanceof Pellet || board[i][j] instanceof LargePellet)
                    dotCount++;
            }
        }
        
        if(dotCount == 0)
            return true;
        else
            return false;
    }
    
    public GameObject get(int boardX, int boardY)
    {
        if(boardX>=0 && boardX<board.length && boardY>=0 && boardY<board[0].length)
            return board[boardX][boardY];
        else
            return null;
    }
    public GameObject[] get(int boardX) // returns the column, necessary???
    {
        if(boardX>=0 && boardX<board.length)
            return board[boardX];
        else
            return null;
    }
    public void set(int boardX, int boardY, GameObject gameObject)
    {
        if(boardX>=0 && boardX<board.length && boardY>=0 && boardY<board[0].length)
            board[boardX][boardY] = gameObject;
        // else what???
    }
    public Pacman getPacman()
    {
        return p;
    }
    
    public void advance(int count)
    {
        movePacman();
        
        if(count > 230 && board[12][26] == null)
        {
            int rand = (int) (50000*Math.random());
            if(rand < 40)
                board[12][26] = new Cherry(240, 520);
            else if(rand >= 40 && rand < 70)
                board[12][26] = new Strawberry(240, 520);
            else if(rand >= 70 && rand < 90)
                board[12][26] = new Pear(240, 520);
            else if(rand >= 90 && rand < 100)
                board[12][26] = new Banana(240, 520);
        }
        
        
        if(rG.getIsDead() == true && rG.getX() == 240 && rG.getY() == 360)
            rG.setIsDead(false);
        if(bG.getIsDead() == true && bG.getX() == 240 && bG.getY() == 360)
            bG.setIsDead(false);
        if(pG.getIsDead() == true && pG.getX() == 240 && pG.getY() == 360)
            pG.setIsDead(false);
        if(oG.getIsDead() == true && oG.getX() == 240 && oG.getY() == 360)
            oG.setIsDead(false);
        
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            // invokeAndWait with a necessary try-catch loop seemed better with the tested printed lines, but invokeLater is noticeably faster
            public void run() {
                if(count == 0 || count == 1)
                {
                    rG.setX(rG.getX() - rG.getGhostSpeed());
                    rG.setDirection('l');
                    
                    bG.setY(bG.getY() + 5);
                    bG.setDirection('d');
                    pG.setY(pG.getY() - 5);
                    pG.setDirection('u');
                    oG.setY(oG.getY() + 5);
                    oG.setDirection('d');
                }
                else if(count >= 2 && count <= 5)
                {
                    moveGhost(rG);
                    bG.setY(bG.getY() - 5);
                    bG.setDirection('u');
                    pG.setY(pG.getY() - 5);
                    pG.setDirection('u');
                    oG.setY(oG.getY() - 5);
                    oG.setDirection('u');
                }
                else if(count >= 6 && count <= 9)
                {
                    moveGhost(rG);
                    bG.setY(bG.getY() + 5);
                    bG.setDirection('d');
                    pG.setY(pG.getY() - 5);
                    pG.setDirection('u');
                    oG.setY(oG.getY() + 5);
                    oG.setDirection('d');
                }
                else if(count == 10)
                {
                    moveGhost(rG);
                    bG.setY(bG.getY() - 5);
                    bG.setDirection('u');
                    pG.setX(pG.getX() - pG.getGhostSpeed());
                    pG.setDirection('l');
                    oG.setY(oG.getY() - 5);
                    oG.setDirection('u');
                }
                else if(count >= 11 && count <= 13)
                {
                    moveGhost(rG);
                    bG.setY(bG.getY() - 5);
                    bG.setDirection('u');
                    moveGhost(pG);
                    oG.setY(oG.getY() - 5);
                    oG.setDirection('u');
                }
                else if(count >= 14 && count <= 17+8*6+4)
                {
                    moveGhost(rG);
                    moveGhost(pG);
                    if(((count-14)/4)%2 == 0)
                    {
                        bG.setY(bG.getY() + 5);
                        bG.setDirection('d');
                        oG.setY(oG.getY() + 5);
                        oG.setDirection('d');
                    }
                    else
                    {
                        bG.setY(bG.getY() - 5);
                        bG.setDirection('u');
                        oG.setY(oG.getY() - 5);
                        oG.setDirection('u');
                    }
                }
                //supposed to be 9 then 18 bounces on the top of the box
                else if(count >= 70 && count <= 73)
                {
                    moveGhost(rG);
                    moveGhost(pG);
                    bG.setX(bG.getX() + 5);
                    bG.setDirection('r');
                    oG.setY(oG.getY() + 5);
                    oG.setDirection('d');
                }
                else if(count >= 74 && count <= 77)
                {
                    moveGhost(rG);
                    moveGhost(pG);
                    bG.setY(bG.getY() - 5);
                    bG.setDirection('u');
                    oG.setY(oG.getY() - 5);
                    oG.setDirection('u');
                }
                else if(count >= 78 && count <= 81)
                {
                    moveGhost(rG);
                    moveGhost(pG);
                    bG.setY(bG.getY() - 5);
                    bG.setDirection('u');
                    oG.setY(oG.getY() + 5);
                    oG.setDirection('d');
                }
                else if(count >= 82 && count <= 85+8*17)
                {
                    moveGhost(rG);
                    moveGhost(pG);
                    moveGhost(bG);
                    if(((count-82)/4)%2 == 0)
                    {
                        oG.setY(oG.getY() - 5);
                        oG.setDirection('u');
                    }
                    else
                    {
                        oG.setY(oG.getY() + 5);
                        oG.setDirection('d');
                    }
                }
                else if(count >= 222 && count <= 225)
                {
                    moveGhost(rG);
                    moveGhost(pG);
                    moveGhost(bG);
                    oG.setX(oG.getX() - 5);
                    oG.setDirection('l');
                }
                else if(count >= 226 && count <= 229)
                {
                    moveGhost(rG);
                    moveGhost(pG);
                    moveGhost(bG);
                    oG.setY(oG.getY() - 5);
                    oG.setDirection('u');
                }
                else
                {
                    moveGhost(rG);
                    moveGhost(pG);
                    moveGhost(bG);
                    moveGhost(oG);
                }
            }
        } );
    }
    public void incrementGhostTimesMoved()
    {
        rG.incrementTimesMoved();
        bG.incrementTimesMoved();
        pG.incrementTimesMoved();
        oG.incrementTimesMoved();
    }
    public char getPacmanDirection()
    {
        return p.getDirection();
    }
    public void setPacmanDirection(char dir)
    {
        p.setDirection(dir);
    }
    public void setFuturePacmanDirection(char futureDir)
    {
        p.setFutureDirection(futureDir);
    }
    public int getAdvanceCount()
    {
        return advanceCount;
    }
    public void setAdvanceCount(int value)
    {
        advanceCount = value;
    }
    public void movePacman()
    {
        if(p.getX() == 0)
        {
            p.setX(480 - pacmanSpeed);
            maybeEat();
            return;
        }
        else if(p.getX() == 480-3*pacmanSpeed || p.getX() == 480-2*pacmanSpeed || p.getX() == 480-pacmanSpeed)
        {
            if(p.getDirection() == 'l')
            {
                p.setX(p.getX() - pacmanSpeed);
                if((p.getY()%20 == 0) && (board[p.getBoardX() - 1][p.getBoardY()] instanceof Wall) && (advanceCount == 0))
                    advanceCount++;
            }
            else if(p.getDirection() == 'r')
            {
                p.setX(p.getX() + pacmanSpeed);
            }
            maybeEat();
            return;
        }
        else if(p.getX() == 480)
        {
            p.setX(0 + pacmanSpeed);
            maybeEat();
            return;
        }
        
        if(p.getY() == pacmanSpeed || p.getY() == 2*pacmanSpeed || p.getY() == 3*pacmanSpeed)
        {
            if(p.getDirection() == 'u')
            {
                p.setY(p.getY() - pacmanSpeed);
            }
            else if(p.getDirection() == 'd')
            {
                p.setY(p.getY() + pacmanSpeed);
                if((p.getX()%20 == 0) && (board[p.getBoardX()][p.getBoardY() + 1] instanceof Wall) && (advanceCount == 0))
                    advanceCount+=2;
            }
            maybeEat();
            return;
        }
        else if(p.getY() == 0)
        {
            p.setY(680 - pacmanSpeed);
            maybeEat();
            return;
        }
        else if(p.getY() == 680-3*pacmanSpeed || p.getY() == 680-2*pacmanSpeed || p.getY() == 680-pacmanSpeed)
        {
            if(p.getDirection() == 'u')
            {
                p.setY(p.getY() - pacmanSpeed);
            }
            else if(p.getDirection() == 'd')
            {
                p.setY(p.getY() + pacmanSpeed);
            }
            maybeEat();
            return;
        }
        else if(p.getY() == 680)
        {
            p.setY(0 + pacmanSpeed);
            maybeEat();
            return;
        }
        
        
        
        //System.out.println("before" + advanceCount + ", " + p.getX() + ", " + p.getY() + ", " + p.getDirection() + ", " + p.getFutureDirection());
        
        if(p.getFutureDirection() == 'u')
        {
            if((p.getX()%20 == 0) && !(board[p.getBoardX()][p.getBoardY() - 1] instanceof Wall))
            {
                p.setY(p.getY() - pacmanSpeed);
                p.setDirection('u');
                advanceCount = 0;
            }
            else if(!(p.getX()%20 == 0))
            {
                if(p.getDirection() == 'l')
                    p.setX(p.getX() - pacmanSpeed);
                else if(p.getDirection() == 'r')
                    p.setX(p.getX() + pacmanSpeed);
            }
            else if((p.getX()%20 == 0) && (board[p.getBoardX()][p.getBoardY() - 1] instanceof Wall))
            {
                if(p.getDirection() == 'u' && ( !(board[p.getBoardX()][p.getBoardY() - 1] instanceof Wall) || (advanceCount == 0) ))
                {
                    p.setY(p.getY() - pacmanSpeed);
                    advanceCount++;
                }
                else if(p.getDirection() == 'd' && !(board[p.getBoardX()][p.getBoardY() + 1] instanceof Wall))
                    p.setY(p.getY() + pacmanSpeed);
                else if(p.getDirection() == 'l' && !(board[p.getBoardX() - 1][p.getBoardY()] instanceof Wall))
                    p.setX(p.getX() - pacmanSpeed);
                else if(p.getDirection() == 'r' && !(board[p.getBoardX() + 1][p.getBoardY()] instanceof Wall))
                    p.setX(p.getX() + pacmanSpeed);
            }
        }
        else if(p.getFutureDirection() == 'd')
        {
            if((p.getX()%20 == 0) && !(board[p.getBoardX()][p.getBoardY() + 1] instanceof Wall))
            {
                p.setY(p.getY() + pacmanSpeed);
                p.setDirection('d');
                advanceCount = 0;
            }
            else if(!(p.getX()%20 == 0))
            {
                if(p.getDirection() == 'l')
                    p.setX(p.getX() - pacmanSpeed);
                else if(p.getDirection() == 'r')
                    p.setX(p.getX() + pacmanSpeed);
            }
            else if((p.getX()%20 == 0) && (board[p.getBoardX()][p.getBoardY() + 1] instanceof Wall))
            {
                if(p.getDirection() == 'u' && !(board[p.getBoardX()][p.getBoardY() - 1] instanceof Wall))
                    p.setY(p.getY() - pacmanSpeed);
                else if(p.getDirection() == 'd' && ( !(board[p.getBoardX()][p.getBoardY() + 1] instanceof Wall) || (advanceCount <= 1) ))
                {
                    p.setY(p.getY() + pacmanSpeed);
                    advanceCount++;
                }
                else if(p.getDirection() == 'l' && !(board[p.getBoardX() - 1][p.getBoardY()] instanceof Wall))
                    p.setX(p.getX() - pacmanSpeed);
                else if(p.getDirection() == 'r' && !(board[p.getBoardX() + 1][p.getBoardY()] instanceof Wall))
                    p.setX(p.getX() + pacmanSpeed);
            }
        }
        else if(p.getFutureDirection() == 'l')
        {
            if((p.getY()%20 == 0) && ( (p.getBoardX() < 1) || !(board[p.getBoardX() - 1][p.getBoardY()] instanceof Wall) ))
            // added stuff here...
            {
                p.setX(p.getX() - pacmanSpeed);
                p.setDirection('l');
                advanceCount = 0;
            }
            else if(!(p.getY()%20 == 0))
            {
                if(p.getDirection() == 'u')
                    p.setY(p.getY() - pacmanSpeed);
                else if(p.getDirection() == 'd')
                    p.setY(p.getY() + pacmanSpeed);
            }
            else if((p.getY()%20 == 0) && (board[p.getBoardX() - 1][p.getBoardY()] instanceof Wall))
            {
                if(p.getDirection() == 'u' && !(board[p.getBoardX()][p.getBoardY() - 1] instanceof Wall))
                    p.setY(p.getY() - pacmanSpeed);
                else if(p.getDirection() == 'd' && !(board[p.getBoardX()][p.getBoardY() + 1] instanceof Wall))
                    p.setY(p.getY() + pacmanSpeed);
                else if(p.getDirection() == 'l' && ( !(board[p.getBoardX() - 1][p.getBoardY()] instanceof Wall) || (advanceCount == 0) ))
                {
                    p.setX(p.getX() - pacmanSpeed);
                    advanceCount++;
                }
                else if(p.getDirection() == 'r' && !(board[p.getBoardX() + 1][p.getBoardY()] instanceof Wall))
                    p.setX(p.getX() + pacmanSpeed);
            }
        }
        else if(p.getFutureDirection() == 'r')
        {
            if((p.getY()%20 == 0) && ( (p.getBoardX() > 25) || !(board[p.getBoardX() + 1][p.getBoardY()] instanceof Wall) ))
            // added stuff here...
            {
                p.setX(p.getX() + pacmanSpeed);
                p.setDirection('r');
                advanceCount = 0;
            }
            else if(!(p.getY()%20 == 0))
            {
                if(p.getDirection() == 'u')
                    p.setY(p.getY() - pacmanSpeed);
                else if(p.getDirection() == 'd')
                    p.setY(p.getY() + pacmanSpeed);
            }
            else if((p.getY()%20 == 0) && (board[p.getBoardX() + 1][p.getBoardY()] instanceof Wall))
            {
                if(p.getDirection() == 'u' && !(board[p.getBoardX()][p.getBoardY() - 1] instanceof Wall))
                    p.setY(p.getY() - pacmanSpeed);
                else if(p.getDirection() == 'd' && !(board[p.getBoardX()][p.getBoardY() + 1] instanceof Wall))
                    p.setY(p.getY() + pacmanSpeed);
                else if(p.getDirection() == 'l' && !(board[p.getBoardX() - 1][p.getBoardY()] instanceof Wall))
                    p.setX(p.getX() - pacmanSpeed);
                else if(p.getDirection() == 'r' && ( !(board[p.getBoardX() + 1][p.getBoardY()] instanceof Wall) || (advanceCount <= 1) ))
                {
                    p.setX(p.getX() + pacmanSpeed);
                    advanceCount++;
                }
            }
        }
        
        maybeEat();
    }
    public void setGhostSpeed(int value)
    {
        rG.setGhostSpeed(value);
        pG.setGhostSpeed(value);
        oG.setGhostSpeed(value);
        bG.setGhostSpeed(value);
    }
    public void maybeEat()
    {
        if(p.getX()%20 == 0 && p.getY()%20 == 0)
        {
            if(board[p.getBoardX()][p.getBoardY()] instanceof Pellet)
            {
                //ChomperChompSound chomperChompSound = new ChomperChompSound(); // is it possible to make this & the same below a class variable???
                                                                               // so this is fast enough not to have color prolems???
                Thread chompThread = new Thread(chomperChompSound);
                chompThread.start();
                
                setPointsToAdd(getPointsToAdd() + 10);
                board[p.getBoardX()][p.getBoardY()] = null;
            }
            else if(board[p.getBoardX()][p.getBoardY()] instanceof LargePellet)
            {
                rG.setIsNegative(true);
                pG.setIsNegative(true);
                oG.setIsNegative(true);
                bG.setIsNegative(true);
                
                if(!rG.getIsDead())
                    rG.setGhostSpeed(2);
                if(!pG.getIsDead())
                    pG.setGhostSpeed(2);
                if(!oG.getIsDead())
                    oG.setGhostSpeed(2);
                if(!bG.getIsDead())
                    bG.setGhostSpeed(2);
                
                rG.setIsBlueNegative(true);
                pG.setIsBlueNegative(true);
                oG.setIsBlueNegative(true);
                bG.setIsBlueNegative(true);
                
                negativeGhostTimer.start();
                negativeGhostTimeCount = 0;
                
                //ChomperChompSound chomperChompSound = new ChomperChompSound(); // so this is fast enough not to have color prolems???
                Thread chompThread = new Thread(chomperChompSound);
                chompThread.start();
                
                setPointsToAdd(getPointsToAdd() + 50);
                board[p.getBoardX()][p.getBoardY()] = null;
            }
            else if(board[p.getBoardX()][p.getBoardY()] instanceof Fruit)
            {
                ChomperEatFruitSound chomperEatFruitSound = new ChomperEatFruitSound(); // so this is fast enough not to have color prolems???
                Thread eatFruitThread = new Thread(chomperEatFruitSound);
                eatFruitThread.start();
                
                if(board[p.getBoardX()][p.getBoardY()] instanceof Cherry)
                    setPointsToAdd(getPointsToAdd() + 100);
                else if(board[p.getBoardX()][p.getBoardY()] instanceof Strawberry)
                    setPointsToAdd(getPointsToAdd() + 200);
                else if(board[p.getBoardX()][p.getBoardY()] instanceof Pear)
                    setPointsToAdd(getPointsToAdd() + 400);
                else if(board[p.getBoardX()][p.getBoardY()] instanceof Banana)
                    setPointsToAdd(getPointsToAdd() + 800);
                
                board[p.getBoardX()][p.getBoardY()] = null;
            }
        }
    }
    /*public int getNegativeGhostTimeCount()
    {
        return negativeGhostTimeCount;
    }
    public void setNegativeGhostTimeCount(int value)
    {
        negativeGhostTimeCount = value; // set to 0
    }*/
    
    public Timer getNegativeGhostTimer()
    {
        return negativeGhostTimer;
    }
    public void restartNegativeGhostTimer()
    {
        negativeGhostTimer = new Timer(500, new TimerListener());
        negativeGhostTimer.setInitialDelay(500);
        negativeGhostTimer.start();
    }
    private class TimerListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) // called every 500ms
        {
            negativeGhostTimeCount++;
            
            if(negativeGhostTimeCount >= 30) //returns to normal colors after 15 seconds total
            {
                rG.setIsBlueNegative(true);
                pG.setIsBlueNegative(true);
                oG.setIsBlueNegative(true);
                bG.setIsBlueNegative(true);
                
                rG.setIsNegative(false);
                pG.setIsNegative(false);
                oG.setIsNegative(false);
                bG.setIsNegative(false);
                
                rG.setGhostSpeed(4);
                pG.setGhostSpeed(4);
                oG.setGhostSpeed(4);
                bG.setGhostSpeed(4);
                
                negativeGhostTimeCount = 0;
                
                negativeGhostTimer.stop();
            }
            else if(negativeGhostTimeCount >= 20) // starts switching after 10 seconds
            {
                rG.setIsBlueNegative(negativeGhostTimeCount%2 == 1);
                pG.setIsBlueNegative(negativeGhostTimeCount%2 == 1);
                oG.setIsBlueNegative(negativeGhostTimeCount%2 == 1);
                bG.setIsBlueNegative(negativeGhostTimeCount%2 == 1);
            }
        }
    }
    private class TimerListener2 implements ActionListener
    {
        public void actionPerformed(ActionEvent e) // called every 25ms
        {
            boardColorTimeCount = (boardColorTimeCount + 1)%(6*51);
            
            setWallColorsAtTime(boardColorTimeCount);
        }
    }
    public int getBoardColorTimeCount()
    {
        return boardColorTimeCount;
    }
    public void setBoardColorTimeCount(int value)
    {
        boardColorTimeCount = value;
    }
    public void setWallColorsAtTime(int timeCount)
    {
        timeCount = timeCount%(6*51);
        boardColorTimeCount = timeCount;
        
        int r, g, b;
        
        if(timeCount <= 51)
        {
            r = 5*(timeCount%51);
            if(r == 0 && timeCount != 0)
                r = 255;
            g = 0;
            b = 255;
        }
        else if(timeCount <= 2*51)
        {
            r = 255;
            g = 0;
            b = 255-5*(timeCount%51);
            if(b == 255)
                b = 0;
        }
        else if(timeCount <= 3*51)
        {
            r = 255;
            g = 5*(timeCount%51);
            if(g == 0)
                g = 255;
            b = 0;
        }
        else if(timeCount <= 4*51)
        {
            r = 255-5*(timeCount%51);
            if(r == 255)
                r = 0;
            g = 255;
            b = 0;
        }
        else if(timeCount <= 5*51)
        {
            r = 0;
            g = 255;
            b = 5*(timeCount%51);
            if(b == 0)
                b = 255;
        }
        else //if(timeCount <= 6*51)
        {
            r = 0;
            g = 255-5*(timeCount%51);
            if(g == 255)
                g = 0;
            b = 255;
        }
        
        Color c = new Color(r, g, b);
        
        for(int i=0; i<25; i++)
        {
            for(int j=0; j<35; j++)
            {
                if(board[i][j] instanceof Wall)
                    board[i][j].setColor(c);
            }
        }
    }
    
    public int getPointsToAdd()
    {
        return pointsToAdd;
    }
    public void setPointsToAdd(int value)
    {
        pointsToAdd = value;
    }
    
    public void moveGhost(Ghost g)
    {
        moveGhost(g.getTargetX(p.getX(), p.getY(), p.getDirection(), rG.getX(), rG.getY(), g.getX(), g.getY()), g.getTargetY(p.getX(), p.getY(), p.getDirection(), rG.getX(), rG.getY(), g.getX(), g.getY()), g);
    }
    public void moveGhost(int targetX, int targetY, Ghost g)
    {
        if(g.getGhostSpeed() == 4) // for off-center ghosts when the ghostSpeed changes from 2 to 4
        {
            if((double) (g.getX())/4.0 != (double) (g.getX()/4)) // change 4 & 2 to be in terms of ghostSpeed...
            {
                if(g.getDirection() == 'l')
                    g.setX(g.getX() - 2);
                else if(g.getDirection() == 'r')
                    g.setX(g.getX() + 2);
                return;
            }
            if((double) (g.getY())/4.0 != (double) (g.getY()/4) && g.getY() != 305) // to fix the next w/pink ghost moving up
            {
                if(g.getDirection() == 'u')
                    g.setY(g.getY() - 2);
                else if(g.getDirection() == 'd')
                    g.setY(g.getY() + 2);
                return;
            }
        }
            
        
        if(g.getX() == 0)
        {
            g.setX(480 - g.getGhostSpeed());
            return;
        }
        else if(g.getX() == 480-3*g.getGhostSpeed() || g.getX() == 480-2*g.getGhostSpeed() || g.getX() == 480-g.getGhostSpeed())
        {
            if(g.getDirection() == 'l')
            {
                g.setX(g.getX() - g.getGhostSpeed());
            }
            else if(g.getDirection() == 'r')
            {
                g.setX(g.getX() + g.getGhostSpeed());
            }
            return;
        }
        else if(g.getX() == 480)
        {
            g.setX(0 + g.getGhostSpeed());
            return;
        }
        
        if(g.getY() == g.getGhostSpeed() || g.getY() == 2*g.getGhostSpeed() || g.getY() == 3*g.getGhostSpeed())
        {
            if(g.getDirection() == 'u')
            {
                g.setY(g.getY() - g.getGhostSpeed());
            }
            else if(p.getDirection() == 'd')
            {
                g.setY(g.getY() + g.getGhostSpeed());
            }
            return;
        }
        else if(g.getY() == 0)
        {
            g.setY(680 - g.getGhostSpeed());
            return;
        }
        else if(g.getY() == 680-3*g.getGhostSpeed() || g.getY() == 680-2*g.getGhostSpeed() || g.getY() == 680-g.getGhostSpeed())
        {
            if(g.getDirection() == 'u')
            {
                g.setY(g.getY() - g.getGhostSpeed());
            }
            else if(p.getDirection() == 'd')
            {
                g.setY(g.getY() + g.getGhostSpeed());
            }
            return;
        }
        else if(g.getY() == 680)
        {
            g.setY(0 + g.getGhostSpeed());
            return;
        }
        
        
        int width = Math.abs(targetX - g.getX());
        int height = Math.abs(targetY - g.getY());
        
        char firstDir = ' ';
        char secondDir = ' ';
        
        if(targetX > g.getX() && targetY <= g.getY()) // quadrant I // check </> or = to in these 4 statements
        {
            if(width > height)
            {
                firstDir = 'r';
                secondDir = 'u';
            }
            else if(width < height)
            {
                firstDir = 'u';
                secondDir = 'r';
            }
            else // if they're equal, change this later to be smarter???
            {
                firstDir = 'r';
                secondDir = 'u';
            }
        }
        else if(targetX <= g.getX() && targetY < g.getY()) // quadrant II
        {
            if(width > height)
            {
                firstDir = 'l';
                secondDir = 'u';
            }
            else if(width < height)
            {
                firstDir = 'u';
                secondDir = 'l';
            }
            else
            {
                firstDir = 'l';
                secondDir = 'u';
            }
        }
        else if(targetX < g.getX() && targetY >= g.getY()) // quadrant III
        {
            if(width > height)
            {
                firstDir = 'l';
                secondDir = 'd';
            }
            else if(width < height)
            {
                firstDir = 'd';
                secondDir = 'l';
            }
            else
            {
                firstDir = 'l';
                secondDir = 'd';
            }
        }
        else if(targetX >= g.getX() && targetY > g.getY()) // quadrant IV
        {
            if(width > height)
            {
                firstDir = 'r';
                secondDir = 'd';
            }
            else if(width < height)
            {
                firstDir = 'd';
                secondDir = 'r';
            }
            else
            {
                firstDir = 'r';
                secondDir = 'd';
            }
        }
        else {}
        
        
        if((g.getX()%20 == 0) && (g.getY()%20 == 0))
        {
            moveGhostAtIntersection(firstDir, secondDir, g);
        }
        else
        {
            if(g.getDirection() == 'u')
                g.setY(g.getY() - g.getGhostSpeed());
            else if(g.getDirection() == 'd')
                g.setY(g.getY() + g.getGhostSpeed());
            else if(g.getDirection() == 'l')
                g.setX(g.getX() - g.getGhostSpeed());
            else if(g.getDirection() == 'r')
                g.setX(g.getX() + g.getGhostSpeed());
        }
    }
    public void moveGhostAtIntersection(char firstDir, char secondDir, Ghost g)
    {
        if(firstDir == 'u')
        {
            if((g.getDirection() != 'd') && !(board[(int) ((g.getX()+10)/20)][(int) ((g.getY()+10)/20) - 1] instanceof Wall))
            {
                g.setY(g.getY() - g.getGhostSpeed());
                g.setDirection('u');
            }
            else
            {
                if(secondDir == 'l')
                {
                    if((g.getDirection() != 'r') && !(board[(int) ((g.getX()+10)/20) - 1][(int) ((g.getY()+10)/20)] instanceof Wall))
                    {
                        g.setX(g.getX() - g.getGhostSpeed());
                        g.setDirection('l');
                    }
                    else
                    {
                        if((g.getDirection() != 'l') && !(board[(int) ((g.getX()+10)/20) + 1][(int) ((g.getY()+10)/20)] instanceof Wall))
                        {
                            g.setX(g.getX() + g.getGhostSpeed());
                            g.setDirection('r');
                        }
                        else if(!(board[(int) ((g.getX()+10)/20)][(int) ((g.getY()+10)/20) + 1] instanceof Wall))
                        {
                            g.setY(g.getY() + g.getGhostSpeed());
                            g.setDirection('d');
                        }
                    }
                }
                else if(secondDir == 'r')
                {
                    if((g.getDirection() != 'l') && !(board[(int) ((g.getX()+10)/20) + 1][(int) ((g.getY()+10)/20)] instanceof Wall))
                    {
                        g.setX(g.getX() + g.getGhostSpeed());
                        g.setDirection('r');
                    }
                    else
                    {
                        if((g.getDirection() != 'r') && !(board[(int) ((g.getX()+10)/20) - 1][(int) ((g.getY()+10)/20)] instanceof Wall))
                        {
                            g.setX(g.getX() - g.getGhostSpeed());
                            g.setDirection('l');
                        }
                        else if(!(board[(int) ((g.getX()+10)/20)][(int) ((g.getY()+10)/20) + 1] instanceof Wall))
                        {
                            g.setY(g.getY() + g.getGhostSpeed());
                            g.setDirection('d');
                        }
                    }
                }
            }
        }
        else if(firstDir == 'd')
        {
            if((g.getDirection() != 'u') && !(board[(int) ((g.getX()+10)/20)][(int) ((g.getY()+10)/20) + 1] instanceof Wall))
            {
                g.setY(g.getY() + g.getGhostSpeed());
                g.setDirection('d');
            }
            else
            {
                if(secondDir == 'l')
                {
                    if((g.getDirection() != 'r') && !(board[(int) ((g.getX()+10)/20) - 1][(int) ((g.getY()+10)/20)] instanceof Wall))
                    {
                        g.setX(g.getX() - g.getGhostSpeed());
                        g.setDirection('l');
                    }
                    else
                    {
                        if((g.getDirection() != 'l') && !(board[(int) ((g.getX()+10)/20) + 1][(int) ((g.getY()+10)/20)] instanceof Wall))
                        {
                            g.setX(g.getX() + g.getGhostSpeed());
                            g.setDirection('r');
                        }
                        else if(!(board[(int) ((g.getX()+10)/20)][(int) ((g.getY()+10)/20) - 1] instanceof Wall))
                        {
                            g.setY(g.getY() - g.getGhostSpeed());
                            g.setDirection('u');
                        }
                    }
                }
                else if(secondDir == 'r')
                {
                    if((g.getDirection() != 'l') && !(board[(int) ((g.getX()+10)/20) + 1][(int) ((g.getY()+10)/20)] instanceof Wall))
                    {
                        g.setX(g.getX() + g.getGhostSpeed());
                        g.setDirection('r');
                    }
                    else
                    {
                        if((g.getDirection() != 'r') && !(board[(int) ((g.getX()+10)/20) - 1][(int) ((g.getY()+10)/20)] instanceof Wall))
                        {
                            g.setX(g.getX() - g.getGhostSpeed());
                            g.setDirection('l');
                        }
                        else if(!(board[(int) ((g.getX()+10)/20)][(int) ((g.getY()+10)/20) - 1] instanceof Wall))
                        {
                            g.setY(g.getY() - g.getGhostSpeed());
                            g.setDirection('u');
                        }
                    }
                }
            }
        }
        else if(firstDir == 'l')
        {
            if((g.getDirection() != 'r') && !(board[(int) ((g.getX()+10)/20) - 1][(int) ((g.getY()+10)/20)] instanceof Wall))
            {
                g.setX(g.getX() - g.getGhostSpeed());
                g.setDirection('l');
            }
            else
            {
                if(secondDir == 'u')
                {
                    if((g.getDirection() != 'd') && !(board[(int) ((g.getX()+10)/20)][(int) ((g.getY()+10)/20) - 1] instanceof Wall))
                    {
                        g.setY(g.getY() - g.getGhostSpeed());
                        g.setDirection('u');
                    }
                    else
                    {
                        if((g.getDirection() != 'u') && !(board[(int) ((g.getX()+10)/20)][(int) ((g.getY()+10)/20) + 1] instanceof Wall))
                        {
                            g.setY(g.getY() + g.getGhostSpeed());
                            g.setDirection('d');
                        }
                        else if(!(board[(int) ((g.getX()+10)/20) + 1][(int) ((g.getY()+10)/20)] instanceof Wall))
                        {
                            g.setX(g.getX() + g.getGhostSpeed());
                            g.setDirection('r');
                        }
                    }
                }
                else if(secondDir == 'd')
                {
                    if((g.getDirection() != 'u') && !(board[(int) ((g.getX()+10)/20)][(int) ((g.getY()+10)/20) + 1] instanceof Wall))
                    {
                        g.setY(g.getY() + g.getGhostSpeed());
                        g.setDirection('d');
                    }
                    else
                    {
                        if((g.getDirection() != 'd') && !(board[(int) ((g.getX()+10)/20)][(int) ((g.getY()+10)/20) - 1] instanceof Wall))
                        {
                            g.setY(g.getY() - g.getGhostSpeed());
                            g.setDirection('u');
                        }
                        else if(!(board[(int) ((g.getX()+10)/20) + 1][(int) ((g.getY()+10)/20)] instanceof Wall))
                        {
                            g.setX(g.getX() + g.getGhostSpeed());
                            g.setDirection('r');
                        }
                    }
                }
            }
        }
        else if(firstDir == 'r')
        {
            if((g.getDirection() != 'l') && !(board[(int) ((g.getX()+10)/20) + 1][(int) ((g.getY()+10)/20)] instanceof Wall))
            {
                g.setX(g.getX() + g.getGhostSpeed());
                g.setDirection('r');
            }
            else
            {
                if(secondDir == 'u')
                {
                    if((g.getDirection() != 'd') && !(board[(int) ((g.getX()+10)/20)][(int) ((g.getY()+10)/20) - 1] instanceof Wall))
                    {
                        g.setY(g.getY() - g.getGhostSpeed());
                        g.setDirection('u');
                    }
                    else
                    {
                        if((g.getDirection() != 'u') && !(board[(int) ((g.getX()+10)/20)][(int) ((g.getY()+10)/20) + 1] instanceof Wall))
                        {
                            g.setY(g.getY() + g.getGhostSpeed());
                            g.setDirection('d');
                        }
                        else if(!(board[(int) ((g.getX()+10)/20) - 1][(int) ((g.getY()+10)/20)] instanceof Wall))
                        {
                            g.setX(g.getX() - g.getGhostSpeed());
                            g.setDirection('l');
                        }
                    }
                }
                else if(secondDir == 'd')
                {
                    if((g.getDirection() != 'u') && !(board[(int) ((g.getX()+10)/20)][(int) ((g.getY()+10)/20) + 1] instanceof Wall))
                    {
                        g.setY(g.getY() + g.getGhostSpeed());
                        g.setDirection('d');
                    }
                    else
                    {
                        if((g.getDirection() != 'd') && !(board[(int) ((g.getX()+10)/20)][(int) ((g.getY()+10)/20) - 1] instanceof Wall))
                        {
                            g.setY(g.getY() - g.getGhostSpeed());
                            g.setDirection('u');
                        }
                        else if(!(board[(int) ((g.getX()+10)/20) - 1][(int) ((g.getY()+10)/20)] instanceof Wall))
                        {
                            g.setX(g.getX() - g.getGhostSpeed());
                            g.setDirection('l');
                        }
                    }
                }
            }
        }
        else {}
    }
    
    public void setBoardNum(int value)
    {
        boardNum = value;
    }
    public int getBoardNum()
    {
        return boardNum;
    }
    
    public void drawWithoutGhosts(Graphics g)
    {
        for(int i = 0; i < 25; i++)
        {
            for (int j = 0; j < 35; j++)
            {
                if(board[i][j] != null)
                {
                    board[i][j].draw(g);
                }
            }
        }
        
        // this order matters depending on scatter vs attack mode... & blue always on top...
    }
    public void draw(Graphics g)
    {
        for(int i = 0; i < 25; i++)
        {
            for (int j = 0; j < 35; j++)
            {
                if(board[i][j] != null)
                {
                    board[i][j].draw(g);
                }
            }
        }
        
        // this order matters depending on scatter vs attack mode... & blue always on top...
        // will pacman's future triangular mouth cover the pellets???
        rG.draw(g);
        pG.draw(g);
        oG.draw(g);
        bG.draw(g);
        p.draw(g);
    }
}
