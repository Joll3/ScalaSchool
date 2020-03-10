

package o1.auctionhouse/**
 * @author Joll4
 */
class FixedPriceSale(val description: String, val price: Int, var duration: Int) {
  
  private var ostaja: Option[String] = None
  
  def daysLeft = this.duration
  
  override def toString = this.description
  
  def buyer = this.ostaja
  
  def isExpired: Boolean = if (this.duration <= 0) true else false
  
  def isOpen: Boolean = !this.isExpired && this.ostaja.isEmpty
    
  def advanceOneDay() = if (this.isOpen) this.duration = this.duration - 1
  
  def buy(buyer: String) = {
    
    if (this.isOpen){
      this.ostaja = Some(buyer)
      true
      }
    else false
  }
  
  
  def buyer(buyer: Option[String]) = ostaja
  
}