package chapter7.exercises.ex3

import chapter7.sec3.Par
import chapter7.sec3.Pars
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import utils.SOLUTION_HERE
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

data class TimedMap2Future<A, B, C>(
    val pa: Future<A>,
    val pb: Future<B>,
    val f: (A, B) -> C
) : Future<C> {

    override fun isDone(): Boolean = TODO()

    override fun get(): C = TODO()

    override fun get(to: Long, tu: TimeUnit): C =
        SOLUTION_HERE("will be used to get the future result")

    override fun cancel(b: Boolean): Boolean = TODO()

    override fun isCancelled(): Boolean = TODO()
}

fun <A, B, C> map2(
    a: Par<A>,
    b: Par<B>,
    f: (A, B) -> C
): Par<C> =
    SOLUTION_HERE()

//TODO: Enable tests by removing `!` prefix
class Exercise3 : WordSpec({

    val es: ExecutorService =
        ThreadPoolExecutor(
            1, 1, 5, TimeUnit.SECONDS, LinkedBlockingQueue()
        )

    "map2" should {
        "!allow two futures to run within a given timeout" {

            val pa = Pars.fork {
                Thread.sleep(400L)
                Pars.unit(1)
            }
            val pb = Pars.fork {
                Thread.sleep(500L)
                Pars.unit("1")
            }
            val pc: Par<Long> =
                map2(pa, pb) { a: Int, b: String ->
                    a + b.toLong()
                }

            withContext(Dispatchers.IO) {
                pc(es).get(1, TimeUnit.SECONDS) shouldBe 2L
            }
        }

        "!timeout if first future exceeds timeout" {

            val pa = Pars.fork {
                Thread.sleep(1100L)
                Pars.unit(1)
            }
            val pb = Pars.fork {
                Thread.sleep(500L)
                Pars.unit("1")
            }
            val pc: Par<Long> =
                map2(pa, pb) { a: Int, b: String ->
                    a + b.toLong()
                }

            withContext(Dispatchers.IO) {
                shouldThrow<TimeoutException> {
                    pc(es).get(1, TimeUnit.SECONDS)
                }
            }
        }

        "!timeout if second future exceeds timeout" {

            val pa = Pars.fork {
                Thread.sleep(100L)
                Pars.unit(1)
            }
            val pb = Pars.fork {
                Thread.sleep(1000L)
                Pars.unit("1")
            }
            val pc: Par<Long> =
                map2(pa, pb) { a: Int, b: String ->
                    a + b.toLong()
                }

            withContext(Dispatchers.IO) {
                shouldThrow<TimeoutException> {
                    pc(es).get(1, TimeUnit.SECONDS)
                }
            }
        }
    }
})
