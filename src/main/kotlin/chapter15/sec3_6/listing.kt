package chapter15.sec3_6

import chapter13.boilerplate.io.ForIO
import chapter13.boilerplate.io.IO
import chapter15.sec3_3.Process
import chapter15.sec3_5.join
import chapter15.sec3_5.zipWith
import java.sql.Connection
import java.sql.PreparedStatement

//tag::init1[]
fun <F, I, O> Process<F, I>.through(
    p2: Process<F, (I) -> Process<F, O>>
): Process<F, O> =
    join(zipWith(this, p2) { o: I, f: (I) -> Process<F, O> -> f(o) })
//end::init1[]

//tag::init2[]
typealias Channel<F, I, O> = Process<F, (I) -> Process<F, O>>
//end::init2[]

//tag::init3[]
fun query(
    conn: IO<Connection>
): Channel<ForIO, (Connection) -> PreparedStatement, Map<String, Any>>
//end::init3[]
    = TODO()
