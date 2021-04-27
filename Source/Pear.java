import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class Pear extends Fruit
{
    private BufferedImage pearImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    
    public Pear(int xLoc, int yLoc)
    {
        super(xLoc, yLoc);
        
        try {
            pearImage = ImageIO.read(new File("Images/Fruit/Pear.png"));
        } catch(IOException e) {e.printStackTrace();}
    }
    
    public void draw(Graphics g)
    {
        g.drawImage(pearImage, getX(), getY(), null);
    }
}
