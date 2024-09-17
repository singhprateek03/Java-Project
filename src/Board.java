import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener {

    private Image apple;    // Image AWT ki class hai
    private Image dot;      // Image AWT ki class hai
    private Image head;     // Image AWT ki class hai

    private final int ALL_DOTS = 900;      // All_DOTS frame ka size hai
    private final int DOT_SIZE = 10;       // yeah 1 dot ka size hai
    private final int RANDOM_POSITION = 29; // yeah randomly kahi bhi apple de sakta hai frame ke andar

    private int apple_x;
    private int apple_y;

    private final int x[] = new int[ALL_DOTS];  // snake apples khane ke baad jo badhta jayega woo iss main store hoga
    private final int y[] = new int[ALL_DOTS];

    private boolean leftDirection = false;
    private boolean rightDirection = true;      // by default right main hee chale isi liye true kiya hai
    private boolean upDirection = false;
    private boolean downDirection = false;

    private boolean inGame = true;
    // Global variable
    private int dots;
    private Timer timer;
    // Default constructor
    Board() {
        addKeyListener(new TAdapter());

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300, 300));
        setFocusable(true);

        loadImages();   // method call
        initGame();     // method call
    }

    public void loadImages() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons\\apple.png"));  // getSystemResource() yeah ek static function hai jo ki ClassLoader ke andar hota hai

        apple = i1.getImage();  // yaha image ko store kar rahe hai

        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("icons\\dot.png"));
        dot = i2.getImage();

        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icons\\head.png"));
        head = i3.getImage();
    }

    public void initGame() {
        dots = 3;

        for (int i = 0; i < dots; i++) {
            y[i] = 50;   // frame ke andar upar se 50 ka gap aayega
            x[i] = 50 - i * DOT_SIZE;
        }

        locateApple(); // method call

        timer = new Timer(140, this);
        timer.start();
    }

    public void locateApple() {
        // yeah x ke liye hai
        int r = (int)(Math.random() * RANDOM_POSITION); // Type Casting agar nhi karenege toh value float main aayegi
        apple_x = r * DOT_SIZE;  // r main random number aayega or DOT_SIZE apple ka size batayega

        // yeah y ke liye hai
        r = (int)(Math.random() * RANDOM_POSITION);
        apple_y = r * DOT_SIZE; // r main random number aayega or DOT_SIZE apple ka size batayega
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);    // super ki help se hum parent ke component ko call karta hai

        draw(g);     // method call
    }

    public void draw(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);

            for (int i = 0 ; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();     // Default se initialized ho jayega
        } else {
            gameOver(g);    // method call
        }
    }

    public void gameOver(Graphics g) {
        String msg = "Game Over!";
        Font font = new Font("SAN_SERIF", Font.BOLD, 14);
        FontMetrics metrices = getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (300 - metrices.stringWidth(msg)) / 2, 300/2);
    }

    public void move() {
        for (int i = dots ; i > 0 ; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (leftDirection) {      // isse snake right side ko jayega
            x[0] = x[0] - DOT_SIZE;
        }
        if (rightDirection) {   // isse snake left side ko jayega
            x[0] = x[0] + DOT_SIZE;
        }
        if (upDirection) {       // isse snake down side ko jayega
            y[0] = y[0] - DOT_SIZE;
        }
        if (downDirection) {      // isse snake up side ko jayega
            y[0] = y[0] + DOT_SIZE;
        }
    }

    public void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            locateApple();  // method call
        }
    }

    public void checkCollision() {
        // snake agar khud ko kaat leta hai toh collision hoga
        for(int i = dots; i > 0; i--) {
            if (( i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }

        // snake agar boundry se takrata hai toh collision hoga
        if (y[0] >= 300) {
            inGame = false;
        }
        if (x[0] >= 300) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    public void actionPerformed(ActionEvent ae) {   // yeah method ko override kar dega
        if (inGame) {
            checkApple();   // method call
            checkCollision();   // method call
            move(); // method call
        }

        repaint();    // method call
    }

    public class TAdapter extends KeyAdapter {      // isse humare keys work karegi
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && (!rightDirection)) {     // VK_LEFT -> left wali key ka sara kaam dekehgi
                leftDirection = true;
            //  rightDirection -> yeah false nhi karenge kyu ki agar false kar diya toh user left to right switch kar jayega jo ki nhi hona chaiye
                upDirection = false;
                downDirection = false;
            }

            if (key == KeyEvent.VK_RIGHT && (!leftDirection)) { // VK_LEFT -> left wali key ka sara kaam dekehgi
                rightDirection = true;
            //  leftDirection -> yeah false nhi karenge kyu ki agar false kar diya toh user left to right switch kar jayega jo ki nhi hona chaiye
                upDirection = false;
                downDirection = false;
            }

            if (key == KeyEvent.VK_UP && (!downDirection)) {     // VK_LEFT -> left wali key ka sara kaam dekehgi
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
            }

            if (key == KeyEvent.VK_DOWN && (!upDirection)) {    // VK_LEFT -> left wali key ka sara kaam dekehgi
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
        }
    }

}