package venmo
import venmo._
import scala.collection.concurrent.TrieMap
object Wallet {

  val wallet  = new TrieMap[String, Long]()
  def init(user: String, amount: Long): Either[String, Unit] = {
    if(!wallet.contains(user)) {
      wallet + (user -> amount)
      Right(())
    } else {
      Left(s"User $user exists")
    }
  }

  def getAccount(id: String): Either[String, Long] = {
    if(wallet.contains(id)) {
      Right(wallet(id))
    } else {
      Left(s"No account for id ${id}")
    }
  }

  def makePayment(payment: Payment): Either[String, Unit] = wallet.synchronized {
    for {
    amount1 <- getAccount(payment.from)
    amount2 <- getAccount(payment.to)
  } yield {
    wallet(payment.from) = amount1 - payment.amount
    wallet(payment.to) = amount2 + payment.amount
  }
}

  def bankTransfer(id: String, transfer: (String) => Either[String, Long]): Either[String, Unit] = wallet.synchronized
  {
    for {
      current <- getAccount(id)
      add <- transfer(id)
    } yield {
      wallet(id) = current + add
    }
  }


}
