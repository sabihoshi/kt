package rack
import Player
import ScrabbleForm
import java.awt.GridLayout

public class VerticalRack(player: Player, letters: ArrayList<Letter>, parent: ScrabbleForm) : Rack(player, letters,
    parent
) {
    init {
        layout = GridLayout(RACK_SIZE, 1)
    }
}