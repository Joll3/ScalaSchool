package kierros3
import scala.math._

/**
 * @author put your student number(s) here
 */
object Color {
  
  def clamp(i: Int): Int = {
    if (i < 255)
      max(0, i)
    else 255
  }
  
  def clamp(i: Float): Int = {
    clamp(Math.round(i))
  }
  
}
/**
 * @param r    The value for red color component as `scala.Int`.
 * @param g    The value for green color component as `scala.Int`.
 * @param b    The value for blue color component as `scala.Int`.
 * @param a    The value for alpha component as `scala.Int`.
 * @param rgba The color value as `scala.Int`.
 */
class Color(var r: Int, var g: Int, var b: Int, var a: Int, var rgba: Int) {

  /**
   * @constructor
   * @param rgba The color value as `scala.Int`.
   */
  def this(rgba: Int) {
    this(-1, -1, -1, -1, rgba)
    this.setComponents()
  }
  

  /**
   * @constructor
   * @param r The value for red color component as `scala.Int`.
   * @param g The value for green color component as `scala.Int`.
   * @param b The value for blue color component as `scala.Int`.
   * @param a The value for alpha component as `scala.Int`.
   */
  def this(r: Int, g: Int, b: Int, a: Int) {
    this(Color.clamp(r), Color.clamp(g), Color.clamp(b), Color.clamp(a), 0)
    this.setInt
  }

  /**
   * @constructor
   * @param r The value for red color component as `scala.Float`.
   * @param g The value for green color component as `scala.Float`.
   * @param b The value for blue color component as `scala.Float`.
   * @param a The value for alpha component as `scala.Float`.
   */
  def this(r: Float, g: Float, b: Float, a: Float) {
    this(Color.clamp(r), Color.clamp(g), Color.clamp(b), Color.clamp(a), 0)
    this.setInt
  }

  private def setInt() : Unit = {
    val blue  = this.b << 8
    val green = this.g << 16
    val red   = this.r << 24
    
    this.rgba = (this.a | blue | green | red).toInt
  }

  private def setComponents() : Unit = {
    this.a = 255 &  this.rgba
    this.b = 255 & (this.rgba >> 8)
    this.g = 255 & (this.rgba >> 16)
    this.r = 255 & (this.rgba >> 25)
  }

}
