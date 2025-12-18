package algorithms;

import model.*;
import ui.*;
import java.util.*;

/**
 * Depth-First Search pathfinding algorithm.
 */
public class DFS implements Pathfinder {
    private Grid grid;
    private VisualizerPanel panel;
    
    /**
     * Creates a DFS pathfinder.
     * 
     * @param grid The grid to search
     * @param panel The visualization panel
     */
    public DFS(Grid grid, VisualizerPanel panel) {
        this.grid = grid;
        this.panel = panel;
    }
    
    /**
     * Finds path using DFS algorithm.
     * 
     * @return true if path found, false otherwise
     * @throws InterruptedException
     */
    public boolean findPath() throws InterruptedException {
        grid.clearPath();
        
        Stack<Cell> stack = new Stack<>();
        Set<Cell> visited = new HashSet<>();
        Cell start = grid.getStartCell();
        Cell end = grid.getEndCell();
        
        stack.push(start);
        visited.add(start);
        
        while (!stack.isEmpty()) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            
            Cell current = stack.pop();
            
            if (current == end) {
                reconstructPath(current);
                return true;
            }
            
            if (current != start && current != end) {
                current.type = Cell.CellType.VISITED;
                panel.repaint();
                Thread.sleep(10);
            }
            
            for (Cell neighbor : grid.getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    neighbor.parent = current;
                    stack.push(neighbor);
                }
            }
        }
        
        return false;
    }
    
    /**
     * Reconstructs path by following parent pointers.
     */
    private void reconstructPath(Cell current) {
        while (current != null && current != grid.getStartCell()) {
            if (current != grid.getEndCell()) {
                current.type = Cell.CellType.PATH;
            }
            current = current.parent;
        }
        panel.repaint();
    }
}

