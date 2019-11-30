package rack
import Player
import ScrabbleForm
import java.awt.GridLayout

public class HorizontalRack(player: Player, letters: ArrayList<Char>, parent: ScrabbleForm) : Rack(player, letters,
    parent
) {
    init {
        layout = GridLayout(1, RACK_SIZE)
    }
}