import java.awt.*;
public abstract class GameObject
{
    private int x;
    private int y;
    
    private int pacmanX;
    private int pacmanY;
    private char pacmanDir;
    
    private Color color;
    
    public GameObject(int xLoc, int yLoc)
    {
        x = xLoc;
        y = yLoc;
        
        color = Color.BLUE;
    }
    
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public void setX(int value)
    {
        x = value;
    }
    public void setY(int value)
    {
        y = value;
    }
    
    public int getBoardX()
    {
        return ((x+10)/20);
    }
    public int getBoardY()
    {
        return ((y+10)/20);
    }
    
    public char getPacmanDirection()
    {
        return pacmanDir;
    }
    public void setPacmanDirection(char dir)
    {
        pacmanDir = dir;
    }
    public int getPacmanX()
    {
        return pacmanX;
    }
    public int getPacmanY()
    {
        return pacmanY;
    }
    public void setPacmanX(int value)
    {
        pacmanX = value;
    }
    public void setPacmanY(int value)
    {
        pacmanY = value;
    }
    
    public Color getColor()
    {
        return color;
    }
    public abstract void setColor(Color c);
    
    public abstract void draw(Graphics g);
}
