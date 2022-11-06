import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Block {

    //position of the block in the maze
    //it goes from 0 to Maze.rows-1 and Maze.cols-1
    private int thisRow;
    private int thisCol;
    //the distance from the top-left point of the mazePanel
    private int paddingFromLeft = 5;
    private int getPaddingFromTop = 5;
    private boolean isStart;
    private boolean isEnd;
    private boolean isColored;
    //the color of the block if it is colored
    private Color color;

    //the coordinate of the top-left vertex block inside the panel
    private int x;
    private int y;

    //true = there's a wall; false = there's not a wall
    private boolean top = true;
    private boolean down = true;
    private boolean right = true;
    private boolean left = true;
    //the dimension of each wall of the block
    private int size;
    //the list of all the adjacent blocks this block can reach
    private List<Block> neighbors = new ArrayList<>();
    //the pathCost from the start block to this block
    private int pathCost = 0;


    public Block(int row, int col, boolean top, boolean down, boolean right, boolean left, int size){
        this.thisRow = row;
        this.thisCol = col;
        this.top = top;
        this.right = right;
        this.down = down;
        this.left = left;
        this.size = size;
    }

    //when this constructor is called, all walls are true
    public Block(int row, int col, int size){
        this.thisRow = row;
        this.thisCol = col;
        this.size = size;
    }

    public void setPaddingFromLeft(int padding){
        this.paddingFromLeft = padding;
    }

    public void setIsStart(boolean isStart) {
        this.isStart = isStart;
    }

    public void setIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public void addNeighbor(Block b){
        neighbors.add(b);
    }

    public boolean getIsStart() {
        return isStart;
    }

    public boolean getIsEnd() {
        return isEnd;
    }

    public boolean hasRightWall(){
        return right;
    }

    public boolean hasLeftWall(){
        return left;
    }

    public boolean hasTopWall(){
        return top;
    }

    public boolean hasDownWall(){
        return down;
    }

    public List<Block> getNeighbors() {
        return neighbors;
    }

    public void setIsColored(boolean isColored){
        this.isColored = isColored;
    }

    public int getThisRow() {
        return thisRow;
    }

    public int getThisCol() {
        return thisCol;
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void initCoordinates(){
        this.x = paddingFromLeft + thisCol*size;
        this.y = getPaddingFromTop + thisRow*size;
    }

    @Override
    public String toString() {
        return "[Row: " + thisRow +"; Col: " +thisCol+"]";
    }

    public void paintComponent(Graphics2D g){
        g.setColor(Color.WHITE);

        this.initCoordinates();

        if(this.top){
            Line2D.Double top = new Line2D.Double(x,y,x+size,y);
            g.draw(top);
        }
        if(this.right){
            Line2D.Double right = new Line2D.Double(x+size,y,x+size,y+size);
            g.draw(right);
        }
        if(this.down){
            Line2D.Double down = new Line2D.Double(x,y+size,x+size,y+size);
            g.draw(down);
        }
        if(this.left){
            Line2D.Double left = new Line2D.Double(x,y,x,y+size);
            g.draw(left);
        }

        //draw an empty circle if this is a start block
        //draw a red circle if this is an end block
        if(isStart){
            g.drawOval(x+size/4,y+size/4,size/2,size/2);
        }
        if(isEnd){
            g.setColor(Color.RED);
            g.fillOval(x+size/4,y+size/4,size/2,size/2);
        }
        if(isColored){
            g.setColor(color);
            g.fillOval(x+(3*size/8),y+3*(size/8), size/4, size/4);
        }
    }

}