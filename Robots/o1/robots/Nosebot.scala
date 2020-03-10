package o1.robots

import o1.grid._

class Nosebot(name: String, body: RobotBody) extends RobotBrain(name, body) {

  
  /** Moves the robot. A spinbot simply spins 90 degrees clockwise as its move. 
    * This method assumes that it is never called if the robot is broken or stuck. */
  def moveBody() = {
  while(moveCarefully == false){
    this.body.spinClockwise
    }
  }

 
}
// TODO: implementation missing