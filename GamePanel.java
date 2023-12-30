import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
//class extends class,implements interface
//ActionListener is notified whenever you click on the button or menu item.
//It has only one method: actionPerformed().
public class GamePanel extends JPanel implements ActionListener {
    //default-allows access only within the same package
    //public-scope is everywhere
    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HEIGHT=600;
    static final int UNIT_SIZE=25;//How big we want items
    static final int GAME_UNITS=(SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY=200;//How fast the game(snake) is running(moving)
//    coordinate values
    final int x[]=new int[GAME_UNITS];
    final int y[]=new int[GAME_UNITS];
    int bodyParts=6;
    int applesEaten;
    int appleX;//X Coordinate of an apple
    int appleY;//Y Coordinate of an apple
    char direction='R';
    boolean running=false;
    Timer timer;
    Random random;
    GamePanel()
    {
         random=new Random();
         //size of GamePanel
         this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
         //GamePanel color is black
         this.setBackground(Color.black);
         //whether a component can gain the focus if it is requested to do so
         this.setFocusable(true);
         this.addKeyListener(new MyKeyAdapter());
         startGame();//start game
    }
    public void startGame()
    {
        newApple();
        running=true;
        timer=new Timer(DELAY,this);
        //fires action event to registered listeners
        timer.start();
        //Starts the Timer, causing it to start sending/firing action events to its listeners.
    }
    //When a window becomes visible or is resized,
    // the "system" automatically calls the paintComponent() method
    // for all areas of the screen that have to be redrawn.
    public void paintComponent(Graphics g)
    {
        //needed to draw something on JPanel other than background color.
       super.paintComponent(g);//JPanel.paintComponent(g)
       draw(g);
    }
    public void draw(Graphics g)
    {
        if(running) {
            //draw grids
//            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);//vertical lines
//                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);//Horizontal Lines
//            }
            //draw Apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            //iterate over all the body parts of the snake
            for (int i = 0; i < bodyParts; i++) {
                //draw Snake's Head
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    //draw Snake's body
                    g.setColor(new Color(random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //Display/Draw current score
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics=getFontMetrics(g.getFont());
            g.drawString("Score:"+applesEaten,(SCREEN_WIDTH-metrics.stringWidth("Score:"+applesEaten))/2,g.getFont().getSize());
        }
        else
        {
            gameOver(g);
        }
    }
    //Understood
    //Generating a new apple
    public void newApple()
    {
        //apple appears somewhere among all the squares
        //x and y coordinates of a generated apple
       appleX= random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
       appleY= random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    //whenever snake moves,only snake head coordinates changes
    public void move()
    {
        for(int i=bodyParts;i>0;i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(direction){
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
                //when u move left,only x coordinate changes
                //decrement
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
                //increment
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;
        }
    }
    //Understood
    //Grabbing the apple
    public void checkApple()
    {
       if(x[0]==appleX && y[0]==appleY)
       {
           bodyParts++;
           applesEaten++;
           newApple();
       }
    }
    public void checkCollisions()
    {
        for(int i=bodyParts;i>0;i--)
        {
            //check if head collides with body
            if((x[0]==x[i]) && (y[0]==y[i]))
            {
                running=false;
            }
        }
        //check if head touches left border
        if(x[0]<0) running=false;
        //check if head touches right border
        if(x[0]>SCREEN_WIDTH) running=false;
        //check if head touches top border
        if(y[0]<0) running=false;
        //check if head touches bottom border
        if(y[0]>SCREEN_HEIGHT) running=false;
        if(!running) timer.stop();
    }
    public void gameOver(Graphics g)
    {
        //Display/Draw current score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics1=getFontMetrics(g.getFont());
        g.drawString("Score:"+applesEaten,(SCREEN_WIDTH-metrics1.stringWidth("Score:"+applesEaten))/2,g.getFont().getSize());
        //Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics=getFontMetrics(g.getFont());
        g.drawString("Game Over",(SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);

    }
    // invoked automatically whenever you click on the directions on keyboard
    @Override
    public void actionPerformed(ActionEvent e) {
         if(running)
         {
             move();
             checkApple();
             checkCollisions();
         }
         //If snake is not moving(game isn't running),restarts game
         repaint();
    }
    //Understood
    public class MyKeyAdapter extends KeyAdapter
    {
        //To control the snake
        @Override
        public void keyPressed(KeyEvent e)
        {
            switch (e.getKeyCode()){
                //KeyEvent.getKeyCode==VK_LEFT(if u clicked '<-')
                case KeyEvent.VK_LEFT:
                    if(direction!='R') direction='L';
                    break;
                case KeyEvent.VK_0:
                    if(direction!='L') direction='R';
                    break;
                case KeyEvent.VK_UP:
                    if(direction!='D') direction='U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U') direction='D';
                    break;
            }
        }
    }
}
