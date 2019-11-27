package Board.Tiles

import java.awt.*

class Star : Tile() {
    override val wordMultiplier: Int
        get() = 2

    override val color: Color
        get() = Color.YELLOW
}