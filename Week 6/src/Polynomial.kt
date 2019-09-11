fun main() {
    val maxDeg = promptInt("Input highest degree")
    val nums = promptArr("Numeric Coefficients", maxDeg) { it.toInt() }

    val polynomials = nums.withIndex().map { (index, num) -> "${num}x^${index}"}
    print(polynomials.joinToString(" + "))
}

fun promptInt(ask: String): Int {
    return prompt(ask) { it.toInt() }
}

fun <T> prompt(ask: String, parse: (String) -> T): T {
    print("$ask > ")
    return parse(readLine()!!)
}


inline fun <reified T> promptArr(ask: String, size: Int, crossinline parse: (String) -> T): Array<T> {
    return prompt(ask) { s ->
        val arr = s
            .split(" ")
            .map { parse(it) }
            .take(size)

        arr.toTypedArray();
    };
}