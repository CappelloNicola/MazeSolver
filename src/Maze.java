import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class Maze extends JComponent {

    //number of blocks
    private int rows;
    private int cols;
    //the width of the maze is equal to the number of block per columns * blockSize
    private int mazeWidth;
    private int blockPadding;
    private int [][] mazeStructure;
    private BlockPoint start;
    private BlockPoint end;
    //the size of each block, based upon the dimension of the MazePanel
    private int blockSize;
    private Block[][] blocks;
    private Block startBlock;
    private Block endBlock;
    //keep trace of the current colored block to remove the color after a new paint
    private Block currentColoredBlock = null;

    //construct an existing maze
    //takes the DrawableMaze to construct, and the panel dimension to calculate the block size
    public Maze(DrawableMaze drawableMaze, int panelWidth, int panelHeight){

        //we consider that the first and last rows has a distance of 5 px from the top and bottom of the panel
        //that's why we subtract 10 pixel
        int minWidth = (panelWidth - 10)/drawableMaze.getRows();
        int minHeight = (panelHeight - 10)/ drawableMaze.getCols();
        this.blockSize = Math.min(minWidth, minHeight);
        this.mazeWidth = drawableMaze.getCols() * blockSize;
        this.blockPadding = (panelWidth - mazeWidth)/2;

        this.rows = drawableMaze.getRows();
        this.cols = drawableMaze.getCols();

        mazeStructure = drawableMaze.getMazeStructure();
        this.start = drawableMaze.getStart();
        this.end = drawableMaze.getEnd();

        blocks = new Block[rows][cols];

        //creates the blocks
        for (int i = 0; i<rows; i++){
            for (int j = 0; j<cols; j++){
                Block block = null;

                switch(mazeStructure[i][j]){
                    case 0:
                        block = new Block(i,j,false,false,false,false,blockSize);
                        break;
                    case 1:
                        block = new Block(i,j,true,false,false,false,blockSize);
                        break;
                    case 2:
                        block = new Block(i,j,false,false,true,false,blockSize);
                        break;
                    case 3:
                        block = new Block(i,j,false,true,false,false,blockSize);
                        break;
                    case 4:
                        block = new Block(i,j,false,false,false,true,blockSize);
                        break;
                    case 5:
                        block = new Block(i,j,true,false,true,false,blockSize);
                        break;
                    case 6:
                        block = new Block(i,j,true,true,false,false,blockSize);
                        break;
                    case 7:
                        block = new Block(i,j,true,false,false,true,blockSize);
                        break;
                    case 8:
                        block = new Block(i,j,false,true,true,false,blockSize);
                        break;
                    case 9:
                        block = new Block(i,j,false,false,true,true,blockSize);
                        break;
                    case 10:
                        block = new Block(i,j,false,true,false,true,blockSize);
                        break;
                    case 11:
                        block = new Block(i,j,true,true,true,false,blockSize);
                        break;
                    case 12:
                        block = new Block(i,j,true,false,true,true,blockSize);
                        break;
                    case 13:
                        block = new Block(i,j,true,true,false,true,blockSize);
                        break;
                    case 14:
                        block = new Block(i,j,false,true,true,true,blockSize);
                        break;
                    case 15:
                        block = new Block(i,j,true,true,true,true,blockSize);
                        break;
                }

                blocks[i][j] = block;
                block.setPaddingFromLeft(this.blockPadding);

                if(start.getRow() == i && start.getCol() == j){
                    block.setIsStart(true);
                    this.startBlock = block;
                }
                else if(end.getRow() == i && end.getCol() == j){
                    block.setIsEnd(true);
                    this.endBlock = block;
                }
            }
        }

        //adding the neighbors to each block

        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                Block block = blocks[i][j];
                int neighborRow;
                int neighborCol;

                if(!block.hasRightWall()){
                    neighborRow = i;
                    neighborCol = j+1;
                    if(neighborRow < this.rows){
                        if(neighborCol < this.cols){
                            block.addNeighbor(blocks[neighborRow][neighborCol]);
                        }
                    }
                }
                if(!block.hasLeftWall()){
                    neighborRow = i;
                    neighborCol = j-1;
                    if(neighborRow < this.rows){
                        if(neighborCol >= 0 && neighborCol < this.cols){
                            block.addNeighbor(blocks[neighborRow][neighborCol]);
                        }
                    }
                }
                if(!block.hasTopWall()){
                    neighborRow = i-1;
                    neighborCol = j;
                    if(neighborRow >= 0 && neighborRow < this.rows){
                        if(neighborCol < this.cols){
                            block.addNeighbor(blocks[neighborRow][neighborCol]);
                        }
                    }
                }
                if(!block.hasDownWall()){
                    neighborRow = i+1;
                    neighborCol = j;
                    if(neighborRow < this.rows){
                        if(neighborCol < this.cols){
                            block.addNeighbor(blocks[neighborRow][neighborCol]);
                        }
                    }
                }

            }
        }

    }

    public Block getStartBlock() {
        return startBlock;
    }

    public Block getEndBlock() {
        return endBlock;
    }

    //paint the block in the specified position
    public void paintBlock(int row, int col, Color color){
        currentColoredBlock = blocks[row][col];
        blocks[row][col].setIsColored(true);
        blocks[row][col].setColor(color);
    }

    //Unpaint the current colored block
    public void unPaintBlock(){
        if(currentColoredBlock!=null){
            currentColoredBlock.setIsColored(false);
        }

    }

    public void unpaintBlocks(){
        for (int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                blocks[i][j].setIsColored(false);
            }
        }


    }

    public void paintBlockNeighbors(int row, int col){
        List<Block> neighbors = blocks[row][col].getNeighbors();
        Iterator<Block> iter = neighbors.iterator();
        while(iter.hasNext()){
            Block block = iter.next();
            block.setIsColored(true);
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i<rows; i++){
            for (int j = 0; j<cols; j++){
                blocks[i][j].paintComponent(g2d);
            }
        }

    }
}
