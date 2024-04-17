
import java.util.Scanner;
public class Main {
    static  Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to our Connect Four Game!");
        System.out.println("Choose option to play: 1 - 2 players Game, 2 - Play against AI");
        int mode = scanner.nextInt();

        if (mode == 1) {
            playGame();
        } else if (mode == 2) {
            playAiGame();
        } else {
            System.out.println("Invalid Choice, Exiting...");
        }
        scanner.close();
    }


    public static void playGame(){
        System.out.println("Enter player 1 name and color you want.");
        String name1  = sc.nextLine();
        char color;
        do{
            System.out.println("Choose only between 'r' and 'y'.");
            color = sc.next().charAt(0);}
        while(color!='r' && color !='y');
        System.out.println("Enter player 2 name");
        String name2  = sc.next();
        ConnectFour c4 = new ConnectFour(name1,name2);
        c4.fillTheBoard();
        c4.printBoard();
        c4.chooseColor(color);


        while (!c4.checkBoard()) {
            System.out.println(c4.current + " or "+c4.currPlayer+"'s turn.");
            System.out.println("Enter column number 1-" + c4.column + ": ");
            int column = sc.nextInt();
            if (c4.isValidInput(column)) {
                c4.takeTurn(column);
                c4.printBoard();
                if (c4.returnWinner(c4.currPlayer)) {
                    System.out.println(c4.current + " is winner!");
                    break;
                }
                c4.switchPlayer();
            } else {
                System.out.println("Invalid move!");

                if (!c4.returnWinner(c4.currPlayer) && c4.checkBoard()) {
                    System.out.println("It's a draw!");
                }

            }
        }
    }

    public static void playAiGame() {
        Scanner sc = new Scanner(System.in);
        ConnectFour c = new ConnectFour();
        c.fillTheBoard();
        c.printBoard();
        int currPlayer = 0;
        System.out.println("Enter player 1 name and color you want.");
        String name1 = sc.nextLine();
        char color;
        do {
            System.out.println("Choose only between 'r' and 'y'.");
            color = sc.next().charAt(0);
        }
        while (color != 'r' && color != 'y');
        c.onePlayer(color, name1);

        while (!c.checkBoard()) {
            if (currPlayer == 0) {
                System.out.println(name1 + "'s turn.");
                int coll = sc.nextInt();
                if (c.isValidInput(coll)) {
                    c.takeTurn(coll);
                    c.printBoard();
                    if (c.returnWinner(c.currPlayer)) {
                        System.out.println(c.player1 + " is a winner!");
                        break;
                    }
                    currPlayer = 1;
                    c.switchPlayer();
                }
            } else {
                System.out.println("AI/" + c.currPlayer + "'s turn.");
                int coll = c.BestMove(c.board);
                if (c.isValidInput(coll)) {
                    c.takeTurn(coll);
                    c.printBoard();
                    if (c.returnWinner(c.currPlayer)) {
                        System.out.println("You lose, Ai is a winner!");
                        break;
                    }
                    currPlayer = 0;
                    c.switchPlayer();
                }
            }
        }
    }
}