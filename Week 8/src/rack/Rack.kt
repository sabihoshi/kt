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
    val parent: ScrabbleForm
) : JPanel() {
    var buttonPressed: JButton? = null
    val buttons: ArrayList<JButton> = arrayListOf()

    init {
        fill()
        toggle(false)
    }

    fun fill() {
        for (letter in getLetters(RACK_SIZE - buttons.size)) {
            addButton(letter)
        }
    }

    private fun getLetters(amount: Int): ArrayList<Letter> {
        val result = ArrayList<Letter>(amount)
        repeat(amount) {
            if (parent.availableLetters.isEmpty())
                return result
            result.add(Letter(parent.availableLetters.removeAt(0)))
        }
        return result
    }

    fun toggle(enabled: Boolean) {
        for(button in buttons) {
            button.isEnabled = enabled
        }
    }

    private fun addButton(letter: Letter) {
        val button = JButton()
        button.text = letter.character.toString()
        button.isEnabled = false
        buttons.add(button)
        add(button)

        button.addActionListener { e ->
            val source = e.source as JButton
            parent.removeRack()
            if (parent.pressedRack != this) {
                buttonPressed = source
                parent.pressedRack = this
                source.border = LineBorder(Color.CYAN)

                if (parent.hasTilePressed) {
                    parent.placeTile()
                }
            }
        }
    }
}
