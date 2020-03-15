package chapter2

object Listing_2_6_1 {
    //tag::par1[]
    fun <A, B, C> partial1(a: A, f: (A, B) -> C): (B) -> C = TODO()
    //end::par1[]
}

object Listing_2_6_2 {
    //tag::par2[]
    fun <A, B, C> partial1(a: A, f: (A, B) -> C): (B) -> C =
        { b: B -> TODO() }
    //end::par2[]
}

object Listing_2_6_3 {
    //tag::par3[]
    fun <A, B, C> partial1(a: A, f: (A, B) -> C): (B) -> C =
        { b: B -> f(a, b) }
    //end::par3[]
}

object Listing_2_6_4 {
    //tag::par4[]
    fun <A, B, C> partial1(a: A, f: (A, B) -> C): (B) -> C =
        { b -> f(a, b) }
    //end::par4[]
}
