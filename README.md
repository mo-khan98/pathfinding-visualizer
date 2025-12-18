# Pathfinding Algorithm Visualizer

A Java Swing application for visualizing pathfinding algorithms on a grid. Supports A*, Dijkstra, BFS, and DFS algorithms with interactive wall placement and real-time visualization.

## Implementation

The application uses a grid-based model where each cell can be empty, a wall, start, end, visited, or part of the path. Algorithms are implemented as separate classes implementing the Pathfinder interface, and the VisualizerPanel handles the GUI rendering and user interactions.

## How to Run

Compile all Java files and run Main.java:

```
javac *.java algorithms/*.java model/*.java ui/*.java
java Main
```

