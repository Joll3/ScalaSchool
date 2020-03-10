package auction
import auction.event._
import scala.collection.immutable.Set

/**
 * Auction holds auctioned items that users can try to bid on until the auction
 * item is closed.
 *
 * Auction is a fairly easily extendable base for building an electronic
 * auctionhouse. One of the key elements is the use of events, which allows
 * for easy to plug in mixins that can register themselves as event listeners
 * and react to what happens in the auction.
 *
 * The dispatcher is provided in a separate mixin called Dispatcher. This
 * allows substituting the mixin when testing or for example providing a
 * dispatcher that can dispatch events to remote Listeners etc.
 */
trait Auction {
  self: Dispatcher =>

  /**
   * Auction keeps a record of auction items that are currently open or closed.
   */
  private var openAuctions = Set[AuctionItem]();
  private var closedAuctions = Set[AuctionItem]();

  def getOpenAuctions = openAuctions
  def getClosedAuctions = closedAuctions

  /**
   * Logs a message describing what happens in the auction class.
   * The default functionality is to suppress log messages, but this can be
   * overridden in subclasses or mixins.
   */
  def log(message: String, append: Boolean = true) = {};

  /**
   * Tries to place a bid on an item currently being auctioned.
   *
   * @param who a user bidding
   * @param item an item to bid for
   * @param amount an amount to bid
   * @return true if bid accepted
   */
  def makeBid(who: User, item: AuctionItem, amount: Int): Boolean = {
    log(s"Trying to make a bid on ${item.getName} for $amount for ${who.getName}")

    if (item.acceptableBid(who, amount)) {
      val newBid = Bid(item, who, amount)

      if (!item.hasNoBids) {
        fireEvent(BidBeaten(item.currentBid.get))
        fireEvent(BidRaised(newBid))
      }
      item.registerBid(newBid)

      log(s"Bid ($item, $amount, ${who.getName}) registered")
      true

    } else {
      log(s"Bid ($item, $amount, ${who.getName}) not accepted")
      false
    }
  }

  /**
   * Adds an item to the auction.
   *
   * @param who an owner and seller of the item
   * @param description a description of the item
   * @param initialAmount an initial amount, also known as a starting bid
   * @param minimumRaise a minimum raise from the last bid
   * @return the created auction item
   */
  def addAuction(who: User, description: String, initialAmount: Int, minimumRaise: Int): AuctionItem = {
    val newAuction = new AuctionItem(description, who, initialAmount, minimumRaise)
    openAuctions += newAuction
    log(s"New item opened for auction $newAuction")
    newAuction
  }

  /**
   * Closes an item in the auction.
   *
   * @param item an item to close
   */
  def closeAuction(item: AuctionItem) = {
    log(s"Auction for $item closes")
    closedAuctions += item
    if (item.hasNoBids) {
      log("\titem was not sold")
      fireEvent(AuctionExpired(item))
    } else {
      log(s"\titem sold to ${item.currentBid.get.who.getName}")
      fireEvent(AuctionWon(item))
    }
  }
}