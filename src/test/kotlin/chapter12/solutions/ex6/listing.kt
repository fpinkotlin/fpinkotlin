package chapter12.solutions.ex6

import chapter12.sec4.Failure
import chapter12.sec4.Success
import chapter12.sec4.Validation
import chapter12.sec4.ValidationOf
import chapter12.sec4.ValidationPartialOf
import chapter12.sec4.fix
import chapter12.Applicative
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import java.time.Instant
import java.util.Date

//tag::init1[]
fun <E> validation() =
    object : Applicative<ValidationPartialOf<E>> {

        override fun <A, B> apply(
            fab: ValidationOf<E, (A) -> B>,
            fa: ValidationOf<E, A>
        ): ValidationOf<E, B> =
            map2(fab, fa) { f, a -> f(a) }

        override fun <A> unit(a: A): ValidationOf<E, A> =
            Success(a)

        override fun <A, B> map(
            fa: ValidationOf<E, A>,
            f: (A) -> B
        ): ValidationOf<E, B> =
            apply(unit(f), fa)

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
                    is Failure -> Failure(
                        va.head,
                        va.tail + vb.head + vb.tail
                    )
                }
            }
        }
    }
//end::init1[]

class ValidationSpec : WordSpec({
    "validation" should {

        data class WebForm(val f1: String, val f2: Date, val f3: String)

        fun validName(name: String): Validation<String, String> =
            Success(name)

        fun validDateOfBirth(dob: Date): Validation<String, Date> =
            Success(dob)

        fun validPhone(phone: String): Validation<String, String> =
            Success(phone)

        fun <A> invalidInput(input: A) = Failure("invalid: $input")

        val F = validation<String>()

        val name = "Claire"
        val dob = Date.from(Instant.now())
        val phone = "6060-842"

        "pass for valid inputs" {

            val result: ValidationOf<String, WebForm> = F.map3(
                validName(name).fix(),
                validDateOfBirth(dob).fix(),
                validPhone(phone).fix()
            ) { n, d, p ->
                WebForm(n, d, p)
            }

            result shouldBe Success(WebForm(name, dob, phone))
        }

        "fail for single invalid input" {

            val result: ValidationOf<String, WebForm> = F.map3(
                validName(name).fix(),
                invalidInput(dob).fix(),
                validPhone(phone).fix()
            ) { n, d, p ->
                WebForm(n, d, p)
            }

            result shouldBe Failure("invalid: $dob")
        }

        "accumulate failures for multiple invalid input" {

            val result: ValidationOf<String, WebForm> = F.map3(
                invalidInput(name).fix(),
                invalidInput(dob).fix(),
                invalidInput(phone).fix()
            ) { n, d, p ->
                WebForm(n, d, p)
            }

            result shouldBe Failure(
                "invalid: $name",
                listOf("invalid: $dob", "invalid: $phone")
            )
        }
    }
})
