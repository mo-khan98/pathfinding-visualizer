package ui;

import model.*;
import algorithms.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Panel for displaying and interacting with the grid.
 */
public class VisualizerPanel extends JPanel {
    private Grid grid;
    private int cellSize = 20;
    private Pathfinder pathfinder;
    private String mode = "WALL"; // WALL, START, END
    private Thread pathfindingThread;
    
    /**
     * Creates visualizer panel.
     * 
     * @param grid The grid to visualize
     */
    public VisualizerPanel(Grid grid) {
        this.grid = grid;
        this.pathfinder = new AStar(grid, this);
        
        setPreferredSize(new Dimension(grid.getCols() * cellSize, grid.getRows() * cellSize));
        setBackground(Color.WHITE);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY(), e.getButton());
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int button = SwingUtilities.isRightMouseButton(e) ? MouseEvent.BUTTON3 : MouseEvent.BUTTON1;
                handleMouseClick(e.getX(), e.getY(), button);
            }
        });
    }
    
    /**
     * Handles mouse clicks.
     */
    private void handleMouseClick(int x, int y, int button) {
        int col = x / cellSize;
        int row = y / cellSize;
        
        Cell cell = grid.getCell(row, col);
        if (cell == null) return;
        
        if (button == MouseEvent.BUTTON3 || (button & MouseEvent.BUTTON3_DOWN_MASK) != 0) {
            // Right click to remove a wall
            if (cell.type == Cell.CellType.WALL) {
                grid.setCellType(row, col, Cell.CellType.EMPTY);
            }
        } else {
            // Left click, changes depending on mode
            if (mode.equals("START")) {
                grid.setCellType(row, col, Cell.CellType.START);
                this.mode = "WALL"; // Switch back to wall mode after placing
            } else if (mode.equals("END")) {
                grid.setCellType(row, col, Cell.CellType.END);
                this.mode = "WALL"; // Switch back to wall mode after placing
            } else {
                // WALL mode
                if (cell.type == Cell.CellType.EMPTY) {
                    grid.setCellType(row, col, Cell.CellType.WALL);
                }
            }
        }
        
        repaint();
    }
    
    /**
     * Paints the grid.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getCols(); j++) {
                Cell cell = grid.getCell(i, j);
                int x = j * cellSize;
                int y = i * cellSize;
                
                switch (cell.type) {
                    case EMPTY:
                        g.setColor(Color.WHITE);
                        break;
                    case WALL:
                        g.setColor(Color.BLACK);
                        break;
                    case START:
                        g.setColor(Color.GREEN);
                        break;
                    case END:
                        g.setColor(Color.RED);
                        break;
                    case VISITED:
                        g.setColor(Color.CYAN);
                        break;
                    case PATH:
                        g.setColor(Color.YELLOW);
                        break;
                }
                
                g.fillRect(x, y, cellSize, cellSize);
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(x, y, cellSize, cellSize);
            }
        }
    }
    
    /**
     * Starts pathfinding algorithm.
     */
    public void findPath() {
        // Stop any running pathfinding
        if (pathfindingThread != null && pathfindingThread.isAlive()) {
            pathfindingThread.interrupt();
        }
        
        pathfindingThread = new Thread(() -> {
            try {
                boolean found = pathfinder.findPath();
                if (!found && !Thread.currentThread().isInterrupted()) {
                    JOptionPane.showMessageDialog(this, "No path found!");
                }
            } catch (InterruptedException e) {
                // Pathfinding was interrupted, which is fine
            }
        });
        pathfindingThread.start();
    }
    
    /**
     * Clears path visualization.
     */
    public void clearPath() {
        // Stop any running pathfinding
        if (pathfindingThread != null && pathfindingThread.isAlive()) {
            pathfindingThread.interrupt();
        }
        
        grid.clearPath();
        repaint();
    }
    
    /**
     * Sets pathfinding algorithm.
     */
    public void setAlgorithm(Pathfinder algorithm) {
        this.pathfinder = algorithm;
    }
    
    /**
     * Sets mode.
     */
    public void setMode(String mode) {
        this.mode = mode;
    }
    
    /**
     * Resets grid to initial state.
     */
    public void reset() {
        // Stop any running pathfinding
        if (pathfindingThread != null && pathfindingThread.isAlive()) {
            pathfindingThread.interrupt();
        }
        
        grid.clearPath();
        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getCols(); j++) {
                Cell cell = grid.getCell(i, j);
                if (cell.type == Cell.CellType.WALL) {
                    cell.type = Cell.CellType.EMPTY;
                }
            }
        }
        grid.setCellType(0, 0, Cell.CellType.START);
        grid.setCellType(grid.getRows() - 1, grid.getCols() - 1, Cell.CellType.END);
        repaint();
    }
    
    /**
     * Stops running pathfinding algorithm.
     */
    public void stopPathfinding() {
        if (pathfindingThread != null && pathfindingThread.isAlive()) {
            pathfindingThread.interrupt();
        }
    }
}

