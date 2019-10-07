import Console.color
import java.lang.StringBuilder

private const val COORDINATE_COLOR = 7
private const val PAD_SIZE = 2
private const val MINIMUM_SPACING = 3

class Table(private val foreGround: Int) {

    fun top(width: Int, columns: Int): String {
        return surround("╔", "═", "╗", columns, width)
    }

    fun bottom(width: Int, columns: Int): String {
        return surround("╚", "═", "╝", columns, width)
    }

    fun surround(start: String, middle: String, end: String, items: Int, spacing: Int): String {
        return "$start${middle.repeat((items + 2) * (spacing + 1) + 1)}$end"
    }

    fun printColor(input: String, color: Int) {
        color(color)
        print(input)
        color(foreGround)
    }

    fun xCoordinates(size: Int, space: String, left: String, right: String) {
        print("║$space")
        val result = StringBuilder()
        result.append("${left.padStart(PAD_SIZE)}$space")
        for (i in 'A'.toInt() until 'A'.toInt() + size) {
            result.append("${i.toChar()}$space")
        }
        result.append("${right.padEnd(PAD_SIZE)}$space")
        printColor(result.toString(), COORDINATE_COLOR)
        println("║$space")
    }

    private fun yCoordinates(space: String, row: Int, board: Array<Array<Letter>>) {
        print("║$space")
        printColor("${row.toString().padStart(PAD_SIZE)}$space", COORDINATE_COLOR)
        for (i in board[row]) {
            printColor("${i.letter}$space", i.player.color)
        }
        printColor("${row.toString().padEnd(PAD_SIZE)}$space", COORDINATE_COLOR)
        println("║$space")
    }

    fun printTable(board: Array<Array<Letter>>, cellSize: Int?) {
        val size = board.size + 1
        val spacing = cellSize ?: MINIMUM_SPACING
        val space = " ".repeat(spacing)

        println(top(spacing, size))
        xCoordinates(board.size, space, "╭", "╮")
        for (row in board.indices) {
            yCoordinates(space, row, board)
        }
        xCoordinates(board.size, space, "╰", "╯")
        println(bottom(spacing, size))
    }


}