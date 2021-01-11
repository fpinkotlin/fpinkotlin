package chapter12.sec4

import arrow.Kind
import arrow.Kind2
import arrow.higherkind
import chapter12.Either
import chapter12.EitherApplicative
import chapter12.EitherMonad
import chapter12.sec3.Applicative
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date

@higherkind
sealed class Stream<out A> : StreamOf<A> {

    companion object {

        fun <A> cons(hd: () -> A, tl: () -> Stream<A>): Stream<A> {
            val head: A by lazy { hd() }
            val tail: Stream<A> by lazy { tl() }
            return Cons({ head }, { tail })
        }

        fun <A> continually(a: A): Stream<A> =
            cons({ a }, { continually(a) })

        fun <A> empty(): Stream<A> = Empty
    }

    fun <B> zip(sb: Stream<B>): Stream<Pair<A, B>> = TODO()

    fun <B> map(f: (A) -> B): Stream<B> = TODO()
}

data class Cons<out A>(
    val head: () -> A,
    val tail: () -> Stream<A>
) : Stream<A>()

object Empty : Stream<Nothing>()

//tag::init1[]
val streamApplicative = object : Applicative<ForStream> {

    override fun <A> unit(a: A): StreamOf<A> =
        Stream.continually(a) // <1>

    override fun <A, B, C> map2(
        sa: StreamOf<A>,
        sb: StreamOf<B>,
        f: (A, B) -> C
    ): StreamOf<C> =
        sa.fix().zip(sb.fix()).map { (a, b) -> f(a, b) } // <2>
}
//end::init1[]

fun validName(name: String): Either<String, String> = TODO()
fun validDateOfBirth(dob: Date): Either<String, Date> = TODO()
fun validPhone(phone: String): Either<String, String> = TODO()

//tag::init0[]
data class WebForm(val f1: String, val f2: Date, val f3: String)
//end::init0[]

val name = "Claire"
val dob = Date.from(Instant.now())
val phone = "6060-842"

fun <E> eitherMonad(): EitherMonad<E> = TODO()

val listing1 = {
    //tag::init2[]
    val F = eitherMonad<String>()
    F.flatMap(validName(name)) { f1: String ->
        F.flatMap(validDateOfBirth(dob)) { f2: Date ->
            F.map(validPhone(phone)) { f3: String ->
                WebForm(f1, f2, f3)
            }
        }
    }
    //end::init2[]
}

fun <E> eitherApplicative(): EitherApplicative<E> = TODO()

val listing2 = {
    //tag::init3[]
    val A = eitherApplicative<String>()
    A.map3(
        validName(name),
        validDateOfBirth(dob),
        validPhone(phone)
    ) { f1, f2, f3 ->
        WebForm(f1, f2, f3)
    }
    //end::init3[]
}

sealed class ForValidation private constructor() {
    companion object
}

typealias ValidationOf<E, A> = Kind2<ForValidation, E, A>

typealias ValidationPartialOf<E> = Kind<ForValidation, E>

fun <E, A> ValidationOf<E, A>.fix() = this as Validation<E, A>

//tag::init4[]
sealed class Validation<out E, out A> : ValidationOf<E, A>

data class Failure<E>(
    val head: E,
    val tail: List<E> = emptyList()
) : Validation<E, Nothing>()

data class Success<A>(val a: A) : Validation<Nothing, A>()
//end::init4[]

interface ValidationApplicative<E> : Applicative<ValidationPartialOf<E>>

fun <E> validationApplicative() = object : ValidationApplicative<E> {

    override fun <A, B> apply(
        fab: ValidationOf<E, (A) -> B>,
        fa: ValidationOf<E, A>
    ): ValidationOf<E, B> =
        map2(fab, fa) { f, a -> f(a) }

    override fun <A> unit(a: A): ValidationOf<E, A> = Success(a)

    override fun <A, B, C> map2(
        fa: ValidationOf<E, A>,
        fb: ValidationOf<E, B>,
        f: (A, B) -> C
    ): ValidationOf<E, C> {
        val va = fa.fix()
        val vb = fb.fix()
        return when (va) {
            is Success -> when (vb) {
                is Success -> Success(f(va.a, vb.a))
                is Failure -> vb
            }
            is Failure -> when (vb) {
                is Success -> va
                is Failure -> Failure(va.head, va.tail + vb.head + vb.tail)
            }
        }
    }
}

val listing3 = {
    //tag::init5[]
    fun validName(name: String): Validation<String, String> =
        if (name != "") Success(name)
        else Failure("Name cannot be empty")

    fun validDateOfBirth(dob: String): Validation<String, Date> =
        try {
            Success(SimpleDateFormat("yyyy-MM-dd").parse(dob))
        } catch (e: Exception) {
            Failure("Date of birth must be in format yyyy-MM-dd")
        }

    fun validPhone(phone: String): Validation<String, String> =
        if (phone.matches("[0-9]{10}".toRegex())) Success(phone)
        else Failure("Phone number must be 10 digits")
    //end::init5[]

    //tag::init6[]
    val F = validationApplicative<String>()

    fun validatedWebForm(
        name: String,
        dob: String,
        phone: String
    ): Validation<String, WebForm> {
        val result = F.map3(
            validName(name),
            validDateOfBirth(dob),
            validPhone(phone)
        ) { n, d, p -> WebForm(n, d, p) }
        return result.fix()
    }
    //end::init6[]
}
