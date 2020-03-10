package o1.train

/**
 * @author Joll4
 */
object TrainTest extends App {
  
  val juna1 = new Train("From Helsinki to Oulu, leaves at 8:13")
  val vaunu1 = new SleepingCar(5)
  val vaunu2 = new SittingCar(6,4)
  
  juna1.addCar(vaunu1)
  juna1.addCar(vaunu2)
  
  println("Luodaan yksi juna, jossa makuuvaunu (5 hyttiä a 3 hlö) ja istumavaunu (6x4 hyttiä).")
  println("Junan ensimmäinen vaunu on olemassa: " + juna1.car(1) + "Junan toinen vaunu on olemassa: " + juna1.car(2) + " Junan kuudes vaunu on: " + juna1.car(6))
  println(vaunu1.numberOfPlaces + " Sänkypaikkaa vaunussa " + vaunu1.emptyCabinCount + " Vapaata hyttiä " + vaunu1.numberOfFreePlaces + " Vapaata sänkypaikkaa " )
  println("Varataan yksi hytti")
  
  vaunu1.reserveCabin(4)
  
  println(vaunu1.numberOfPlaces + " Sänkypaikkaa vaunussa " + vaunu1.emptyCabinCount + " Vapaata hyttiä " + vaunu1.numberOfFreePlaces + " Vapaata sänkypaikkaa " )
  println("Varattu hyttivarattuna? " + vaunu1.isEmptyCabin(4) + " Vapaita makuupaikkoja hytissä: " + vaunu1.numberOfFreeBedsInCabin(4))
  println("Varataan 4 paikaa")
  
  vaunu1.reservePlaces(4)
  
  println(vaunu1.numberOfPlaces + " Sänkypaikkaa vaunussa " + vaunu1.emptyCabinCount + " Vapaata hyttiä " + vaunu1.numberOfFreePlaces + " Vapaata sänkypaikkaa " )
  println("Varattu hyttivarattuna? " + vaunu1.isEmptyCabin(4) + " Vapaita makuupaikkoja hytissä: " + vaunu1.numberOfFreeBedsInCabin(4))
  
  println("----------------------------------------------------------------")
  
  println(vaunu2.numberOfPlaces + " Istumapaikkaa vaunussa " + vaunu2.numberOfFreePlaces + " Vapaita paikkoja ")
  println("Varataan 1 paikka ekan rivin ekasta paikasta")
  
  vaunu2.reservePlace(1, 'a')
  
  println(vaunu2.numberOfPlaces + " Istumapaikkaa vaunussa " + vaunu2.numberOfFreePlaces + " Vapaita paikkoja ")
  println("Onko varattu paikka varattu " + vaunu2.isReservedSeat(1, 'a'))
      
  vaunu2.reservePlace(2, 'a')
  
  println(vaunu2.numberOfPlaces + " Istumapaikkaa vaunussa " + vaunu2.numberOfFreePlaces + " Vapaita paikkoja ")
  println("Onko varattu paikka varattu " + vaunu2.isReservedSeat(2, 'a'))
  
  vaunu2.reservePlace(3, 'b')
  
  println(vaunu2.numberOfPlaces + " Istumapaikkaa vaunussa " + vaunu2.numberOfFreePlaces + " Vapaita paikkoja ")
  println("Onko varattu paikka varattu " + vaunu2.isReservedSeat(3, 'b'))
      
  vaunu2.reservePlace(4, 'b')
  
  println(vaunu2.numberOfPlaces + " Istumapaikkaa vaunussa " + vaunu2.numberOfFreePlaces + " Vapaita paikkoja ")
  println("Onko varattu paikka varattu " + vaunu2.isReservedSeat(4, 'b'))
  
  vaunu2.reservePlace(5, 'c')
  
  println(vaunu2.numberOfPlaces + " Istumapaikkaa vaunussa " + vaunu2.numberOfFreePlaces + " Vapaita paikkoja ")
  println("Onko varattu paikka varattu " + vaunu2.isReservedSeat(5, 'c'))
  
  vaunu2.reservePlace(6, 'c')
  
  println(vaunu2.numberOfPlaces + " Istumapaikkaa vaunussa " + vaunu2.numberOfFreePlaces + " Vapaita paikkoja ")
  println("Onko varattu paikka varattu " + vaunu2.isReservedSeat(6, 'c'))
  
  vaunu2.reservePlaces(3)
  
  println("Varataan kolmen hengen ryhmälle paikat (pitäisi olla ekalla rivillä kaikki täynnä)")
  println(vaunu2.numberOfPlaces + " Istumapaikkaa vaunussa " + vaunu2.numberOfFreePlaces + " Vapaita paikkoja ")
  println("Onko varattu paikka varattu " + vaunu2.isReservedSeat(1, 'd'))
  
  vaunu2.reservePlaces(4)
  
  println("Varataan neljän hengen ryhmälle paikat, ei pitäisi varata mitään")
  println(vaunu2.numberOfPlaces + " Istumapaikkaa vaunussa " + vaunu2.numberOfFreePlaces + " Vapaita paikkoja ")
  println("Onko väärin varattu paikka varattu " + vaunu2.isReservedSeat(2, 'c'))
  
}