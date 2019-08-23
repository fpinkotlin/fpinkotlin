package chapter4

sealed class Either<out E, out A> {
    //helper functions
    companion object {

    }
}

data class Left<out E>(val value: E) : Either<E, Nothing> ()
data class Right<out A>(val value: A) : Either<Nothing, A> ()
