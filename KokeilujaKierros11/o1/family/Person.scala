package o1.family

/** The class `Person` represents people in a very simple family tree analysis program.
  * Each person has a name and a number of children (which may be zero).
  *
  * **Note:** All the methods in this class assumes that each descendant descends from
  * a person only through a single line of ancestry.
  *
  * A person object is immutable after creation.
  *
  * @param name      the person's name 
  * @param children  the person's children 
  */
class Person(val name: String, val children: Vector[Person]) {
  
  /** Initializes a new, childless `Person`. */
  def this(name: String) = this(name, Vector())
  
  
  /** Returns the number of children the person has. */
  def numberOfChildren = this.children.size

  
  /** Returns the number of descendants the person has in total. A person's 
    * descendants are that person's children plus all their descendants. */
  def numberOfDescendants: Int = ??? // TODO: replace with a working implementation
  
  
  /** Returns the number of descendants the person has in a particular generation.
    * A generation, is defined here as a number of "steps" indicating how far removed a set 
    * of descendants is from their ancestor. For example, a person's children form a generation 
    * at step 1, the grandchildren at step 2, the greatgrandchildren at step 3, and so on. 
    *
    * @param generation  a positive number of "steps" that identifies a generation
    * @return the number of descendants that are *exactly* the indicated number of steps 
    *         removed from the person in the family tree 
    */
  def numberOfDescendantsAt(generation: Int): Int = ??? // TODO: replace with a working implementation
  
  
  /** Returns a string representation of the person. */
  override def toString = (this.name + "(" + this.children.mkString(",") + ")").take(500) 
  
  
}