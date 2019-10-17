import java.util.*;
import java.util.stream.IntStream;

public class project1_2048 {
    private static Random r = new Random();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int sides = 4;
        int[][] gameBoard = new int[sides][sides];

        for (int i = 0; i < 4; i++) {
            addRandomNumber(gameBoard, sides);
        }

        while (canMove(gameBoard, sides)) {
            addRandomNumber(gameBoard, sides);
            PrintUI(gameBoard, sides);
            getDirection(gameBoard, sides);
        }
        PrintUI(gameBoard, sides);
        println("Game ended! You got " + getPoints(gameBoard) + " points!");
    }

    public static void getDirection(int[][] gameBoard, int sides) {
        print("Enter a direction > ");
        var input = scanner.nextLine();
        switch (input.toUpperCase()) {
            case "W":
                println("Move up");
                moveUp(gameBoard, sides);
                break;
            case "A":
                println("Move left");
                moveLeft(gameBoard, sides);
                break;
            case "S":
                println("Move down");
                moveDown(gameBoard, sides);
                break;
            case "D":
                println("Move right");
                moveRight(gameBoard, sides);
                break;
            default:
                println("Invalid direction!");
                getDirection(gameBoard, sides);
        }
    }

    public static void PrintUI(int[][] gameboard, int sides) {
        Console.cls();
        println("═════════════════════════════════════");
        for (int[] x : gameboard) {
            System.out.format("║ %6d ║ %6d ║ %6d ║ %6d ║ %n", x[0], x[1], x[2], x[3]);
            println("═════════════════════════════════════");
        }
    }

    public static void addRandomNumber(int[][] Gameboard, int sides) {
        int pos = r.nextInt(sides * sides);
        int row, col;
        var c = 0;
        do {
            pos = (pos + 1) % (sides * sides);
            row = pos / sides;
            col = pos % sides;

            if(Gameboard[row][col] == 0)
                break;
        } while (c++ < sides * sides);

        if(Gameboard[row][col] != 0)
            return;

        int val = r.nextInt(10) == 0 ? 4 : 2;
        Gameboard[row][col] = val;
    }

    public static void moveUp(int[][] gameBoard, int sides) {
        for (int row = 0; row < sides; row++) {
            var boardRow = getColumns(gameBoard, sides, row);
            setColumns(gameBoard, sides, row, moveBackwards(sides, boardRow));
        }
    }

    public static void moveLeft(int[][] gameBoard, int sides) {
        for (int row = 0; row < sides; row++) {
            gameBoard[row] = moveBackwards(sides, gameBoard[row]);
        }
    }

    public static void moveDown(int[][] gameBoard, int sides) {
        for (int row = 0; row < sides; row++) {
            var boardRow = getColumns(gameBoard, sides, row);
            setColumns(gameBoard, sides, row, moveForwards(sides, boardRow));
        }
    }

    public static void moveRight(int[][] gameBoard, int sides) {
        for (int row = 0; row < sides; row++) {
            gameBoard[row] = moveForwards(sides, gameBoard[row]);
        }
    }

    private static int[] moveForwards(int sides, int[] boardRow) {
        for (int col = 0; col < sides; col++) {
            // Skip if the value is 0
            var value = boardRow[col];
            if (value == 0) continue;

            for (int i = col + 1; i < sides; i++) {
                // Keep moving left as long as the value is still 0
                if (boardRow[i] == 0) continue;

                // If the next item is not equal to the current value,
                // break as it cannot be merged.
                if (boardRow[i] != value) break;

                // If a match is found, add the value and then set the i to 0
                if (boardRow[i] == value) {
                    boardRow[col] *= 2;
                    boardRow[i] = 0;
                    col = i;
                    break;
                }
            }
        }
        return shiftToRight(boardRow);
    }

    private static int[] moveBackwards(int sides, int[] boardRow) {
        for (int col = sides - 1; col > -1; col--) {
            // Skip if the value is 0
            var value = boardRow[col];
            if (value == 0) continue;

            for (int i = col - 1; i > -1; i--) {
                // Keep moving left as long as the value is still 0
                if (boardRow[i] == 0) continue;

                // If the next item is not equal to the current value,
                // break as it cannot be merged.
                if (boardRow[i] != value) break;

                // If a match is found, add the value and then set the i to 0
                if (boardRow[i] == value) {
                    boardRow[col] *= 2;
                    boardRow[i] = 0;
                    col = i;
                    break;
                }
            }
        }
        return shiftToLeft(boardRow);
    }

    public static int[] getColumns(int[][] gameBoard, int sides, int col) {
        var result = new int[sides];
        for (int i = 0; i < sides; i++) {
            result[i] = gameBoard[i][col];
        }
        return result;
    }

    public static int getPoints(int[][] gameBoard) {
        var result = 0;
        for (var row : gameBoard) {
            for (var num : row) {
                result += num;
            }
        }
        return result;
    }

    public static boolean canMove(int[][] gameBoard, int sides) {
        var right = deepCopy(gameBoard, sides);
        moveRight(right, sides);
        if(!isExact(gameBoard, right, sides))
            return true;

        var left = deepCopy(gameBoard, sides);
        moveLeft(left, sides);
        if(!isExact(gameBoard, left, sides))
            return true;

        var up = deepCopy(gameBoard, sides);
        moveUp(up, sides);
        if(!isExact(gameBoard, up, sides))
            return true;

        var down = deepCopy(gameBoard, sides);
        moveDown(down, sides);
        return !isExact(gameBoard, down, sides);
    }

    private static int[][] deepCopy(int[][] gameBoard, int sides) {
        var temp = new int[sides][sides];
        for (int i = 0; i < sides; i++) {
            System.arraycopy(gameBoard[i], 0, temp[i], 0, sides);
        }
        return temp;
    }

    public static boolean isExact(int[][] gameBoard, int[][] compareTo, int sides) {
        for (int i = 0; i < sides; i++) {
            for (int j = 0; j < sides; j++) {
                if (gameBoard[i][j] != compareTo[i][j])
                    return false;
            }
        }
        return true;
    }

    public static void setColumns(int[][] gamBoard, int sides, int col, int[] set) {
        for (int i = 0; i < sides; i++) {
            gamBoard[i][col] = set[i];
        }
    }

    public static int[] shiftToLeftPerf(int[] row) {
        var result = new int[row.length];
        var index = 0;
        for (int value : row) {
            if (value != 0) {
                result[index++] = value;
            }
        }
        return result;
    }

    public static int[] shiftToRightPerf(int[] row) {
        var result = new int[row.length];
        var index = row.length - 1;
        for (int i = index; i >= 0; i--) {
            if (row[i] != 0) {
                result[i] = row[index--];
            }
        }
        return result;
    }

    public static int[] shiftToLeft(int[] row) {
        var shifted = Arrays.stream(row)
                .filter(i -> i != 0).toArray();
        var zeroes = Arrays.stream(row)
                .filter(i -> i == 0).toArray();
        return IntStream.concat(Arrays.stream(shifted), Arrays.stream(zeroes)).toArray();
    }

    public static int[] shiftToRight(int[] row) {
        var shifted = Arrays.stream(row)
                .filter(i -> i != 0).toArray();
        var zeroes = Arrays.stream(row)
                .filter(i -> i == 0).toArray();
        return IntStream.concat(Arrays.stream(zeroes), Arrays.stream(shifted)).toArray();
    }

    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    public static void print(String str) {
        System.out.print(str);
    }

    public static void println(String str) {
        System.out.println(str);
    }
}