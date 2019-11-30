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
    val letters: ArrayList<Char>,
    val parent: ScrabbleForm
) : JPanel() {
    var buttonPressed: JButton? = null
    val buttons: ArrayList<JButton> = arrayListOf()

    init {
        for (c in letters) {
            addButton(c)
        }
    }

    private fun addButton(letter: Char) {
        val button = JButton()
        button.text = letter.toString()
        buttons.add(button)
        add(button)

        button.addActionListener { e ->
            val source = e.source as JButton
            if (parent.hasTilePressed && parent.hasRackedPressed) {
                parent.tilePressed?.button?.text = source.text
                buttons.remove(source)
            } else {
                parent.rackPressed?.buttonPressed?.border = LineBorder(Color.GRAY)
                parent.rackPressed = this
            }
        }
    }
}