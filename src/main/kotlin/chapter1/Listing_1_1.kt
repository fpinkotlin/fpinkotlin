package chapter1

object Listing_1_1 {
    class CreditCard {
        fun charge(price: Float): Unit = TODO()
    }

    data class Coffee(val price: Float = 2.50F)

    //tag::init[]
    class Cafe { // <1>

        fun buyCoffee(cc: CreditCard): Coffee { // <2>

            val cup = Coffee() // <3>

            cc.charge(cup.price) // <4>

            return cup // <5>
        }
    }
    //end::init[]
}