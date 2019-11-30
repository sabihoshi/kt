package rack

import Player
import javax.swing.JButton
import javax.swing.JPanel

const val RACK_SIZE = 7

abstract class Rack(
    val player: Player,
    val letters: List<Char>
) : JPanel() {
    var characterPressed: Char? = null
    val buttons: List<JButton> =
        listOf(JButton(), JButton(), JButton(), JButton(), JButton(), JButton(), JButton())
}