package dev.kao.hizamakura

import java.lang.StringBuilder

const val PADDING_SIZE = 8;

fun main() {
    val bases = arrayListOf(10)
    bases.addAll(2..16)

    for (base in bases) {
        print("($base)".padStart(PADDING_SIZE))
    }

    println()

    for (i in 1..20) {
        for (base in bases) {
            print(toBase(i, base).padStart(PADDING_SIZE))
        }
        println()
    }

    if (prompt("Run again? (y/n)") {
            it[0] == 'y'
        }) main()
}

fun toBase(input: Int, base: Int): String {
    var temp = input
    val stringBuilder = StringBuilder()

    do {
        stringBuilder.append(Integer.toHexString((temp % base)).toString().toUpperCase());
        temp /= base
    } while (temp != 0)

    return stringBuilder.reverse().toString();
}