package Board.Tiles

import java.awt.*

class Pink : Tile() {
    override val wordMultiplier: Int
        get() = 2

    override val color: Color
        get() = Color.decode("#DB8298")
}
