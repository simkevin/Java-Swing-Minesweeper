import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class MineSweeper implements Runnable {
   
    public void run() {
        // Set the frame
        final JFrame frame = new JFrame("MineSweeper");
        frame.setPreferredSize(new Dimension(290, 340));
       
        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("");
        status_panel.add(status);
        final JPanel timerPanel = new JPanel();
        frame.add(timerPanel, BorderLayout.NORTH);
        final JLabel timerStatus = new JLabel("");
        timerPanel.add(timerStatus);
        
        // adds new board to frame
        frame.add(new Board(status, timerStatus));
        
        // Menu bar with instructions and high scores
        JMenuBar menu = new JMenuBar();
        frame.setJMenuBar(menu);
        
        // Class describing rules to the game
        class rulesActionListener implements ActionListener{
            public void actionPerformed(ActionEvent e ){
                JFrame rules = new JFrame("How To Play");
                rules.setPreferredSize(new Dimension(300, 300));
                
                JLabel rules1 = new JLabel("Welcome to Minesweeper!");
                JLabel rules2 = new JLabel("There are 40 mines. The goal is to uncover");
                JLabel rules3 = new JLabel("all of the cells without mines.");
                JLabel rules4 = new JLabel("If you uncover a mine, you lose.");
                JLabel rules5 = new JLabel("If you uncover all the other cells, you win.");
                JLabel rules6 = new JLabel("When you uncover some cells,");
                JLabel rules7 = new JLabel("there are numbers. These numbers tell you");
                JLabel rules8 = new JLabel("how many mines are around that cell");
                JLabel rules9 = new JLabel("to help you deduce where the mines are.");
                JLabel rules10 = new JLabel("Good luck!");
                JPanel panel = new JPanel();
                panel.add(rules1);
                panel.add(rules2);
                panel.add(rules3);
                panel.add(rules4);
                panel.add(rules5);
                panel.add(rules6);
                panel.add(rules7);
                panel.add(rules8);
                panel.add(rules9);
                panel.add(rules10);
                rules.add(panel);
                
                rules.pack();
                rules.setVisible(true);
            }
          
        }
        
        // Class for keeping track of high scores
        class hsActionListener implements ActionListener{
            public void actionPerformed(ActionEvent e ){
                JFrame highscores = new JFrame("Highscores");
                highscores.setPreferredSize(new Dimension(500, 100));

                String inputLine = "";
                String prevLine = "";
                FileReader inputFile = null;
                try {
                    inputFile = new FileReader("highscores");
                } catch (FileNotFoundException e2) {
                 
                }
                BufferedReader br = new BufferedReader(inputFile);
               
                try {
                    while(( inputLine = br.readLine()) != null){
                        
                        prevLine = inputLine + " " + prevLine;
                        
                        JPanel panel = new JPanel();
                        panel.add(new JLabel(prevLine));
                        highscores.add(panel);
                    
                    }
                } catch (IOException e1) {
                   
                }
                try {
                    br.close();
                } catch (IOException e1) {
                  
                }
               
                highscores.pack();
                highscores.setVisible(true);
            }
          
        }
        
        
        JMenuItem jmiRules = new JMenuItem("How To Play");
        menu.add(jmiRules);
        jmiRules.addActionListener(new rulesActionListener());
        
        
        JMenuItem highScores = new JMenuItem("High Scores");
        menu.add(highScores);
        highScores.addActionListener(new hsActionListener());
        
        // Display frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new MineSweeper());
    }
}