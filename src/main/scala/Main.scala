package venmo
import scala.io.StdIn
import venmo._
object Main extends App {
  var command = ""
  while ({command = StdIn.readLine(); command != "q"}) {
    val p = command.split("")
    p(0) match {
      case "init" =>
      val user = p(1)
      val amount = p(2).toLong
      Wallet.init(user, amount)

      case "transfer" => val from = p(1)
        val to = p(2)
        val amount = p(3).toLong
        val payment = Payment(from, to, amount)
        for {
          _ <- Wallet.makePayment(payment)
        } yield {
          PaymentLedger.add(payment)
        }

      case "listAll" => val id = p(1)
      PaymentLedger.get(id)

      case "bankTransfer" => val id = p(1)
      val amount = p(2).toLong
      for {
        _ <- Wallet.bankTransfer(id, x => Right(amount))
      } yield {
        ()
      }

      case _ => "not yet supported"
    }
  }
}
