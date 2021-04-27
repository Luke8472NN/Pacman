import java.awt.*;
public class LargePellet extends GameObject
{
    private Color color;
    public LargePellet(int xLoc, int yLoc, Color c)
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
        g.fillOval(getX()+5, getY()+5, 10, 10);
    }
}
