package rack

import Player
import ScrabbleForm
import java.awt.Color
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.border.LineBorder

const val RACK_SIZE = 7

abstract class Rack(
    val player: Player,
    val letters: ArrayList<Letter>,
    val parent: ScrabbleForm
) : JPanel() {
    var buttonPressed: JButton? = null
    val buttons: ArrayList<JButton> = arrayListOf()

    init {
        for (letter in letters) {
            addButton(letter)
        }
    }

    private fun addButton(letter: Letter) {
        val button = JButton()
        button.text = letter.character.toString()
        buttons.add(button)
        add(button)

        button.addActionListener { e ->
            val source = e.source as JButton
            parent.removeRackPressed()
            if (parent.rackPressed != this) {
                buttonPressed = source
                parent.rackPressed = this
                source.border = LineBorder(Color.CYAN)

                if (parent.hasTilePressed) {
                    parent.placeTile()
                }
            }
        }
    }
}