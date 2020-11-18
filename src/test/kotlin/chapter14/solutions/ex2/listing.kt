package chapter14.solutions.ex2

import chapter14.sec2.ST
import chapter14.sec2.STArray
import chapter14.sec2.STRef
import chapter14.sec2.fx

fun <S> partition1(
    arr: STArray<S, Int>,
    l: Int,
    r: Int,
    pivot: Int
): ST<S, Int> =
    arr.read(pivot).flatMap { vp ->
        arr.swap(pivot, r).flatMap {
            STRef<S, Int>(l).flatMap { j ->
                (l until r).fold(noop<S>()) { st: ST<S, Unit>, i: Int ->
                    st.flatMap {
                        arr.read(i).flatMap { vi ->
                            if (vi < vp) {
                                j.read().flatMap { vj ->
                                    arr.swap(i, vj).flatMap {
                                        j.write(vj + 1)
                                    }
                                }
                            } else noop()
                        }
                    }
                }.flatMap {
                    j.read().flatMap { x ->
                        arr.swap(x, r).map { x }
                    }
                }
            }
        }
    }

//tag::init[]
fun <S> partition(
    arr: STArray<S, Int>,
    l: Int,
    r: Int,
    pivot: Int
): ST<S, Int> =
    ST.fx {
        val vp = !arr.read(pivot)
        !arr.swap(pivot, r)
        val j = !STRef<S, Int>(l)
        !(l until r).fold(noop<S>()) { st, i: Int ->
            !st
            val vi = !arr.read(i)
            if (vi < vp) {
                val vj = !j.read()
                !arr.swap(i, vj)
                j.write(vj + 1)
            } else noop()
        }
        val x = !j.read()
        !arr.swap(x, r)
        x
    }

fun <S> qs(arr: STArray<S, Int>, l: Int, r: Int): ST<S, Unit> =
    if (l < r)
        partition(arr, l, r, l + (r - l) / 2).flatMap { pi ->
            qs(arr, l, pi - 1).flatMap {
                qs(arr, pi + 1, r)
            }
        } else noop()

fun <S> noop() = ST<S, Unit> { Unit }
//end::init[]
