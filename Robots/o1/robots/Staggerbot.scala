package o1.robots

import o1.grid._
import scala.util.Random

class Staggerbot(name: String, body: RobotBody, randomSeed: Int) extends RobotBrain(name, body) {

  private var seed = new Random(randomSeed)
  
  /** Moves the robot. A spinbot simply spins 90 degrees clockwise as its move. 
    * This method assumes that it is never called if the robot is broken or stuck. */
  def moveBody() = {
    var suunta = Direction.ValuesClockwise(seed.nextInt(4))
    if (this.body.canMoveTowards(suunta) == true){
      this.body.moveTowards(suunta)
      var uusisuunta = Direction.ValuesClockwise(seed.nextInt(4))
      this.body.spinTowards(uusisuunta)
    }
    else {
      this.body.spinTowards(suunta)
    }
  } 

 
}
// TODO: implementation missing
// TODO: implementation missing