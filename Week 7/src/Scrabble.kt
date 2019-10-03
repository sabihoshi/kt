import java.util.*
import java.io.File
import java.io.InputStream
import java.nio.file.Paths

enum class Direction {
    Down,
    Across,
    Upwards,
    Backwards
}

private const val BOARD_SIZE = 15
private const val RACK_SIZE = 7

private var maxPlayers = 4
private val unknownPlayer = Player(15, -1, mutableListOf())
private var currentPlayer = unknownPlayer
private val players = ArrayList<Player>()
private val board = Array(BOARD_SIZE) {
    Array(BOARD_SIZE) {
        Letter('-', unknownPlayer)
    }
}
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
private val validWords = ArrayList<String>()
private val table = Table(15)

fun main() {
    currentPlayer = initialize()

    while (true) {
        Console.cls()
        printBoard()
        println("----- Player #" + (currentPlayer.number) + " -----")
        println("Your letters are: ${currentPlayer.rack.joinToString()}")
        currentPlayer = playerTurn(currentPlayer)
    }
}

fun initialize(): Player {
    maxPlayers = prompt("How many players should there be?") { it.toInt() }
    val inputStream: InputStream = File(Paths.get(".\\src\\words_alpha.txt").toAbsolutePath().toString()).inputStream()
    inputStream.bufferedReader().useLines { lines -> lines.forEach { validWords.add(it) } }

    availableLetters.shuffle()
    for (color in 1 until maxPlayers) {
        players.add(Player(color, color, getLetters(RACK_SIZE).sorted().toMutableList()))
    }
    return players.first()
}

fun playerTurn(player: Player): Player {
    val (x, y) = prompt("Enter x & y") {
        val pair = it.split(" ").take(2)
        Pair(pair[0].toInt(), pair[1].toInt())
    }

    val direction = getDirection()
    val word = getWord(player, x, y, direction)

    for ((letter, i) in getDirectionGenerator(direction, word, x, y)) {
        if (isConflict(letter.letter, word, i))
            return playerTurn(player)
    }

    println("That was " + getPoints(word) + " points!")

    placeLetters(x, y, direction, word, currentPlayer)

    return players.single { it.number == (currentPlayer.number + 1) % (players.size + 1) }
}

private fun isConflict(letter: Char, word: String, i: Int): Boolean {
    return if (letter != '-' && letter != word[i]) {
        println("The letter ${word[i]} does not fit.")
        true
    } else false
}

fun getDirectionGenerator(direction: Direction, word: String, x: Int, y: Int) = sequence {
    when (direction) {
        Direction.Across -> for (i in word.indices) {
            yield(Pair(board[y][x + i], i))
        }
        Direction.Backwards -> for (i in word.indices.reversed()) {
            yield(Pair(board[y][x - i], i))
        }
        Direction.Down -> for (i in word.indices) {
            yield(Pair(board[y + i][x], i))
        }
        Direction.Upwards -> for (i in word.indices.reversed()) {
            yield(Pair(board[y - i][x], i))
        }
    }
}

fun getDirection(): Direction {
    print("Enter direction (W|A|S|D) > ")
    return when (readLine()!!.toUpperCase()) {
        "S" -> Direction.Down
        "D" -> Direction.Across
        "A" -> Direction.Backwards
        "W" -> Direction.Upwards
        else -> getDirection()
    }
}

fun getWord(player: Player, x: Int, y: Int, direction: Direction): String {
    val word = prompt("Enter word")

    val maxLength = when (direction) {
        Direction.Across -> BOARD_SIZE - x
        Direction.Backwards -> x + 1
        Direction.Down -> BOARD_SIZE - y
        Direction.Upwards -> y + 1
    }

    if (word.length > maxLength || word.length > RACK_SIZE) {
        println("That word is too long!")
        return getWord(player, x, y, direction)
    }

    if (!validWords.any { it.equals(word, true) }) {
        println("Invalid word!")
        return getWord(player, x, y, direction)
    }

    if (!strictContains(word, player.rack)) {
        println("You don't have enough letters for that.")
        return getWord(player, x, y, direction)
    }

    player.rack.removeAll(word.toList())
    player.rack.addAll(getLetters(word.length).toList())
    return word
}

fun letterCounts(chars: MutableList<Char>): Map<Char, Int> =
    chars.groupBy { t -> t }.map { it.key to it.value.count() }.toMap()

fun strictContains(word: String, chars: MutableList<Char>): Boolean {
    val wordLetters = letterCounts(word.toMutableList())
    val availableLetters = letterCounts(chars)
    return wordLetters.all { k -> availableLetters.getOrDefault(k.key, 0) >= k.value }
}

fun getPoints(word: String): Int {
    val temp = word.toUpperCase()
    var result = 0
    for (i in 0 until temp.length) {
        result += points[temp[i]]!!
    }
    return result
}

fun getLetters(amount: Int): List<Char> {
    val result = ArrayList<Char>(amount)
    repeat(amount) {
        result.add(availableLetters.pop())
    }
    return result
}

fun addLetters(arr: MutableList<Char>, c: Char, amount: Int) {
    for (i in 0 until amount) {
        arr.add(c)
    }
}

fun placeLetters(x: Int, y: Int, direction: Direction, word: String, player: Player) {
    val temp = word.toUpperCase()

    for ((letter, i) in getDirectionGenerator(direction, word, x, y)) {
        setLetter(letter, temp[i], player)
    }
}

private fun setLetter(letter: Letter, c: Char, player: Player) {
    letter.letter = c
    letter.player = player
}

fun printBoard() = table.printTable(board, 2)

fun prompt(s: String): String {
    print("$s > ")
    return readLine()!!
}

fun <T> prompt(s: String, parse: (String) -> T): T {
    return parse(prompt(s))
}