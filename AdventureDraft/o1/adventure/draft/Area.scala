package o1.adventure.draft

import scala.collection.mutable.Map
/** The class `Area` represents locations in a text adventure game world. A game world 
  * consists of areas. In general, an "area" can be pretty much anything: a room, a building, 
  * an acre of forest, or something completely different. What different areas have in 
  * common is that players can be located in them and that they can have exits leading to 
  * other, neighboring areas. An area also has a name and a description. 
  *
  * '''NOTE: The AdventureDraft project is not even close to being well designed. 
  * See Chapter 9.2 in the course materials.'''
  *
  * @param name          the name of the area 
  * @param description   a description of the area 
  */
class Area(var name: String, var description: String) {

  private val neighbors = Map[String, Area]()
  
  def neighbor(direction: String) = this.neighbors.get(direction)
  
  def setNeighbors(exits: Vector[(String, Area)]) = {
  for (exit <- exits) {
    this.neighbors += exit
    }
  }
  
  def setNeighbor(direction: String, neighbor: Area) = {
    this.neighbors += direction -> neighbor
  }
  
  def printAreaInfo() = {
  println("\n\n" + this.name)
  println(this.name.replaceAll(".", "-"))
  println(this.description)
  println("\nExits available: " + this.neighbors.keys.mkString(" ") + "\n\n")
  }

  /** the area that is reached by exiting due north from this area; `None` if there is no northwardly exit */ 
  var northNeighbor: Option[Area] = None 
  /** the area that is reached by exiting due east from this area; `None` if there is no eastwardly exit */ 
  var eastNeighbor:  Option[Area] = None 
  /** the area that is reached by exiting due south from this area; `None` if there is no southwardly exit */ 
  var southNeighbor: Option[Area] = None 
  /** the area that is reached by exiting due west from this area; `None` if there is no westwardly exit */ 
  var westNeighbor:  Option[Area] = None 

  
  /** Returns a description of the area for debugging purposes. */
  override def toString = this.name + ": " + this.description.replaceAll("\n", " ").take(150)
  
}
