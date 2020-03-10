package o1.robots

import o1.grid._
import scala.math
import scala.collection.mutable.Buffer

class Psychobot(name: String, body: RobotBody) extends RobotBrain(name, body) {

  
  /** Moves the robot. A spinbot simply spins 90 degrees clockwise as its move. 
    * This method assumes that it is never called if the robot is broken or stuck. */
//  def moveBody() = {
//    
//    var N: Option[Direction] = None
//    var E: Option[Direction] = None
//    var S: Option[Direction] = None
//    var W: Option[Direction] = None
//      
//    var paikka = this.body.location
//    var target = Buffer[Option[Direction]]()
//     
//    for (range <- 1 until this.body.world.height-2-this.location.y){
//      paikka = this.location.relative(North, range)
//      if(this.world.elementAt(paikka) != Wall && this.world.elementAt(paikka).robot.forall(_.isWorkingRight) == true) {
//        target :+ Some(this.world.elementAt(paikka))
//        N = Some(North)
//        }
//      }
//
//    for (range <- 1 until this.body.world.width-2-this.location.x){
//      paikka = this.location.relative(East, range)
//      if(this.world.elementAt(paikka) != Wall && this.world.elementAt(paikka).robot.forall(_.isWorkingRight) == true) {
//        target :+ Some(this.world.elementAt(paikka))
//        E = Some(East)
//        }
//      }    
//    
//    for (range <- 1 until this.location.y - this.body.world.width-2){
//      paikka = this.location.relative(South, range)
//      if(this.world.elementAt(paikka) != Wall && this.world.elementAt(paikka).robot.forall(_.isWorkingRight) == true) {
//        target :+ Some(this.world.elementAt(paikka))
//        S = Some(South)
//        }
//      }    
//    
//    for (range <- 1 until this.location.x - this.body.world.width-2){
//      paikka = this.location.relative(West, range)
//      if(this.world.elementAt(paikka) != Wall && this.world.elementAt(paikka).robot.forall(_.isWorkingRight) == true) {
//        target :+ Some(this.world.elementAt(paikka))
//        W = Some(West)
//        }
//      }
//    
//    if (!target(3).isEmpty) {
//    if(!N.isEmpty) while(this.body.isWorkingRight)this.body.moveTowards(North)
//    else if(!E.isEmpty) while(this.body.isWorkingRight)this.body.moveTowards(East)
//    else if(!S.isEmpty) while(this.body.isWorkingRight)this.body.moveTowards(South)
//    else while(this.body.isWorkingRight)this.body.moveTowards(West)
//    
//    }
//  }
  
//  def moveBody() = {
//    for (direction <- Direction.ValuesClockwise){
//      var location = this.body.location
//      var spot: Square = null
//      do {
//        location = location.relative(direction, 1)
//        if(spot.forall(_.robot.forall(_.isWorkingRight)) == true && spot != Wall){
//          while (this.body.isWorkingRight) this.body.moveTowards(direction)
//          }
//      } while (spot.isEmpty)
//    
//    if(spot.forall(_.robot.forall(_.isWorkingRight)) && spot != Wall){this.body.moveTowards(direction)}
//    }
//  }
  
  def moveBody() : Unit = {
        
    for(direction <- Direction.ValuesClockwise){
      
      var place = this.body.location     
      var square: Square = null
      
      do {
         place = place.relative(direction,1)
         square = this.body.world.elementAt(place)
      } while(square.isEmpty)
      
    
      if(square != Wall && square.robot.get.isWorkingRight){
      while (square.robot.get.isWorkingRight){
        this.body.moveTowards(direction)
      }
      }
      
    }
  }
  
}

 

// TODO: implementation missing