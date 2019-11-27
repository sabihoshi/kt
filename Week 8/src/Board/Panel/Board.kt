package Board.Panel

import Board.Tiles.*
import Letter
import java.awt.Color
import java.awt.Graphics
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.util.*
import javax.swing.JPanel

class Board : JPanel(), MouseListener {
    private val spacing = 3

    private val squares = ArrayList<ArrayList<Tile>>()
    private val letters = ArrayList<ArrayList<Letter>>()

    init {
        this.buildSquaresList()
    }

    protected fun buildSquaresList() {
        this.squares.addAll(
            Arrays.asList(
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList(),
                ArrayList()
            )
        )

        // Line 1
        this.squares[0].addAll(
            Arrays.asList(
                Red(),
                Tile(),
                Tile(),
                Blue(),
                Tile(),
                Tile(),
                Tile(),
                Red(),
                Tile(),
                Tile(),
                Tile(),
                Blue(),
                Tile(),
                Tile(),
                Red()
            )
        )

        // Line 2
        this.squares[1].addAll(
            Arrays.asList(
                Tile(),
                Pink(),
                Tile(),
                Tile(),
                Tile(),
                DarkBlue(),
                Tile(),
                Tile(),
                Tile(),
                DarkBlue(),
                Tile(),
                Tile(),
                Tile(),
                Pink(),
                Tile()
            )
        )

        // Line 3
        this.squares[2].addAll(
            Arrays.asList(
                Tile(),
                Tile(),
                Pink(),
                Tile(),
                Tile(),
                Tile(),
                Blue(),
                Tile(),
                Blue(),
                Tile(),
                Tile(),
                Tile(),
                Pink(),
                Tile(),
                Tile()
            )
        )

        // Line 4
        this.squares[3].addAll(
            Arrays.asList(
                Blue(),
                Tile(),
                Tile(),
                Pink(),
                Tile(),
                Tile(),
                Tile(),
                Blue(),
                Tile(),
                Tile(),
                Tile(),
                Pink(),
                Tile(),
                Tile(),
                Blue()
            )
        )

        // Line 5
        this.squares[4].addAll(
            Arrays.asList(
                Tile(),
                Tile(),
                Tile(),
                Tile(),
                Pink(),
                Tile(),
                Tile(),
                Tile(),
                Tile(),
                Tile(),
                Pink(),
                Tile(),
                Tile(),
                Tile(),
                Tile()
            )
        )

        // Line 6
        this.squares[5].addAll(
            Arrays.asList(
                Tile(),
                DarkBlue(),
                Tile(),
                Tile(),
                Tile(),
                DarkBlue(),
                Tile(),
                Tile(),
                Tile(),
                DarkBlue(),
                Tile(),
                Tile(),
                Tile(),
                DarkBlue(),
                Tile()
            )
        )

        // Line 7
        this.squares[6].addAll(
            Arrays.asList(
                Tile(),
                Tile(),
                Blue(),
                Tile(),
                Tile(),
                Tile(),
                Blue(),
                Tile(),
                Blue(),
                Tile(),
                Tile(),
                Tile(),
                Blue(),
                Tile(),
                Tile()
            )
        )

        // Line 8
        this.squares[7].addAll(
            Arrays.asList(
                Red(),
                Tile(),
                Tile(),
                Blue(),
                Tile(),
                Tile(),
                Tile(),
                Star(),
                Tile(),
                Tile(),
                Tile(),
                Blue(),
                Tile(),
                Tile(),
                Red()
            )
        )

        // Line 9
        this.squares[8].addAll(
            Arrays.asList(
                Tile(),
                Tile(),
                Blue(),
                Tile(),
                Tile(),
                Tile(),
                Blue(),
                Tile(),
                Blue(),
                Tile(),
                Tile(),
                Tile(),
                Blue(),
                Tile(),
                Tile()
            )
        )

        // Line 10
        this.squares[9].addAll(
            Arrays.asList(
                Tile(),
                DarkBlue(),
                Tile(),
                Tile(),
                Tile(),
                DarkBlue(),
                Tile(),
                Tile(),
                Tile(),
                DarkBlue(),
                Tile(),
                Tile(),
                Tile(),
                DarkBlue(),
                Tile()
            )
        )

        // Line 11
        this.squares[10].addAll(
            Arrays.asList(
                Tile(),
                Tile(),
                Tile(),
                Tile(),
                Pink(),
                Tile(),
                Tile(),
                Tile(),
                Tile(),
                Tile(),
                Pink(),
                Tile(),
                Tile(),
                Tile(),
                Tile()
            )
        )

        // Line 12
        this.squares[11].addAll(
            Arrays.asList(
                Blue(),
                Tile(),
                Tile(),
                Pink(),
                Tile(),
                Tile(),
                Tile(),
                Blue(),
                Tile(),
                Tile(),
                Tile(),
                Pink(),
                Tile(),
                Tile(),
                Blue()
            )
        )

        // Line 13
        this.squares[12].addAll(
            Arrays.asList(
                Tile(),
                Tile(),
                Pink(),
                Tile(),
                Tile(),
                Tile(),
                Blue(),
                Tile(),
                Blue(),
                Tile(),
                Tile(),
                Tile(),
                Pink(),
                Tile(),
                Tile()
            )
        )

        // Line 14
        this.squares[13].addAll(
            Arrays.asList(
                Tile(),
                Pink(),
                Tile(),
                Tile(),
                Tile(),
                DarkBlue(),
                Tile(),
                Tile(),
                Tile(),
                DarkBlue(),
                Tile(),
                Tile(),
                Tile(),
                Pink(),
                Tile()
            )
        )

        // Line 15
        this.squares[14].addAll(
            Arrays.asList(
                Red(),
                Tile(),
                Tile(),
                Blue(),
                Tile(),
                Tile(),
                Tile(),
                Red(),
                Tile(),
                Tile(),
                Tile(),
                Blue(),
                Tile(),
                Tile(),
                Red()
            )
        )
    }

    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        val width = width
        val height = height

        g.color = Color.BLACK
        g.fillRect(0, 0, width, height)
        for (i in squares.indices) {
            for (j in 0 until squares[i].size) {
                val currentSquare = squares[i][j]
                g.color = currentSquare.color
                g.fillRect(
                    i * (width + spacing) / squares.size,
                    j * (height + spacing) / squares.size,
                    width / squares.size - spacing,
                    height / squares.size - spacing
                )

            }
        }
    }

    fun mouseListener(e: MouseListener) {

    }

    override fun mouseClicked(mouseEvent: MouseEvent) {

    }

    override fun mousePressed(mouseEvent: MouseEvent) {

    }

    override fun mouseReleased(mouseEvent: MouseEvent) {

    }

    override fun mouseEntered(mouseEvent: MouseEvent) {

    }

    override fun mouseExited(mouseEvent: MouseEvent) {

    }
}