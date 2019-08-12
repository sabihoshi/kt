package dev.kao.hizamakura

// 1:46m
fun main(vararg args: String) {
    /*
        Sample Dialogue
        Choose the Sequence:
        1 Counting Numbers
        2 Odd Numbers
        3 Even Numbers
        4 Multiple of N

        Choice (1-4): 4
        LB: 5
        UB: 20
        N: 3

        6, 9, 12, 15, 18
     */

    println("Choose the Sequence:")
    println("1 Counting Numbers")
    println("2 Odd Numbers")
    println("3 Even Numbers")
    println("4 Multiple of N")

    // User input, including validation
    val Choice = GetInt("Choice", 1, 4)
    val LB = GetInt("LB")
    val UB = GetInt("UB")

    when (Choice) {
        1 -> print((LB..UB).joinToString())
        2 -> print((LB..UB).filter { it % 2 == 1 }.joinToString())
        3 -> print((LB..UB).filter { it % 2 == 0 }.joinToString())
        4 -> {
            val N = GetInt("N")
            print((LB..UB).filter { it % N == 0 }.joinToString("-"))
        }
    }
}

// Returns an Integer from min-max
fun GetInt(prompt: String, min: Int = 0, max: Int): Int {
    var input: Int?
    do {
        print("$prompt ($min-$max): ")
        input = readLine()!!.toIntOrNull()
    } while (input == null || input < min || input > max)
    return input
}

// A Prompt without bounds
fun GetInt(prompt: String): Int {
    var input: Int?
    do {
        print("$prompt: ")
        input = readLine()!!.toIntOrNull()
    } while (input == null)
    return input
}