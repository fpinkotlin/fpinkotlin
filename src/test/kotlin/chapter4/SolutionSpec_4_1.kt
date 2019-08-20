package chapter4

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun <A, B> Option<A>.map(f: (A) -> B): Option<B> =
        when (this) {
            is None -> None
            is Some -> Some(f(this.get))
        }

fun <A> Option<A>.getOrElse(default: () -> A): A =
        when (this) {
            is None -> default()
            is Some -> this.get
        }

fun <A, B> Option<A>.flatMap(f: (A) -> Option<B>): Option<B> =
        this.map(f).getOrElse { None }

fun <A, B> Option<A>.flatMap_2(f: (A) -> Option<B>): Option<B> =
        when (this) {
            is None -> None
            is Some -> f(this.get)
        }

fun <A> Option<A>.orElse(ob: () -> Option<A>): Option<A> =
        this.map { Some(it) }.getOrElse { ob() }

fun <A> Option<A>.orElse_2(ob: () -> Option<A>): Option<A> =
        when (this) {
            is None -> ob()
            is Some -> this
        }

fun <A> Option<A>.filter(f: (A) -> Boolean): Option<A> =
        this.flatMap { a -> if (f(a)) Some(a) else None }

fun <A> Option<A>.filter_2(f: (A) -> Boolean): Option<A> =
        when (this) {
            is None -> None
            is Some ->
                if (f(this.get)) this
                else None
        }

class SolutionSpec_4_1 : WordSpec({

    val none = Option.empty<Int>()

    "Option.map" should {
        "apply the given function to the content of an option of some value" {
            Some(10).map { it * 2 } shouldBe Some(20)
        }
        "pass over an option of none" {
            none.map { it * 10 } shouldBe None
        }
    }

    "Option.flatMap" should {
        "apply the given function to the content of an option of some value" {
            Some(10).flatMap { a -> Some(a.toString()) } shouldBe Some("10")
            Some(10).flatMap_2 { a -> Some(a.toString()) } shouldBe Some("10")
        }
        "pass over an option of none" {
            none.flatMap { a -> Some(a.toString()) } shouldBe None
            none.flatMap_2 { a -> Some(a.toString()) } shouldBe None
        }
    }

    "Option.getOrElse" should {
        "extract the value of an option of some value" {
            Some(10).getOrElse { 0 } shouldBe 10
        }
        "return a default if the option is none" {
            none.getOrElse { 10 } shouldBe 10
        }
    }

    "Option.orElse" should {
        "return the option if the option is some" {
            Some(10).orElse { Some(20) } shouldBe Some(10)
            Some(10).orElse_2 { Some(20) } shouldBe Some(10)

        }
        "return a default option if the option is none" {
            none.orElse { Some(20) } shouldBe Some(20)
            none.orElse_2 { Some(20) } shouldBe Some(20)
        }
    }

    "Option.filter" should {
        "return some option if the predicate is met" {
            Some(10).filter { it > 0 } shouldBe Some(10)
            Some(10).filter_2 { it > 0 } shouldBe Some(10)
        }
        "return a none option if the predicate is not met" {
            Some(10).filter { it < 0 } shouldBe None
            Some(10).filter_2 { it < 0 } shouldBe None
        }
    }
})