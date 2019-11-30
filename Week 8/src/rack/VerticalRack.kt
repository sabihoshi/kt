package rack
import Player
import java.awt.GridLayout

public class VerticalRack(player: Player, letters: List<Char>) : Rack(player, letters) {
    init {
        layout = GridLayout(RACK_SIZE, 1)
        for(button in buttons) {
            add(button)
        }
    }
}