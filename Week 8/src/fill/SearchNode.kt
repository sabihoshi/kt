package fill

import BOARD_SIZE
import Board

class SearchNode(val board: Board, var start: Pair<Int, Int>, val orientation: Board.Orientation) {
    enum class Direction {
        Up, Down, Left, Right
    }


    var left: Pair<Int, Int> = start
    var right: Pair<Int, Int> = start
    var triple = Triple(start.first, start.second, orientation)

    fun seekGenerator(direction: Direction, start: Pair<Int, Int>) = sequence {
        when (direction) {
            Direction.Right -> for (i in start.second until BOARD_SIZE) {
                yield(Pair(start.first, start.second + i))
            }
            Direction.Left -> for (i in 0 until start.second) {
                yield(Pair(start.first, start.second - i))
            }
            Direction.Down -> for (i in start.first until BOARD_SIZE) {
                yield(Pair(start.first + i, start.second))
            }
            Direction.Up -> for (i in 0 until start.first) {
                yield(Pair(start.first - i, start.second))
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

    private fun reverse(orientation: Board.Orientation): Board.Orientation {
        return if (orientation == Board.Orientation.Vertical) Board.Orientation.Horizontal
        else Board.Orientation.Horizontal
    }

    private fun modifyLeft(coordinates: Sequence<Pair<Int, Int>>) {
        for (coordinate in coordinates) {
            if (board.tiles[coordinate.first][coordinate.second].text != "") {
                left = Pair(coordinate.first, coordinate.second)
                addNode(left, orientation)
            }
        }
    }

    private fun modifyRight(coordinates: Sequence<Pair<Int, Int>>) {
        for (coordinate in coordinates) {
            if (board.tiles[coordinate.first][coordinate.second].text != "") {
                right = Pair(coordinate.first, coordinate.second)
                addNode(right, reverse(orientation))
            }
        }
    }

    private fun addNode(coordinate: Pair<Int, Int>, orientation: Board.Orientation) {
        board.nodes[Triple(coordinate.first, coordinate.second, orientation)] = this
        val node = SearchNode(board, coordinate, reverse(orientation))
        if(!board.nodes.contains(node.triple)) {
            board.nodes[node.triple] = node
            node.search()
        }
    }
}

class DirectionChecker {
    var Up: Boolean = false
    var Down: Boolean = false
    var Left: Boolean = false
    var Right: Boolean = false
}