public class Cell {
    // fields
    private int number;
    
    private boolean isCovered;
    private boolean isFlagged;
    private boolean isClicked;
    private boolean isMine; 
    
    // constructor
    public Cell() {
        this.number = 0;
        this.isClicked = false;
        this.isCovered = true;
        this.isFlagged  = false;
        this.isMine  = false;
    }
    
    // methods
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
    
    public void numAdjacentMines(int num) {
        this.number = num;
    }

    public int getNumber() {
        return this.number;
    }
    
    public boolean isFlagged() {
        return this.isFlagged;
    }
    
    public void flag(boolean flag) {
        this.isFlagged = flag;
    }

    public boolean isEmpty() {
        return this.number == 0;
    }

    public boolean hasMine() {
        return this.isMine;
    }

    public void placeMine(boolean mine) {
        this.isMine = mine;
    }
    
}