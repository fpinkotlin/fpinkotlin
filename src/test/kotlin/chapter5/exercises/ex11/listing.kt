package chapter5.exercises.ex11

import chapter3.List
import chapter4.Option
import chapter4.Some
import chapter5.Stream
import chapter5.solutions.sol1.toList
import chapter5.solutions.sol13.take
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init[]
fun <A, S> unfold(z: S, f: (S) -> Option<Pair<A, S>>): Stream<A> = TODO()
//end::init[]

/**
 * Re-enable the tests by removing the `!` prefix!
 */
class Exercise11 : WordSpec({
    "unfold" should {
        """!return a stream based on an initial state and a function
            applied to each subsequent element""" {
                unfold(0, { s: Int ->
                    Some(Pair(s, s + 1))
                }).take(5).toList() shouldBe
                    List.of(0, 1, 2, 3, 4)
            }
    }
})
