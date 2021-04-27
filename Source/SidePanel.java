import java.awt.*;
import javax.swing.*;
import javax.swing.JTextField;
import java.io.File;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

import javax.swing.event.*;
import java.util.*;
import java.io.IOException;


public class SidePanel extends JPanel
{
    private JList highScoreJList;
    private JScrollPane scrollPane;
    private DefaultListModel jListModel;
    private ArrayList<String> highScores;
    
    private GamePanel gamePanel;
    
    private BufferedImage backgroundImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private BufferedImage pacman = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private JLabel score = new JLabel("0000000");
    private JLabel level = new JLabel("0");
    
    private boolean hasExtraSoundYet = false;
    
    private boolean isACheater = false;
    
    private JLabel cheaterLabel = new JLabel("CHEATER");
    
    public SidePanel(GamePanel gP) 
    {
        setLayout(null);
        
        gamePanel = gP;
        
        highScores = getStringArrayList();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new frame.setVisible(true);
                jListModel = new DefaultListModel/*<String>*/();
                for(String s : highScores)
                {
                    jListModel.addElement(s);
                }
                
                highScoreJList = new JList(jListModel);
                
                //highScoreJList.setCellRenderer(new CellRenderer());
                
                scrollPane = new JScrollPane();
                scrollPane.setBounds(10, 215, 280, 455);
                
                Color newColor = new Color(35, 67, 199);
                highScoreJList.setBackground(newColor); //sets the JList's color, not the JLabel or Cells or ScrollPane
                
                highScoreJList.setCellRenderer(new CellRenderer());
                highScoreJList.setLayoutOrientation(JList.VERTICAL);
                highScoreJList.setFixedCellWidth(100);
                highScoreJList.setFixedCellHeight(25);
                //highScoreJList.addListSelectionListener(new JListListener());
                
                scrollPane.getViewport().setView(highScoreJList);
                scrollPane.revalidate();
                
                add(scrollPane);
                
                scrollPane.setVisible(true);
                highScoreJList.setVisible(true);
            }
        } );
        
        score.setFont(new Font("Serif", Font.PLAIN, 40));
        score.setBounds(142, 22, 150, 35);
        score.setForeground(new Color(238, 228, 12));
        add(score);
        
        level.setFont(new Font("Serif", Font.PLAIN, 25));
        level.setBounds(80, 53, 150, 35);
        level.setForeground(new Color(238, 228, 12));
        add(level);
        
        cheaterLabel.setFont(new Font("Serif", Font.PLAIN, 40));
        cheaterLabel.setBounds(57, 115, 200, 35);
        cheaterLabel.setForeground(new Color(238, 228, 12));
        add(cheaterLabel);
        
        try {
            backgroundImage = ImageIO.read(new File("Images/Screens and Buttons/SidePanel.png"));
            pacman = ImageIO.read(new File("Images/Pacman/PacManLife.png"));
        } catch(IOException e) {e.printStackTrace();}
        
        Dimension panelSize = new Dimension(300, 700);
        setPreferredSize(panelSize);
    }
    
    public void updateHighScoreList()
    {
        if(gamePanel.getGameWindow().getUser() != null && !isACheater)
            gamePanel.getGameWindow().save(Integer.parseInt(score.getText()));
        
        try {
            //javax.swing.SwingUtilities.invokeLater(new Runnable() {
            //javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
            //java.awt.EventQueue.invokeAndWait(new Runnable() {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    jListModel.clear();
                    
                    highScores = getStringArrayList();
                    
                    for(String s : highScores)
                    {
                        jListModel.addElement(s);
                    }
                    
                    highScoreJList = new JList(jListModel);
                    
                    revalidate();
                }
            } );
        } catch(Exception e) {e.printStackTrace();}
        
        if(!hasExtraSoundYet && gamePanel.getGameManager().getScore() >= gamePanel.getGameWindow().getScores().get(0).getHighScore())
        {
            hasExtraSoundYet = true;
            
            ChomperExtraPacSound extra = new ChomperExtraPacSound();
            Thread extraThread = new Thread(extra);
            extraThread.start();
            
            /*gamePanel.getBoard().getNegativeGhostTimer().stop(); // no pause necessary for beating highscore, just sound also with whatever sound causes the last points
            try {
                for(int i=0; i<3000/25; i++)
                {
                    Thread.sleep(25);
                    gamePanel.getBoard().setWallColorsAtTime(gamePanel.getBoard().getBoardColorTimeCount() + 1);
                    gamePanel.repaint();
                }
            } catch(InterruptedException e) {Thread.currentThread().interrupt();}
            gamePanel.getBoard().restartNegativeGhostTimer();*/
        }
    }
    public ArrayList<String> getStringArrayList()
    {
        ArrayList<User> userList = gamePanel.getUserList();
        
        ArrayList<String> returnedAL = new ArrayList<>();
        
        for(int i = 0; i < userList.size(); i++)
        {
            String name = userList.get(i).getName();
            int score = userList.get(i).getHighScore();
            
            String scoreString = Integer.toString(score);
            if(scoreString.length() == 1)
                scoreString = "000000" + scoreString;
            else if(scoreString.length() == 2)
                scoreString = "00000" + scoreString;
            else if(scoreString.length() == 3)
                scoreString = "0000" + scoreString;
            else if(scoreString.length() == 4)
                scoreString = "000" + scoreString;
            else if(scoreString.length() == 5)
                scoreString = "00" + scoreString;
            else if(scoreString.length() == 6)
                scoreString = "0" + scoreString;
            
            String label = (i+1) + ".    ";
            if( i+1 <= 9 )
                label += "    ";
            else if( i+1 <= 99 )
                label += "  ";
            label += scoreString + "       " + name;
            
            /*
            StringBuffer buffer = new StringBuffer();
            buffer.setLength(40);
            
            
            //buffer.insert(0, "               ");
            
            if(i >= 9 )
            {
                buffer.replace(4, 4 + scoreList.get(i).getName().length() + 2, scoreList.get(i).getName() );
                //buffer.replace(4 + scoreList.get(i).getName().length() + 3, 4 + scoreList.get(i).getName().length() + 3 + 
                buffer.replace(33, 49, "  "+scoreList.get(i).getHighScore() );
            } else {
                buffer.replace(6, 6 + scoreList.get(i).getName().length() + 4, scoreList.get(i).getName() );
                buffer.replace(33, 49, "  "+scoreList.get(i).getHighScore() );
          
            }
            
            buffer = buffer.insert(0, "" + (i+1) + "." );
            String s = buffer.toString();
            s = s.replace('\0', ' ');
            stringList[i] = s;
            */
            
            returnedAL.add(label);
        }
        return returnedAL;
    }
    
    public void setScoreLabel(int newScore)
    {
        String newScoreString = Integer.toString(newScore);
        
        if(newScoreString.length() == 1)
            newScoreString = "000000" + newScoreString;
        else if(newScoreString.length() == 2)
            newScoreString = "00000" + newScoreString;
        else if(newScoreString.length() == 3)
            newScoreString = "0000" + newScoreString;
        else if(newScoreString.length() == 4)
            newScoreString = "000" + newScoreString;
        else if(newScoreString.length() == 5)
            newScoreString = "00" + newScoreString;
        else if(newScoreString.length() == 6)
            newScoreString = "0" + newScoreString;
        
        score.setText(newScoreString);
        
        updateHighScoreList();
    }
    public void setLevelLabel(int newLevel)
    {
        level.setText(Integer.toString(newLevel));
    }
    
    public void paintComponent(Graphics g)
    {
        g.drawImage(backgroundImage, 0, 0, null);
        if(gamePanel.getGameManager().getLives() >= 1)
            g.drawImage(pacman, 20,17, null);
        if(gamePanel.getGameManager().getLives() >= 2)
            g.drawImage(pacman, 60,17, null);
        if(gamePanel.getGameManager().getLives() >= 3)
            g.drawImage(pacman, 100,17, null);
        
        if(!isACheater)
            cheaterLabel.setVisible(false);
        else
            cheaterLabel.setVisible(true);
        
        // draw pacman animation here &/or in GameWindow somewhere...
    }
    
    private class CellRenderer extends DefaultListCellRenderer
    {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
        {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            Color newColor = new Color(35, 67, 199);
            c.setBackground(newColor);
            
            return c;
        }
    }
    
    /*
    private class JListListener implements ListSelectionListener
    {
        public void valueChanged(ListSelectionEvent e)
        {            
            //what happends when you click on a row
            //display the user's information to the right - user drawString
            int index = highScoreJList.getSelectedIndex();
            ArrayList<User> list = gameWindow.getScores();
            
           if(index != -1 )
           {
               String name = list.get(index).getName();
 
               User selectedUser = gameWindow.getNewUser( name );

               cuLabel.setText( selectedUser.getName() );
               scLabel.setText( "" + selectedUser.getHighScore() );
               stLabel.setText( "" + selectedUser.getHighStars() );
               hLLabel.setText( "" + selectedUser.getHighLevel() );
               gpLabel.setText( "" + selectedUser.getGamesPlayed() );
            
               revalidate();
            }
            
            
        }
        
    }*/
    
    /*public boolean getHasExtraSoundYet()
    {
        return hasExtraSoundYet;
    }*/
    public void setHasExtraSoundYet(boolean value)
    {
        hasExtraSoundYet = value;
    }
    
    public boolean getIsACheater()
    {
        return isACheater;
    }
    public void setIsACheater(boolean value)
    {
        isACheater = value;
    }
}