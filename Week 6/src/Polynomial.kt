import kotlin.math.abs

fun main() {
    val maxDeg = promptInt("Input highest degree")
    val nums = promptArr("Numeric Coefficients", maxDeg) { it.toInt() }

    val polynomials = nums.withIndex()
        .filter { (i, n) -> n != 0 }
        .map { (i, n) ->
            if (abs(n) == 1 && i == 0) n
            else {
                val base =
                    if (n < 0 && abs(n) == 1) "-"
                    else "$n"

                val degree =
                    when (i) {
                        0 -> ""
                        1 -> "x"
                        else -> "x^$i"
                    }

                "$base$degree"
            }
        }
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