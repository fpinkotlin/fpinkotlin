package chapter3

sealed class List<out A> {
    companion object {
        fun <A> of(vararg aa: A): List<A> {
            val tail = aa.sliceArray(1 until aa.size)
            return if (aa.isEmpty()) Nil else Cons(aa[0], of(*tail))
        }
        fun <A> empty(): List<A> = Nil
    }
}

object Nil : List<Nothing>() {
    override fun toString(): String = "Nil"
}

data class Cons<out A>(val head: A, val tail: List<A>) : List<A>()
