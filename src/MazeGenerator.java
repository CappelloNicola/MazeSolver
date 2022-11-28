import java.util.Random;

public class MazeGenerator {
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
        end = this.createMaze(new BlockPoint(0,0));
    }

    public BlockPoint createMaze(BlockPoint currentNode){
        //mark the current node as visited
        visited[currentNode.getRow()][currentNode.getCol()] = true;

        //get a random, unvisited Neighbor of currentNode
        BlockPoint nextNode = randomUnvisitedNeighbour(currentNode);

        while(nextNode != null){
            //connect currentNode and nextNode, by eliminating the wall between them
            this.connectNodes(currentNode, nextNode);
            createMaze(nextNode);
            nextNode = randomUnvisitedNeighbour(currentNode);
        }
        //if a block doesn't have unvisited neighbors, it's the ending block
        return currentNode;
    }

    private void connectNodes (BlockPoint a, BlockPoint b){
        //to connect two Nodes, a  wall must be deleted
        //what
    }

    /*
    * @returns BlockPoint of a random, unvisited Neighbor. If all neighbors are visited returns null*/
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
        return unvisitedNeighbors[new Random().nextInt(5)];
    }
}
