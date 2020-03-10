package o1.auctionhouse

import scala.math._

class EnglishAuction(val description: String, val startingPrice: Int,private var duration: Int) {

  // Keep these variables; they will be useful for implementing the methods.
  private var highestBid = new Bid(None, startingPrice)       // most-wanted holder
  private var secondHighestBid = new Bid(None, startingPrice) // most-wanted holder  

  // However, the method implementations given below leave a lot to be desired. 

  
  def daysLeft: Int =  this.duration
  
  
  def advanceOneDay() = {
    if (isOpen) {
      this.duration -= 1
    }
   
  }

  
  def isOpen: Boolean = if (this.duration > 0) true else false 
  
  def isExpired: Boolean = if (this.duration <= 0 && this.highestBid.bidder.isEmpty) true else false 

  
  def buyer: Option[String] = this.highestBid.bidder

  
  def price: Int = {
    if (this.secondHighestBid.bidder.isEmpty) this.startingPrice 
    //else if ()
    else min(this.secondHighestBid.limit + 1,highestBid.limit)
  }
  
  def requiredBid = {
    if (this.highestBid.bidder.isEmpty) this.startingPrice 
    else this.price + 1
  }
  
  def bid(bidder: String, amount: Int): Boolean = {
    var toinen = this.highestBid
    if (this.isOpen && amount > this.highestBid.limit) {
      this.highestBid = new Bid(Some(bidder), amount)
      this.secondHighestBid = toinen
      true
    }
    else if (this.isOpen && amount >= this.secondHighestBid.limit) {
      this.secondHighestBid = new Bid(Some(bidder), amount)
      false
    }
    else false
  }
  
  
  override def toString = this.description
  
  
}
