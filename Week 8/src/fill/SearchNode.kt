package fill

import BOARD_SIZE
import Board

class SearchNode(val board: Board, var start: Pair<Int, Int>, val orientation: Board.Orientation) {
    enum class Direction {
        Up, Down, Left, Right
    }


    var min: Pair<Int, Int> = start
    var max: Pair<Int, Int> = start
    var triple = Triple(start, start, orientation)
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

    fun search() {
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

    // Words have forward and backward
    fun getWords(): Pair<String, String> {
        val sb = StringBuilder()

        if (orientation == Board.Orientation.Horizontal) {
            for (i in min.second..max.second) {
                sb.append(board.tiles[start.first][i].text)
            }
        } else {
            for (i in min.first..max.first) {
                sb.append(board.tiles[i][start.second].text)
            }
        }

        val ret = sb.toString()
        return Pair(ret, ret.reversed())
    }

    private fun reverse(orientation: Board.Orientation): Board.Orientation {
        return if (orientation == Board.Orientation.Vertical) Board.Orientation.Horizontal
        else Board.Orientation.Vertical
    }

    private fun modifyLeft(coordinates: Sequence<Pair<Int, Int>>) {
        for (coordinate in coordinates) {
            if (board.tiles[coordinate.first][coordinate.second].text != "") {
                min = Pair(coordinate.first, coordinate.second)
            } else break
        }
    }

    private fun modifyRight(coordinates: Sequence<Pair<Int, Int>>) {
        for (coordinate in coordinates) {
            if (board.tiles[coordinate.first][coordinate.second].text != "") {
                max = Pair(coordinate.first, coordinate.second)
            } else break
        }
    }

    fun extraNode() {
        val node = SearchNode(board, start, reverse(orientation))
        node.search()
        if (!board.nodes.contains(node.triple) && node.getWords().toList().all { w -> w.length > 1 }) {
            board.nodes[node.triple] = node
            println("EXTRA NODE: ${node.min} - ${node.max}: ${node.getWords().first}&${node.getWords().second}, ${node.orientation}")
        }
    }
}

class DirectionChecker {
    var Up: Boolean = false
    var Down: Boolean = false
    var Left: Boolean = false
    var Right: Boolean = false
}