package chapter10.solutions.ex8

import chapter10.Monoid
import chapter10.stringMonoid
import chapter7.sec4_4.Par
import chapter7.sec4_4.map2
import chapter7.sec4_4.splitAt
import chapter7.sec4_4.unit
import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.specs.WordSpec
import org.awaitility.Awaitility.await
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicReference

//tag::init1[]
fun <A> par(m: Monoid<A>): Monoid<Par<A>> = object : Monoid<Par<A>> {

    override fun combine(pa1: Par<A>, pa2: Par<A>): Par<A> =
        map2(pa1, pa2) { a1: A, a2: A -> // <1>
            m.combine(a1, a2)
        }

    override val nil: Par<A>
        get() = unit(m.nil) // <2>
}

fun <A, B> parFoldMap(
    la: List<A>,
    pm: Monoid<Par<B>>,
    f: (A) -> B
): Par<B> =
    when {
        la.size >= 2 -> {
            val (la1, la2) = la.splitAt(la.size / 2)
            pm.combine(parFoldMap(la1, pm, f), parFoldMap(la2, pm, f))
        }
        la.size == 1 ->
            unit(f(la.first()))
        else -> pm.nil
    }
//end::init1[]

class Exercise8 : WordSpec() {

    val es = Executors.newFixedThreadPool(4)

    val result = AtomicReference("not updated")

    override fun afterTest(testCase: TestCase, result: TestResult) =
        es.shutdown()

    init {
        "balanced folding parForMap" should {
            "fold a list in parallel" {
                //tag::init2[]
                parFoldMap(
                    listOf("lorem", "ipsum", "dolor", "sit"),
                    par(stringMonoid), // <3>
                    { it.toUpperCase() }
                )(es).invoke { cb -> result.set(cb) } // <4>
                //end::init2[]

                await().until {
                    result.get() == "LOREMIPSUMDOLORSIT"
                }
            }
        }
    }
}
