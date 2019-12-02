import rack.HorizontalRack
import rack.Letter
import rack.Rack
import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.border.LineBorder

public const val BOARD_SIZE = 15
public const val RACK_SIZE = 7
public const val MAX_PLAYERS = 4
public const val MIN_PLAYERS = 2

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
    private val validWords: ArrayList<String> = arrayListOf()

    var board = Board(this)
    var playerOne: Player
    var playerOneRack: Rack
    var confirm = JButton()

    val hasTilePressed
        get() = tilePressed != null

    val hasRackedPressed
        get() = rackPressed != null

    fun addLetters(arr: MutableList<Char>, c: Char, amount: Int) {
        for (i in 0 until amount) {
            arr.add(c)
        }
    }

    fun getLetters(amount: Int): ArrayList<Letter> {
        val result = ArrayList<Letter>(amount)
        repeat(amount) {
            if (availableLetters.isEmpty())
                return result
            result.add(Letter(availableLetters.removeAt(0)))
        }
        return result
    }

    fun confirmMove() {
        board.enableAllButtons(true)
        board.validateWords(tilesPlaced)

        tilesPlaced.clear()
        tilesSearched.addAll(tilesPlaced)
    }

    fun removeTile() {
        tilePressed?.border = LineBorder(Color.GRAY)
        tilePressed = null
    }

    fun removeRack(delete: Boolean = false) {
        if (delete) {
            rackPressed?.buttons?.remove(rackPressed?.buttonPressed)
            rackPressed?.remove(rackPressed?.buttonPressed)
        } else rackPressed?.buttonPressed?.border = LineBorder(Color.GRAY)
        rackPressed?.buttonPressed = null
        rackPressed = null
    }

    fun placeTile() {
        tilePressed?.let { tilesPlaced.add(it) }
        tilePressed?.text = rackPressed?.buttonPressed?.text
        removeRack(true)
        removeTile()

        // Disable buttons depending on how many tiles has been placed
        when (tilesPlaced.size) {
            1 -> {
                board.enableAllButtons(false)
                tilesPlaced[0].coordinates?.let {
                    board.enableButtons(Board.Orientation.Horizontal, it)
                    board.enableButtons(Board.Orientation.Vertical, it)
                }
            }
            2 -> {
                // Horizontally the same
                if (tilesPlaced.map { t -> t.coordinates?.first }.distinct().size == 1) {
                    addOrientation(Board.Orientation.Horizontal)
                }
                // Vertically the same
                else if (tilesPlaced.map { t -> t.coordinates?.second }.distinct().size == 1) {
                    addOrientation(Board.Orientation.Vertical)
                }
            }
        }
    }

    private fun addOrientation(orientation: Board.Orientation) {
        board.enableAllButtons(false)
        board.enableButtons(orientation, tilesPlaced[0].coordinates!!)
        for(tile in tilesPlaced) {
            tile.orientation = orientation
        }
    }

    private var tilesSearched: ArrayList<Tile> = arrayListOf()
    private var tilesPlaced: ArrayList<Tile> = arrayListOf()
    var tilePressed: Tile? = null
    var rackPressed: Rack? = null

    init {
        availableLetters.shuffle()
        playerOne = Player(Color.GREEN, 1)
        playerOneRack = HorizontalRack(playerOne, getLetters(7), this)

        confirm.text = "Confirm Move"
        confirm.addActionListener{_ -> confirmMove()}

        playerOneRack.preferredSize = Dimension(500, 10)
        preferredSize = Dimension(500, 510)

        contentPane.layout = GridLayout(3, 1)

        contentPane.add(board)
        contentPane.add(playerOneRack)
        contentPane.add(confirm)

        defaultCloseOperation = EXIT_ON_CLOSE
        pack()
        isVisible = true
    }
}