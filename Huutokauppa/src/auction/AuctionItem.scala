package auction

/**
 * An item sold in an auction. The seller can set a starting bid and a minimum
 * amount to raise for a bid to be acceptable.
 *
 * @param name a name or description of the item
 * @param seller a seller and owner of the item
 * @param startingBid a starting bid to accept
 * @param minimumRaise a minimum raise to accept
 */
class AuctionItem(name: String, seller: User, startingBid: Int, minimumRaise: Int) {

  //  The AuctionItem keeps a list of bids made on it.      
  private var bids = scala.collection.immutable.List[Bid]()

  /**
   * Returns a list of bids on this auction item
   */
  def getBids = bids

  // Status of the auction (open or closed)
  private var open = true

  /**
   * Returns the status of the auction (open or closed)
   */
  def isOpen = open

  /**
   * Closes the auction on this item.
   */
  def close() = {
    open = false
  }

  /**
   * Attempts a register a bid on this item.
   * A bid can fail if the auction is closed or it the amount bid is too small.
   *
   * @param bid a bid to try
   * @return true if the bid was accepted as the new best bid
   */
  def registerBid(bid: Bid): Boolean = {
    if (acceptableBid(bid)) {
      bids = bid :: bids
      true
    } else false
  }

  /**
   * The current best bid
   */
  def currentBid = bids.headOption

  /**
   * True, if no bids have yet been made on this item
   */
  def hasNoBids = bids.isEmpty

  /**
   * Checks whether a bid can be accepted as the new best bid.
   */
  def acceptableBid(bidder: User, amount: Int): Boolean =
    ((hasNoBids && amount >= startingBid)
      || (!hasNoBids && currentBid.get.who != bidder && amount >= currentBid.get.price + minimumRaise))

  /**
   * Checks whether a bid can be accepted as the new best bid.
   */
  def acceptableBid(bid: Bid): Boolean = acceptableBid(bid.who, bid.price)

  /**
   * The name of the item being auctioned
   */
  def getName = name

  /**
   * Describes the item and the state of the auction.
   */
  override def toString = s"Item $name sold by $seller, starting price: ${startingBid}"
}