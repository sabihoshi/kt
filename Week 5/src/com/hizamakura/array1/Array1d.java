package com.hizamakura.array1;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// Time:
public class Array1d {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        // input n
        int n = prompt("How many values?", Integer::parseInt);

        // input values
        var values = promptArr("Enter the values", n, Integer::parseInt);

        // display sum & avg
        var stream = values.stream().mapToInt(Integer::valueOf);
        println(join(" + ", values) + " = " + stream.sum());

        stream = values.stream().mapToInt(Integer::valueOf);
        println("ave(" + join(values) + ") = " + stream.average().getAsDouble());
    }

    public static <T> String join(List<T> array) {
        return join(", ", array);
    }

    public static <T> String join(String delimiter, List<T> array) {
        return array.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(delimiter));
    }

    public static <T> T prompt(String ask, Func<String, T> parse) {
        print(ask + ": ");
        return parse.invoke(in.nextLine());
    }

    public static <T> List<T> promptArr(String ask, int size, Func<String, T> parse) {
        return prompt(ask, s -> {
            var stream = Arrays.stream(s.split(" "));
            var result = stream
                    .limit(size)
                    .map(x -> parse.invoke(x))
                    .collect(Collectors.toList());
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

