import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
public class UserList
{
    private ArrayList<User> list = new ArrayList<>();
    public UserList(GamePanel gP)
    {
        load();
    }
    
    public User getUser(String name)
    {
        for(User u : list)
        {
            if(u.getName().equals(name))
                return u;
        }
        return null;
    }
    public ArrayList<User> getScoreList()
    {
        return list;
    }
    
    public void load()
    {
        list.clear();
        
        BufferedReader reader;
        String line;
        try
        {
            reader = new BufferedReader(new FileReader("Text Files/PacmanUserList.txt"));
            while((line=reader.readLine()) != null)
            {
                String[] userData = line.split("\\s");
                User u = new User(userData[0], userData[1], Integer.parseInt(userData[2]));//, Integer.parseInt(userData[3]));
                list.add(u);
            }
        }
        catch(IOException e) {}
    }
    public void save()
    {
        try
        {
            BufferedWriter out = new BufferedWriter(new FileWriter("Text Files/PacmanUserList.txt"));
            for(int i=0; i<list.size(); i++)
            {
                User u = list.get(i);
                out.write(u.getName() + " " + u.getPassword() + " " + u.getHighScore() /*+ " " + u.getTimesPlayed()*/ + "\n");
            }
            out.close();
        }
        catch(IOException e) {}
    }
    public void addUser(User u)
    {
        if(u.getName().matches("(.*) (.*)") || u.getPassword().matches("(.*) (.*)"))
            return;
        if(userExists(u.getName()) == null)
            list.add(u);
    }
    public User userExists(String n)
    {
        for(int i=0; i<list.size(); i++)
        {
            if(list.get(i).getName().equals(n))
                return list.get(i);
        }
        return null;
    }
    /*public void signIn(String n, String p)
    {
        User u = userExists(n);
        if(u != null)
        {
            if(u.getPassword().equals(p))
            {}  //sign them in
            else
            {}  //display some JPane words saying "password incorrect"
        }
        else
        {} //display some JPane words saying "username invalid"
    }*/
    
    public void maybeOrderList(User currentUser)
    {
        int counter = 0;
        while(counter < list.size() && list.get(counter).getHighScore() > currentUser.getHighScore())
            counter++;
        int currentUserIndex = list.indexOf(currentUser);
        list.add(counter, currentUser);
        list.remove(currentUserIndex + 1);
    }
}
