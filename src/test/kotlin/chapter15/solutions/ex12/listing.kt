package chapter15.solutions.ex12

import chapter15.sec3_3.Process

//tag::init[]
fun <F, O> join(p: Process<F, Process<F, O>>): Process<F, O> =
    p.flatMap { it }
//end::init[]
