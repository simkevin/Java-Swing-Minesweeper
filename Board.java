import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.image.BufferedImage;
import java.io.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.util.Random;

import javax.swing.*;

public class Board extends JPanel {
    
    // Constants
    private final int CELLSIZE = 15;   
    private final int ROWS = 16;
    private final int COLUMNS = 16;
    
    private static BufferedImage ONE;
    private static BufferedImage TWO;
    private static BufferedImage THREE;
    private static BufferedImage FOUR;
    private static BufferedImage FIVE;
    private static BufferedImage SIX;
    private static BufferedImage SEVEN;
    private static BufferedImage EIGHT;
    private static BufferedImage MINE;
    private static BufferedImage EXPLODEDMINE;
    private static BufferedImage COVERED;
    private static BufferedImage UNCOVERED;
    private static BufferedImage FLAG;
    private static BufferedImage NOTMINE;
    
    // Fields
    private Cell[][] cells;
    private int allMines = 40;
    private int minesLeft = 40;
    
    private JLabel statusPanel;
    private JLabel timerStatus;

    private boolean gameRunning;
    private JFrame restart;
    private long start;
    
    private static Timer timer;
    private int tick = 1000;
    private int duration;
    private int count;
    
    public Board(JLabel status, JLabel timerStatus) {
        this.statusPanel = status;
        this.timerStatus = timerStatus;
        try {
                ONE = ImageIO.read(new File("1.gif"));
                TWO = ImageIO.read(new File("2.gif"));
                THREE = ImageIO.read(new File("3.gif"));
                FOUR = ImageIO.read(new File("4.gif"));
                FIVE = ImageIO.read(new File("5.gif"));
                SIX = ImageIO.read(new File("6.gif"));
                SEVEN = ImageIO.read(new File("7.gif"));
                EIGHT = ImageIO.read(new File("8.gif"));
                MINE = ImageIO.read(new File("mine.gif"));
                EXPLODEDMINE = ImageIO.read(new File("exploded.gif"));
                COVERED =ImageIO.read(new File("covered.gif"));
                UNCOVERED = ImageIO.read(new File("uncovered.gif"));
                FLAG = ImageIO.read(new File("flag.gif"));
                NOTMINE = ImageIO.read(new File("notmine.gif"));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        addMouseListener(new MinesAdapter());
        setUpGame();
    }
    
    // this method initiates the game
    public void setUpGame() {
        createCells();
        
        start = System.currentTimeMillis();
        count = 0;
        duration = 0;
        timer = new Timer(tick, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                count++;
                timerStatus.setText(Integer.toString(count) + " sec");
                }
            });
        timer.start();
        
        gameRunning = true;
        minesLeft = allMines;
        
        // Indicates how many mines are remaining based on flags placed by user
        this.statusPanel.setText(Integer.toString(minesLeft)+ " Mines Remaining");
        
        int minesRemaining = allMines;
        Random rand = new Random();
        
        // Places mines randomly in 40 different cells
        while (minesRemaining > 0) {
            int someX = rand.nextInt(ROWS);
            int someY = rand.nextInt(COLUMNS);
            Cell cell = cells[someX][someY];
                if (!cell.hasMine()) {
                    cell.placeMine();
                    minesRemaining -= 1;
                }
        }
        
