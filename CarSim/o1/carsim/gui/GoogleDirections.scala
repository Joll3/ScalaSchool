
////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it's not necessary    
// that you understand or even look at the code in this file. 
//////////////////////////////////////////////////////////////

package o1.carsim.gui

import scala.util._
import rapture.net._
import rapture.json._
import rapture.io._
import rapture.json.jsonParsers.jackson._ 
import rapture.core.strategy.throwExceptions
import rapture.core.timeSystems.javaUtil

/** The singleton object `GoogleDirections` provides an interface to Google's Directions API over the internet.
  *
  * '''NOTE TO STUDENTS: In this course, you don't need to understand how this object works or can be used.'''
  *
  * (This class is not really a part of the GUI ''per se''. It is here as an artifact of the programming assigment)  
  */
object GoogleDirections {

  def findRoute(origin: String, destination: String) = { 

    def parseJson(directionsString: String) = {
      val directionsJson = Try(Json.parse(directionsString)).getOrElse(throw new DirectionsException("Received an invalid JSON response from Google Directions."))
      fetchRoute(directionsJson)
    }
    
    def fetchRoute(directionsJson: Json) = {
      val status = Try(directionsJson.status.as[String]).getOrElse(throw new DirectionsException("Received an invalid response from Google Directions: no status code in JSON."))
      status match {
        case "OK"               => Try(directionsJson.routes(0).legs(0).steps.as[Array[Step]].flatMap( _.segments )).getOrElse(throw new DirectionsException("Received an invalid JSON response from Google Directions: failed to get the route.")) 
        case "NOT_FOUND"        => throw new DirectionsException("Unknown location. (Usage: Provide a place name, an address, or a pair of coordinates.)") 
        case "ZERO_RESULTS"     => throw new DirectionsException("Directions to the destination could not be found. Either no route exists or it is not known to Google Directions.") 
        case "OVER_QUERY_LIMIT" => throw new DirectionsException("Could not determine the directions. The Google Directions service has a daily usage quota, which has been exceeded on this computer.") 
        case otherStatus        => throw new DirectionsException("Could not determine the directions. Status message from the Google Directions service: " + otherStatus) 
      }  
    }

    val searchParameters = Map('sensor -> "false", 'origin -> origin, 'destination -> destination)
    val searchURL = Http / "maps.googleapis.com" / "maps/api/directions/json" /? searchParameters
    val jsonStringResponse = Try(searchURL.get(5000L).slurp[Char]).getOrElse(throw new DirectionsException("Failed to access Google Directions over the Internet. Please make sure your network connection is working."))
    parseJson(jsonStringResponse)
  }
  
  case class Coords(val lat: Double, val lng: Double) 
  
  case class Distance(val value: Int, val text: String) 
  
  case class Segment(val distance: Double, val origin: Coords, val destination: Coords)   
  
  case class Step(val start_location: Coords, val end_location: Coords, val polyline: Json, val distance: Distance, val html_instructions: String) { 
    
    lazy val segments = {
      def distanceSortOf(a: Coords, b: Coords) = math.hypot(b.lat - a.lat, b.lng - a.lng) 
      val points = decodePolyline(polyline.points.as[String].iterator)
      val lengthsAndEnds = for ((from, to) <- points zip points.tail) yield (distanceSortOf(from, to), from, to) 
      val totalLength = lengthsAndEnds.foldLeft(0.0)( _ + _._1 )  
      for ((dist, from, to) <- lengthsAndEnds) yield Segment(dist / totalLength * this.distance.value, from, to) // the length in meters for each polyline segment is not provided, so we use this simple metric that is good enough for current purposes   
    }

    // see: https://developers.google.com/maps/documentation/utilities/polylinealgorithm
    private def decodePolyline(encodedPolyline: Iterator[Char]) = {

      val offsets = new Iterator[(Int, Int)] {
        def hasNext = encodedPolyline.hasNext 
        def next() = (nextOffset(), nextOffset())
        private def nextOffset() = { 
          var shift = 0
          var chunks = 0
          var continueBitPlusChunk = 0
          do {
            continueBitPlusChunk = encodedPolyline.next() - 63
            val chunk = (continueBitPlusChunk & 0x1F) 
            chunks |= (chunk << shift) 
            shift += 5
          } while (continueBitPlusChunk > 0x1F)
          val offsetPossiblyInverted = chunks >> 1
          if ((chunks & 1) == 0) offsetPossiblyInverted else ~offsetPossiblyInverted 
        }
      }   

      if (offsets.hasNext) {
        def addOffset(base: (Int,Int), offset: (Int,Int)) = (base, offset) match { case ((lat, lng), (latOffset, lngOffset)) => (lat + latOffset, lng + lngOffset) }
        val coordPairs = offsets.scanLeft(offsets.next())(addOffset)  
        val coords = for ((lat, lng) <- coordPairs) yield Coords(lat.toDouble / 100000, lng.toDouble / 100000)
        coords.toIndexedSeq 
      } else {
        IndexedSeq()
      }
    }

  }

  class DirectionsException(val message: String) extends Exception(message)
  
}



