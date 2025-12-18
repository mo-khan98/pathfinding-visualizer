package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the pathfinding grid.
 */
public class Grid {
    private Cell[][] cells;
    private int rows, cols;
    private Cell startCell, endCell;
    
    /**
     * Creates a new grid.
     * 
     * @param rows Number of rows
     * @param cols Number of columns
     */
    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
        
        // Set default start and end
        startCell = cells[0][0];
        startCell.type = Cell.CellType.START;
        endCell = cells[rows - 1][cols - 1];
        endCell.type = Cell.CellType.END;
    }
    
    /**
     * Gets cell at position.
     * 
     * @param row Row index
     * @param col Column index
     * @return Cell or null if out of bounds
     */
    public Cell getCell(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return cells[row][col];
        }
        return null;
    }
    
    /**
     * Sets cell type at position.
     * 
     * @param row Row index
     * @param col Column index
     * @param type New cell type
     */
    public void setCellType(int row, int col, Cell.CellType type) {
        Cell cell = getCell(row, col);
        if (cell != null) {
            if (type == Cell.CellType.START) {
                if (startCell != null) {
                    startCell.type = Cell.CellType.EMPTY;
                }
                startCell = cell;
            } else if (type == Cell.CellType.END) {
                if (endCell != null) {
                    endCell.type = Cell.CellType.EMPTY;
                }
                endCell = cell;
            }
            cell.type = type;
        }
    }
    
    /**
     * Clears path visualization data.
     */
    public void clearPath() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (cells[i][j].type == Cell.CellType.PATH || 
                    cells[i][j].type == Cell.CellType.VISITED) {
                    cells[i][j].type = Cell.CellType.EMPTY;
                }
                cells[i][j].parent = null;
                cells[i][j].gCost = 0;
                cells[i][j].hCost = 0;
                cells[i][j].fCost = 0;
            }
        }
        if (startCell != null) startCell.type = Cell.CellType.START;
        if (endCell != null) endCell.type = Cell.CellType.END;
    }
    
    /**
     * Gets traversable neighbors of a cell.
     * 
     * @param cell The cell
     * @return List of neighbors
     */
    public List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        
        for (int i = 0; i < 4; i++) {
            Cell neighbor = getCell(cell.row + dr[i], cell.col + dc[i]);
            if (neighbor != null && neighbor.type != Cell.CellType.WALL) {
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }
    
    /**
     * Gets start cell.
     */
    public Cell getStartCell() {
        return startCell;
    }
    
    /**
     * Gets end cell.
     */
    public Cell getEndCell() {
        return endCell;
    }
    
    /**
     * Gets number of rows.
     */
    public int getRows() {
        return rows;
    }
    
    /**
     * Gets number of columns.
     */
    public int getCols() {
        return cols;
    }
}