        assignNumMines();
    }
    
    // cells are created for each row and column
    private void createCells() {
        cells = new Cell[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                cells[i][j] = new Cell();
            }
        }
    }
    
    // figures out how many mines are adjacent to a certain cell
    private int numAdjacentMines(int x, int y) {
        int count = 0;
        
        // search every cell around a certain cell
        for (int i = -1; i < 2; i++) {
            int tempX = x + i;
            if (tempX < 0 || tempX >= ROWS) {
                continue;
            }

            for (int j = -1; j < 2; j++) {
                int tempY = y + j;
                if (tempY < 0 || tempY >= COLUMNS) {
                    continue;
                }

                if (i == 0 && j == 0) {
                    continue;
                }

                if (cells[tempX][tempY].hasMine()) {
                    count += 1;
                }
            }
        }
        return count;
    }
    
    // updates the number of mines around each cell
    private void assignNumMines() {
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                Cell cell = cells[i][j];

                if (!cell.hasMine()) {
                    int numMines = numAdjacentMines(i, j);
                    cell.setNumAdjacentMines(numMines);
                }
            }
        }
    }
    
    /* determines which image to use depending on what
     * type of cell each one is */
    private BufferedImage correctImage(Cell cell) {
        BufferedImage typeOfImage = UNCOVERED;
        if (cell.getNumAdjacentMines() == 1)
            typeOfImage = ONE;
        if (cell.getNumAdjacentMines() == 2)
            typeOfImage = TWO;
        if (cell.getNumAdjacentMines() == 3)
            typeOfImage = THREE;
        if (cell.getNumAdjacentMines() == 4)
            typeOfImage = FOUR;
        if (cell.getNumAdjacentMines() == 5)
            typeOfImage = FIVE;
        if (cell.getNumAdjacentMines() == 6)
            typeOfImage = SIX;
        if (cell.getNumAdjacentMines() == 7)
            typeOfImage = SEVEN;
        if (cell.getNumAdjacentMines() == 8)
            typeOfImage = EIGHT;    

        if (!gameRunning) {
            if (cell.isCovered() && cell.hasMine()) {
                cell.uncover();
                typeOfImage = MINE;
            } else if (!cell.isCovered() && cell.hasMine()) {
                typeOfImage = EXPLODEDMINE;
            } else if (cell.isFlagged()) {
                if (cell.hasMine()) {
                    typeOfImage = FLAG;
                } else {
                    typeOfImage = NOTMINE;
                }
            }
            else {
                typeOfImage = COVERED;
            }
        } else {
            if (cell.isFlagged()) {
                typeOfImage = FLAG;
            } else if (cell.isCovered()) {
                typeOfImage = COVERED;
            }
        }
        return typeOfImage;
    }

    // the visual aspect of the game is updated based on clicks
    public void paint(Graphics g) {
        int coveredCells = 0;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Cell cell = cells[i][j];

                if (cell.isCovered()) {
                    coveredCells += 1;
                }

                if (gameRunning && cell.hasMine() && !cell.isCovered()) {
                    gameRunning = false;
                }

                int xPos = CELLSIZE * j;
                int yPos = CELLSIZE * i;
                BufferedImage kindOfImage = correctImage(cell);
                g.drawImage(kindOfImage, xPos, yPos, this);
            }
        }
        
        long now = System.currentTimeMillis();
        long elapsed = now - start;
        if (elapsed > 5000) {
            timer.stop();
            statusPanel.setText("Game Over. You Lose! :(");
            Restart();
        }
        
        if (gameRunning && coveredCells == 40) {
            gameRunning = false;
            timer.stop();
            duration = timer.getInitialDelay();
            statusPanel.setText("You Won! :)");
        } else if (!gameRunning) {
            timer.stop();
            statusPanel.setText("Game Over. You Lose! :(");
            Restart();
        }
    }
    
    // uncovers all the neighboring cells when a blank cell has been clicked
    private void uncoverNeighboring(int x, int y) {
        for (int i = -1; i < 2; i++) {
            int tempX = x + i;
            if (tempX < 0 || tempX >= ROWS) {
                continue;
            }

            for (int j = -1; j < 2; j++) {
                int tempY = y + j;
                if (tempY < 0 || tempY >= COLUMNS) {
                    continue;
                }

                Cell cell = cells[tempX][tempY];
                if (cell.isCovered() && !cell.isEmpty()) {
                    cell.uncover();
                }
            }
        }
    }
   
    // once a empty cell is clicked, other empty cells around it 
    // are found using recursion
    public void searchForEmpty(int x, int y) {
        for (int i = -1; i < 2; i++) {
            int tempX = x + i;
            if (tempX < 0 || tempX >= ROWS) {
                continue;
            }

            for (int j = -1; j < 2; j++) {
                int tempY = y + j;
                if (tempY < 0 || tempY >= COLUMNS) {
                    continue;
                }

                Cell cell = cells[tempX][tempY];
                if (!cell.isClicked() && cell.isEmpty()) {
                    cell.uncover();
                    cell.click();

                    uncoverNeighboring(tempX, tempY);
                    searchForEmpty(tempX, tempY);
                }
            }
        }
    }
    
    // restarts and creates a new game
    public void Restart() {
        restart = new JFrame("Restart?");
        restart.setPreferredSize(new Dimension(300, 60));
     
        JPanel control_panel = new JPanel();
        restart.add(control_panel, BorderLayout.NORTH);
        
        JButton yes = new JButton("Yes");
        yes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setUpGame();
                repaint();
                restart.dispose();
            }
        });
        
        JButton no = new JButton("No");
        no.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        control_panel.add(yes);
        control_panel.add(no);
        
        restart.pack();
        restart.setVisible(true);
    }
    
    class MinesAdapter extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (!gameRunning) {
                return;
            }
            else {
                int rowPressed = e.getY() / CELLSIZE;
                int columnPressed = e.getX() / CELLSIZE;
                boolean redraw = false;   

                if ((rowPressed < 0 || rowPressed >= ROWS) ||
                        (columnPressed < 0 || columnPressed >= COLUMNS)) {
                    return;
                }

                Cell cellPressed = cells[rowPressed][columnPressed];

                if (e.getButton() == MouseEvent.BUTTON3) {
                    redraw = true;

                    if (!cellPressed.isCovered()) {
                        return;
                    }
                    if (cellPressed.isFlagged()) {
                        cellPressed.unflag();
                        minesLeft += 1;
                    } else {
                        cellPressed.flag();
                        minesLeft -= 1;
                    }
                    statusPanel.setText(Integer.toString(minesLeft) + " Mines Remaining");
                } else {
                    if (!cellPressed.isCovered() || cellPressed.isFlagged()) {
                        return;
                    }

                    redraw = true;

                    cellPressed.uncover();
                    if (cellPressed.hasMine()) {
                        gameRunning = false;
                        
                    } else if (cellPressed.isEmpty()) {
                        searchForEmpty(rowPressed, columnPressed);
                    }
                }

                if (redraw) {
                    repaint();
                }
            }
        }
    }  
}