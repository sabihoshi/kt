
import rack.HorizontalRack
import rack.Rack
import rack.VerticalRack
import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.border.LineBorder

const val BOARD_SIZE = 15
const val RACK_SIZE = 7
const val MAX_PLAYERS = 4
const val MIN_PLAYERS = 2

fun main() {
    ScrabbleForm()
}

class ScrabbleForm : JFrame("Scrabble") {
    val availableLetters = object : ArrayList<Char>() {
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
    private val racks = arrayListOf<Rack>()
    var currentRack: Rack? = null
    var currentTurn = 0

    var board = Board(this)
    var confirm = JButton()

    val hasTilePressed
        get() = pressedTile != null

    val hasRackedPressed
        get() = pressedRack != null

    private fun play() {
        currentRack?.toggle(true)
    }

    fun addLetters(arr: MutableList<Char>, c: Char, amount: Int) {
        for (i in 0 until amount) {
            arr.add(c)
        }
    }

    private fun confirmMove() {
        currentTurn++
        board.toggleEmptyTiles(true)
        board.validateWords(tilesPlaced)

        println("Points: ${currentRack?.player?.points}")

        currentRack = nextRack()

        tilesPlaced.clear()
        tilesSearched.addAll(tilesPlaced)
    }

    fun removeTile() {
        pressedTile?.border = LineBorder(Color.GRAY)
        pressedTile = null
    }

    fun removeRack(delete: Boolean = false) {
        if (delete) {
            pressedRack?.buttons?.remove(pressedRack?.buttonPressed)
            pressedRack?.remove(pressedRack?.buttonPressed)
        } else pressedRack?.buttonPressed?.border = LineBorder(Color.GRAY)
        pressedRack?.buttonPressed = null
        pressedRack = null
    }

    fun placeTile() {
        pressedTile?.turnPlaced = currentTurn
        pressedTile?.let { tilesPlaced.add(it) }
        pressedTile?.text = pressedRack?.buttonPressed?.text

        // Disable buttons depending on how many tiles has been placed
        when (tilesPlaced.size) {
            1 -> {
                board.toggleEmptyTiles(false)
                tilesPlaced[0].coordinates?.let {
                    board.enableOrientation(Board.Orientation.Horizontal, it)
                    board.enableOrientation(Board.Orientation.Vertical, it)
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
            else -> pressedTile?.orientation = orientation
        }

        removeRack(true)
        removeTile()
    }

    private fun nextRack(): Rack {
        currentRack?.toggle(false)
        currentRack?.fill()
        val currentRackIndex = racks.indexOf(currentRack)
        val nextRack = racks[(currentRackIndex + 1) % racks.size]
        nextRack.toggle(true)
        return nextRack
    }

    private fun addOrientation(orientation: Board.Orientation) {
        this.orientation = orientation
        board.toggleEmptyTiles(false)
        tilesPlaced[0].coordinates?.let { board.enableOrientation(orientation, it) }
        for (tile in tilesPlaced) {
            tile.orientation = orientation
        }
    }

    private var orientation: Board.Orientation? = null
    private var tilesSearched: ArrayList<Tile> = arrayListOf()
    private var tilesPlaced: ArrayList<Tile> = arrayListOf()
    var pressedTile: Tile? = null
    var pressedRack: Rack? = null

    init {
        availableLetters.shuffle()
        racks.add(HorizontalRack(Player(Color.RED), this))
        racks.add(VerticalRack(Player(Color.ORANGE), this))
        racks.add(VerticalRack(Player(Color.GREEN), this))
        racks.add(HorizontalRack(Player(Color.BLUE), this))
        currentRack = racks.first()

        contentPane.layout = GridLayout(4, 1)
        preferredSize = Dimension(500, 510)

        confirm.text = "Confirm Move"
        confirm.addActionListener { _ -> confirmMove() }

        val first = JPanel(FlowLayout(FlowLayout.CENTER))
        first.add(racks[0])

        val second = JPanel(FlowLayout(FlowLayout.CENTER))
        second.preferredSize = Dimension(350, 300)
        second.add(racks[1])
        second.add(board)
        second.add(racks[2])

        val third = JPanel(FlowLayout(FlowLayout.CENTER))
        third.add(racks[3])

        contentPane.add(first)
        contentPane.add(second)
        contentPane.add(third)
        contentPane.add(confirm)

        defaultCloseOperation = EXIT_ON_CLOSE
        pack()
        isVisible = true
        play()
    }
}