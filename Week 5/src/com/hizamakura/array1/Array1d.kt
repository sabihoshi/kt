package com.hizamakura.array1

fun main() {
    // input n
    val n = prompt("How many values?") { it.toInt() }

    // input values
    val values = promptArr("Enter the values", n) { it.toInt() }

    // display sum & avg
    println("${values.joinToString(" + ")} = ${values.sum()}")
    println("ave(${values.joinToString()}) = ${values.average()}")
}

fun <T> prompt(ask: String, parse: (String) -> T): T {
    print("$ask: ")
    return parse(readLine()!!)
}

inline fun <reified T> promptArr(ask: String, size: Int, crossinline parse: (String) -> T): Array<T> {
    return prompt(ask) { s ->
        val arr = s
                .split(" ")
                .map { parse(it) }
                .take(size)

        arr.toTypedArray()
    };
}