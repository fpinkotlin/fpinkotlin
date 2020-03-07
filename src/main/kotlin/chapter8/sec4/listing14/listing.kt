package chapter8.sec4.listing14

import chapter7.sec4.Par
import chapter7.sec4.map
import chapter7.sec4.unit
import chapter8.sec3.listing3.Gen
import chapter8.sec3.listing3.Prop
import chapter8.sec4.listing13.forAllPar
import chapter8.sec4.listing9.equal

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
