package chapter15

import chapter10.None
import chapter10.Option
import chapter10.Some
import chapter15.sec2.Await
import chapter15.sec2.Emit
import chapter15.sec2.Halt
import chapter15.sec2.Process

fun product(): Process<Double, Double> {
    fun go(acc: Double): Process<Double, Double> =
        Await { i: Option<Double> ->
            when (i) {
                is Some ->
                    if (i.get == 0.0) Emit(0.0)
                    else Emit(i.get * acc, go(i.get * acc))
                is None -> Halt<Double, Double>()
            }
        }
    return go(1.0)
}
