package kierros5.model

import java.awt.Color

import scala.collection.immutable.List
import scala.util.Random

/**
 * The only task of Shape companion object is to provide a method for selecting
 * the next piece to drop to the game. 
 * 
 * @author ankkukku
 */
object Shape {
  def getRandom() : Shape = {
    val options = Array(I, J, L, O, S, Z, T)
    options(Random.nextInt(options.length))
  }
}

/**
 * An abstract class for representing the general properties of a shape for the
 * piece.
 * 
 * The subclasses can override the methods to fit their properties. The default
 * implementation represents properties of an empty shape. 
 * 
 * @author ankkukku
 */
abstract class Shape {
  
  /**
   * @return The color that represents the shape in GamePanel.
   */
  def getColor() : Color = Color.BLACK // This needs to be BLACK, at it is integral while checking for empty slots

  /**
   * @return The shape as two-dimensional list containing true in places where
   *         the shape is present and false where there is no shape. 
   */
  def getShape() : List[List[Boolean]] = List(List(false))

}

/**
 * Represents the shape I or a line, that is one block wide and four blocks
 * tall.
 * 
 * __*__
 * __*__
 * __*__
 * __*__
 * _____
 * 
 * @author ankkukku
 */
object I extends Shape {
  override def getColor() : Color = Color.ORANGE

  override def getShape() : List[List[Boolean]] = {
    List(List(false, false, true, false, false),
         List(false, false, true, false, false),
         List(false, false, true, false, false),
         List(false, false, true, false, false),
         List(false, false, false, false, false))
  }

}

/**
 * Represents the shape J.
 * 
 * _____
 * __*__
 * __*__
 * _**__
 * _____
 * 
 * @author ankkukku
 */
object J extends Shape {
  override def getColor() : Color = Color.BLUE

  override def getShape() : List[List[Boolean]] = {
    List(List(false, false, false, false, false),
         List(false, false, true, false, false),
		     List(false, false, true, false, false),
		     List(false, true,  true, false, false),
		     List(false, false, false, false, false))
  }

}

/**
 * Represents the shape L.
 * 
 * _____
 * _*___
 * _*___
 * _**__
 * _____
 * 
 * @author ankkukku
 */
object L extends Shape {
  override def getColor() : Color = Color.MAGENTA

  override def getShape() : List[List[Boolean]] = {
    List(List(false, false, false, false, false),
		     List(false, true, false, false, false),
		     List(false, true, false, false, false),
		     List(false, true, true, false, false),
		     List(false, false, false, false, false))
  }

}

/**
 * Represents the shape O or a square, that is two block wide and tall.
 * 
 * The block is the only piece that is not affected by turn.
 * 
 * _____
 * _**__
 * _**__
 * _____
 * _____
 * 
 * @author ankkukku
 */
object O extends Shape {
  override def getColor() : Color = Color.RED

  override def getShape() : List[List[Boolean]] = {
    List(List(false, false, false, false, false),
		     List(false, true, true, false, false),
		     List(false, true, true, false, false),
		     List(false, false, false, false, false),
		     List(false, false, false, false, false))
  }

}

/**
 * Represents the shape S.
 * 
 * _____
 * __**_
 * _**__
 * _____
 * _____
 * 
 * @author ankkukku
 */
object S extends Shape {
  override def getColor() : Color = Color.CYAN

  override def getShape() : List[List[Boolean]] = {
    List(List(false, false, false, false, false),
         List(false, false, true, true, false),
		     List(false, true,  true, false, false),
		     List(false, false, false, false, false),
		     List(false, false, false, false, false))
  }

}

/**
 * Represents the shape Z.
 * 
 * _____
 * _**__
 * __**_
 * _____
 * _____
 * 
 * @author ankkukku
 */
object Z extends Shape {
  override def getColor() : Color = Color.GREEN

  override def getShape() : List[List[Boolean]] = {
    List(List(false, false, false, false, false),
         List(false, true, true, false, false),
		     List(false, false, true, true, false),
		     List(false, false, false, false, false),
		     List(false, false, false, false, false))
  }

}

/**
 * Represents the shape T.
 * 
 * _____
 * __*__
 * _***_
 * _____
 * _____
 * 
 * @author ankkukku
 */
object T extends Shape {
  override def getColor() : Color = Color.YELLOW

  override def getShape() : List[List[Boolean]] = {
    List(List(false, false, false, false, false),
         List(false, false, true, false, false),
		     List(false, true, true, true, false),
		     List(false, false, false, false, false),
		     List(false, false, false, false, false))
  }

}

/**
 * Represents an empty shape.
 * 
 * @author ankkukku
 */
object ` ` extends Shape
