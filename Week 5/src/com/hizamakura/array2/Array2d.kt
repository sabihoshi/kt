package com.hizamakura.array2

// Time: 1m
fun main() {
    /*// input rows
    val rows = prompt("Input rows") { it.toInt() }

    // input columns
    val columns = prompt("Input columns") { it.toInt() }

    // convert to 2d array
    var matrix = Array(rows) { IntArray(columns) }
    for(row in matrix.indices) {
        matrix[row] = promptArr("", columns) { it.toInt() }.toIntArray()
    }

    println("2d array")
    for (row in matrix) {
        println(row.joinToString(" ") { it.toString().padStart(3) })
    }*/

    println("Magic Square")
    val n = prompt("Input size of magic square") { it.toInt() }
    val magicSquare = generateMagicSquare(n)
    for (row in magicSquare) {
        println("${row.joinToString(" ") { it.toString().padStart(5) }} = ${row.sum()}")
    }
}

fun generateMagicSquare(size: Int): Array<IntArray> {
    val magicSquare = Array(size) { IntArray(size) }

    // Initialize position for 1
    var r = size / 2
    var c = size - 1

    // One by one put all values in magic square
    var num = 1
    while (num <= size * size) {
        if (r == -1 && c == size)
        //3rd condition
        {
            c = size - 2
            r = 0
        } else {
            //1st condition helper if next number
            // goes to out of square's right side
            if (c == size)
                c = 0

            //1st condition helper if next number is
            // goes to out of square's upper side
            if (r < 0)
                r = size - 1
        }

        //2nd condition
        if (magicSquare[r][c] != 0) {
            c -= 2
            r++
            continue
        } else
        //set number
            magicSquare[r][c] = num++
3
        // move upwards right
        c++
        r--
    }

    return magicSquare
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

        arr.toTypedArray();
    };
}
