package CompSci;
//import the awt 
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;//a random import
import java.awt.Color;
import java.awt.GridLayout;
//import the swing 
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
/*
 * Alexis Hodges 10-18-24
 * The code below creates a Minesweeper board
 * that will use User entered dimensions to create a pop up GUI 
 * The board will be click-able with buttons
 * The board will be filled with randomly place mines
 * Warning numbers are place around the mines
 * 
 */

public class playMinesweeper extends JFrame implements ActionListener {//JFrame base
	Minesweeper call = new Minesweeper(); //instance of my old minesweeper class
    JTextField inHeight;//initialize and declare text field
    JTextField inWidth;//initialize and declare text field
    JButton go;//initialize and declare button
    int[][] board;//initialize and declare the variables for the board array
    int h, w;//initialize and declare the variables for the width and height coordinates

    public playMinesweeper() {//playMinesweeper constructor
        setLayout(null);
        //label
        JLabel label = new JLabel("Please select the height and width of the game you would like to play.");
        label.setBounds(100, 100, 800, 20);//setBounds is equal to (x,y, width, height)
        add(label);//add the label to the frame


        // height label
        JLabel height = new JLabel("Height:");
        height.setBounds(10, 200, 80, 20);//setBounds is equal to (x,y, width, height)
        add(height);//add the label to the frame

        inHeight = new JTextField(5);//I believe this is what controls the size of the text field box
        inHeight.setBounds(70, 200, 100, 20);//setBounds is equal to (x,y, width, height)
        add(inHeight);

        //width label
        JLabel width = new JLabel("Width:");
        width.setBounds(200, 200, 80, 20);//setBounds is equal to (x,y, width, height)
        add(width);//add the label to the frame

        inWidth = new JTextField(5);//the size of the text field box
        inWidth.setBounds(250, 200, 100, 20);//setBounds is equal to (x,y, width, height)
        add(inWidth);

        //the go button
        go = new JButton("Go!");
        go.setBounds(370,200,100,20);//setBounds is equal to (x,y, width, height)
        go.addActionListener(this);//add an actionListener
        add(go);//add the button to the frame
    }

    public void actionPerformed(ActionEvent e) {//actionListener class yay! not really :( 
        if (e.getSource() == go) {//e.getSource method for when you click go
            try {//try 
                h = Integer.parseInt(inHeight.getText());
                w = Integer.parseInt(inWidth.getText());

                if (h <= 0 || w <= 0) {//if the user enters the wrong numbers
                    JOptionPane.showMessageDialog(null, "Please enter valid positive integers for height and width.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create and initialize the board
                board = createMinesweeperBoard(h, w);
                createGameBoardWindow();

            } catch (NumberFormatException ex) {//catch and throws an exception
                JOptionPane.showMessageDialog(null, "Please enter valid integers for height and width.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int[][] createMinesweeperBoard(int h, int w) {
        int[][] board = new int[h][w];
        Random rand = new Random();
        int mines = (h + w) / 2;

        for (int i = 0; i < mines; i ++) {//the loop places a mine and a number in each spot that is valid
// if there is a mine already at the coordinate, then the code will continue
            int a = rand.nextInt(h);
            int b = rand.nextInt(w);

            if (board[a][b] == 9) {
                continue; 
            }

            board[a][b] = 9; // places a mine
            numNextToMine(board, a, b, h, w); //uses the numNextToMine method
            
        }
        return board;//returns the board
    }

    private void numNextToMine(int[][] board, int a, int b, int height, int width) {
        for (int i = -1; i <= 1; i++) {//the loop fills the board!
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; //this will jump over the mine
                int row = a + i;//fills the rest of the rows
                int column = b + j;//fills the rest of the column
                if (row >= 0 && row < height && column >= 0 && column < width && board[row][column] != 9) {
                    board[row][column]++;
                }
            }
        }
    }

    private void createGameBoardWindow() {
        JFrame gameM = new JFrame("Minesweeper Game Play");
        gameM.setLayout(new GridLayout(h, w));
        gameM.setSize(500, 500); // Adjust the size as needed

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                JButton button = new JButton();
                final int row = i;
                final int column = j;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        revealCell(row, column, button);
                    }
                });

                gameM.add(button);
            }
        }

        gameM.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//closes the operation

        gameM.setVisible(true);//allows the JFrame to pop up

    }

    private void revealCell(int row, int col, JButton button) {
        if (board[row][col] == 9) {//sets 9 as the mine
            button.setText("M"); // Indicate mine
            button.setBackground(Color.RED);//makes the button you click on red for the mine
        } else {
            button.setText(String.valueOf(board[row][col]));
            button.setEnabled(false);
        }
    }

    public static void main(String args[]) {
        playMinesweeper p = new playMinesweeper();//instance of the class
        p.setTitle("Minesweeper Dimensions");
        p.setVisible(true);//allows the JFrame to pop up

        p.setLocation(500, 500);
        p.setSize(700, 400);//the size of the pop up
        p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//closes the operation

    }
}
