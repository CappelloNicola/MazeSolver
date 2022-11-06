import javax.swing.*;
import java.awt.*;

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
        /*Maze maze = new Maze(PresetMaze.Default11x11,mazePanel.getWidth(), mazePanel.getHeight());
        mazePanel.add(maze);*/

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

    }
}