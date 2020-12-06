package chapter15.solutions.ex9

import chapter15.sec2.lift
import chapter15.sec2.processFile
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import java.io.File

//tag::init1[]
fun toCelsius(fahrenheit: Double): Double =
    (5.0 / 9.0) * (fahrenheit - 32.0)
//end::init1[]

//tag::init2[]
fun convert(infile: File, outfile: File): File =
    outfile.bufferedWriter().use { bw ->
        val fn = { of: File, celsius: Double ->
            bw.write(celsius.toString())
            bw.newLine()
            of
        }
        processFile(
            infile,
            lift { df -> toCelsius(df.toDouble()) },
            outfile,
            fn
        ).run()
    }
//end::init2[]

class Exercise9 : WordSpec({
    "convert" should {
        "apply a process and write a new file" {
            val infile = File("src/test/resources/samples/preprocess.txt")
            val outfile = File("build/postprocess.txt")
            hasContent(convert(infile, outfile),
                """
                    30.0
                    20.0
                    10.0
                """.trimIndent()
            )
        }
    }
})

private fun hasContent(f: File, content: String) {
    f.bufferedReader().use {
        it.lines().toArray().joinToString("\n") shouldBe content
    }
}
