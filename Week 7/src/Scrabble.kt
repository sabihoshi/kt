import java.util.*
import java.io.File
import java.io.InputStream
import java.nio.file.Paths

enum class Direction {
    Down,
    Across
}

private const val BOARD_SIZE = 15
private const val RACK_SIZE = 7

private var currentPlayer = 0
private var maxPlayers = 4
private val board = Array(BOARD_SIZE) { CharArray(BOARD_SIZE) }
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
        this['A'] = 1
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
private val playerRacks = ArrayList<MutableList<Char>>()
private val validWords = ArrayList<String>()

fun main() {
    initialize()
    while (true) {
        cls()
        printBoard()
        println("----- Player #" + (currentPlayer + 1) + " -----")
        currentPlayer = playerTurn(currentPlayer, playerRacks[currentPlayer])
    }
}


fun initialize() {
    val inputStream: InputStream = File(Paths.get(".\\src\\words_alpha.txt").toAbsolutePath().toString()).inputStream()
    inputStream.bufferedReader().useLines { lines -> lines.forEach { validWords.add(it) } }

    for (row in board.indices) {
        for (column in 0 until board[row].size) {
            board[column][row] = '-'
        }
    }

    availableLetters.shuffle()
    for (i in 0 until maxPlayers) {
        playerRacks.add(getLetters(RACK_SIZE).toMutableList())
    }
}

fun playerTurn(player: Int, playerRack: MutableList<Char>): Int {
    // Ask user x and y
    print("Enter x > ")
    val x = readLine()!!.toInt()

    print("Enter y > ")
    val y = readLine()!!.toInt()

    // Ask user direction
    print("Enter direction (down|across) > ")
    val dirInput = readLine()
    val direction = when (dirInput) {
        "down" -> Direction.Down
        "across" -> Direction.Across
        else -> Direction.Down
    }

    val word = getWord(playerRack, x, y, direction)

    if (direction == Direction.Across) {
        for (i in 0 until word.length) {
            val char = board[y][x + i]
            if (isConflict(char, word, i))
                return playerTurn(player, playerRack)
        }
    } else if (direction == Direction.Down) {
        for (i in 0 until word.length) {
            val char = board[y + i][x]
            if (isConflict(char, word, i))
                return playerTurn(player, playerRack)
        }
    }

    println("That was " + getPoints(word) + " points!")

    placeLetters(x, y, direction, word)

    return (player + 1) % maxPlayers
}

private fun isConflict(char: Char, word: String, i: Int): Boolean {
    return if (char != '-' && char != word[i]) {
        println("The letter ${word[i]} does not fit.")
        true
    } else false
}

fun getWord(playerRack: MutableList<Char>, x: Int, y: Int, direction: Direction): String {
    println("Your letters are: ${playerRack.joinToString()}")
    print("Enter word > ")

    val temp = playerRack.toMutableList()
    val word = readLine()!!

    val maxLength = when (direction) {
        Direction.Across -> BOARD_SIZE - x
        Direction.Down -> BOARD_SIZE - y
    }

    if (word.length > maxLength) {
        println("That word is too long!")
        return getWord(playerRack, x, y, direction)
    }

    if (!validWords.any { it.equals(word, true) }) {
        println("Invalid word!")
        return getWord(playerRack, x, y, direction)
    }

    for (letter in word.toUpperCase()) {
        if (temp.contains(letter)) {
            temp.remove(letter)
        } else {
            println("You don't have enough letters for that.")
            return getWord(playerRack, x, y, direction)
        }
    }

    playerRack.removeAll(word.toList())
    playerRack.addAll(getLetters(word.length).toList())
    return word
}

fun getPoints(word: String): Int {
    val temp = word.toUpperCase()
    var result = 0
    for (i in 0 until temp.length) {
        result += points[temp[i]]!!
    }
    return result
}

fun getLetters(amount: Int): CharArray {
    val result = CharArray(amount)
    for (i in 0 until amount) {
        result[i] = availableLetters.pop()
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
    print("\u001b[H\u001b[2J")
}