package dev.kao.hizamakura

fun main(vararg args: String) {
    /*
        Sample Dialogue
        Start: 1
        End: 12
        Gap: 3
        Output: 1 -4 7 -10
     */

    // User input, including validation
    val start = GetInt("Start", 0, 10)
    val end = GetInt("End", 11, 99)
    val gap = GetInt("Gap", 2, 10)

    for (i in start..end step gap) {
        print("${if (i % 2 == 0) "-" else ""}$i ")
    }
}

// A prompt that retuns an Int. min and max are exclusive.
fun GetInt(prompt: String, min: Int, max: Int): Int {
    var input: Int?
    do {
        print("$prompt (${min+1}-${max-1}): ")
        input = readLine()!!.toIntOrNull()
    } while(input == null || input <= min || input >= max)
    return input
}