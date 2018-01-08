import org.junit.Test;
import static org.junit.Assert.*;

public class CellTest {
    
    @Test
    public void testCell() {
        Cell c = new Cell();
        assertNotEquals(c, null);
    }
    
    @Test
    public void testIsCovered () {
        Cell c = new Cell();
        assertTrue(c.isCovered());
    }
    
    @Test
    public void testUncover () {
        Cell c = new Cell();
        c.uncover();
        assertFalse(c.isCovered());
    }
    
    @Test
    public void testIsClicked() {
        Cell c = new Cell();
        assertFalse(c.isClicked());
    }
    
    @Test
    public void testClick() {
        Cell c = new Cell();
        c.click();
        assertTrue(c.isClicked());
    }
    
    @Test
    public void testUnclick() {
        Cell c = new Cell();
        c.click();
        assertTrue(c.isClicked());
        c.unclick();
        assertFalse(c.isClicked());
    }
    
    @Test
    public void testGetNumAdjacentMines() {
        Cell c = new Cell();
        assertEquals(c.getNumAdjacentMines(), 0);
    }
    
    @Test
    public void testNumAdjacentMines() {
        Cell c = new Cell();
        c.setNumAdjacentMines(3);
        assertEquals(c.getNumAdjacentMines(), 3);
    }
    
    @Test
    public void testIsFlagged() {
        Cell c = new Cell();
        assertFalse(c.isFlagged());
    }

    @Test
    public void testFlag() {
        Cell c = new Cell();
        c.flag();
        assertTrue(c.isFlagged());
    }
    
    @Test
    public void testUnflag() {
        Cell c = new Cell();
        c.flag();
        assertTrue(c.isFlagged());
        c.unflag();
        assertFalse(c.isFlagged());
    }
    
    @Test
    public void testIsEmptyTrue() {
        Cell c = new Cell();
        assertTrue(c.isEmpty());
    }
    
    @Test
    public void testIsEmptyFalse() {
        Cell c = new Cell();
        c.setNumAdjacentMines(4);
        assertFalse(c.isEmpty());
    }
    
    @Test
    public void testHasMine() {
        Cell c = new Cell();
        assertFalse(c.hasMine());
    }
    
    @Test
    public void testPlaceMine() {
        Cell c = new Cell();
        c.placeMine();
        assertTrue(c.hasMine());
    }
}
