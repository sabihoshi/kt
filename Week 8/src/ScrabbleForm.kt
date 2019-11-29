import Board.Panel.Board
import java.awt.GridLayout
import javax.swing.JFrame
import javax.swing.JSeparator
import javax.swing.JTextField
import javax.swing.SwingConstants

fun main() {
    ScrabbleForm()
}

class ScrabbleForm : JFrame {
    var board = Board()
    var textInput = JTextField()

    constructor() : super() {
        initializeGui()
    }


    fun initializeGui() {
        contentPane.layout = GridLayout(3, 1)

        contentPane.add(board)
        contentPane.add(JSeparator(SwingConstants.VERTICAL))
        contentPane.add(textInput)

        defaultCloseOperation = EXIT_ON_CLOSE
        addMouseListener(board)
        pack()
        isVisible = true
    }
}
