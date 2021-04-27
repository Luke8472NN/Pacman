import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class Banana extends Fruit
{
    private BufferedImage bananaImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    
    public Banana(int xLoc, int yLoc)
    {
        super(xLoc, yLoc);
        
        try {
            bananaImage = ImageIO.read(new File("Images/Fruit/Banana.png"));
        } catch(IOException e) {e.printStackTrace();}
    }
    
    public void draw(Graphics g)
    {
        g.drawImage(bananaImage, getX(), getY(), null);
    }
}