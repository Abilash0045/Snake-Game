import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Gameplay extends JPanel implements ActionListener, KeyListener {

    ImageIcon snaketitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));
    ImageIcon revhead = new ImageIcon(getClass().getResource("revhead.png"));
    ImageIcon head = new ImageIcon(getClass().getResource("head.png"));
    ImageIcon lefthead = new ImageIcon(getClass().getResource("lefthead.png"));
    ImageIcon rightead = new ImageIcon(getClass().getResource("righthead.png"));
    ImageIcon food = new ImageIcon(getClass().getResource("food.png"));
    ImageIcon snakebody = new ImageIcon(getClass().getResource("snakebody.png"));

    int[] Xpos = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    int[] Ypos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
    Random random = new Random();
    int foodX = 150,foodY = 200;
    int[] snakeXlen = new int[750];
    int[] snakeYlen = new int[750];
    boolean left = false;
    boolean right = true;
    boolean up = false;
    boolean down = false;
    int move = 0;
    int lenghtofSnake = 3;
    Timer timer;
    int delay = 150;
    boolean gameover = false;
    int score = 0;

    Gameplay(){
        addKeyListener(this);
        setFocusable(true);
        timer = new Timer(delay, this);
        timer.start();
    }
    public void newfood(){
        foodX = Xpos[random.nextInt(34)];
        foodY = Ypos[random.nextInt(23)];
        for (int i=lenghtofSnake-1;i>=0;i--){
            if (snakeXlen[i] == foodX && snakeYlen[i] == foodY){
                newfood();
            }
        }
    }
    public void collideWithfood(){
        if (snakeXlen[0] == foodX && snakeYlen[0] == foodY){
            newfood();
            lenghtofSnake++;
            score++;
            snakeXlen[lenghtofSnake-1] = snakeXlen[lenghtofSnake-2];
            snakeYlen[lenghtofSnake-1] = snakeYlen[lenghtofSnake-2];
        }
    }
    public void collideWithBody(){
        for (int i = lenghtofSnake-1;i>0;i--){
            if (snakeXlen[i] == snakeXlen[0] && snakeYlen[i] == snakeYlen[0]){
                timer.stop();
                gameover = true;
                score++;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.white);
        g.drawRect(24,10,851,55);
        g.drawRect(24,74,851,576);

        snaketitle.paintIcon(this,g,25,11);
        g.setColor(Color.black);
        g.fillRect(25,75,851,576);

        if (move == 0){
            snakeXlen[0] = 100;
            snakeXlen[1] = 75;
            snakeXlen[2] = 50;

            snakeYlen[0] = 100;
            snakeYlen[1] = 100;
            snakeYlen[2] = 100;
            move++;
        }
        if (left){
            lefthead.paintIcon(this,g,snakeXlen[0],snakeYlen[0]);
        }
        if (right){
            rightead.paintIcon(this,g,snakeXlen[0],snakeYlen[0]);
        }if (up){
            head.paintIcon(this,g,snakeXlen[0],snakeYlen[0]);
        }if (down){
            revhead.paintIcon(this,g,snakeXlen[0],snakeYlen[0]);
        }
        for (int i=1; i<lenghtofSnake;i++){
            snakebody.paintIcon(this,g,snakeXlen[i],snakeYlen[i]);
        }
        food.paintIcon(this,g,foodX,foodY);
        if (gameover){
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,50));
            g.drawString("Game Over",300,350);
            g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
            g.drawString("Press space to restart",350,410);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,15));
        g.drawString("Score:" + score,750,30);
        g.drawString("Length: "+lenghtofSnake,750,50);
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i= lenghtofSnake-1;i>0;i--){
            snakeXlen[i] = snakeXlen[i-1];
            snakeYlen[i] = snakeYlen[i-1];
        }
        if (left){
            snakeXlen[0] = snakeXlen[0]-25;
        }
        if (right){
            snakeXlen[0] = snakeXlen[0]+25;
        }
        if (down){
            snakeYlen[0] = snakeYlen[0]+25;
        }
        if (up){
            snakeYlen[0] = snakeYlen[0]-25;
        }
        if (snakeXlen[0] > 850) snakeXlen[0] = 25;
        if (snakeXlen[0] < 25) snakeXlen[0] = 850;
        if (snakeYlen[0] > 625) snakeYlen[0] = 75;
        if (snakeYlen[0] < 75) snakeYlen[0] = 625;
        collideWithfood();
        collideWithBody();
        repaint();
    }
    public void restart(){
        gameover = false;
        move = 0;
        lenghtofSnake = 3;
        score = 0;
        left = false;
        right = true;
        up = false;
        down = false;
        timer.start();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameover){
            restart();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && (!right)){
            left = true;
            right = false;
            up = false;
            down = false;
            move++;
        } if (e.getKeyCode() == KeyEvent.VK_RIGHT && (!left)){
            left = false;
            right = true;
            up = false;
            down = false;
            move++;
        } if (e.getKeyCode() == KeyEvent.VK_DOWN && (!up)){
            left = false;
            right = false;
            up = false;
            down = true;
            move++;
        } if (e.getKeyCode() == KeyEvent.VK_UP && (!down)){
            left = false;
            right = false;
            up = true;
            down = false;
            move++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
