import javax.swing.*;
import java.awt.*;

public class SnakeGame extends JFrame{
    JFrame frame;
    // Creating the constructor
    SnakeGame(){
        // Adding the frame
        frame = new JFrame("Snake Game");
        frame.setBounds(10,10,905,700);
        Gameplay panel = new Gameplay();
        panel.setBounds(0,0,905,700);
        panel.setBackground(Color.gray);
        frame.add(panel);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGame();
    }
}
