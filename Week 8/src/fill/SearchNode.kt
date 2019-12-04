package fill

import BOARD_SIZE
import Board

class SearchNode(val board: Board, var start: Pair<Int, Int>, val orientation: Board.Orientation) {
    enum class Direction {
        Up, Down, Left, Right
    }



    var min: Pair<Int, Int> = start
    var max: Pair<Int, Int> = start
    val triple
        get() = Triple(min, max, orientation)

    fun seekGenerator(direction: Direction, start: Pair<Int, Int>) = sequence {
        when (direction) {
            Direction.Right -> for (i in start.second..BOARD_SIZE) {
                yield(Pair(start.first, i))
            }
            Direction.Left -> for (i in start.second downTo 0) {
                yield(Pair(start.first, i))
            }
            Direction.Down -> for (i in start.first..BOARD_SIZE) {
                yield(Pair(i, start.second))
            }
            Direction.Up -> for (i in start.first downTo 0) {
                yield(Pair(i, start.second))
            }
        }
    }

    private fun coordinateGenerator() = sequence {
        if (orientation == Board.Orientation.Horizontal) {
            for (i in min.second..max.second) {
                yield(Pair(start.first, i))
            }
        } else {
            for (i in min.first..max.first) {
                yield(Pair(i, start.second))
            }
        }
    }

    private fun search() {
        if (orientation == Board.Orientation.Vertical) {
            // adjust the start and end Up and Down
            val upGenerator = seekGenerator(Direction.Up, start)
            val downGenerator = seekGenerator(Direction.Down, start)
            modifyLeft(upGenerator)
            modifyRight(downGenerator)
        } else if (orientation == Board.Orientation.Horizontal) {
            // adjust the start and end Left and Right
            val leftGenerator = seekGenerator(Direction.Left, start)
            val rightGenerator = seekGenerator(Direction.Right, start)
            modifyLeft(leftGenerator)
            modifyRight(rightGenerator)
        }

    }

    fun getWord(): Pair<String, Int> {
        val sb = StringBuilder()
        var points = 0
        var wordMultiplier = 1

        for(coordinate in coordinateGenerator()) {
            val tile = getTile(coordinate)
            val char = getChar(coordinate)
            sb.append(char)
            wordMultiplier *= wordMultiplier
            points += char?.let { getPoints(it) }?.times(tile.letterMultiplier) ?: 0
        }

        points *= wordMultiplier
        val word = sb.toString()

        return Pair(word, points)
    }

    private fun getChar(coordinate: Pair<Int, Int>) = getTile(coordinate).text.firstOrNull()

    private fun getTile(coordinate: Pair<Int, Int>) = board.tiles[coordinate.first][coordinate.second]

    private fun reverse(orientation: Board.Orientation): Board.Orientation {
        return if (orientation == Board.Orientation.Vertical) Board.Orientation.Horizontal
        else Board.Orientation.Vertical
    }

    private fun modifyLeft(coordinates: Sequence<Pair<Int, Int>>) {
        for (coordinate in coordinates) {
            if (getTile(coordinate).text != "") {
                min = Pair(coordinate.first, coordinate.second)
            } else break
        }
    }

    private fun modifyRight(coordinates: Sequence<Pair<Int, Int>>) {
        for (coordinate in coordinates) {
            if (getTile(coordinate).text != "") {
                max = Pair(coordinate.first, coordinate.second)
            } else break
        }
    }

    fun extraNode(): SearchNode {
        return SearchNode(board, start, reverse(orientation))
    }

    fun getPoints(char: Char) = points.single { p -> p.first.contains(char) }.second

    private val points = arrayOf(
        Pair(arrayOf('A', 'E', 'I', 'L', 'N', 'O', 'R', 'S', 'T', 'U'), 1),
        Pair(arrayOf('D', 'G'), 2),
        Pair(arrayOf('B', 'C', 'M', 'P'), 3),
        Pair(arrayOf('F', 'H', 'V', 'W', 'Y'), 4),
        Pair(arrayOf('K'), 5),
        Pair(arrayOf('J', 'X'), 8),
        Pair(arrayOf('Q', 'Z'), 10)
    )

    init {
        search()
    }
}

class DirectionChecker {
    var Up: Boolean = false
    var Down: Boolean = false
    var Left: Boolean = false
    var Right: Boolean = false
}