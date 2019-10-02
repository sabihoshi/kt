package com.hizamakura.kao;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class Exam_1 {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        var degree = prompt("What is the highest exponent in the polynomial (1..10)", 1, 10);
        var coeff = promptArr("Enter the Numerical Coefficients (starting with x^0): 5 4 -3 0 1", degree + 1);
        var x = prompt("Enter the value of X", Double::parseDouble);

        System.out.println("Evaluating the Polynomial...");
        Poly1(coeff);
        Poly2(coeff, x);
        Poly3(coeff, x);
        Poly4(coeff, x);
        Poly5(coeff, x);
    }

    private static void Poly1(int[] coeff) {
        printPolynomial(coeff);
    }

    private static void Poly2(int[] coeff, Double x) {
        printPolynomial(coeff, x);
    }

    private static void Poly3(int[] coeff, Double x) {
        printPolynomial(coeff, x, true);
    }

    private static void Poly4(int[] coeff, Double x) {
        System.out.println(getSimplified(coeff, x)
            .stream()
            .map(String::valueOf)
            .collect(Collectors.joining(" + ")));
    }

    private static void Poly5(int[] coeff, Double x) {
        System.out.println(getSimplified(coeff, x).stream().mapToDouble(Double::doubleValue).sum());
    }

    private static void printPolynomial(int[] nums) {
        printPolynomial(nums, null, false);
    }

    private static void printPolynomial(int[] nums, double x) {
        printPolynomial(nums, x, false);
    }

    private static void printPolynomial(int[] nums, Double x, boolean solveDegree) {
        var polynomials = IntStream.range(0, nums.length)
            .filter(i -> nums[i] != 0)
            .mapToObj((i) -> {
                var n = nums[i];
                if (i == 0) return n;
                else {
                    var base = "";
                    if (n < 0 && abs(n) == 1) base = "-";
                    else if (n != 1) base = String.valueOf(n);

                    var degree = x == null ? "x" : String.valueOf(abs(x));
                    if (i != 1) {
                        degree = x == null ?
                            "x^" + i :
                            solveDegree ?
                                surround(pow(x, i)) :
                                surround(abs(x) + "^" + i);
                    }

                    return base + degree;
                }
            })
            .toArray();

        System.out.println(join(" + ", 1, polynomials));
    }

    private static String surround(double x) {
        return surround(String.valueOf(x));
    }

    private static String surround(String x) {
        return "(" + x + ")";
    }

    private static List<Double> getSimplified(int[] nums, double x) {
        return IntStream.range(0, nums.length)
            .filter(i -> nums[i] != 0)
            .mapToObj(i -> nums[i] * (pow(x, i)))
            .collect(Collectors.toList());
    }

    private static <T> T prompt(String question, Func<String, T> parse) {
        System.out.print(question + " > ");
        return parse.invoke(scanner.nextLine());
    }

    private static int prompt(String question, int min, int max) {
        var result = prompt(question, Integer::valueOf);
        if (result > max || result < min)
            return prompt(question, min, max);
        return result;
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

    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    public static String join(String delimiter, int pad, Object[] array) {
        var sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(padLeft(String.valueOf(array[i]), pad));
            if (i != array.length - 1)
                sb.append(delimiter);
        }
        return sb.toString();
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
