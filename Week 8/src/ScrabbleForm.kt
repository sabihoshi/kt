
import rack.HorizontalRack
import rack.Rack
import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import java.util.*
import javax.swing.JFrame


fun main() {
    ScrabbleForm()
}

class ScrabbleForm : JFrame("Scrabble") {
    private val availableLetters = object : ArrayList<Char>() {
        init {
            addLetters(this, 'A', 9)
            addLetters(this, 'B', 2)
            addLetters(this, 'C', 2)
            addLetters(this, 'D', 4)
            addLetters(this, 'E', 12)
            addLetters(this, 'F', 2)
            addLetters(this, 'G', 3)
            addLetters(this, 'H', 2)
            addLetters(this, 'I', 9)
            addLetters(this, 'J', 1)
            addLetters(this, 'K', 5)
            addLetters(this, 'L', 4)
            addLetters(this, 'M', 2)
            addLetters(this, 'N', 6)
            addLetters(this, 'O', 8)
            addLetters(this, 'P', 2)
            addLetters(this, 'Q', 1)
            addLetters(this, 'R', 6)
            addLetters(this, 'S', 4)
            addLetters(this, 'T', 6)
            addLetters(this, 'U', 4)
            addLetters(this, 'V', 2)
            addLetters(this, 'W', 2)
            addLetters(this, 'X', 1)
            addLetters(this, 'Y', 2)
            addLetters(this, 'Z', 1)
        }
    }

    var board = Board(this)
    var playerOne = Player(Color.GREEN, 1)
    var playerOneRack: Rack = HorizontalRack(playerOne, getLetters(7))

    val hasTilePressed
        get() = tilePressed != null

    val hasRackedPressed
        get() = rackPressed != null

    fun addLetters(arr: MutableList<Char>, c: Char, amount: Int) {
        for (i in 0 until amount) {
            arr.add(c)
        }
    }

    fun getLetters(amount: Int): List<Char> {
        val result = ArrayList<Char>(amount)
        repeat(amount) {
            if(availableLetters.isEmpty())
                return result.sorted()
            result.add(availableLetters.removeAt(0))
        }
        return result.sorted()
    }


    var tilePressed: Tile? = null
    var rackPressed: Rack? = null

    init {
        preferredSize = Dimension(500, 510)
        contentPane.layout = GridLayout(2, 1)

        contentPane.add(board)
        playerOneRack.preferredSize = Dimension(500, 10)
        contentPane.add(playerOneRack)

        defaultCloseOperation = EXIT_ON_CLOSE
        pack()
        isVisible = true
    }
}
