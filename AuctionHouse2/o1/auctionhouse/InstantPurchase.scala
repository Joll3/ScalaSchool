package o1.auctionhouse

abstract class InstantPurchase (description: String) extends ItemForSale (description) {
  
  private var ostaja: Option[String] = None
   
  def buy(buyer: String): Boolean = {
    if (this.isOpen) {
      ostaja = Some(buyer)
      true
    }
    else false
  }
   //Buys the item for the given customer.
  
  def buyer: Option[String] =  this.ostaja//Returns the winner of the auction
  
  def isOpen: Boolean = if (!this.isExpired && this.ostaja.isEmpty) true else false
  
}