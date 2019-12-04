import rack.HorizontalRack
import rack.Rack
import rack.VerticalRack
import java.awt.*
import java.io.File
import java.io.InputStream
import java.nio.file.Paths
import javax.swing.BoxLayout
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
    var validWords = arrayListOf<String>()
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
    var reset = JButton()

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
        val result = board.validateWords(tilesPlaced)
        if (!result.first) {
            reset()
            return
        }
        currentTurn++
        board.toggleEmptyTiles(true)
        currentRack?.player?.points = currentRack?.player?.points?.plus(result.second)!!
        println("Points: ${currentRack?.player?.points}")

        currentRack = nextRack()
        tilesPlaced.clear()
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
        if (pressedTile?.text != "") {
            val temp = pressedTile?.text
            pressedTile?.text = pressedRack?.buttonPressed?.text
            pressedRack?.buttonPressed?.text = temp
        }
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

    private fun reset() {
        for (tile in tilesPlaced) {
            currentRack?.addButton(tile.text.firstOrNull() ?: ' ', true)
            tile.text = ""
        }
        tilesPlaced.clear()
        board.toggleEmptyTiles(true)
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
    private var tilesPlaced: ArrayList<Tile> = arrayListOf()
    var pressedTile: Tile? = null
    var pressedRack: Rack? = null

    init {
        // Initialize board
        board.toggleEmptyTiles(false)
        board.tiles[7][7].isEnabled = true

        val inputStream: InputStream = File(Paths.get(".\\src\\words\\collins_scrabble.txt").toAbsolutePath().toString()).inputStream()
        inputStream.bufferedReader().useLines { lines -> lines.forEach { validWords.add(it) } }

        // Initialize players
        availableLetters.shuffle()
        racks.add(HorizontalRack(Player(Color.RED, "1"), this))
        racks.add(VerticalRack(Player(Color.ORANGE, "2"), this))
        racks.add(VerticalRack(Player(Color.GREEN, "3"), this))
        racks.add(HorizontalRack(Player(Color.BLUE, "4"), this))
        currentRack = racks.first()

        // Initialize layout
        preferredSize = Dimension(500, 510)
        contentPane.layout = GridBagLayout()
        var c = GridBagConstraints()

        fun reset() {
            c = GridBagConstraints()
        }

        val top = JPanel()
        top.add(racks[0])
        c.gridx = 1
        c.gridy = 0
        c.weighty = 0.5
        contentPane.add(top, c)
        reset()

        val left = JPanel()
        left.add(racks[1])
        c.gridx = 0
        c.gridy = 1
        c.weightx = 0.5
        contentPane.add(left, c)
        reset()

        c.fill = GridBagConstraints.REMAINDER
        c.gridx = 1
        c.gridy = 1
        c.insets = Insets(10, 10, 10, 10)
        c.ipadx = 500
        c.ipady = 500
        c.weightx = 2.0
        c.weighty = 2.0
        contentPane.add(board, c)
        reset()

        val right = JPanel()
        right.add(racks[2])
        c.gridx = 2
        c.gridy = 1
        c.weightx = 0.5
        contentPane.add(right, c)
        reset()

        val bottom = JPanel()
        bottom.add(racks[3])
        c.gridx = 1
        c.gridy = 2
        c.weighty = 0.5
        contentPane.add(bottom, c)
        reset()

        val boardButtons = JPanel(FlowLayout(FlowLayout.CENTER))
        confirm.text = "Confirm Move"
        confirm.addActionListener { confirmMove() }
        reset.text = "Reset"
        reset.addActionListener { reset() }
        boardButtons.add(confirm)
        boardButtons.add(reset)
        c.gridx = 1
        c.gridy = 3
        contentPane.add(boardButtons, c)
        reset()

        // Scoreboard and Info
        val scores = JPanel()
        scores.layout = BoxLayout(scores, BoxLayout.PAGE_AXIS)
        racks.forEach {
            scores.add(it.player.scoreField)
        }
        c.gridx = 3
        c.gridy = 0
        c.gridheight = 3
        contentPane.add(scores, c)

        defaultCloseOperation = EXIT_ON_CLOSE
        pack()
        isVisible = true
        play()
    }
}