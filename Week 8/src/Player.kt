import rack.Rack
import java.awt.Color

class Player(val color: Color, val number: Int) {
    var points = 0
    var rack: Rack? = null
}