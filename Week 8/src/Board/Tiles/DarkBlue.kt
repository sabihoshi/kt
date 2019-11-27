package Board.Tiles

import java.awt.*

class DarkBlue : Tile() {
    override val letterMultiplier: Int
        get() = 3

    override val color: Color
        get() = Color.decode("#3187C2")
}
