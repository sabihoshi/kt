package rack
import Player
import java.awt.GridLayout

public class HorizontalRack(player: Player, letters: List<Char>) : Rack(player, letters) {
    init {
        layout = GridLayout(1, RACK_SIZE)
        for(button in buttons) {
            add(button)
        }
    }
}