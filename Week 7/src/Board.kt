class Letter(var letter: Char, var player: Player)

class Player(val color: Int, val number: Int, val rack: MutableList<Char>, var points: Int)