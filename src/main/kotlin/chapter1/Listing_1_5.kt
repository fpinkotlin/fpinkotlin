package chapter1

object Listing_1_5 {

    class CreditCard

    data class Coffee(val price: Float = 2.50F)

    data class Charge(val cc: CreditCard, val amount: Float) {
        fun combine(other: Charge): Charge = TODO()
    }

    //tag::init[]
    class Cafe {

        fun buyCoffee(cc: CreditCard): Pair<Coffee, Charge> = TODO()

        fun buyCoffees(
            cc: CreditCard,
            n: Int
        ): Pair<List<Coffee>, Charge> {

            val purchases: List<Pair<Coffee, Charge>> =
                List(n) { buyCoffee(cc) } // <1>

            val (coffees, charges) = purchases.unzip() // <2>

            return Pair(
                coffees,
                charges.reduce { c1, c2 -> c1.combine(c2) }
            ) // <3>
        }
    }
    //end::init[]
}
