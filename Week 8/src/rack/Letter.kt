package rack

import fill.DirectionChecker

class Letter(val character: Char) {
    var position: Pair<Int, Int>? = null
    var directionChecker = DirectionChecker()
}