package chapter7.solutions.sol10

import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import kotlinx.collections.immutable.PersistentMap

typealias Par<A> = (ExecutorService) -> Future<A>

//tag::init[]
fun <K, V> choiceMap(
    key: Par<K>,
    choices: PersistentMap<K, Par<V>>
): Par<V> =
    { es: ExecutorService ->
        choices[key(es).get()]!!.invoke(es)
    }
//end::init[]
