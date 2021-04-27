import java.awt.*;
public class Pellet extends GameObject
{
    private Color color;
    public Pellet(int xLoc, int yLoc, Color c)
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
        g.fillOval(getX()+8, getY()+8, 4, 4);
    }
}
