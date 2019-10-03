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
            print("║$delimiter")
            for(i in row) {
                color(i.player.color)
                print("${i.letter}$delimiter")
                color(foreGround)
            }
            println("║$delimiter")
        }
        println(bottom(spacing, size))
    }
}