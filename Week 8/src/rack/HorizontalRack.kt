package rack

import Player
import ScrabbleForm
import java.awt.GridLayout

class HorizontalRack(player: Player, parent: ScrabbleForm) : Rack(
    player, parent
) {
    init {
        layout = GridLayout(1, RACK_SIZE)
    }
}