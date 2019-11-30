import TileFactory.Type
import java.awt.GridLayout
import java.util.*
import javax.swing.JPanel

private const val BOARD_SIZE = 15
private const val RACK_SIZE = 7
private const val MAX_PLAYERS = 4
private const val MIN_PLAYERS = 2

class Board(val parent: ScrabbleForm) : JPanel() {

    enum class Direction {
        Down,
        Across,
        Upwards,
        Backwards
    }



    val tiles = ArrayList<ArrayList<Tile>>()
    val player = ArrayList<Player>()

    private val tileFactory = TileFactory(parent)

    init {
        layout = GridLayout(BOARD_SIZE, BOARD_SIZE)
        initTiles()
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

        for(row in tiles) {
            for(tile in row) {
                add(tile.button)
            }
        }
    }
}