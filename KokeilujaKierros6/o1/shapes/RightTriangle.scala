package o1.shapes
import scala.math._

class RightTriangle(val sideLength: Double, val anotherSideLength: Double) extends Shape {
  
  def area = (sideLength * anotherSideLength) / 2 
  
  //def hypotenuse = sqrt((sideLength * sideLength + anotherSideLength * anotherSideLength))
  
  def hypotenuse = hypot(sideLength, anotherSideLength)
  
  def perimeter = sideLength + anotherSideLength + this.hypotenuse
  
}
  