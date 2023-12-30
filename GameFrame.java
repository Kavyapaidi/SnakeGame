import javax.swing.JFrame;

//JFrame is a class
//It has diff methods and constructors.
//JFrame works like the main window where components like labels,
// buttons,textfields are added to create a GUI.
//frame-panel-components
public class GameFrame extends JFrame {
    GameFrame()//It constructs a new frame that is initially invisible.
    {
        //These are all the methods in JFrame class.
        //this means parent class(JFrame here)
       this.add(new GamePanel());
       this.setTitle("Snake");//we are accessing the JFrame.setTitle("") method.
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//close the window by default
       this.setResizable(false);//can't resize JFrame/window
       this.pack();//size the JFrame automatically to the size of the widgets(Gallery,  within the page.Don't need to set manually
        //calls paintComponent(g) method
       this.setVisible(true);//JFrame/window is visible
       this.setLocationRelativeTo(null);//center the JFrame/window on screen
    }
}
