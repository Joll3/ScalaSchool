package o1.items

// TODO: täydennä luokka Container luvun 6.4 ohjeiden mukaisesti.

class Container(name: String) extends Item (name) {
  var counter = 0
  
  override def toString = name + " containing " + counter + " item(s)"
  
  def addContent(newContent: Item): Unit = {
    counter += 1
  }
  
  
  
  
}