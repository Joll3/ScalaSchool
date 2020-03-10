package auction

/**
 * A Single bid on an AuctionItem by an auction user for a given price.
 * 
 * @param item an auction item
 * @param who a user bidding
 * @param price an amount of the bid
 */
case class Bid(item: AuctionItem, who: User, price: Int)