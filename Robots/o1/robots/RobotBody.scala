package o1.robots

import o1.grid._


/** The class `RobotBody` represents virtual robots (or "bots") which inhabit two-dimensional 
  * grid worlds. More specifically, each instance of the class represents a "robot body" 
  * (or "chassis" or "hardware") and basic functionality of such a robot. Each "robot body" 
  * is associated with a "robot brain" that controls the body and determines what functionality 
  * is activated and when.
  *
  * A robot is equipped with the various capabilities:
  *
  * - It can sense its own surroundings (location, facing, the world that it is in).
  *
  * - It can spin around in any one of the four main compass directions.
  *
  * - It can move into the next square in a given direction.
  *
  * - It can sense whether it is broken or not, and whether it is "stuck" in a square between 
  *   four walls.
  *
  * When a robot's `takeTurn` method is called, it uses its "brain" to figure out what to do 
  * (move, turn about, etc.). Robots with different kinds of brains behave differently.
  *
  * @param world            the world inhabited by the robot
  * @param initialLocation  the initial location of the robot in its world
  * @param initialFacing    the direction the robot initially faces in
  *
  * @see [[RobotWorld]]
  * @see [[RobotBrain]]
  */
class RobotBody(val world: RobotWorld, initialLocation: Coords, initialFacing: Direction) { 
  
  /** the robot's brain (if it has one) */ 
  var brain: Option[RobotBrain] = None              // most-recent holder (it is possible for robot to change brains, but this is only done in Chapter 12.1)
  private var coordinates = initialLocation         // gatherer: changes in relation to the old location
  private var isIntact = true                       // flag: can be broken or repaired
  private var facesTowards = initialFacing          // most-recent holder (can be changed arbitrarily)
  
  
  /** Returns the coordinates that indicate the robot's current location in the robot world. */
  def location = this.coordinates

  
  /** Returns the square the robot is currently in. */
  def locationSquare = this.world(this.location)

  
  /** Returns a square that neighbors the robot's current location in the given direction. */
  def neighboringSquare(direction: Direction): Square = this.world.elementAt(coordinates.relative(direction, 1))// TODO: replace with a working implementation


  /** Returns the direction the robot is currently facing in. */
  def facing = this.facesTowards
  
  
  /** Turns the robot to face in the specified direction. This method must only be called 
    * if the robot is working right as defined by the method `isWorkingRight`. */
  def spinTowards(newFacing: Direction) = {
    this.facesTowards = newFacing 
  }

  
  /** Turns the robot 90 degrees clockwise. This method must only be called if the robot 
    * is working right as defined by the method `isWorkingRight`. */
  def spinClockwise() = {
    if(this.isWorkingRight) facesTowards = facesTowards.clockwise// TODO: implementation missing
  } 

  
  /** Causes the robot to malfunction (typically as the result of a collision).
    * A broken robot does not do anything during its turns.
    *
    * @see [[fix]]
    */
  def destroy() = {
    this.isIntact = false
  }

  
  /** Repairs a broken robot. The robot can now start taking its turns normally.
    *
    * @see [[destroy]]
    */
  def fix() = {
    this.isIntact = true
  }
  
  
  /** Relocates the robot within its current world to the square next to the robot's 
    * current location, in the given direction. The direction does not necessarily have 
    * to be the same one that the robot is originally facing in.
    *
    * This method turns the robot to face in the direction it moves in. 
    *
    * Two robots can never be in the same square; neither can a robot and a wall. If the 
    * robot's would-be location is not empty, a collision occurs instead and the robot 
    * does not change locations (but still turns to face whatever it collided with). 
    *
    * If the moving robot collides with a wall, the robot itself breaks. If a moving robot 
    * collides with another robot, the other robot breaks and the moving robot stays intact. 
    *
    * This method must only be called if the robot is working right as defined by the 
    * method `isWorkingRight`.
    *
    * @return `true` if the robot successfully changed locations, `false` if it 
    *         did not (even if it changed facing) 
    */
  def moveTowards(direction: Direction) = { 
    this.spinTowards(direction)
    val targetCoordinates = this.location.neighbor(direction)
    val targetSquare = this.world(targetCoordinates)
    val managedToMove = targetSquare.addRobot(this)
    if (managedToMove) {
      this.locationSquare.clear()
      this.coordinates = targetCoordinates
    }
    managedToMove
  }
  
  
  /** Gives the robot a turn to act. 
    *
    * A robot that is not working right (as defined by `isWorkingRight`) or that is stuck 
    * (as defined by `isStuck`) does not do anything during its turn. A working, unstuck 
    * robot consults its brain to find out what to do. It does this by calling the brain's 
    * `moveBody` method.  
    *
    * @see [[RobotBrain.moveBody]]
    */
  def takeTurn() = {
    if(this.isWorkingRight && !this.isStuck)
      this.brain.foreach(_.moveBody)
    
    // TODO: implementation missing
  }
  
  
  /** Determines whether the robot is operational or not. A robot is inoperational if it has 
    * been broken with the `destroy` method and not fixed since. A robot that has no brain 
    * is also inoperational. Otherwise, the robot is considered to be "working right". */
  def isWorkingRight = this.isIntact && this.brain.isDefined
  
  
  /** Determines whether the square neighboring the robot in the given direction contains 
    * a permanently unpassable obstacle (a wall, that is). Another robot is not considered an 
    * unpassable obstacle for this purpose. */
  def canMoveTowards(direction: Direction): Boolean = if (!this.neighboringSquare(direction).isUnpassable) true else false // TODO: replace with a working implementation
  
  
  /** Determines whether the robot is stuck or not, that is, if there are any squares around 
    * the robot that don't contain unpassable obstacles (walls). Only the nearest squares in 
    * the four main compass directions are considered. If there is a wall in all directions, 
    * the robot is considered stuck. Another robot is not considered an unpassable obstacle 
    * for this purpose.
    *
    * @see [[canMoveTowards]]
    */
//  def isStuck: Boolean = !this.canMoveTowards(North) && !this.canMoveTowards(South) && !this.canMoveTowards(East) && !this.canMoveTowards(West)// TODO: replace with a working implementation
  def isStuck: Boolean = {
    val neighbors = this.world.neighbors(this.location, false)
    var walls = 0 
    
    for (naapuri <- neighbors) {
      if (naapuri == Wall) walls += 1
    }
    if (walls == 4) true else false
  }// TODO: replace with a working implementation

 
  
}
