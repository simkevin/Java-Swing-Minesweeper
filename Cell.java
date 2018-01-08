public class Cell {

    private int number;
    private boolean isCovered;
    private boolean isFlagged;
    private boolean isClicked;
    private boolean isMine; 
    
    public Cell() {
        this.number = 0;
        this.isClicked = false;
        this.isCovered = true;
        this.isFlagged  = false;
        this.isMine  = false;
    }
    
    public boolean isCovered() {
        return this.isCovered;
    }
    
    public void uncover() {
        this.isCovered = false;
    }
    
    public boolean isClicked() {
        return this.isClicked;
    }
    
    public void click() {
        this.isClicked = true;
    }

    public void unclick() {
        this.isClicked = false;
    }
    
    public void setNumAdjacentMines(int num) {
        this.number = num;
    }

    public int getNumAdjacentMines() {
        return this.number;
    }
    
    public boolean isFlagged() {
        return this.isFlagged;
    }
    
    public void flag() {
        this.isFlagged = true;
    }
    
    public void unflag() {
        this.isFlagged = false;
    }

    public boolean isEmpty() {
        return this.number == 0;
    }

    public boolean hasMine() {
        return this.isMine;
    }

    public void placeMine() {
        this.isMine = true;
    }
    
}