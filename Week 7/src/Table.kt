import Console.color

private const val X_COORDINATE_COLOR = 5
private const val Y_COORDINATE_COLOR = 4
private const val PAD_SIZE = 2
private const val MINIMUM_SPACING = 3

class Letter(var letter: Char, var player: Player)

class Player(val color: Int, val number: Int, val rack: MutableList<Char>, var points: Int)

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

    fun xCoordinates(size: Int, space: String, corner: String) {
        print("║$space")
        print("${corner.padStart(PAD_SIZE)}$space")
        for (i in 'A'.toInt() until 'A'.toInt() + size) {
            printColor("${i.toChar()}$space", X_COORDINATE_COLOR)
        }
        print("${corner.padEnd(PAD_SIZE)}$space")
        println("║$space")
    }

    private fun yCoordinates(space: String, row: Int, board: Array<Array<Letter>>) {
        print("║$space")
        printColor("${row.toString().padStart(PAD_SIZE)}$space", Y_COORDINATE_COLOR)
        for (i in board[row]) {
            printColor("${i.letter}$space", i.player.color)
        }
        printColor("${row.toString().padEnd(PAD_SIZE)}$space", Y_COORDINATE_COLOR)
        println("║$space")
    }

    fun printTable(board: Array<Array<Letter>>, cellSize: Int?) {
        val size = board.size + 1
        val spacing = cellSize ?: MINIMUM_SPACING
        val space = " ".repeat(spacing)

        println(top(spacing, size))
        xCoordinates(board.size, space, "■")
        for (row in board.indices) {
            yCoordinates(space, row, board)
        }
        xCoordinates(board.size, space, "■")
        println(bottom(spacing, size))
    }
}