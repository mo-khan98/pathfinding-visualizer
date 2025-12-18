package algorithms;

import model.*;
import ui.*;
import java.util.*;

/**
 * Breadth-First Search pathfinding algorithm.
 */
public class BFS implements Pathfinder {
    private Grid grid;
    private VisualizerPanel panel;
    
    /**
     * Creates a BFS pathfinder.
     * 
     * @param grid The grid to search
     * @param panel The visualization panel
     */
    public BFS(Grid grid, VisualizerPanel panel) {
        this.grid = grid;
        this.panel = panel;
    }
    
    /**
     * Finds path using BFS algorithm.
     * 
     * @return true if path found, false otherwise
     * @throws InterruptedException
     */
    public boolean findPath() throws InterruptedException {
        grid.clearPath();
        
        Queue<Cell> queue = new LinkedList<>();
        Set<Cell> visited = new HashSet<>();
        Cell start = grid.getStartCell();
        Cell end = grid.getEndCell();
        
        queue.add(start);
        visited.add(start);
        
        while (!queue.isEmpty()) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            
            Cell current = queue.poll();
            
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
                    queue.add(neighbor);
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

