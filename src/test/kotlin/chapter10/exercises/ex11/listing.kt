package chapter10.exercises.ex11

import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

//tag::init1[]
fun wordCount(s: String): Int = TODO()
//end::init1[]

class Exercise11 : WordSpec({

    val words: List<String> =
        "lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do"
            .split(" ")

    "word count" should {
        "!count words using balanced folding" {
            assertAll(Gen.list(Gen.from(words))) { ls ->
                val text = ls.joinToString(" ")
                println("${ls.size}: $text")
                wordCount(text) shouldBe ls.size
            }
        }
    }
})
