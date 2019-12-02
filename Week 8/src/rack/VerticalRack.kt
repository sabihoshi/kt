package rack

import Player
import ScrabbleForm
import java.awt.GridLayout

class VerticalRack(player: Player, parent: ScrabbleForm) : Rack(
    player,
    parent
) {
    init {
        layout = GridLayout(RACK_SIZE, 1)
    }
}