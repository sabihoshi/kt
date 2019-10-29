package com.hizamakura.kao;

import javafx.util.Pair;

import java.util.Scanner;
import java.util.function.BiFunction;

import static java.lang.Math.abs;

public class Remedial {
    private static Template t = new Template();

    public static void main(String[] args) {
        var first = ask("first");
        var second = ask("second");

        var added = first.add(second.Coefficients);
        var subtracted = first.subtract(second.Coefficients);

        t.println("(" + first + ") + (" + second + ") = " + added);
        t.println("(" + first + ") - (" + second + ") = " + subtracted);
    }

    public static Polynomial ask(String name) {
        var maxDegree = t.PromptInt("What is the degree of the " + name + " polynomial");
        var arr = t.PromptIntArr("Enter the numerical coefficients (from lowest term)", maxDegree + 1);
        return new Polynomial(arr);
    }
}

class Template {
    private Scanner scanner = new Scanner(System.in);

    int PromptInt(String question) {
        print(question + " > ");
        var result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

    int[] PromptIntArr(String question, int amount) {
        print(question + " > ");

        var result = new int[amount];
        for (int i = amount - 1; i > -1; i--) {
            result[i] = scanner.nextInt();
        }
        scanner.nextLine();
        return result;
    }

    public void println(String print) {
        System.out.println(print);
    }

    public void print(String print) {
        System.out.print(print);
    }
}

class Term {
    public Term(int coefficient, int degree) {
        Coefficient = coefficient;
        Degree = degree;
    }

    public Term add(Term term) {
        return new Term(Coefficient + term.Coefficient, Degree);
    }

    public Term subtract(Term term) {
        return new Term(Coefficient - term.Coefficient, Degree);
    }

    public int Coefficient;
    public int Degree;

    @Override
    public String toString() {
        if (Degree == 0) return String.valueOf(Coefficient);
        var base = "";
        if (Coefficient < 0 && abs(Coefficient) == 1) base = "-";
        else if (Coefficient != 1) base = String.valueOf(Coefficient);
        var degree = Degree == 1 ? "x" : "x^" + Degree;
        return base + degree;
    }
}

class Polynomial {
    public Polynomial(int[] coefficients) {
        Coefficients = coefficients;
        Terms = new Term[coefficients.length];
        for (int i = 0; i < coefficients.length; i++) {
            Terms[i] = new Term(coefficients[i], coefficients.length - i - 1);
        }
    }

    public int[] Coefficients;
    public Term[] Terms;

    public static int[] calculate(int[] first, int[] second, BiFunction<Integer, Integer, Integer> calculateFunc) {
        var max = Math.max(first.length, second.length);
        var result = new int[max];
        var fCounter = first.length;
        var sCounter = second.length;
        for (int i = max - 1; i > -1; i--) {
            var left = --fCounter < 0 ? 0 : first[fCounter];
            var right = --sCounter < 0 ? 0 : second[sCounter];
            result[i] = calculateFunc.apply(left, right);
        }
        return result;
    }

    public Polynomial add(int[] coefficients) {
        return new Polynomial(calculate(Coefficients, coefficients, (x, y) -> x + y));
    }

    public Polynomial subtract(int[] coefficients) {
        return new Polynomial(calculate(Coefficients, coefficients, (x, y) -> x - y));
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (int i = 0; i < Terms.length; i++) {
            sb.append(Terms[i].toString());
            if(i < Terms.length - 1) sb.append(" + ");
        }
        return sb.toString();
    }
}
