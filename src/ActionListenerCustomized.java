import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

public class ActionListenerCustomized implements ActionListener {

    private Maze maze;
    JPanel mazePanel;
    Method searchAlgorithm;

    /**
     *
     * @param maze: the maze on which the search algorithms are called
     * @param mazePanel: the mazePanel to update
     * @param searchAlgorithm: the search algorithm to be called
     */
    public ActionListenerCustomized(Maze maze, JPanel mazePanel, Method searchAlgorithm){
        this.maze = maze;
        this.mazePanel = mazePanel;
        this.searchAlgorithm = searchAlgorithm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //unpaint all blocks
        maze.unpaintBlocks();
        ReturningValues returningValues;
        try {
            returningValues = (ReturningValues) searchAlgorithm.invoke(null,maze);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
        Iterator<Block> iter = returningValues.getPath().iterator();
        //delay for 100 blocks is 50
        //delay for 200 blocks is 25
        //using inversely proportion
        int delay = 5000/(maze.getRows()* maze.getCols());

        Timer t = new Timer(delay, null);

        ReturningValues finalPathAndParents = returningValues;
        ActionListener actionListener = e1 -> {
            if(iter.hasNext()) {
                Block block = iter.next();
                maze.paintBlock(block.getThisRow(), block.getThisCol(), Color.PINK);
                mazePanel.repaint();
            }
            else{
                //after it stopped is time to print the actual path
                java.util.List<Block> actualPath = Main.calculateActualPath(finalPathAndParents.getParents(), maze);

                for (Block block : actualPath) {
                    maze.paintBlock(block.getThisRow(), block.getThisCol(), Color.RED);
                }
                mazePanel.repaint();

                System.out.println();
                System.out.println(searchAlgorithm.getName()+":");
                System.out.println("Number of visited Nodes: "+ returningValues.getExploredNodes());
                System.out.println("Path cost: "+(actualPath.size()-1));

                t.stop();
            }
        };
        t.addActionListener(actionListener);
        t.start();
    }
}
