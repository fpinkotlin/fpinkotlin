package chapter11

import arrow.higherkind
import java.math.BigDecimal

//tag::init1[]
@higherkind
sealed class Option<out A> : OptionOf<A> {
    companion object // <1>
}

data class Some<out A>(val get: A) : Option<A>()
object None : Option<Nothing>()
//end::init1[]

/*
//tag::init2[]
typealias OptionMonad = Monad<ForOption>
//end::init2[]
 */

//tag::init3[]
val optionMonad = object : OptionMonad {

    override fun <A> unit(a: A): OptionOf<A> =
        if (a == null) None else Some(a)

    override fun <A, B> flatMap(
        fa: OptionOf<A>,
        f: (A) -> OptionOf<B>
    ): OptionOf<B> =
        when (val ffa = fa.fix()) {
            is None -> None
            is Some -> f(ffa.get)
        }
}
//end::init3[]

//tag::init4[]
class CryptoCurrencyWallet(
    private val bitcoinAmount: Option<BigDecimal>,
    private val ethereumAmount: Option<BigDecimal>,
    private val OM: OptionMonad
) {
    val totalBoth: Option<BigDecimal> =
        OM.flatMap(bitcoinAmount) { ba: BigDecimal ->
            OM.map(ethereumAmount) { ea: BigDecimal ->
                ba.plus(ea)
            }
        }.fix()
}
//end::init4[]

//tag::init5[]
interface OptionMonad : Monad<ForOption> {

    override fun <A> unit(a: A): OptionOf<A> =
        if (a == null) None else Some(a)

    override fun <A, B> flatMap(
        fa: OptionOf<A>,
        f: (A) -> OptionOf<B>
    ): OptionOf<B> =
        when (val ffa = fa.fix()) {
            is None -> None
            is Some -> f(ffa.get)
        }
}
//end::init5[]

//tag::init6[]
fun <A> Option.Companion.monad(): OptionMonad = object : OptionMonad {}
//end::init6[]

//tag::init7[]
class ImprovedCryptoCurrencyWallet(
    private val bitcoinAmount: Option<BigDecimal>,
    private val ethereumAmount: Option<BigDecimal>
) {

    private val OM = Option.monad<BigDecimal>()

    val totalBoth: Option<BigDecimal> =
        OM.flatMap(bitcoinAmount) { ba: BigDecimal ->
            OM.map(ethereumAmount) { bp: BigDecimal ->
                ba.plus(bp)
            }
        }.fix()
}
//end::init7[]
