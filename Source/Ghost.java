import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public abstract class Ghost extends GameObject
{
    private Color color;
    
    private char direction;
    
    private int redGhostX;
    private int redGhostY;
    
    private boolean isNegative;
    private boolean isDead;
    
    BufferedImage image3b = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    BufferedImage image4b = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    BufferedImage image3w = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    BufferedImage image4w = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    BufferedImage imageU = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    BufferedImage imageD = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    BufferedImage imageL = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    BufferedImage imageR = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    
    private int prevXLoc;
    private int prevYLoc;
    private char prevDir;
    
    private boolean isBlueNegative = true;
    
    private int timesMoved;
    
    private int ghostSpeed;
    
    public Ghost(int xLoc, int yLoc, char dir, int pXL, int pYL, char pD, int gS)
    {
        super(xLoc, yLoc);
        direction = dir;
        
        isNegative = false; // these 2 lines necessary???
        isDead = false;
        
        prevXLoc = pXL;
        prevYLoc = pYL;
        prevDir = pD;
        
        timesMoved = 0;
        
        ghostSpeed = gS;
        
        try {
            image3b = ImageIO.read(new File("Images/Ghosts/Dead Ghost/deadBlue3.png"));
            image4b = ImageIO.read(new File("Images/Ghosts/Dead Ghost/deadBlue4.png"));
            image3w = ImageIO.read(new File("Images/Ghosts/Dead Ghost/deadWhite3.png"));
            image4w = ImageIO.read(new File("Images/Ghosts/Dead Ghost/deadWhite4.png"));
            imageU = ImageIO.read(new File("Images/Ghosts/Eyes/eyesUp.png"));
            imageD = ImageIO.read(new File("Images/Ghosts/Eyes/eyesDown.png"));
            imageL = ImageIO.read(new File("Images/Ghosts/Eyes/eyesLeft.png"));
            imageR = ImageIO.read(new File("Images/Ghosts/Eyes/eyesRight.png"));
        } catch(IOException e) {e.printStackTrace();}
    }
    
    public char getDirection()
    {
        return direction;
    }
    public void setDirection(char dir)
    {
        direction = dir;
    }
    
    public int getRedGhostX()
    {
        return redGhostX;
    }
    public int getRedGhostY()
    {
        return redGhostY;
    }
    public void setRedGhostX(int value)
    {
        redGhostX = value;
    }
    public void setRedGhostY(int value)
    {
        redGhostY = value;
    }
    
    public boolean getIsNegative()
    {
        return isNegative;
    }
    public void setIsNegative(boolean value)
    {
        isNegative = value;
    }
    public boolean getIsDead()
    {
        return isDead;
    }
    public void setIsDead(boolean value)
    {
        isDead = value;
    }
    
    public int getPrevX()
    {
        return prevXLoc;
    }
    public int getPrevY()
    {
        return prevYLoc;
    }
    public void setPrevX(int value)
    {
        prevXLoc = value;
    }
    public void setPrevY(int value)
    {
        prevYLoc = value;
    }
    public char getPrevDir()
    {
        return prevDir;
    }
    public void setPrevDir(char value)
    {
        prevDir = value;
    }
    
    public boolean getIsBlueNegative()
    {
        return isBlueNegative;
    }
    public void setIsBlueNegative(boolean value)
    {
        isBlueNegative = value;
    }
    
    public abstract void draw(Graphics g);
    
    public abstract int getTargetX(int pacmanX, int pacmanY, char pacmanDirection, int redGhostX, int redGhostY, int x, int y);
    public abstract int getTargetY(int pacmanX, int pacmanY, char pacmanDirection, int redGhostX, int redGhostY, int x, int y);
    
    public void setColor(Color newColor)
    {
        color = newColor;
    }
    
    public void incrementTimesMoved()
    {
        timesMoved++;
    }
    
    public int getGhostSpeed()
    {
        return ghostSpeed;
    }
    public void setGhostSpeed(int value)
    {
        ghostSpeed = value;
    }
    
    public int getTimesMoved() // for the 4 subclasses
    {
        return timesMoved;
    }
}
