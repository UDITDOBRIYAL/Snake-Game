import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

//Basically, a JFrame represents a framed window and a JPanel represents some area in which controls
// (e.g., buttons, checkboxes, and textfields) and visuals (e.g., figures, pictures, and even text) can appear.
public class MyPanel extends JPanel implements KeyListener , ActionListener {
    //add images
    ImageIcon snaketitle=new ImageIcon(getClass().getResource("snaketitle.jpg"));
    ImageIcon rightmouth=new ImageIcon(getClass().getResource("rightmouth.png"));
    ImageIcon snakeimage=new ImageIcon(getClass().getResource("snakeimage.png"));
    ImageIcon up=new ImageIcon(getClass().getResource("upmouth.png"));
    ImageIcon down=new ImageIcon(getClass().getResource("downmouth.png"));
    ImageIcon left=new ImageIcon(getClass().getResource("leftmouth.png"));
    ImageIcon food=new ImageIcon(getClass().getResource("enemy.png"));

    boolean isUp=false;
    boolean isDown=false;
    boolean isRight=true;
    boolean isLeft=false;
    //postion for snake food
    int[] xpos={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    int[] ypos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
    //take random integer for food
    Random random=new Random();
    int foodX=150;
    int foodY=150;

    int snakeX[]=new int[750];
    int snakeY[]=new int[750];
    int move=0;
    int lengthOfSnake=3;
    boolean GameOver=false;
    int score=0;
    Timer time;
    //keyaction litsner for using keyboard to play snakegame

    MyPanel()
    {    //function to provide playing game through keyboard
        addKeyListener(this);
        setFocusable(true);
        time=new Timer(100,this);
        time.start();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //border of rectangle
        g.setColor(Color.white);
        // in the game there is two rectangel

        g.drawRect(24,10,851,55);
        g.drawRect(24,74,851,576);

        //this image is over first rectangle
        snaketitle.paintIcon(this,g,25,11);

        // this color is for second rectangle
        g.setColor(Color.black);
        g.fillRect(25,75,850,575);

        //coordinates of snake body (head with two body parts)
        if(move==0)
        {
            snakeX[0]=100;
            snakeX[1]=75;
            snakeX[2]=50;

            snakeY[0]=100;
            snakeY[1]=100;
            snakeY[2]=100;
        }
        if(isLeft)
        {
            left.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
        if(isRight) {//this is the head part from x-axis and y-axis
            //this is head part
            rightmouth.paintIcon(this, g, snakeX[0], snakeY[0]);
        }
        if(isUp)
        {
            up.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
        if(isDown){
            down.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
        //now two part remains
        for(int i=1;i<lengthOfSnake;i++)
        {
            snakeimage.paintIcon(this,g,snakeX[i],snakeY[i]);
        }
        //print food
        food.paintIcon(this,g,foodX,foodY);

        //if gameover print gameover text
        if(GameOver)
        {
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
            g.drawString("GAME OVER",300,300);
            //after gameover
            g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
            g.drawString("Press the space key to restart the game",330,360);
        }
        // score and length font and color
        g.setColor(Color.white);
        g.setFont(new Font("ITALIC",Font.PLAIN,15));
        g.drawString("Score="+score,750,30);
        g.drawString("Length="+lengthOfSnake,750,50);
        g.dispose();
    }
    //keyaction litsner for using keyboard to play snakegame

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE && GameOver)
        {
            restart();
        }
        // !isleft condition for not moving in apposite direction
      if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!isLeft))
      {   isUp=false;
          isDown=false;
          isLeft=false;
          isRight=true;
          move++;
      }
      if(e.getKeyCode()==KeyEvent.VK_LEFT && (!isRight))
      {   isUp=false;
          isDown=false;
          isLeft=true;
          isRight=false;
          move++;

      }
        if(e.getKeyCode()==KeyEvent.VK_UP && (!isDown))
        {   isUp=true;
            isDown=false;
            isLeft=false;
            isRight=false;
            move++;

        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN && (!isUp))
        {   isUp=false;
            isDown=true;
            isLeft=false;
            isRight=false;
            move++;

        }
    }

    private void restart() {
        GameOver=false;

        move=0;
        score=0;
        lengthOfSnake=3;
        isLeft=false;
        isRight=true;
        isUp=false;
        isDown=false;
        time.start();
        newfood();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = lengthOfSnake - 1; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        //decrease horzontally(going left)
        if (isLeft)
        {
            snakeX[0]=snakeX[0]-25;
        }
        //increase horizotally(goin right)
        if (isRight)
        {
            snakeX[0]=snakeX[0]+25;
        }
        //for vertical operation
        if(isUp)
        {
            snakeY[0]=snakeY[0]-25;
        }
        if(isDown)
        {
            snakeY[0]=snakeY[0]+25;
        }
        //rectangle width is 850
        //moving horizontally x-axis
        if(snakeX[0]>850) snakeX[0]=25;
        if(snakeX[0]<25)  snakeX[0]=850;
        //rectangle height is 576
        //moving vertically y-axis
        if(snakeY[0]>625) snakeY[0]=75;
        if(snakeY[0]<75)  snakeY[0]=625;
        CollisionWithFood();
        CollisionWithBody();
        repaint();
    }
    //for colloision with body game over
    private void CollisionWithBody() {
        for(int i=lengthOfSnake-1;i>0;i--)
        {
            if(snakeX[i]==snakeX[0] && snakeY[i]==snakeY[0])
            {
                time.stop();
                GameOver=true;
            }
        }
    }
    //for eating food
    private void CollisionWithFood() {
       if( snakeX[0]==foodX && snakeY[0]==foodY)
       {    newfood();

           lengthOfSnake++;
           score++;
           //if 4
           //3->2
           //if 5
           //4->3
           snakeX[lengthOfSnake-1]=snakeX[lengthOfSnake-2];
           snakeY[lengthOfSnake-1]=snakeY[lengthOfSnake-2];
       }
    }

    private void newfood() {
        foodX=xpos[random.nextInt(xpos.length-1)];
        foodY=ypos[random.nextInt(ypos.length-1)];
        for(int i=lengthOfSnake-1;i>=0;i--)
        {
            if(snakeX[i]==foodX && snakeY[i]==foodY)
            {
                newfood();
            }
        }
    }
}
