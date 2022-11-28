/**
 * The PresetMaze class specifies the structure of the default mazes
 */

public enum PresetMaze {
    Default11x11(11,11,0);

    //The number of rows of the maze
    private int rows;
    //The number of columns of the maze
    private int cols;
    //each block of the maze has a number assigned, indicating the walls it has
    //1: top
    //2: right
    //3: down
    //4: left
    //5: top, right
    //6: top, down
    //7: top, left
    //8: right, down
    //9: right, left
    //10: down, left
    //11: top, right, down
    //12: top, right, left
    //13: top, down, left
    //14: right, down, left
    //15: all
    private int mazeStructure[][];
    private BlockPoint start;
    private BlockPoint end;
    private PresetMaze(int rows, int cols, int mazeType){
        this.rows = rows;
        this.cols = cols;

        if(mazeType == 0){
            mazeStructure = new int[][]{
                    {13, 1, 6, 1, 6, 6, 6, 6, 6, 5, 12},
                    {13, 3, 5, 9, 13, 6, 1, 6, 5, 4, 2},
                    {12, 7, 2, 4, 6, 6, 3, 11, 9, 9, 14},
                    {9, 9, 9, 9, 7, 6, 5, 7, 8, 9, 12},
                    {9, 9, 9, 9, 9, 12, 4, 2, 13, 8, 9},
                    {4, 2, 4, 8, 4, 2, 9, 4, 1, 6, 8},
                    {9, 9, 10, 6, 2, 9, 9, 9, 10, 6, 5},
                    {9, 10, 6, 11, 9, 9, 9, 10, 6, 5, 14},
                    {10, 6, 1, 11, 14, 9, 10, 6, 1, 3, 11},
                    {7, 6, 3, 6, 6, 8, 7, 11, 9, 7, 5},
                    {14, 13, 6, 6, 6, 6, 3, 6, 3, 8, 14}
            };

            this.start = new BlockPoint(1,0);
            this.end = new BlockPoint(8,10);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public BlockPoint getStart() {
        return start;
    }

    public BlockPoint getEnd() {
        return end;
    }

    public int[][] getMazeStructure() {
        return mazeStructure;
    }
}
