package chapter4

object Listing_4_8 {

    // boilerplate developed in exercises

    fun <E, A, B> Either<E, A>.flatMap(f: (A) -> Either<E, B>): Either<E, B> =
            when (this) {
                is Left -> this
                is Right -> f(this.value)
            }

    fun <E, A, B> Either<E, A>.map(f: (A) -> B): Either<E, B> =
            when (this) {
                is Left -> this
                is Right -> Right(f(this.value))
            }

    fun <E, A, B, C> map2(ae: Either<E, A>, be: Either<E, B>, f: (A, B) -> C): Either<E, C> =
            ae.flatMap { a -> be.map { b -> f(a, b) } }

    data class Person(val name: Name, val age: Age)
    data class Name(val value: String)
    data class Age(val value: Int)

    fun mkName(name: String): Either<String, Name> =
            if (name.isBlank()) Left("Name is empty.")
            else Right(Name(name))

    fun mkAge(age: Int): Either<String, Age> =
            if (age < 0) Left("Age is out of range.")
            else Right(Age(age))

    fun mkPerson(name: String, age: Int): Either<String, Person> =
            map2(mkName(name), mkAge(age)) { n, a -> Person(n, a) }

}