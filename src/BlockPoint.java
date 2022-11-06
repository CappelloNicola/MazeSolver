//this class is used to specify the starting and ending block inside Preset Mazes
public class BlockPoint {
    //the position of the block inside the mazeStructure 2D array
    private int row;
    private int col;

    public BlockPoint(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
