import java.awt.Color
import java.awt.Container
import java.awt.Font
import java.awt.GridLayout
import javax.swing.*
import kotlin.system.exitProcess

public class TicTacToeGUI : JFrame {
    var pane: Container
    var currentPlayer = "x"
    var board = Array<Array<JButton?>>(3) { arrayOfNulls(3) }
    var hasWinner = false
    val menuBar = JMenuBar()
    val menu = JMenu("File")
    val quit = JMenuItem("Quit")
    val newGame = JMenuItem("New Game...")

    constructor() : super() {

        pane = contentPane
        pane.layout = GridLayout(3, 3)
        title = "Tic Tac Toe"
        setSize(500, 500)
        isResizable = false
        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
        isVisible = true
        currentPlayer
        initializeMenu()
        initializeBoard()
    }

    fun resetBoard() {0
        isVisible = false
        TicTacToeGUI()
    }

    fun initializeMenu() {
        quit.addActionListener { _ -> exitProcess(0) }
        newGame.addActionListener { _ -> resetBoard() }
        menu.add(quit)
        menu.add(newGame)
        jMenuBar = menuBar
        jMenuBar.add(menu)
    }

    fun initializeBoard() {
        for (i in board.indices) {
            for (j in board[i].indices) {
                val button = JButton()
                button.setFont(Font(Font.SANS_SERIF, Font.BOLD, 100))
                board[i][j] = button

                button.addActionListener { e ->
                    if ((e.source as JButton).text == "" && !hasWinner) {
                        button.text = currentPlayer
                        hasWinner()
                        togglePlayer()
                    }
                }

                pane.add(button)
            }
        }
    }

    fun togglePlayer() {
        if (currentPlayer == "x") {
            currentPlayer = "o"
        } else
            currentPlayer = "x"
    }

    fun hasWinner() {
        for (i in board.indices) {
            if (board[i][0]?.text == currentPlayer && board[i][1]?.text == currentPlayer && board[i][2]?.text == currentPlayer) {
                JOptionPane.showMessageDialog(null, "Player $currentPlayer has won")
                hasWinner = true
                return
            }
        }
        for (i in board.indices) {
            if (board[0][i]?.text == currentPlayer && board[1][i]?.text == currentPlayer && board[2][i]?.text == currentPlayer) {
                JOptionPane.showMessageDialog(null, "Player $currentPlayer has won")
                hasWinner = true
                return
            }
        }
        if (board[0][0]?.text == currentPlayer && board[1][1]?.text == currentPlayer && board[2][2]?.text == currentPlayer) {
            JOptionPane.showMessageDialog(null, "Player $currentPlayer has won")
            hasWinner = true
        } else if (board[2][0]?.text == currentPlayer && board[1][1]?.text == currentPlayer && board[0][2]?.text == currentPlayer) {
            JOptionPane.showMessageDialog(null, "Player $currentPlayer has won")
            hasWinner = true
        }
    }
}