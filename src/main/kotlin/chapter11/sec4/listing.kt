package chapter11.sec4

import arrow.Kind
import arrow.core.None
import arrow.core.Some
import chapter11.Gen

//tag::init1[]
data class Order(val item: Item, val quantity: Int)
data class Item(val name: String, val price: Double)

val genOrder: Gen<Order> =
    Gen.string().flatMap { name: String -> // <1>
        Gen.double(0..10).flatMap { price: Double -> // <2>
            Gen.choose(1, 100).map { quantity: Int -> // <3>
                Order(Item(name, price), quantity)
            }
        }
    }
//end::init1[]

//tag::init2[]
val genItem: Gen<Item> =
    Gen.string().flatMap { name: String ->
        Gen.double(0..10).map { price: Double ->
            Item(name, price)
        }
    }
//end::init2[]

//tag::init3[]
val genOrder2: Gen<Order> =
    Gen.choose(1, 100).flatMap { quantity: Int ->
        genItem.map { item: Item ->
            Order(item, quantity)
        }
    }
//end::init3[]

//tag::init4[]
val genOrder3: Gen<Order> =
    Gen.choose(1, 100).flatMap { quantity: Int ->
        Gen.string().flatMap { name: String ->
            Gen.double(0..10).map { price: Double ->
                Order(Item(name, price), quantity)
            }
        }
    }
//end::init4[]

val listing1 = {
    val x = Gen.unit<Int>(1)
    fun f(i: Int) = Gen.unit<Int>(i)
    fun g(i: Int) = Gen.unit<Int>(i)

    //tag::init5[]
    x.flatMap { a -> f(a) }.flatMap { b -> g(b) } ==
        x.flatMap { a -> f(a).flatMap { b -> g(b) } }
    //end::init5[]
}

fun f(i: Int) = Some(i)
fun g(i: Int) = Some(i)
fun h(i: Int) = Some(i)

val listing2 = {
    //tag::init6[]
    None.flatMap { a -> f(a) }.flatMap { b -> g(b) } ==
        None.flatMap { a -> f(a).flatMap { b -> g(b) } }
    //end::init6[]
}

val listing3 = {
    //tag::init7[]
    None == None
    //end::init7[]
}

val listing4 = {
    val v = 1
    val x = Some(v)

    //tag::init8[]
    x.flatMap { a -> f(a) }.flatMap { b -> g(b) } ==
        x.flatMap { a -> f(a).flatMap { b -> g(b) } } // <1>

    Some(v).flatMap { a -> f(a) }.flatMap { b -> g(b) } ==
        Some(v).flatMap { a -> f(a).flatMap { b -> g(b) } } // <2>

    f(v).flatMap { g(it) } == f(v).flatMap { g(it) } // <3>
    //end::init8[]
}

interface Monad<F> {

    //tag::init21[]
    fun <A> unit(a: A): Kind<F, A>
    //end::init21[]

    fun <A, B> flatMap(fa: Kind<F, A>, f: (A) -> Kind<F, B>): Kind<F, B>

    //tag::init9[]
    fun <A, B, C> compose(
        f: (A) -> Kind<F, B>,
        g: (B) -> Kind<F, C>
    ): (A) -> Kind<F, C>
    //end::init9[]
}

interface Listing<F, A> : Monad<F> {

    val f: (A) -> Kind<F, A>
    val g: (A) -> Kind<F, A>
    val h: (A) -> Kind<F, A>
    val x: Kind<F, A>

    fun listing() {
        //tag::init10[]
        compose(compose(f, g), h) == compose(f, compose(g, h))
        //end::init10[]

        //left side
        val left1 =
            //tag::init11[]
            compose(compose(f, g), h) // <1>
        //end::init11[]

        val left2: (A) -> Kind<F, A> =
            //tag::init12[]
            { a -> flatMap(compose(f, g)(a), h) } // <2>
        //end::init12[]

        val left3: (A) -> Kind<F, A> =
            //tag::init13[]
            { a -> flatMap({ b: A -> flatMap(f(b), g) }(a), h) } // <3>
        //end::init13[]

        val left4: (A) -> Kind<F, A> =
            //tag::init14[]
            { a -> flatMap(flatMap(f(a), g), h) } // <4>
        //end::init14[]

        val left5 =
            //tag::init15[]
            flatMap(flatMap(x, g), h) // <5>
        //end::init15[]

        //right side
        val right1 =
            //tag::init16[]
            compose(f, compose(g, h)) // <1>
        //end::init16[]

        val right2: (A) -> Kind<F, A> =
            //tag::init17[]
            { a -> flatMap(f(a), compose(g, h)) } // <2>
        //end::init17[]

        val right3: (A) -> Kind<F, A> =
            //tag::init18[]
            { a -> flatMap(f(a)) { b -> flatMap(g(b), h) } } // <3>
        //end::init18[]

        val right4: Kind<F, A> =
            //tag::init19[]
            flatMap(x) { b -> flatMap(g(b), h) } // <4>
        //end::init19[]

        //tag::init20[]
        flatMap(flatMap(x, g), h) ==
            flatMap(x) { b -> flatMap(g(b), h) }
        //end::init20[]
    }
}

interface Listing2<F, A> : Monad<F> {

    val fx: (A) -> Kind<F, A>
    val x: Kind<F, A>
    val v: A

    fun listing() {
        //tag::init22[]
        compose(fx, { a: A -> unit(a) }) == fx
        compose({ a: A -> unit(a) }, fx) == fx
        //end::init22[]

        //tag::init23[]
        flatMap(x) { a -> unit(a) } == x
        flatMap(unit(v), fx) == fx(v)

        fx(v) == x

        flatMap(x) {a -> unit(a)} ==
            flatMap(unit(v), fx)
        //end::init23[]

    }
}