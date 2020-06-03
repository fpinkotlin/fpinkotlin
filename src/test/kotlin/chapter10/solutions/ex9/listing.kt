package chapter10.solutions.ex9

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.getOrElse
import chapter10.Monoid
import chapter10.foldMap
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import kotlin.math.max
import kotlin.math.min

//tag::init1[]
typealias TrackingState = Triple<Int, Int, Boolean>

val m = object : Monoid<Option<TrackingState>> {
    override fun combine(
        a1: Option<TrackingState>,
        a2: Option<TrackingState>
    ): Option<TrackingState> =
        when (a1) {
            is None -> a2
            is Some ->
                when (a2) {
                    is None -> a1
                    is Some -> Some(
                        Triple(
                            min(a1.t.first, a2.t.first),
                            max(a1.t.second, a2.t.second),
                            a1.t.third &&
                                a2.t.third &&
                                a1.t.second <= a2.t.first
                        )
                    )
                }
        }

    override val nil: Option<TrackingState> = None
}

fun ordered(ints: Sequence<Int>): Boolean =
    foldMap(ints, m) { i: Int -> Some(TrackingState(i, i, true)) }
        .map { it.third }
        .getOrElse { true }
//end::init1[]

class Exercise9 : WordSpec({
    "ordered using balanced fold" should {
        "verify ordering ordered list" {
            ordered(sequenceOf(1, 2, 3, 4, 5, 6, 7, 8, 9)) shouldBe true
        }

        "fail verification of unordered list" {
            ordered(sequenceOf(3, 2, 5, 6, 1, 4, 7, 9, 8)) shouldBe false
        }
    }
})
