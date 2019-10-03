import Console.color

class Table(val foreGround: Int) {

    fun top(width: Int, columns: Int): String {
        return surround("╔", "═", "╗",  columns, width)
    }

    fun bottom(width: Int, columns: Int): String {
        return surround("╚", "═", "╝",  columns, width)
    }

    fun surround(start: String, middle: String, end: String, items: Int, spacing: Int): String {
        return "$start${middle.repeat(items * (spacing + 1) - 1)}$end"
    }

    fun printTable(board: Array<Array<Letter>>, cellSize: Int?) {
        val size = board.size + 1
        val spacing = cellSize ?: 3
        val delimiter = " ".repeat(spacing)

        println(top(spacing, size))
        for (row in board) {
            row.map{x -> {
                color(x.second)
                "${x.first}"
                color(foreGround)
            }}.joinToString(
                delimiter,
                postfix = delimiter,
                prefix = delimiter
            ).also {
                println("║$it║")
            }
        }
        println(bottom(spacing, size))
    }
}