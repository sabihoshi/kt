
import TileFactory.Type
import fill.SearchNode
import java.awt.GridLayout
import java.util.*
import javax.swing.JPanel


class Board(val parent: ScrabbleForm) : JPanel() {
    enum class Direction {
        Down,
        Across,
        Upwards,
        Backwards
    }

    enum class Orientation { Vertical, Horizontal }

    val tiles = ArrayList<ArrayList<Tile>>()

    private val tileFactory = TileFactory(parent)

    init {
        layout = GridLayout(BOARD_SIZE, BOARD_SIZE)
        initTiles()
    }

    fun toggleEmptyTiles(isEnabled: Boolean = true) {
        for (row in tiles) {
            for (tile in row) {
                tile.isEnabled = if(tile.text == "") isEnabled else tile.turnPlaced == parent.currentTurn
            }
        }
    }

    fun enableOrientation(orientation: Orientation, coordinates: Pair<Int, Int>) {
        for (tile in getTiles(orientation, coordinates)) {
            tile.isEnabled = if(tile.text == "") true else tile.turnPlaced == parent.currentTurn
        }
    }

    var nodes = HashMap<Triple<Pair<Int, Int>, Pair<Int, Int>, Orientation>, SearchNode>()

    fun validateWords(letters: ArrayList<Tile>) {
        for(letter in letters) {
            if(letter.coordinates != null && letter.orientation != null) {
                val validate = SearchNode(this, letter.coordinates!!, letter.orientation!!)
                val extra = validate.extraNode()

                validateNode(validate)
                validateNode(extra)
            }
        }
    }

    private fun validateNode(node: SearchNode): Pair<String, Int>? {
        var ret: Pair<String, Int>? = null
        if (!nodes.contains(node.triple)) {
            nodes[node.triple] = node
            ret = node.getWord()
            parent.currentRack?.player?.points = parent.currentRack?.player?.points?.plus(ret.second)!!
        }
        return ret
    }

    fun getTiles(orientation: Orientation, coordinates: Pair<Int, Int>) = sequence {
        if (orientation == Orientation.Horizontal) {
            for (i in tiles.indices) {
                yield(tiles[coordinates.first][i])
            }
        } else {
            for (i in tiles.indices) {
                yield(tiles[i][coordinates.second])
            }
        }
    }

    private fun initTiles() {
        tiles.addAll(
            listOf(
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList()
            )
        )

        // Line 1
        tiles[0].addAll(
            listOf(
                tileFactory.createTile(Type.Red),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Red),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Red)
            )
        )

        // Line 2
        tiles[1].addAll(
            listOf(
                tileFactory.createTile(),
                tileFactory.createTile(Type.Pink),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Pink),
                tileFactory.createTile()
            )
        )

        // Line 3
        tiles[2].addAll(
            listOf(
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Pink),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Pink),
                tileFactory.createTile(),
                tileFactory.createTile()
            )
        )

        // Line 4
        tiles[3].addAll(
            listOf(
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Pink),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Pink),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue)
            )
        )

        // Line 5
        tiles[4].addAll(
            listOf(
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Pink),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Pink),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile()
            )
        )

        // Line 6
        tiles[5].addAll(
            listOf(
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile()
            )
        )

        // Line 7
        tiles[6].addAll(
            listOf(
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile()
            )
        )

        // Line 8
        tiles[7].addAll(
            listOf(
                tileFactory.createTile(Type.Red),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Star),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Red)
            )
        )

        // Line 9
        tiles[8].addAll(
            listOf(
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile()
            )
        )

        // Line 10
        tiles[9].addAll(
            listOf(
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile()
            )
        )

        // Line 11
        tiles[10].addAll(
            listOf(
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Pink),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Pink),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile()
            )
        )

        // Line 12
        tiles[11].addAll(
            listOf(
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Pink),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Pink),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue)
            )
        )

        // Line 13
        tiles[12].addAll(
            listOf(
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Pink),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Pink),
                tileFactory.createTile(),
                tileFactory.createTile()
            )
        )

        // Line 14
        tiles[13].addAll(
            listOf(
                tileFactory.createTile(),
                tileFactory.createTile(Type.Pink),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Pink),
                tileFactory.createTile()
            )
        )

        // Line 15
        tiles[14].addAll(
            listOf(
                tileFactory.createTile(Type.Red),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Red),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Blue),
                tileFactory.createTile(),
                tileFactory.createTile(),
                tileFactory.createTile(Type.Red)
            )
        )

        for (x in tiles.indices) {
            for (y in tiles[x].indices) {
                tiles[x][y].coordinates = Pair(x, y)
                add(tiles[x][y])
            }
        }
    }
}