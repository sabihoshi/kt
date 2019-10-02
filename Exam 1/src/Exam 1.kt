import kotlin.math.abs
import kotlin.math.pow

/*
    What is the highest exponent in the polynomial (1..10)? 4
    Enter the Numerical Coefficients (starting with x^0): 5 4 -3 0 1
    Enter the value for X: 2
    Evaluating the Polynomial…
    y = 5 + 4x -3x^2 + x^4
    y = 5 + 4(2) -3(2^2) + (2^4)
    y = 5 + 4(2) -3(4) + (16)
    y = 5 + 8 -12 + 16
    y = 17

    Outputs:
    y = 5 + 4x -3x^2 + x^4 //the given polynomial in increasing terms
    y = 5 + 4(2) -3(2^2) + (2^4) //polynomial showing value of x
    y = 5 + 4(2) -3(4) + (16) //polynomial showing value for x k
    y = 5 + 8 -12 + 16 //polynomial showing value of each term
    y = 17 //final evaluation of the polynomial
*/

fun main() {
    val degree = prompt("What is the highest exponent in the polynomial (1..10)", 1, 10)
    val coeff = promptList("Enter the Numerical Coefficients (starting with x^0): 5 4 -3 0 1", degree + 1)
    val x = prompt("Enter the value of X") { it.toDouble() }

    println("Evaluating the Polynomial...")
    Poly1(coeff)
    Poly2(coeff, x)
    Poly3(coeff, x)
    Poly4(coeff, x)
    Poly5(coeff, x)
}

fun Poly1(coeff: List<Int>) {
    printPolynomial(coeff)
}

fun Poly2(coeff: List<Int>, x: Double) {
    printPolynomial(coeff, x)
}

fun Poly3(coeff: List<Int>, x: Double) {
    printPolynomial(coeff, x, true)
}

fun Poly4(coeff: List<Int>, x: Double) {
    println(getSimplified(coeff, x).joinToString(" + "))
}

fun Poly5(coeff: List<Int>, x: Double) {
    println(getSimplified(coeff, x).sum())
}

fun printPolynomial(nums: List<Int>) {
    printPolynomial(nums, null, false)
}

fun printPolynomial(nums: List<Int>, x: Double) {
    printPolynomial(nums, x, false)
}

fun printPolynomial(nums: List<Int>, x: Double?, solveDegree: Boolean) {
    val polynomials = nums.withIndex()
        .filter { (i, n) -> n != 0 }
        .map { (i, n) ->
            if (i == 0) n
            else {
                val base =
                    if (n < 0 && abs(n) == 1) "-"
                    else if (n != 1) "$n"
                    else ""


                val degree =
                    when (i) {
                        0 -> ""
                        1 -> if (x == null) "x" else "(${abs(x)})"
                        else -> when {
                            x == null -> "x^$i"
                            solveDegree -> "(${x.pow(i.toDouble())})"
                            else -> "(${abs(x)}^$i)"
                        }
                    }

                "$base$degree"
            }
        }

    println(polynomials.joinToString(" + "))
}

fun getSimplified(nums: List<Int>, x: Double): List<Double> {
    return nums.withIndex()
        .filter { (i, n) -> n != 0 }
        .map { (i, n) ->
            n * (x.pow(i.toDouble()))
        }
}

fun prompt(question: String): String {
    print("$question > ")
    return readLine()!!
}

fun <T> prompt(question: String, parse: (String) -> T): T {
    return parse(prompt(question))
}

fun prompt(question: String, min: Int, max: Int): Int {
    val result = prompt(question) { it.toInt() }
    if (result > max || result < min)
        return prompt(question, min, max)
    return result
}

fun promptList(question: String): List<Int> {
    return prompt(question).split(" ").map { it.toInt() }
}

fun promptList(question: String, max: Int): List<Int> {
    val result = promptList(question).take(max)
    if (result.size != max)
        return promptList(question, max)
    return result
}