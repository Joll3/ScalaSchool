package backends.sms

import auction.event._
import auction.User

/**
 * A mixin that turns messaging events into SMS messages.
 */
trait SMSService {
  self: Dispatcher =>

  /**
   * Picks out messaging events and sends the messages to recipients as SMS messages.
   */
  object SMSListener extends Listener {

    def receiveEvent(e: AuctionEvent) {
      e match {
        case MessagingEvent(client, message) => {
          sendSMS(client, message)
        }
        case _ => // Ignore other events
      }
    }
  }

  // Register to the dispatcher object this trait is mixin with.
  registerListener(SMSListener)

  /**
   * Sends a fake SMS message. Should not be used directly but only via firing
   * messaging events on the dispatcher object.
   */
  def sendSMS(client: User, message: String) = {
    println(s"SMS ${client.phoneNumber} : $message")
  }
}