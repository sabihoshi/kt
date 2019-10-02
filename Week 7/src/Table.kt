class Table {
    fun top(width: Int, columns: Int): String {
        return surround("╔", "╦", "╗", '═', width, columns)
    }

    fun divider(width: Int, columns: Int): String {
        return surround("║", "║", "║", ' ', width, columns)
    }

    fun dividerCross(width: Int, columns: Int): String {
        return surround("╠", "╬", "╣", '═', width, columns)
    }

    fun bottom(width: Int, columns: Int): String {
        return surround("╚", "╩", "╝", '═', width, columns)
    }

    fun surround(start: String, middle: String, end: String, padChar: Char, width: Int, columns: Int): String {
        return "${start.padEnd(width, padChar)}${middle.padEnd(width, padChar).repeat(columns)}$end"
    }

    fun table(width: Int, length: Int, columns: Int, rows: Int) {
        println(top(width, columns))
        repeat(rows) {
            repeat(length) {
                println(divider(width, columns))
            }
            println(dividerCross(width, columns))
        }

        repeat(length) {
            println(divider(width, columns))
        }

        println(bottom(width, columns))
    }
}