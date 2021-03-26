package chapter8.sec6

import chapter7.sec5.Par
import chapter8.Gen

val listing = {
    //tag::init1[]
    fun <A, B> map(a: Par<A>, f: (A) -> B): Par<B> = TODO()
    //end::init1[]

    //tag::init2[]
    fun <A, B> map(a: Gen<A>, f: (A) -> B): Gen<B> = TODO()
    //end::init2[]
}
