import java.awt.*;
public class Pacman extends GameObject
{
    private Color color;
    private char direction;
    private char futureDirection;
    
    private int timesMoved;
    
    public Pacman(int xLoc, int yLoc, Color c, char dir)
    {
        super(xLoc, yLoc);
        color = Color.YELLOW;
        direction = dir; // futureDirections & direction start as left;
        futureDirection = dir;
        
        setPacmanX(xLoc);
        setPacmanY(yLoc);
        setPacmanDirection(direction);
        
        timesMoved = 0;
    }
    
    public char getDirection()
    {
        return direction;
    }
    public void setDirection(char dir)
    {
        direction = dir;
    }
    public char getFutureDirection()
    {
        return futureDirection;
    }
    public void setFutureDirection(char futureDir)
    {
        futureDirection = futureDir;
    }
    
    public Color getColor()
    {
        return Color.YELLOW;
    }
    public void setColor(Color newColor)
    {
        color = newColor;
    }
    public void incrementTimesMoved()
    {
        timesMoved++;
    }
    
    public void draw(Graphics g)
    {
        g.setColor(Color.YELLOW);
        
        if(timesMoved%4 == 0)
        {
            g.fillArc(getX(), getY(), 20, 20, 0, 360);
        }
        else if(timesMoved%4 == 1 || timesMoved%4 == 3)
        {
            if(direction == 'l')
                g.fillArc(getX(), getY(), 20, 20, 180+23, 360-45);
            else if(direction == 'r')
                g.fillArc(getX(), getY(), 20, 20, 23, 360-45);
            else if(direction == 'u')
                g.fillArc(getX(), getY(), 20, 20, 90+23, 360-45);
            else if(direction == 'd')
                g.fillArc(getX(), getY(), 20, 20, 270+23, 360-45);
        }
        else //if(timesMoved%4 == 2)
        {
            if(direction == 'l')
                g.fillArc(getX(), getY(), 20, 20, 180+45, 270);
            else if(direction == 'r')
                g.fillArc(getX(), getY(), 20, 20, 45, 270);
            else if(direction == 'u')
                g.fillArc(getX(), getY(), 20, 20, 90+45, 270);
            else if(direction == 'd')
                g.fillArc(getX(), getY(), 20, 20, 270+45, 270);
        }
    }
}
