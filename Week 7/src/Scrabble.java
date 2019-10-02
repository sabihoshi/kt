import kotlin.NotImplementedError;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Scrabble {
    private static Random rnd = ThreadLocalRandom.current();

    static int currentPlayer = 0;
    static int maxPlayers = 4;
    static Scanner in = new Scanner(System.in);
    private static char[][] board = new char[15][15];
    private static LinkedList<Character> availableLetters = new LinkedList<Character>();
    private static Dictionary<Character, Integer> points = new Hashtable<Character, Integer>();
    private static Dictionary<Integer, ArrayList<Character>> playerRack = new Hashtable<Integer, ArrayList<Character>>();

    public static void main(String[] args) {
        initialize();

        while (true) {
            Console.cls();
            printBoard();
            System.out.println("----- Player #" + (currentPlayer + 1) + " -----");
            currentPlayer = playerTurn(currentPlayer);
        }
    }

    public enum Direction {
        Down,
        Across
    }


    public static void initialize() {
        // Initialize all values to 0
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                board[column][row] = '-';
            }
        }
        points.put('A', 1);
        points.put('B', 3);
        points.put('C', 3);
        points.put('D', 2);
        points.put('E', 1);
        points.put('F', 4);
        points.put('G', 2);
        points.put('H', 4);
        points.put('I', 1);
        points.put('J', 8);
        points.put('K', 5);
        points.put('L', 1);
        points.put('M', 3);
        points.put('N', 1);
        points.put('O', 1);
        points.put('P', 3);
        points.put('Q', 10);
        points.put('R', 1);
        points.put('S', 1);
        points.put('T', 1);
        points.put('U', 1);
        points.put('V', 4);
        points.put('W', 4);
        points.put('X', 8);
        points.put('Y', 4);
        points.put('Z', 10);

        addLetters(availableLetters, 'A', 9);
        addLetters(availableLetters, 'B', 2);
        addLetters(availableLetters, 'C', 2);
        addLetters(availableLetters, 'D', 4);
        addLetters(availableLetters, 'E', 12);
        addLetters(availableLetters, 'F', 2);
        addLetters(availableLetters, 'G', 3);
        addLetters(availableLetters, 'H', 2);
        addLetters(availableLetters, 'I', 9);
        addLetters(availableLetters, 'J', 1);
        addLetters(availableLetters, 'K', 5);
        addLetters(availableLetters, 'L', 4);
        addLetters(availableLetters, 'M', 2);
        addLetters(availableLetters, 'N', 6);
        addLetters(availableLetters, 'O', 8);
        addLetters(availableLetters, 'P', 2);
        addLetters(availableLetters, 'Q', 1);
        addLetters(availableLetters, 'R', 6);
        addLetters(availableLetters, 'S', 4);
        addLetters(availableLetters, 'T', 6);
        addLetters(availableLetters, 'U', 4);
        addLetters(availableLetters, 'V', 2);
        addLetters(availableLetters, 'W', 2);
        addLetters(availableLetters, 'X', 1);
        addLetters(availableLetters, 'Y', 2);
        addLetters(availableLetters, 'Z', 1);

        Collections.shuffle(availableLetters);
    }

    public static int playerTurn(int player) {
        // Ask user x and y
        System.out.print("Enter x > ");
        int x = in.nextInt();

        System.out.print("Enter y > ");
        int y = in.nextInt();

        in.nextLine();

        // Ask user direction
        System.out.print("Enter direction (down|across) > ");
        String dirInput = in.nextLine().toUpperCase();
        var direction = dirInput.equals("DOWN") ? Direction.Down : Direction.Across;
        String word = getWord();


        System.out.println("That was " + getPoints(word) + " points!");

        // placeLetters()
        placeLetters(x, y, direction, word);

        return (player + 1) % maxPlayers;
    }

    private static String getWord() {
        var temp = availableLetters;
        System.out.print("Enter word > ");
        return in.nextLine();
    }

    public static int getPoints(String word) {
        var temp = word.toUpperCase();
        int result = 0;
        for (int i = 0; i < temp.length(); i++) {
            result += points.get(temp.charAt(i));
        }
        return result;
    }

    public static char drawNextLetter() {
        return availableLetters.pop();
    }

    public static char[] getLetters(int amount) {
        var result = new char[amount];
        for (int i = 0; i < amount; i++) {
            result[i] = drawNextLetter();
        }
        Console.viewColors();
        return result;
    }

    public static void addLetters(List<Character> arr, char c, int amount) {
        for (int i = 0; i < amount; i++) {
            arr.add(c);
        }
    }

    public static void placeLetters(int x, int y, Direction direction, String word) {
        var temp = word.toUpperCase();
        if (direction == Direction.Across) {
            for (int i = 0; i < temp.length(); i++) {
                board[y][x + i] = temp.charAt(i);
            }
        } else if (direction == Direction.Down) {
            for (int i = 0; i < temp.length(); i++) {
                board[y + i][x] = temp.charAt(i);
            }
        }
    }

    public int checkBoard() {
        // return 0 if there is no winner
        // 1 for 1st player
        // 2 for 2nd player
        throw new NotImplementedError();
    }

    public static void printBoard() {
        for (char[] row : board) {
            var out = join("", 3, row);
            System.out.println(out);
        }
    }

    public static String join(String delimiter, int pad, char[] array) {
        var sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(padLeft(String.valueOf(array[i]), pad));
            if (i != array.length - 1)
                sb.append(delimiter);
        }
        return sb.toString();
    }

    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }
}