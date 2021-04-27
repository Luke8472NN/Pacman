import java.awt.*;
public class Wall extends GameObject
{
    private Color color;
    public Wall(int xLoc, int yLoc, Color c)
    {
        super(xLoc, yLoc);
        color = c;
    }
    
    public Color getColor()
    {
        return color;
    }
    public void setColor(Color newColor)
    {
        color = newColor;
    }
    
    public void draw(Graphics g)
    {
        g.setColor(color);
        g.fillRect(getX(), getY(), 20, 20); // x, y, width, height
    }
}
