
public class User
{
    private String name;
    private String password;
    private int highScore;
    
    public User(String n, String p, int h)
    {
        name = n;
        password = p;
        highScore = h;
    }
    public User(User copy)
    {
        if(copy != null)
        {
            name = copy.getName();
            password = copy.getPassword();
            highScore = copy.getHighScore();
        }
        else
        {
            name = null;
            password = null;
            highScore = 0; // good default???
        }
    }
    
    public void addGame(int score)
    {
        if(score > highScore)
            highScore = score;
    }
    
    public String getName()
    {
        return name;
    }
    public String getPassword()
    {
        return password;
    }
    public int getHighScore()
    {
        return highScore;
    }
    
    public void setName(String n)
    {
        name = n;
    }
    public void setPassword(String p)
    {
        password = p;
    }
    public void setHighScore(int h)
    {
        highScore = h;
    }
}
