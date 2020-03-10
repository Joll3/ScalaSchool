package o1.robots

import o1.grid._
import scala.math._

class Lovebot(name: String, body: RobotBody, val beloved: RobotBody) extends RobotBrain(name, body) {

  private var loved = false
  def moveBody() = {

    for (suunta <- Direction.ValuesClockwise){
      if (this.body.neighboringSquare(suunta).robot.getOrElse(None) == beloved) loved = true
      }
  
  if (loved == false) {
  val xdistance = abs(this.body.location.x - this.beloved.location.x)
   val ydistance = abs(this.body.location.y - this.beloved.location.y)
   if (xdistance >= ydistance){
     if (this.body.location.x > this.beloved.location.x){
       this.body.moveTowards(West)
     } else{
       this.body.moveTowards(East)
     }
   }
   else if (ydistance > xdistance && this.body.location.y > this.beloved.location.y){
     this.body.moveTowards(North)
   }
   else {
     this.body.moveTowards(South)
   }
  }
  }
}
// TODO: implementation missing