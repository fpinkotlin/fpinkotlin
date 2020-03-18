package chapter1

object Listing_1_4 {
    class CreditCard

    //tag::init[]
    data class Charge(val cc: CreditCard, val amount: Float) { // <1>

        fun combine(other: Charge): Charge = // <2>
            if (cc == other.cc) // <3>
                Charge(cc, amount + other.amount) // <4>
            else throw Exception(
                "Cannot combine charges to different cards"
            )
    }
    //end::init[]
}
