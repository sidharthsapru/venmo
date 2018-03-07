package venmo
case class Payment(from: String, to: String, amount: Long)

object PaymentLedger {
  val ledger = scala.collection.mutable.ListBuffer[Payment]()

  def add(payment: Payment) = ledger += payment

  def get(id: String): List[Payment] =
    ledger.filter(x => x.from == id || x.to == id).toList
}
