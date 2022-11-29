/*
* This interface must be implemented by all the classes that make possible to the Maze class to draw Mazes
* */
public interface DrawableMaze {
    public int getRows();
    public int getCols();
    public int[][] getMazeStructure();
    public BlockPoint getStart();
    public BlockPoint getEnd();
}
