package chapter6.exercises.ex4

// import chapter3.Cons
import chapter3.List
// import chapter3.Nil
import chapter6.RNG
import chapter6.rng1
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
/**
 * TODO: Re-enable tests by removing `!` prefix!
 */
class Exercise4 : WordSpec({

    //tag::init[]
    fun ints(count: Int, rng: RNG): Pair<List<Int>, RNG> = TODO()
    //end::init[]

    "ints" should {
        "!generate a list of ints of a specified length" {

            ints(5, rng1) shouldBe
                Pair(List.of(1, 1, 1, 1, 1), rng1)
        }
    }
})
