package Board.Tiles

import java.awt.*

open class Tile {
    var isMultiplierUnused = true
        protected set

    open val color: Color
        get() = Color.WHITE

    open val wordMultiplier: Int
        get() = 1

    open val letterMultiplier: Int
        get() = 1

    fun makeMultiplierUsed() {
        this.isMultiplierUnused = false
    }
}
