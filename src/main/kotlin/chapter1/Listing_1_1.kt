package chapter1

object Listing_1_1 {
    class CreditCard {
        fun charge(price: Float): Unit = TODO()
    }

    data class Coffee(val price: Float = 2.50F)

    //tag::init[]
    class Cafe {

        fun buyCoffee(cc: CreditCard): Coffee {

            val cup = Coffee() // <1>

            cc.charge(cup.price) // <2>

            return cup // <3>
        }
    }
    //end::init[]
}
