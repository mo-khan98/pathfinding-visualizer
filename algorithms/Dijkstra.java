package algorithms;

import model.*;
import ui.*;
import java.util.*;

/**
 * Dijkstra's pathfinding algorithm.
 */
public class Dijkstra implements Pathfinder {
    private Grid grid;
    private VisualizerPanel panel;
    
    /**
     * Creates a Dijkstra pathfinder.
     * 
     * @param grid The grid to search
     * @param panel The visualization panel
     */
    public Dijkstra(Grid grid, VisualizerPanel panel) {
        this.grid = grid;
        this.panel = panel;
    }
    
    /**
     * Finds path using Dijkstra's algorithm.
     * 
     * @return true if path found, false otherwise
     * @throws InterruptedException
     */
    public boolean findPath() throws InterruptedException {
        grid.clearPath();
        
        PriorityQueue<Cell> queue = new PriorityQueue<>((a, b) -> a.gCost - b.gCost);
        Set<Cell> visited = new HashSet<>();
        Cell start = grid.getStartCell();
        Cell end = grid.getEndCell();
        
        start.gCost = 0;
        queue.add(start);
        
        while (!queue.isEmpty()) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            
            Cell current = queue.poll();
            
            if (visited.contains(current)) continue;
            visited.add(current);
            
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
                if (visited.contains(neighbor)) continue;
                
                int newCost = current.gCost + 1;
                
                if (newCost < neighbor.gCost || neighbor.gCost == 0) {
                    neighbor.gCost = newCost;
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

