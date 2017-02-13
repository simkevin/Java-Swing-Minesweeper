import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CellTest {

    private Cell c;
    
    @Before
    public void setUp() {
        c = new Cell();
    }
    
    @Test
    public void Covered () {
        assertTrue(c.isCovered());
    }
    
    @Test
    public void Click() {
        c.click();
        assertTrue(c.isClicked());
    }
    
    @Test
    public void SetMines() {
        c.numAdjacentMines(3);
        assertEquals(3, c.getNumber());
    }
    
    @Test
    public void Flag() {
        c.flag(true);
        assertTrue(c.isFlagged());
    }
    
    @Test
    public void HasMine() {
        assertFalse(c.hasMine());
    }
    
    @Test
    public void PlaceMine() {
        c.placeMine(true);
        assertTrue(c.hasMine());
    }
}
