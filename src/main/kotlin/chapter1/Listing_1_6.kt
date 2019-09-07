package chapter1

object Listing_1_6 {

  class CreditCard

  data class Charge(val cc: CreditCard, val amount: Float) {
    fun combine(other: Charge): Charge = TODO()
  }

  //tag::init[]
  fun List<Charge>.coalesce(): List<Charge> =
          this.groupBy { it.cc }.values.map { value ->
            value.reduce { a, b ->
              a.combine(b)
            }
          }
  //end::init[]
}