package kierros5.model

/**
 * @author 424796 and 429254
 */

// A helper object for the four directions
object Direction extends Enumeration{
  type Direction = Value
  val up, down, left, right = Value
  
  // Method returning the shift on the x-axis
  def getXChange(x: Direction.Value) : Int = x match {
    case Direction.left  => -1
    case Direction.right => 1 
    case _               => 0
  }
  
  // Method returning the shift on the y-axis
  def getYChange(y: Direction.Value) : Int = y match {
    case Direction.up    => -1
    case Direction.down  => 1 
    case _               => 0
  }
}