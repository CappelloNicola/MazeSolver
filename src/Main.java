import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // java - get screen size using the Toolkit class
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        //width and height of Jframe
        int width = screenWidth - 50;
        int height = screenHeight - 50;

        //the dimension of the content of the Jframe
        int actualWidth;
        int actualHeight;

        //create the buttons to run the research algorithm
        JButton button1 = new JButton("Breadth-First Search"); //Ricerca in ampiezza
        JButton button2 = new JButton("Uniform-Cost Search");
        JButton button3 = new JButton("A* Search");



        //create the frame that will store the panels
        //get the size of the content
        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setSize(width, height);
        frame.setTitle("Maze");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension actualSize = frame.getContentPane().getSize();
        actualWidth = actualSize.width;
        actualHeight = actualSize.height;

        //create the Panel that will store the maze
        JPanel mazePanel = new JPanel();
        mazePanel.setBounds(0,0,actualWidth,actualHeight-40);
        mazePanel.setBackground(Color.BLACK);
        mazePanel.setLayout(new BorderLayout());

        //calculate the size of the maze's block and add it to the panel
        //we consider that the first and last rows and cols has a distance of 5 px to the panel
        Maze maze = new Maze(PresetMaze.Default11x11,mazePanel.getWidth(), mazePanel.getHeight());
        mazePanel.add(maze);

        //create the Panel for the buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBounds(0,mazePanel.getHeight(),actualWidth,40);
        buttonsPanel.setBackground(Color.BLACK);
        buttonsPanel.add(button1);
        buttonsPanel.add(button2);
        buttonsPanel.add(button3);



        //adding the panels to the frame
        frame.add(mazePanel);
        frame.add(buttonsPanel);

        //add listeners to buttons
        try {
            Class<?> myClass = Class.forName("Main");
            button1.addActionListener(new ActionListenerCustomized(maze, mazePanel, myClass.getMethod("breadthFirstSearch",Maze.class)));
            button2.addActionListener(new ActionListenerCustomized(maze,mazePanel,myClass.getMethod("uniformCostSearch",Maze.class)));
            button3.addActionListener(new ActionListenerCustomized(maze,mazePanel,myClass.getMethod("aStarSearch",Maze.class)));
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }

    public static java.util.List<Block> calculateActualPath (Map<Block,Block> parents, Maze maze){
        List<Block> path = new ArrayList<>();
        Block currentBlock = maze.getEndBlock();
        path.add(currentBlock);
        while(!currentBlock.getIsStart()){
            currentBlock = parents.get(currentBlock);
            path.add(currentBlock);
        }
        return path;
    }

    //this method implements the breadthFirstSearch
    //it returns the sequence of blocks explored and the couples (block, parent)
    //it return the nearest path. The path cost function is monotone, so it will always give the best path
    public static ReturningValues breadthFirstSearch(Maze maze){

        //keep trace of the parents of each generated block
        //In this way we can backtrace the path from the start to the end
        Map<Block,Block> parents = new HashMap<>();

        Block currentNode = maze.getStartBlock();

        //the path of blocks searched to reach the end
        List<Block> path = new ArrayList<>();

        if(currentNode.getIsEnd()){
            List<Block> oneBlockPath = new ArrayList<>();
            oneBlockPath.add(currentNode);
            return new ReturningValues(oneBlockPath, parents);
        }

        //FIFO queue, frontier
        Queue<Block> queue = new LinkedList<>();
        queue.add(currentNode);

        Set<Block> visitedNodes = new HashSet<>();

        while(true){
            if(queue.isEmpty()){
                return null;
            }

            currentNode = queue.poll();
            path.add(currentNode);
            visitedNodes.add(currentNode);

            java.util.List<Block> neighbors = currentNode.getNeighbors();
            for (Block neighbor : neighbors) {
                if (!visitedNodes.contains(neighbor)) {
                    parents.put(neighbor, currentNode);
                }
                if (!queue.contains(neighbor) && !visitedNodes.contains(neighbor)) {
                    if (neighbor.getIsEnd()) {
                        path.add(neighbor);
                        return new ReturningValues(path, parents);
                    }
                    queue.add(neighbor);

                }

            }
        }
    }

    public static ReturningValues uniformCostSearch(Maze maze){
        Block node = maze.getStartBlock();
        node.setPathCost(0);
        //the priority queue, representing the frontier, is ordered by the pathCost of each block
        PriorityQueue<Block> frontier = new PriorityQueue<>(1000, (o1, o2) -> {
            if(o1.getPathCost()<o2.getPathCost()){
                return -1;
            }
            else if(o1.getPathCost()>o2.getPathCost()){
                return 1;
            }
            return 0;
        });

        frontier.add(node);

        Set<Block> explored = new HashSet<>();
        //the path of blocks searched to reach the end
        List<Block> path = new ArrayList<>();
        //keep trace of the parents of each generated block
        //In this way we can backtrace the path from the start to the end
        Map<Block,Block> parents = new HashMap<>();



        while(true){

            if(frontier.isEmpty()){
                return null;
            }

            node = frontier.poll();
            path.add(node);
            if(node.getIsEnd()){
                return new ReturningValues(path, parents);
            }

            explored.add(node);

            List<Block> neighbors = node.getNeighbors();
            for (Block e: neighbors) {
                //each action has a cost of 1.
                //the pathCost of the neighbor is equal to the parent pathCost plus 1
                //this is the pathCost at this moment
                int actualPathCost = node.getPathCost()+1;
                if(!explored.contains(e) && !frontier.contains(e)){
                    frontier.add(e);
                    e.setPathCost(actualPathCost);
                    parents.put(e, node);
                }
                else if(frontier.contains(e)){
                    //we must check the pathCost inside the frontier because it may be overwritten
                    if(e.getPathCost()>actualPathCost){
                        e.setPathCost(actualPathCost);
                        parents.put(e,node);
                    }
                }
            }
        }
    }

    //It's equal to the uniformCostSearch, except for the order given to the PriorityQueue
    public static ReturningValues aStarSearch(Maze maze){
        Block node = maze.getStartBlock();
        node.setPathCost(0);
        //the priority queue, representing the frontier, is ordered by f(n):
        //For a node n, f(n) = g(n) + h(n) where
        //g(n) is the pathCost (the distance from the starting node to n) and h(n) is the esteemed distance from n to the objective node
        //h(n) is equal to the distance between two blocks in an imaginary 2D vector
        //For Example: if the objective node is at pos[7][7], then h(pos[6][6])=2

        PriorityQueue<Block> frontier = new PriorityQueue<>(1000, (o1, o2) -> {
            int endingBlockRow = maze.getEndBlock().getThisRow();
            int endingBlockCol = maze.getEndBlock().getThisCol();
            int hnO1 = Math.abs(o1.getThisRow() - endingBlockRow) + Math.abs(o1.getThisCol() - endingBlockCol);
            int hnO2 = Math.abs(o2.getThisRow() - endingBlockRow) + Math.abs(o2.getThisCol() - endingBlockCol);

            /*double hnO1 =
                    Math.sqrt((endingBlock.getY() - o1.getY()) * (endingBlock.getY() - o1.getY()) + (endingBlock.getX() - o1.getX()) * (endingBlock.getX() - o1.getX()));
            double hnO2 =
                    Math.sqrt((endingBlock.getY() - o2.getY()) * (endingBlock.getY() - o2.getY()) + (endingBlock.getX() - o2.getX()) * (endingBlock.getX() - o2.getX()));
            */


            if(o1.getPathCost()+hnO1 < o2.getPathCost()+hnO2){
                return -1;
            }
            else if(o1.getPathCost()+hnO1 > o2.getPathCost()+hnO2){
                return 1;
            }
            return 0;
        });

        frontier.add(node);

        Set<Block> explored = new HashSet<>();
        //the path of blocks searched to reach the end
        List<Block> path = new ArrayList<>();
        //keep trace of the parents of each generated block
        //In this way we can backtrace the path from the start to the end
        Map<Block,Block> parents = new HashMap<>();


        while(true){

            if(frontier.isEmpty()){
                return null;
            }

            node = frontier.poll();
            path.add(node);
            if(node.getIsEnd()){
                return new ReturningValues(path, parents);
            }

            explored.add(node);

            List<Block> neighbors = node.getNeighbors();
            for (Block e: neighbors) {
                //each action has a cost of 1.
                //the pathCost of the neighbor is equal to the parent pathCost plus 1
                //this is the pathCost at this moment
                int actualPathCost = node.getPathCost()+1;
                if(!explored.contains(e) && !frontier.contains(e)){
                    frontier.add(e);
                    e.setPathCost(actualPathCost);
                    parents.put(e, node);
                }
                else if(frontier.contains(e)){
                    //we must check the pathCost inside the frontier because it may be overwritten
                    if(e.getPathCost()>actualPathCost){
                        e.setPathCost(actualPathCost);
                        parents.put(e,node);
                    }
                }
            }
        }
    }
}

