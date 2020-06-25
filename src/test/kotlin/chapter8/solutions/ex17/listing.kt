package chapter8.solutions.ex17

/*
//tag::rel[]
l.takeWhile(f) + l.dropWhile(f) == l
//end::rel[]
*/

fun main() {
    //tag::list[]
    val l = listOf(1, 2, 3, 4, 5)
    val f = { i: Int -> i < 3 }
    val res0 = l.takeWhile(f) + l.dropWhile(f)

    assert(res0 == l)
    //end::list[]
}
