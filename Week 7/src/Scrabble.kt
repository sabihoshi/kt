import java.io.IOException
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import java.util.stream.Collectors

class Scrabble {

    enum class Direction {
        Down,
        Across
    }

    fun checkBoard(): Int {
        // return 0 if there is no winner
        // 1 for 1st player
        // 2 for 2nd player
        throw NotImplementedError()
    }

    companion object {
        private val rnd = ThreadLocalRandom.current()

        internal var currentPlayer = 0
        internal var maxPlayers = 4
        internal var `in` = Scanner(System.`in`)
        private val board = Array(8) { CharArray(8) }
        private val availableLetters: LinkedList<Char> = object : LinkedList<Char>() {
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
        private val points = object : Hashtable<Char, Int>() {
            init {
                points.put('A', 1)
                this['B'] = 3
                this['C'] = 3
                this['D'] = 2
                this['E'] = 1
                this['F'] = 4
                this['G'] = 2
                this['H'] = 4
                this['I'] = 1
                this['J'] = 8
                this['K'] = 5
                this['L'] = 1
                this['M'] = 3
                this['N'] = 1
                this['O'] = 1
                this['P'] = 3
                this['Q'] = 10
                this['R'] = 1
                this['S'] = 1
                this['T'] = 1
                this['U'] = 1
                this['V'] = 4
                this['W'] = 4
                this['X'] = 8
                this['Y'] = 4
                this['Z'] = 10
            }
        }
        private val playerRack = Hashtable<Int, ArrayList<Char>>()

        @JvmStatic
        fun main(args: Array<String>) {
            initialize()

            while (true) {
                cls()
                printBoard()
                println("----- Player #" + (currentPlayer + 1) + " -----")
                currentPlayer = playerTurn(currentPlayer)
            }
        }


        fun initialize() {
            // Initialize all values to 0
            for (row in board.indices) {
                for (column in 0 until board[row].size) {
                    board[column][row] = '-'
                }
            }

            availableLetters.shuffle()
        }

        fun playerTurn(player: Int): Int {
            // Ask user x and y
            print("Enter x > ")
            val x = `in`.nextInt()

            print("Enter y > ")
            val y = `in`.nextInt()

            `in`.nextLine()

            // Ask user direction
            print("Enter direction (down|across) > ")
            val dirInput = `in`.nextLine().toUpperCase()
            val direction = if (dirInput == "DOWN") Direction.Down else Direction.Across
            val word = word


            println("That was " + getPoints(word) + " points!")

            // placeLetters()
            placeLetters(x, y, direction, word)

            return (player + 1) % maxPlayers
        }

        private val word: String
            get() {
                val temp = availableLetters
                print("Enter word > ")
                return `in`.nextLine()
            }

        fun getPoints(word: String): Int {
            val temp = word.toUpperCase()
            var result = 0
            for (i in 0 until temp.length) {
                result += points[temp[i]]
            }
            return result
        }

        fun drawNextLetter(): Char {
            return availableLetters.pop()
        }

        fun getLetters(amount: Int): CharArray {
            val result = CharArray(amount)
            for (i in 0 until amount) {
                result[i] = drawNextLetter()
            }
            return result
        }

        fun addLetters(arr: MutableList<Char>, c: Char, amount: Int) {
            for (i in 0 until amount) {
                arr.add(c)
            }
        }

        fun placeLetters(x: Int, y: Int, direction: Direction, word: String) {
            val temp = word.toUpperCase()
            if (direction == Direction.Across) {
                for (i in 0 until temp.length) {
                    board[y][x + i] = temp[i]
                }
            } else if (direction == Direction.Down) {
                for (i in 0 until temp.length) {
                    board[y + i][x] = temp[i]
                }
            }
        }

        fun printBoard() {
            for (row in board) {
                val out = join("", 3, row)
                println(out)
            }
        }

        fun join(delimiter: String, pad: Int, array: CharArray): String {
            val sb = StringBuilder()
            for (i in array.indices) {
                sb.append(padLeft(array[i].toString(), pad))
                if (i != array.size - 1)
                    sb.append(delimiter)
            }
            return sb.toString()
        }

        fun padLeft(s: String, n: Int): String {
            return String.format("%" + n + "s", s)
        }

        fun cls() {
            try {
                Runtime.getRuntime().exec("cls")
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }
}