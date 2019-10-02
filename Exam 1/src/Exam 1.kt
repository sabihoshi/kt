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
    val coefficient = promptList("Enter the Numerical Coefficients (starting with x^0): 5 4 -3 0 1", degree)
    val x = prompt("Enter the value of X")

    println("Evaluating the Polynomial...")
}

fun prompt(question: String): Int {
    println("$question > ")
    return readLine()!!.toInt()
}

fun prompt(question: String, min: Int, max: Int): Int {
    val result = prompt(question)
    if(result > max || result < min)
        return prompt(question, min, max)
    return result
}

fun promptList(question: String): List<String> {
    println("$question > ")
    return readLine()!!.split(" ")
}

fun promptList(question: String, max: Int): List<String> {
    val result = promptList(question).take(max)
    if(result.size != max)
        return promptList(question, max)
    return result
}