package chapter5.solutions.sol11

import chapter3.List
import chapter4.Option
import chapter4.Some
import chapter4.getOrElse
import chapter5.Stream
import chapter5.Stream.Companion.cons
import chapter5.Stream.Companion.empty
import chapter5.solutions.sol1.toList
import chapter5.solutions.sol13.take
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init[]
fun <A, S> unfold(z: S, f: (S) -> Option<Pair<A, S>>): Stream<A> =
    f(z).map { pair ->
        cons({ pair.first },
            { unfold(pair.second, f) })
    }.getOrElse {
        empty()
    }
//end::init[]

class Solution11 : WordSpec({
    "unfold" should {
        """return a stream based on an initial state and a function
            applied to each subsequent element""" {
                unfold(0, { s: Int -> Some(Pair(s, s + 1)) })
                    .take(5).toList() shouldBe
                    List.of(0, 1, 2, 3, 4)
            }
    }
})
