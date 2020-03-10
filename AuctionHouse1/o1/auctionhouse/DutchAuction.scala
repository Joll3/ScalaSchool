package o1.auctionhouse
import scala.math._

/**
 * @author Joll4
 */
class DutchAuction(val kuvaus:String, val startingPrice: Int, val decrement: Int, val minimumPrice: Int) {
  
  private var daysSold = 0
  private var ostaja: Option[String] = None
  private  var stagnantPrice = 0
  private var hinta = startingPrice
  
  def advanceOneDay(): Unit = {
    if (isOpen){
      daysSold += 1
      this.hinta = max(this.hinta - this.decrement, this.minimumPrice)
      if ( this.hinta == this.minimumPrice) this.stagnantPrice += 1
    }
  }
  
  def buy(buyer: String): Boolean = {
    if (this.isOpen) {
      this.ostaja = Some(buyer)
      true
    }
    else false
  }
   //Buys the item for the given customer.

  def buyer: Option[String] =  this.ostaja//Returns the winner of the auction

  def description: String = this.kuvaus
    
  def isOpen: Boolean = if (!this.isExpired && this.ostaja.isEmpty) true else false
  
  def isExpired: Boolean = if (this.stagnantPrice > 4) true else false//Determines if the auction has expired, that is, if it has ended without the item being sold.
  
  def price: Int = this.hinta//Returns the current price of the item, that is, the price that the item can be purchased for at the moment, assuming the auction is open.

  def priceRatio: Double = this.hinta.toDouble / this.startingPrice.toDouble//Returns the "price ratio" of the auction that describes the current "cheapness" of the item; the lower, the cheaper.

  override def toString = this.kuvaus
}