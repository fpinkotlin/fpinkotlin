package chapter3.exercises

import chapter3.listings.Tree

object Signatures {

    //tag::exercise_3.1[]
    fun <A> tail(xs: List<A>): List<A> = TODO()
    //end::exercise_3.1[]

    //tag::exercise_3.2[]
    fun <A> setHead(xs: List<A>, x: A): List<A> = TODO()
    //end::exercise_3.2[]

    //tag::exercise_3.3[]
    fun <A> drop(l: List<A>, n: Int): List<A> = TODO()
    //end::exercise_3.3[]

    //tag::exercise_3.4[]
    fun <A> List<A>.dropWhile(f: (A) -> Boolean): List<A> = TODO()
    //end::exercise_3.4[]

    //tag::exercise_3.5[]
    fun <A> init(l: List<A>): List<A> = TODO()
    //end::exercise_3.5[]

    //tag::exercise_3.8[]
    fun <A> length(xs: List<A>): Int = TODO()
    //end::exercise_3.8[]

    //tag::exercise_3.9[]
    fun <A, B> foldLeft(xs: List<A>, z: B, f: (B, A) -> B): B = TODO()
    //end::exercise_3.9[]

    //tag::exercise_3.17[]
    fun <A, B> map(xs: List<A>, f: (A) -> B): List<B> = TODO()
    //end::exercise_3.17[]

    //tag::exercise_3.18[]
    fun <A> filter(xs: List<A>, f: (A) -> Boolean): List<A> = TODO()
    //end::exercise_3.18[]

    //tag::exercise_3.19[]
    fun <A, B> flatMap(xs: List<A>, f: (A) -> List<B>): List<B> = TODO()
    //end::exercise_3.19[]

    //tag::exercise_3.23[]
    fun <A> hasSubsequence(sup: List<A>, sub: List<A>): Boolean = TODO()
    //end::exercise_3.23[]

    //tag::exercise_3.28[]
    fun <A, B> fold(ta: Tree<A>, l: (A) -> B, b: (B, B) -> B): B = TODO()
    //end::exercise_3.28[]
}
