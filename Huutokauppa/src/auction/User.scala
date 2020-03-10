package auction

/**
 * Auction user account with credits to buy automated features provided by the
 * auction implementation.
 * 
 * @param name a user name
 * @param phoneNumber a user phone number (for sending SMS)
 */
class User(name: String, val phoneNumber:Int) {
    
  private var credits = 0
  
  /**
   * Adds new credits for the user.
   * 
   * @param amount an amount of credits to add
   */
  def buyCredits(amount: Int) = {
    credits += amount
  }
  
  /**
   * Uses one credit from the user. One credit is supposed to cover for
   * single use of automated auction features.
   */
  def useCredit() = {
    credits -= 1
  }
  
  def getCredits = credits
  
  def hasCredits = credits > 0

  def getName = name
  
  override def toString = name
}