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
private const val MAX_MOVES = 10

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

    repeat(MAX_MOVES) {
        Console.cls()
        printBoard()
        println("═════ Player #${currentPlayer.number + 1} ═════")
        println("Your letters are: ${currentPlayer.rack.joinToString()}")
        currentPlayer = playerTurn(currentPlayer)
    }
}

fun initialize(): Player {
    maxPlayers = prompt("How many players should there be?") { it.toInt() }
    val inputStream: InputStream = File(Paths.get(".\\src\\words_alpha.txt").toAbsolutePath().toString()).inputStream()
    inputStream.bufferedReader().useLines { lines -> lines.forEach { validWords.add(it) } }

    availableLetters.shuffle()
    for (i in 0 until maxPlayers) {
        players.add(Player(i + 1, i, getLetters(RACK_SIZE).sorted().toMutableList()))
    }
    return players.first()
}

fun removeLetters(player: Player) {
    val word = prompt("Enter letters to remove")
    removeLetters(player, word)
}

private fun removeLetters(player: Player, word: String) {
    player.rack.removeAll(word.toList())
    player.rack.addAll(getLetters(word.length).toList())
}

fun playerTurn(player: Player): Player {
    val nextPlayer = players[(currentPlayer.number + 1) % players.size]

    println("Enter '-' to remove letters.")
    val coords = prompt("Enter coordinates")
    if (coords == "-") {
        removeLetters(player)
        return nextPlayer
    }

    val (x, y) = getCoordinates(coords)

    if (board[y][x].letter != '-' && !player.rack.contains(board[y][x].letter)) {
        println("You don't have any letters in your rack at this letter.")
        return playerTurn(player)
    }

    val direction = getDirection()
    val word = getWord(player, x, y, direction)

    val needed = StringBuilder()
    for ((letter, i) in getDirectionGenerator(direction, word, x, y)) {
        if (letter.letter == '-') {
            needed.append(word[i].toUpperCase())
        } else if (isConflict(letter, word[i])) {
            println("The letter ${word[i]} does not fit at position ${(x + 'A'.toInt()).toChar()}$y.")
            return playerTurn(player)
        }
    }

    if (!strictContains(needed.toString(), player.rack)) {
        println("You don't have enough letters for that.")
        return playerTurn(player)
    }

    println("That was " + getPoints(word) + " points!")

    placeLetters(x, y, direction, word, currentPlayer)
    currentPlayer.rack.removeAll(needed.toString().toList())
    currentPlayer.rack.addAll(getLetters(needed.length))

    return nextPlayer
}

private fun getCoordinates(coords: String): Pair<Int, Int> {
    val xFirst = Regex("^(?<x>[A-O])(?<y>[0-9]+)$", RegexOption.IGNORE_CASE).matchEntire(coords)
    val yFirst = Regex("^(?<y>[0-9]+)(?<x>[A-O])$", RegexOption.IGNORE_CASE).matchEntire(coords)
    if (xFirst == null && yFirst == null) {
        println("Invalid coordinates!")
        return getCoordinates(prompt("Enter coordinates"))
    }
    val x = (xFirst?.groups?.get(1) ?: yFirst!!.groups[2])!!.value.first().toUpperCase() - 'A'
    val y = Integer.parseInt((xFirst?.groups?.get(2) ?: yFirst!!.groups[1])!!.value)
    if (x > BOARD_SIZE || y > BOARD_SIZE) {
        println("Those coordinates are outside of range!")
        return getCoordinates(prompt("Enter coordinates"))
    }
    return Pair(x, y)
}

private fun isConflict(letter: Letter, check: Char): Boolean {
    return letter.letter != '-' && letter.letter != check.toUpperCase()
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
    return when (prompt("Enter direction (W|A|S|D)").toUpperCase()) {
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

    removeLetters(player, word)
    return word
}

fun letterCounts(chars: MutableList<Char>): Map<Char, Int> =
    chars.groupBy { t -> t }.map { it.key to it.value.count() }.toMap()

fun strictContains(word: String, chars: MutableList<Char>): Boolean {
    val wordLetters = letterCounts(word.toUpperCase().toMutableList())
    val availableLetters = letterCounts(chars)
    return wordLetters.all { k ->
        availableLetters.getOrDefault(k.key, 0) >= k.value
    }
}

fun getPoints(word: String): Int {
    val temp = word.toUpperCase()
    var result = 0
    for (char in temp) {
        result += points[char]!!
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
        letter.letter = temp[i]
        letter.player = player
    }
}

fun printBoard() = table.printTable(board, 2)

fun prompt(s: String): String {
    print("$s > ")
    return readLine()!!
}

fun <T> prompt(s: String, parse: (String) -> T): T {
    return parse(prompt(s))
}