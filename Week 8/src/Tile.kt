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

class Tile(val color: Color, val wordMultiplier: Int, val letterMultiplier: Int, val parent: ScrabbleForm) : JButton() {
    var coordinates: Pair<Int, Int>? = null
    private var isMultiplierUnused = true

    fun getLetterMult(): Int {
        if (isMultiplierUnused) return letterMultiplier
        else {
            this.isMultiplierUnused = false
            return 1
        }
    }

    fun getLetterPoints(input: String, c: Char): Int {
        val points = points.first { p -> p.first.contains(c.toUpperCase()) }.second
        return if (isMultiplierUnused) points
        else {

            points * getLetterMult()
        }
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
            parent.removeTilePressed()
            if (parent.tilePressed != this) {
                parent.tilePressed = this
                button.border = LineBorder(Color.CYAN)

                if (parent.hasRackedPressed) {
                    parent.placeTile()
                }
            }
        }
    }

    private val points = arrayOf(
        Pair(arrayOf('A', 'E', 'I', 'L', 'N', 'O', 'R', 'S', 'T', 'U'), 1),
        Pair(arrayOf('D', 'G'), 2),
        Pair(arrayOf('B', 'C', 'M', 'P'), 3),
        Pair(arrayOf('F', 'H', 'V', 'W', 'Y'), 4),
        Pair(arrayOf('K'), 5),
        Pair(arrayOf('J', 'X'), 8),
        Pair(arrayOf('Q', 'Z'), 10)
    )
}
