package o1.robots
import o1.grid._
/**
 * @author Joll4
 */
object Test extends App {
  val ruudukko = new RobotWorld(20, 20)
  
  val body1 = ruudukko.addRobot(new Coords(1,1), South)
  val body2 = ruudukko.addRobot(new Coords(2,1), South)
  val body3 = ruudukko.addRobot(new Coords(1,2), South)
  val body4 = ruudukko.addRobot(new Coords(2,2), South)
  
  val spinBot1 = new Spinbot("Jolla", body1)  
  val spinBot2 = new Spinbot("Juti", body2)
  val spinBot3 = new Spinbot("Murmeli", body3)  
  val spinBot4 = new Spinbot("King", body4)
  
  println(spinBot1.robotInFront)
  println(spinBot2.robotInFront)
  println(spinBot3.robotInFront)
  println(spinBot4.robotInFront)

  println(ruudukko.robotList)
}