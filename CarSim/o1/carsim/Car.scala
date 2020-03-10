package o1.carsim
import math._

class Car(val fuelConsumption: Double, val tankSize: Double, private var initialFuel: Double, private var initialLocation: Location) {

  private var ajettu = 0.0
  
  def location: Location = initialLocation                                          // TODO:: replace ??? with a working implementation in Chapter 5.1
 
  def fuel(toBeAdded: Double): Double = {
    val tankki = initialFuel
    initialFuel = min(initialFuel + toBeAdded, tankSize)
    if(toBeAdded + initialFuel >= tankSize) tankSize - tankki
    else toBeAdded
    }                           
  
  def fillUp(): Double = {
    val täyttö = this.tankSize - this.initialFuel
    initialFuel = tankSize
    täyttö
  }                                         
  
  def fuelRatio: Double = (initialFuel / tankSize) * 100                                          
    
  def metersDriven: Double = {
    ajettu
  }                                   

  def fuelRange: Double = (initialFuel / fuelConsumption) * 100000                                         
  
  def drive(destination: Location, metersToDestination: Double): Unit = {
      val osuus = min(this.fuelRange, metersToDestination) / metersToDestination  // osuus matkasta joka todellisuudessa voidaan ajaa
      val newlat = initialLocation.lat + osuus * (initialLocation.latDiff(destination))  // uudet koordinaatit
      val newlong = initialLocation.long + osuus * (initialLocation.longDiff(destination))
      val pituus = osuus * metersToDestination
      if (initialFuel > 0){
      initialFuel = initialFuel - ((pituus / 100000) * (fuelConsumption))
      ajettu = ajettu + pituus
      initialLocation = new Location(newlat, newlong)
      }
      else None
  }  // TODO:: replace ??? with a working implementation in Chapter 5.1
  
}

