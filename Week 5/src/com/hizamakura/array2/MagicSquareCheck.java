package com.hizamakura.array2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class MagicSquareCheck {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int size = prompt("Input size", Integer::parseInt);
        var matrix = new int[size + 1][size + 1];

        // Input values
        println("Please input " + size + " numbers separated by spaces.");
        for (int i = 0; i < size; i++) {
            matrix[i] = java.util.Arrays.copyOf(promptArr("", size), size + 1);
        }

        var sums = new ArrayList<Integer>();

        int diagSum = 0;
        for (int i = 0; i < size; i++) {
            // Rows
            var row = Arrays.stream(matrix[i]).sum();
            sums.add(row);
            matrix[i][size] = row;

            // Columns
            var col = Arrays.stream(getColumn(matrix, i, 0)).sum();
            sums.add(col);
            matrix[size][i] = col;

            // Diagonal
            diagSum += matrix[i][i];
        }
        sums.add(diagSum);
        matrix[size][size] = diagSum;

        println("Result");
        var widest = String.valueOf(getHighestInt(matrix)).length();
        for (var row : matrix) {
            println(join(" ", widest, row));
        }

        if (isAllEqual(sums))
            println("Is a magic square.");
        else
            println("Is not a magic square.");
    }

    public static int[] getColumn(int[][] matrix, int column, int defaultVal) {
        return IntStream.range(0, matrix.length)
                .map(i -> matrix[i].length < column ? defaultVal : matrix[i][column])
                .toArray();
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

    public static boolean isAllEqual(int[] input) {
        for (int i = 1; i < input.length; i++) {
            if (input[0] != input[i]) return false;
        }

        return true;
    }

    public static boolean isAllEqual(List<Integer> list) {
        return isAllEqual(convertIntegers(list));
    }

    public static int[] convertIntegers(List<Integer> list) {
        int[] ret = list.stream().mapToInt(i -> i).toArray();
        return ret;
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