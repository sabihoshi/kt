package Board.Tiles

import java.awt.*

class Blue : Tile() {
    override val letterMultiplier: Int
        get() = 2

    override val color: Color
        get() = Color.decode("#90C2E5")
}
