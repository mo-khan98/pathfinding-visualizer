package model;

/**
 * Represents a single cell in the grid.
 */
public class Cell {
    /** Row and column position */
    public int row, col;
    
    /** Cell type */
    public CellType type;
    
    /** Parent cell for path reconstruction */
    public Cell parent;
    
    /** Distance from start */
    public int gCost;
    
    /** Heuristic distance to end */
    public int hCost;
    
    /** Total cost */
    public int fCost;
    
    /**
     * Creates a new cell.
     * 
     * @param row Row index
     * @param col Column index
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.type = CellType.EMPTY;
        this.gCost = 0;
        this.hCost = 0;
        this.fCost = 0;
    }
    
    /**
     * Cell types.
     */
    public enum CellType {
        EMPTY,
        WALL,
        START,
        END,
        PATH,
        VISITED
    }
}

