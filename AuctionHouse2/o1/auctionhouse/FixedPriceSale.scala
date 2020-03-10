

package o1.auctionhouse/**
 * @author Joll4
 */
class FixedPriceSale (description: String, val price: Int, private var duration: Int) extends InstantPurchase(description) {
  
  private var ostaja: Option[String] = None
  
  def daysLeft = this.duration
      
  def isExpired: Boolean = if (this.duration <= 0) true else false
      
  def advanceOneDay() = if (this.isOpen) this.duration = this.duration - 1
    
}