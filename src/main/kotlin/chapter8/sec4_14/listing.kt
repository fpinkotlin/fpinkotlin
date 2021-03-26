package chapter8.sec4_14

import chapter7.sec4.Par
import chapter7.sec4.map
import chapter7.sec4.unit
import chapter8.Gen
import chapter8.Prop
import chapter8.sec4_13.forAllPar
import chapter8.sec4_9.equal

//tag::init[]
fun checkPar(p: Par<Boolean>): Prop =
    forAllPar(Gen.unit(Unit)) { p }

val p2 = checkPar(
    equal(
        map(unit(1)) { it + 1 },
        unit(2)
    )
)
//end::init[]
