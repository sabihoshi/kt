package Board.Tiles

import java.awt.*

class Red : Tile() {
    override val wordMultiplier: Int
        get() = 2

    override val color: Color
        get() = Color.decode("#B3172C")
}
