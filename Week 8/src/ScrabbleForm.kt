import Board.Panel.Board
import javax.swing.JFrame
import javax.swing.JMenuBar
import javax.swing.JPanel

class ScrabbleForm internal constructor() {
    var panel: JPanel? = null
    var menuBar: JMenuBar? = null

    private val board = Board()

    init {
        initializeGui()
    }


    fun initializeGui() {

    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val frame = JFrame("Scrabble")
            val board = ScrabbleForm().board
            frame.contentPane = board
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            frame.addMouseListener(board)
            frame.pack()
            frame.isVisible = true
        }
    }
}
