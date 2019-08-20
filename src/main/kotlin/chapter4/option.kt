package chapter4

sealed class Option<out A> {
    //helper functions
    companion object {
        fun <A> empty(): Option<A> = None
    }
}

data class Some<out A>(val get: A) : Option<A>()
object None : Option<Nothing>()

