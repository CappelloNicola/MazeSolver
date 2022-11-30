import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GenerateMazeListener implements ActionListener {

    //the maze to update
    private Maze maze;
    private JTextField rowsNumber;
    private JTextField colsNumber;
    JPanel mazePanel;

    public GenerateMazeListener(Maze maze, JTextField rows, JTextField cols, JPanel mazePanel){
        this.maze = maze;
        this.rowsNumber = rows;
        this.colsNumber = cols;
        this.mazePanel = mazePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        maze.changeMazeStructure(new MazeGenerator(Integer.parseInt(rowsNumber.getText()), Integer.parseInt(colsNumber.getText())),mazePanel.getWidth(),mazePanel.getHeight());
        mazePanel.repaint();
    }
}
