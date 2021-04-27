import java.awt.*;
public abstract class Fruit extends GameObject
{
    private Color color;
    
    public Fruit(int xLoc, int yLoc)
    {
        super(xLoc, yLoc);
        color = Color.WHITE;
    }
    
    // should move() & the targets() be in here???
    
    public Color getColor()
    {
        return color;
    }
    public void setColor(Color newColor) // unnecessary...
    {
        color = newColor;
    }
    
    public abstract void draw(Graphics g);
}
