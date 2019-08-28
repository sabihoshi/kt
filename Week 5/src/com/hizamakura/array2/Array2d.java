package com.hizamakura.array2;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Array2d {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        // input n
        int rows = prompt("Input rows", Integer::parseInt);
        int columns = prompt("Input columns", Integer::parseInt);

        var matrix = new int[rows][columns];

        // input values
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = promptArr("", columns);
        }

        println("Return");
        // return values
        for (var row : matrix) {
            println(join(" ", 3, row));
        }

        println("Magic square");
        var magicSquare = generateMagicSquare(3);
        for(var row: magicSquare) {
            println(join(" ", 3, row));
        }
    }

    public static int[][] generateMagicSquare(int size) {
        var n = size;
        int[][] magicSquare = new int[n][n];

        // Initialize position for 1
        int r = n / 2;
        int c = n - 1;

        // One by one put all values in magic square
        for (int num = 1; num <= n * n; ) {
            if (r == -1 && c == n) //3rd condition
            {
                c = n - 2;
                r = 0;
            } else {
                //1st condition helper if next number
                // goes to out of square's right side
                if (c == n)
                    c = 0;

                //1st condition helper if next number is
                // goes to out of square's upper side
                if (r < 0)
                    r = n - 1;
            }

            //2nd condition
            if (magicSquare[r][c] != 0) {
                c -= 2;
                r++;
                continue;
            } else
                //set number
                magicSquare[r][c] = num++;

            // move upwards right
            c++;
            r--;
        }

        return magicSquare;
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
        for(int i = 0; i < array.length; i++) {
            sb.append(padLeft(String.valueOf(array[i]), pad));
            if(i != array.length - 1)
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