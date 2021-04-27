import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class Strawberry extends Fruit
{
    private BufferedImage strawberryImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    
    public Strawberry(int xLoc, int yLoc)
    {
        super(xLoc, yLoc);
        
        try {
            strawberryImage = ImageIO.read(new File("Images/Fruit/Strawberry.png"));
        } catch(IOException e) {e.printStackTrace();}
    }
    
    public void draw(Graphics g)
    {
        g.drawImage(strawberryImage, getX(), getY(), null);
    }
}
