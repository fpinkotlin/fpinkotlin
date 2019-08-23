package chapter4.exercises

import chapter4.None
import chapter4.Option
import chapter4.Some
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun <A, B> Option<A>.map(f: (A) -> B): Option<B> = TODO()

fun <A> Option<A>.getOrElse(default: () -> A): A = TODO()

fun <A, B> Option<A>.flatMap(f: (A) -> Option<B>): Option<B> = TODO()

fun <A, B> Option<A>.flatMap_2(f: (A) -> Option<B>): Option<B> = TODO()

fun <A> Option<A>.orElse(ob: () -> Option<A>): Option<A> = TODO()

fun <A> Option<A>.orElse_2(ob: () -> Option<A>): Option<A> = TODO()

fun <A> Option<A>.filter(f: (A) -> Boolean): Option<A> = TODO()

fun <A> Option<A>.filter_2(f: (A) -> Boolean): Option<A> = TODO()

class Exercise_4_1 : WordSpec({

    val none = Option.empty<Int>()

    val some = Some(10)

    "Option.map" should {
        "!transform an option of some value" {
            some.map { it * 2 } shouldBe Some(20)
        }
        "!pass over an option of none" {
            none.map { it * 10 } shouldBe None
        }
    }

    "Option.flatMap" should {
        "!apply a function yielding an option to an option of some value" {
            some.flatMap { a -> Some(a.toString()) } shouldBe Some("10")
            some.flatMap_2 { a -> Some(a.toString()) } shouldBe Some("10")
        }
        "!pass over an option of none" {
            none.flatMap { a -> Some(a.toString()) } shouldBe None
            none.flatMap_2 { a -> Some(a.toString()) } shouldBe None
        }
    }

    "Option.getOrElse" should {
        "!extract the value of some option" {
            some.getOrElse { 0 } shouldBe 10
        }
        "!return a default value if the option is none" {
            none.getOrElse { 10 } shouldBe 10
        }
    }

    "Option.orElse" should {
        "!return the option if the option is some" {
            some.orElse { Some(20) } shouldBe some
            some.orElse_2 { Some(20) } shouldBe some
        }
        "!return a default option if the option is none" {
            none.orElse { Some(20) } shouldBe Some(20)
            none.orElse_2 { Some(20) } shouldBe Some(20)
        }
    }

    "Option.filter" should {
        "!return some option if the predicate is met" {
            some.filter { it > 0 } shouldBe some
            some.filter_2 { it > 0 } shouldBe some
        }
        "!return a none option if the predicate is not met" {
            some.filter { it < 0 } shouldBe None
            some.filter_2 { it < 0 } shouldBe None
        }
    }
})
