import java.awt.Color
import javax.swing.JButton
import javax.swing.border.LineBorder

class TileFactory(val parent: ScrabbleForm) {
    enum class Type {
        Blue, DarkBlue, Pink, Red, Star
    }

    fun createTile(color: Type? = null): Tile {
        return when (color) {
            Type.Blue -> Tile("#90C2E5", 1, 2, parent)
            Type.DarkBlue -> Tile("#3187C2", 1, 2, parent)
            Type.Pink -> Tile("#DB8298", 2, 1, parent)
            Type.Red -> Tile("#B3172C", 2, 1, parent)
            Type.Star -> Tile(Color.YELLOW, 2, 1, parent)
            else -> Tile(Color.WHITE, 1, 1, parent)
        }
    }
}

class Tile(color: Color, private val word: Int, private val letter: Int, val parent: ScrabbleForm) : JButton() {
    var coordinates: Pair<Int, Int>? = null
    var orientation: Board.Orientation? = null
    var turnPlaced: Int? = null
    private var isLetterUsed = false
    private var isWordUsed = false

    val letterMultiplier: Int
        get() {
            var ret = 1
            if (!isLetterUsed) {
                isLetterUsed = true
                ret = letter
            }
            return ret
        }

    val wordMultiplier: Int
        get() {
            var ret = 1
            if (!isWordUsed) {
                isWordUsed = true
                ret = word
            }
            return ret
        }

    constructor(color: String, wordMultiplier: Int, letterMultiplier: Int, parent: ScrabbleForm) : this(
        Color.decode(color),
        wordMultiplier,
        letterMultiplier,
        parent
    )

    init {
        background = color
        addActionListener { e ->
            val button = e.source as JButton
            parent.removeTile()
            if (parent.pressedTile != this) {
                parent.pressedTile = this
                button.border = LineBorder(Color.CYAN)

                if (parent.hasRackedPressed) {
                    parent.placeTile()
                }
            }
        }
    }
}
