import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class Cherry extends Fruit
{
    private BufferedImage cherryImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    
    public Cherry(int xLoc, int yLoc)
    {
        super(xLoc, yLoc);
        
        try {
            //cherryImage = ImageIO.read(new File("Images/Fruit/Cherry.png"));
            cherryImage = ImageIO.read(this.getClass().getResource("Images/Fruit/Cherry.png"));
        } catch(IOException e) {e.printStackTrace();}
    }
    
    public void draw(Graphics g)
    {
        g.drawImage(cherryImage, getX(), getY(), null);
    }
}