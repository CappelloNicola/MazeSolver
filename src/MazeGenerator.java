import java.util.Random;

public class MazeGenerator implements DrawableMaze{
    //the number of rows and cols of the maze to generate
    private int rows;
    private int cols;
    //mazeStructure according to PresetMaze class
    private int maze[][];
    //used to verify if a block has been visited from the creation algorithm
    private boolean visited[][];
    //position, inside the maze, of start and ending blocks
    private BlockPoint start;
    private BlockPoint end;

    public MazeGenerator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        maze = new int[rows][cols];
        visited = new boolean[rows][cols];

        //each block of the maze has all 4 walls at the start
        for(int i = 0; i<rows; i++){
            for (int j = 0; j<cols; j++){
                maze[i][j] = 15;
                visited[i][j] = false;
            }
        }

        start = new BlockPoint(0,0);
        end = new BlockPoint(this.rows-1, this.cols-1);
        this.createMaze(new BlockPoint(0,0));

        //to create more than one path to the end block
        this.randomDeleteWalls();
    }

    private void randomDeleteWalls() {
        //choose a number from 0 to index-1 (to choose a random neighbor inside the array)
        int lim = Math.max(this.rows, this.cols);
        for(int index = 0; index<lim-1; index++){
            Random random = new Random();
            int i = random.nextInt((this.rows-1) - 1) + 1;
            int j = random.nextInt((this.cols-1) - 1) + 1;
            int wall = random.nextInt(4);
            switch (wall){
                case 0:
                    removeTopWall(new BlockPoint(i,j));
                    removeDownWall(new BlockPoint(i-1,j));
                    break;
                case 1:
                    removeDownWall(new BlockPoint(i,j));
                    removeTopWall(new BlockPoint(i+1,j));
                    break;
                case 2:
                    removeRightWall(new BlockPoint(i,j));
                    removeLeftWall(new BlockPoint(i,j+1));
                    break;
                case 3:
                    removeLeftWall(new BlockPoint(i,j));
                    removeRightWall(new BlockPoint(i,j-1));
                    break;
            }
        }

    }

    public void createMaze(BlockPoint currentNode){
        //mark the current node as visited
        visited[currentNode.getRow()][currentNode.getCol()] = true;

        //get a random, unvisited Neighbor of currentNode
        BlockPoint nextNode = randomUnvisitedNeighbour(currentNode);

        while(nextNode != null){
            //connect currentNode and nextNode, by eliminating the wall between them
            this.connectNodes(currentNode, nextNode);
            //remove one nextNode's wall
            createMaze(nextNode);
            //continue while currentNode has unvisitedNeighbor, else return to the previous method call
            nextNode = randomUnvisitedNeighbour(currentNode);
        }
    }

    private void connectNodes (BlockPoint a, BlockPoint b){
        //to connect two Nodes, a  wall must be deleted
        //what is the position of b in relation to a?
        //if b is a rightNeighbor remove a's right wall and b's left wall
        //if b is a leftNeighbor remove a's left wall and b's right wall
        //if b id a topNeighbor remove a's top wall and b's down wall
        //if b is a downNeighbor remove a's down wall and b's top wall

        if (b.getRow() == a.getRow()+1){
            //then it's downNeighbor
            this.removeDownWall(a);
            this.removeTopWall(b);
        }
        else if(b.getRow() == a.getRow()-1){
            //then it's topNeighbor
            this.removeTopWall(a);
            this.removeDownWall(b);
        }
        else if(b.getCol() == a.getCol()+1){
            //then it's rightNeighbor
            this.removeRightWall(a);
            this.removeLeftWall(b);
        }
        else{
            //it's leftNeighbor
            this.removeLeftWall(a);
            this.removeRightWall(b);
        }
    }

    private void removeTopWall(BlockPoint node){
        int row = node.getRow();
        int col = node.getCol();
        int walls = maze[row][col];

        switch(walls){
            case 1:
                maze[row][col] = 0;
                break;
            case 5:
                maze[row][col] = 2;
                break;
            case 6:
                maze[row][col] = 3;
                break;
            case 7:
                maze[row][col] = 4;
                break;
            case 11:
                maze[row][col] = 8;
                break;
            case 12:
                maze[row][col] = 9;
                break;
            case 13:
                maze[row][col] = 10;
                break;
            case 15:
                maze[row][col] = 14;
                break;
        }
    }

    private void removeDownWall(BlockPoint node){
        int row = node.getRow();
        int col = node.getCol();
        int walls = maze[row][col];

        switch(walls){
            case 3:
                maze[row][col] = 0;
                break;
            case 6:
                maze[row][col] = 1;
                break;
            case 8:
                maze[row][col] = 2;
                break;
            case 10:
                maze[row][col] = 4;
                break;
            case 11:
                maze[row][col] = 5;
                break;
            case 13:
                maze[row][col] = 7;
                break;
            case 14:
                maze[row][col] = 9;
                break;
            case 15:
                maze[row][col] = 12;
                break;
        }
    }

    private void removeRightWall(BlockPoint node){
        int row = node.getRow();
        int col = node.getCol();
        int walls = maze[row][col];

        switch(walls){
            case 2:
                maze[row][col] = 0;
                break;
            case 5:
                maze[row][col] = 1;
                break;
            case 8:
                maze[row][col] = 3;
                break;
            case 9:
                maze[row][col] = 4;
                break;
            case 11:
                maze[row][col] = 6;
                break;
            case 12:
                maze[row][col] = 7;
                break;
            case 14:
                maze[row][col] = 10;
                break;
            case 15:
                maze[row][col] = 13;
                break;
        }
    }

    private void removeLeftWall(BlockPoint node){
        int row = node.getRow();
        int col = node.getCol();
        int walls = maze[row][col];

        switch(walls){
            case 4:
                maze[row][col] = 0;
                break;
            case 7:
                maze[row][col] = 1;
                break;
            case 9:
                maze[row][col] = 2;
                break;
            case 10:
                maze[row][col] = 3;
                break;
            case 12:
                maze[row][col] = 5;
                break;
            case 13:
                maze[row][col] = 6;
                break;
            case 14:
                maze[row][col] = 8;
                break;
            case 15:
                maze[row][col] = 11;
                break;
        }
    }

    /*
    * @returns BlockPoint of a random, unvisited Neighbor. If all neighbors are visited returns null
    * */
    private BlockPoint randomUnvisitedNeighbour(BlockPoint node){
        //get all unvisited neighbors
        BlockPoint[] unvisitedNeighbors = new BlockPoint[4];
        int index = 0;

        //Has the node a left Neighbor?
        BlockPoint leftNeighbor = new BlockPoint(node.getRow(), node.getCol()-1);
        if(leftNeighbor.getCol() >= 0){
            //Is the neighbor visited?
            if(!visited[leftNeighbor.getRow()][leftNeighbor.getCol()]){
                unvisitedNeighbors[index] = leftNeighbor;
                index++;
            }
        }

        //Has the node a right Neighbor?
        BlockPoint rightNeighbor = new BlockPoint(node.getRow(), node.getCol()+1);
        if(rightNeighbor.getCol() < this.cols){
            //Is the neighbor visited?
            if(!visited[rightNeighbor.getRow()][rightNeighbor.getCol()]){
                unvisitedNeighbors[index] = rightNeighbor;
                index++;
            }
        }

        //Has the node a top Neighbor?
        BlockPoint topNeighbor = new BlockPoint(node.getRow()-1, node.getCol());
        if(topNeighbor.getRow() >= 0){
            //Is the neighbor visited?
            if(!visited[topNeighbor.getRow()][topNeighbor.getCol()]){
                unvisitedNeighbors[index] = topNeighbor;
                index++;
            }
        }

        //Has the node a down Neighbor?
        BlockPoint downNeighbor = new BlockPoint(node.getRow()+1, node.getCol());
        if(downNeighbor.getRow() < this.rows){
            //Is the neighbor visited?
            if(!visited[downNeighbor.getRow()][downNeighbor.getCol()]){
                unvisitedNeighbors[index] = downNeighbor;
                index++;
            }
        }

        //index is the number of unvisited Neighbors
        if(index == 0){
            //there are no unvisited neighbors
            return null;
        }

        //choose a number from 0 to index-1 (to choose a random neighbor inside the array)
        return unvisitedNeighbors[new Random().nextInt(index)];
    }

    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public int getCols() {
        return this.cols;
    }

    @Override
    public int[][] getMazeStructure() {
        return this.maze;
    }

    @Override
    public BlockPoint getStart() {
        return this.start;
    }

    @Override
    public BlockPoint getEnd() {
        return this.end;
    }
}
