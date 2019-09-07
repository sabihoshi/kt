package com.hizamakura.array2;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class MagicSquareGenerate {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        println("Magic square");
        int n = prompt("Input magic square size", Integer::parseInt);
        var magicSquare = generateMagicSquare(n);
        var widest = String.valueOf(getHighestInt(magicSquare)).length();
        for (var row : magicSquare) {
            println(join(" ", widest, row));
        }
    }


    public static int[][] generateMagicSquare(int size) {
        var n = size;
        int[][] magicSquare = new int[n + 1][n + 1];

        // Initialize position for 1
        int r = 0;
        int c = n / 2;

        // Generate all values in square
        for (int num = 1; num <= n * n; ) {
            // Next position has a value
            if (magicSquare[r][c] != 0) {
                c--;
                r += 2;
            }

            // Set the value
            magicSquare[r][c] = num++;

            // Move upwards right
            c++;
            r--;

            if (c == n && r == -1) {
                c--;
                r += 2;
            }

            // Right side
            if (c == n)
                c = 0;

            // Top side
            if (r < 0)
                r = n - 1;
        }

        // Sum rows, columns, and corners.
        int corner = 0;
        for (int i = 0; i < size; i++) {
            var row = Arrays.stream(magicSquare[i]).sum();
            magicSquare[i][size] = row;

            var col = Arrays.stream(getColumn(magicSquare, i, 0)).sum();
            magicSquare[size][i] = col;

            corner += magicSquare[i][i];
        }
        magicSquare[size][size] = corner;

        return magicSquare;
    }

    public static int getHighestInt(int[][] matrix) {
        var highest = 0;
        for (var row : matrix) {
            for (var value : row) {
                highest = value > highest ? value : highest;
            }
        }
        return highest;
    }

    public static int[] getColumn(int[][] matrix, int column, int defaultVal) {
        return IntStream.range(0, matrix.length)
                .map(i -> matrix[i].length < column ? defaultVal : matrix[i][column])
                .toArray();
    }

    public static String join(int[] array, int pad) {
        return join(", ", pad, array);
    }

    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    public static String join(String delimiter, int pad, int[] array) {
        var sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(padLeft(String.valueOf(array[i]), pad));
            if (i != array.length - 1)
                sb.append(delimiter);
        }
        return sb.toString();
    }

    public static <T> T prompt(String ask, Func<String, T> parse) {
        print(ask + ": ");
        return parse.invoke(in.nextLine());
    }

    public static int[] promptArr(String ask, int size) {
        return prompt(ask, s -> {
            var stream = Arrays.stream(s.split(" "));
            var result = stream
                    .limit(size)
                    .mapToInt(Integer::parseInt)
                    .toArray();
            return result;
        });
    }

    public static void print(String str) {
        System.out.print(str);
    }

    public static void println(String str) {
        System.out.println(str);
    }

    interface Func<T, TResult> {
        public TResult invoke(T arg);
    }

    class FuncImpl<T, TResult> implements Func<T, TResult> {
        Func<T, TResult> function;

        public FuncImpl(Func<T, TResult> function) {
            this.function = function;
        }

        @Override
        public TResult invoke(T arg) {
            return this.function.invoke(arg);
        }
    }
}