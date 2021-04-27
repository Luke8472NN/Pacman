import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class RedGhost extends Ghost
{
    private BufferedImage image3u = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private BufferedImage image3d = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private BufferedImage image3l = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private BufferedImage image3r = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private BufferedImage image4u = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private BufferedImage image4d = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private BufferedImage image4l = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private BufferedImage image4r = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    
    public RedGhost(int xLoc, int yLoc, char dir)
    {
        super(xLoc, yLoc, dir, xLoc, yLoc, dir, 4);
        try {
            image3u = ImageIO.read(new File("Images/Ghosts/Red Ghost/redGhost3up.png"));
            image3d = ImageIO.read(new File("Images/Ghosts/Red Ghost/redGhost3down.png"));
            image3l = ImageIO.read(new File("Images/Ghosts/Red Ghost/redGhost3left.png"));
            image3r = ImageIO.read(new File("Images/Ghosts/Red Ghost/redGhost3right.png"));
            image4u = ImageIO.read(new File("Images/Ghosts/Red Ghost/redGhost4up.png"));
            image4d = ImageIO.read(new File("Images/Ghosts/Red Ghost/redGhost4down.png"));
            image4l = ImageIO.read(new File("Images/Ghosts/Red Ghost/redGhost4left.png"));
            image4r = ImageIO.read(new File("Images/Ghosts/Red Ghost/redGhost4right.png"));
        } catch(IOException e) {e.printStackTrace();}
        
        setRedGhostX(xLoc);
        setRedGhostY(yLoc);
        
        
                    //image3u = createResizedCopy(image3u, 40, 40, true);
                    //image4u = createResizedCopy(image4u, 40, 40, true);
    }
    
    /*public BufferedImage createResizedCopy(Image originalImage, 
            int scaledWidth, int scaledHeight, 
            boolean preserveAlpha)
    {
        System.out.println("resizing...");
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null); 
        g.dispose();
        return scaledBI;
    }*/
    
    public int getTargetX(int pacmanX, int pacmanY, char pacmanDirection, int redGhostX, int redGhostY, int x,int y)
    {
        if(getIsDead())
        {
            if(x > 240 && x <= 260 && y == 360)
                return 240;
            else
                return 260;
        }
        else if(x >= 220 && x <= 240 && y <= 340 && y > 320)
            return 240;
        else if(x >= 220 && x <= 260 && y <= 360 && y > 320)
            return 220;
        else if(!getIsNegative() && !getIsDead())
        {
            return pacmanX;
        }
        else
        {
            return (20*(int) (24*Math.random()));
        }
    }
    public int getTargetY(int pacmanX, int pacmanY, char pacmanDirection, int redGhostX, int redGhostY, int x, int y)
    {
        if(getIsDead())
        {
            return 360;
        }
        else if(x >= 220 && x <= 240 && y <= 340 && y > 320)
            return 320;
        else if(x >= 220 && x <= 260 && y <= 360 && y > 320)
            return 340;
        else if(!getIsNegative() && !getIsDead())
        {
            return pacmanY;
        }
        else
        {
            if(x == 240 && y == 300)
                return 0; // so that the negative ghosts won't wander into the center
            else
                return (20*(int) (34*Math.random()));
        }
    }
    
    public void draw(Graphics g)
    {
        if(getIsDead())
        {
            if(getDirection() == 'u')
                g.drawImage(imageU, getX(), getY(), null);
            else if(getDirection() == 'd')
                g.drawImage(imageD, getX(), getY(), null);
            else if(getDirection() == 'l')
                g.drawImage(imageL, getX(), getY(), null);
            else if(getDirection() == 'r')
                g.drawImage(imageR, getX(), getY(), null);
        }
        else if(getIsNegative())
        {
            if(getIsBlueNegative())
            {
                if(getTimesMoved()%2 == 0)
                {
                    g.drawImage(image3b, getX(), getY(), null);
                }
                else
                {
                    g.drawImage(image4b, getX(), getY(), null);
                }
            }
            else
            {
                if(getTimesMoved()%2 == 0)
                {
                    g.drawImage(image3w, getX(), getY(), null);
                }
                else
                {
                    g.drawImage(image4w, getX(), getY(), null);
                }
            }
        }
        else
        {
            if(getTimesMoved()%2 == 0)
            {
                if(getDirection() == 'u')
                    g.drawImage(image3u, getX(), getY(), null);
                else if(getDirection() == 'd')
                    g.drawImage(image3d, getX(), getY(), null);
                else if(getDirection() == 'l')
                    g.drawImage(image3l, getX(), getY(), null);
                else if(getDirection() == 'r')
                    g.drawImage(image3r, getX(), getY(), null);
            }
            else
            {
                if(getDirection() == 'u')
                    g.drawImage(image4u, getX(), getY(), null);
                else if(getDirection() == 'd')
                    g.drawImage(image4d, getX(), getY(), null);
                else if(getDirection() == 'l')
                    g.drawImage(image4l, getX(), getY(), null);
                else if(getDirection() == 'r')
                    g.drawImage(image4r, getX(), getY(), null);
            }
        }
    }
}
