package chapter10.solutions.ex11

import chapter10.foldMap
import chapter10.solutions.ex10.Part
import chapter10.solutions.ex10.Stub
import chapter10.solutions.ex10.WC
import chapter10.solutions.ex10.wcMonoid
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import kotlin.math.min

//tag::init1[]
fun wordCount(s: String): Int {

    fun wc(c: Char): WC =
        if (c.isWhitespace()) Part("", 0, "")
        else Stub("$c")

    fun unstub(s: String): Int = min(s.length, 1)

    val WCM = wcMonoid()
    return when (val wc = foldMap(s.asSequence(), WCM) { wc(it) }) {
        is Stub -> unstub(wc.chars)
        is Part -> unstub(wc.rs) + wc.words + unstub(wc.rs)
    }
}
//end::init1[]

class Exercise11 : WordSpec({

    val words: List<String> =
        "lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do"
            .split(" ")

    "word count" should {
        "count words using balanced folding" {
            assertAll(Gen.list(Gen.from(words))) { ls ->
                val text = ls.joinToString(" ")
                println("${ls.size}: $text")
                wordCount(text) shouldBe ls.size
            }
        }
    }
})
