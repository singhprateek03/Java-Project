import javax.swing.*;
public class SnakeGame extends JFrame{

    // Default constructor
    SnakeGame(){
        super("Snake Game");
        add(new Board());
        pack();  // yeah frame ko refresh karta hai. frame khula hoga tabhi bhi

        setLocationRelativeTo(null);  // frame ki location ko center main kar dega automatically. agar change nhi karenge toh by default left main khulega.
        setResizable(false);    // iss se hum frame ka size naa chota kar sakte hai naa bada
    }
    public static void main(String[] args){
    new SnakeGame().setVisible(true);
    }
}