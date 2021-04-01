package chapter5.exercises.ex11

import chapter3.List
import chapter4.Option
import chapter4.Some
import chapter5.Stream
import chapter5.toList
import chapter5.solutions.ex13.take
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

//tag::init[]
fun <A, S> unfold(z: S, f: (S) -> Option<Pair<A, S>>): Stream<A> =

    SOLUTION_HERE()
//end::init[]

//TODO: Enable tests by removing `!` prefix
class Exercise11 : WordSpec({
    "unfold" should {
        """!return a stream based on an initial state and a function
            applied to each subsequent element""" {
            unfold(0, { s: Int ->
                Some(s to (s + 1))
            }).take(5).toList() shouldBe
                List.of(0, 1, 2, 3, 4)
        }
    }
})
