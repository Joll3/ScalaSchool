package o1.carsim

object Location {
  
  def apply(lat: Double, long: Double) = new Location(lat,long)
}
/**
 * @author Joll4
 */
class Location(val lat: Double, val long: Double) {
   
  def latDiff(another: Location): Double = another.lat - this.lat
   
  def longDiff(another: Location): Double = another.long - this.long
   
  def relative(latOffset: Double, longOffset: Double) = Location(this.lat + latOffset, this.long + longOffset)
   
  override def toString = this.lat + ", " + this.long
   
}