package algorithms;

import model.*;
import ui.*;
import java.util.*;

/**
 * A* pathfinding algorithm using Manhattan distance heuristic.
 */
public class AStar implements Pathfinder {
    private Grid grid;
    private VisualizerPanel panel;
    
    /**
     * Creates an A* pathfinder.
     * 
     * @param grid The grid to search
     * @param panel The visualization panel
     */
    public AStar(Grid grid, VisualizerPanel panel) {
        this.grid = grid;
        this.panel = panel;
    }
    
    /**
     * Finds path using A* algorithm.
     * 
     * @return true if path found, false otherwise
     * @throws InterruptedException
     */
    public boolean findPath() throws InterruptedException {
        grid.clearPath();
        
        PriorityQueue<Cell> openSet = new PriorityQueue<>((a, b) -> {
            if (a.fCost != b.fCost) return a.fCost - b.fCost;
            return a.hCost - b.hCost;
        });
        
        Set<Cell> closedSet = new HashSet<>();
        Cell start = grid.getStartCell();
        Cell end = grid.getEndCell();
        
        start.gCost = 0;
        start.hCost = heuristic(start, end);
        start.fCost = start.gCost + start.hCost;
        openSet.add(start);
        
        while (!openSet.isEmpty()) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            
            Cell current = openSet.poll();
            
            if (current == end) {
                reconstructPath(current);
                return true;
            }
            
            closedSet.add(current);
            
            if (current != start && current != end) {
                current.type = Cell.CellType.VISITED;
                panel.repaint();
                Thread.sleep(10);
            }
            
            for (Cell neighbor : grid.getNeighbors(current)) {
                if (closedSet.contains(neighbor)) continue;
                
                int tentativeGCost = current.gCost + 1;
                
                if (!openSet.contains(neighbor)) {
                    neighbor.gCost = tentativeGCost;
                    neighbor.hCost = heuristic(neighbor, end);
                    neighbor.fCost = neighbor.gCost + neighbor.hCost;
                    neighbor.parent = current;
                    openSet.add(neighbor);
                } else if (tentativeGCost < neighbor.gCost) {
                    neighbor.gCost = tentativeGCost;
                    neighbor.fCost = neighbor.gCost + neighbor.hCost;
                    neighbor.parent = current;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Calculates Manhattan distance between two cells.
     */
    private int heuristic(Cell a, Cell b) {
        return Math.abs(a.row - b.row) + Math.abs(a.col - b.col);
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

