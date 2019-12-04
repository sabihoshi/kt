import rack.Rack
import java.awt.Color
import javax.swing.JLabel

class Player(val color: Color, val name: String) {
    var points = 0
        set(value) {
            field = value
            scoreField.text = "Player #$name:$points"
        }
    var rack: Rack? = null
    val scoreField = JLabel()
    init {
        points = 0
    }
}