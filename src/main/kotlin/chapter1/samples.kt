package chapter1

class CreditCard

data class Coffee(val price: Float = 2.50F)

data class Charge(val cc: CreditCard, val amount: Float) {

    fun combine(other: Charge): Charge =
            if (cc == other.cc)
                Charge(cc, amount + other.amount)
            else throw Exception("Cannot combine charges to different cards")
}

fun List<Charge>.coalesce(): List<Charge> =
        this.groupBy { it.cc }.values.map { it.reduce { a, b -> a.combine(b) } }

class Cafe {

    fun buyCoffee(cc: CreditCard): Pair<Coffee, Charge> {

        val cup = Coffee()

        return Pair(cup, Charge(cc, cup.price))
    }

    fun buyCoffees(cc: CreditCard, n: Int): Pair<List<Coffee>, Charge> {

        val purchases: List<Pair<Coffee, Charge>> = List(n) { buyCoffee(cc) }

        val (coffees, charges) = purchases.unzip()

        return Pair(coffees, charges.coalesce().first())
    }
}

fun main() {
    val cafe = Cafe()
    val cc = CreditCard()

    //buy a single coffee
    val (coffee, charge1) = cafe.buyCoffee(cc)
    println("Coffees: $coffee; Total charge: $charge1")

    //buy multiple coffees
    val (coffees, charge2) = cafe.buyCoffees(cc, 3)
    println("Coffees: $coffees; Total charge: $charge2")
}
