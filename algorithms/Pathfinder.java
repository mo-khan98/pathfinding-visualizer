package algorithms;

/**
 * Interface for pathfinding algorithms.
 */
public interface Pathfinder {
    /**
     * Finds a path from start to end.
     * 
     * @return true if path found, false otherwise
     * @throws InterruptedException
     */
    boolean findPath() throws InterruptedException;
}

