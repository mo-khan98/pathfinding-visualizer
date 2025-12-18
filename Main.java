import model.*;
import ui.*;
import algorithms.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Entry point for the application.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Grid grid = new Grid(30, 30);
            VisualizerPanel panel = new VisualizerPanel(grid);
            
            JFrame frame = new JFrame("Pathfinding Visualizer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            
            JPanel controlPanel = new JPanel(new BorderLayout());
            
            JPanel buttonPanel = new JPanel();
            JButton findPathButton = new JButton("Find Path");
            JButton clearButton = new JButton("Clear Path");
            JButton resetButton = new JButton("Reset");
            JButton chooseStartButton = new JButton("Choose Start");
            JButton chooseEndButton = new JButton("Choose End");
            JButton placeWallsButton = new JButton("Place Walls");
            
            findPathButton.addActionListener(e -> panel.findPath());
            clearButton.addActionListener(e -> panel.clearPath());
            resetButton.addActionListener(e -> panel.reset());
            chooseStartButton.addActionListener(e -> panel.setMode("START"));
            chooseEndButton.addActionListener(e -> panel.setMode("END"));
            placeWallsButton.addActionListener(e -> panel.setMode("WALL"));
            
            buttonPanel.add(findPathButton);
            buttonPanel.add(clearButton);
            buttonPanel.add(resetButton);
            buttonPanel.add(chooseStartButton);
            buttonPanel.add(chooseEndButton);
            buttonPanel.add(placeWallsButton);
            
            JPanel algorithmPanel = new JPanel();
            algorithmPanel.setBorder(BorderFactory.createTitledBorder("Algorithm"));
            ButtonGroup algorithmGroup = new ButtonGroup();
            JRadioButton aStarButton = new JRadioButton("A*", true);
            JRadioButton dijkstraButton = new JRadioButton("Dijkstra");
            JRadioButton bfsButton = new JRadioButton("BFS");
            JRadioButton dfsButton = new JRadioButton("DFS");
            
            algorithmGroup.add(aStarButton);
            algorithmGroup.add(dijkstraButton);
            algorithmGroup.add(bfsButton);
            algorithmGroup.add(dfsButton);
            
            aStarButton.addActionListener(e -> panel.setAlgorithm(new AStar(grid, panel)));
            dijkstraButton.addActionListener(e -> panel.setAlgorithm(new Dijkstra(grid, panel)));
            bfsButton.addActionListener(e -> panel.setAlgorithm(new BFS(grid, panel)));
            dfsButton.addActionListener(e -> panel.setAlgorithm(new DFS(grid, panel)));
            
            algorithmPanel.add(aStarButton);
            algorithmPanel.add(dijkstraButton);
            algorithmPanel.add(bfsButton);
            algorithmPanel.add(dfsButton);
            
            controlPanel.add(algorithmPanel, BorderLayout.NORTH);
            controlPanel.add(buttonPanel, BorderLayout.SOUTH);
            
            // Wrap panel in a container to center it
            JPanel centerPanel = new JPanel(new GridBagLayout());
            centerPanel.setBackground(Color.LIGHT_GRAY);
            centerPanel.add(panel);
            
            frame.add(centerPanel, BorderLayout.CENTER);
            frame.add(controlPanel, BorderLayout.SOUTH);
            
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    panel.stopPathfinding();
                    System.exit(0);
                }
            });
            
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

