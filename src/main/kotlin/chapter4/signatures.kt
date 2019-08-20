package chapter4

fun <A, B> Option<A>.map(f: (A) -> B): Option<B> = TODO()
fun <A, B> Option<A>.flatMap(f: (A) -> Option<B>): Option<B> = TODO()
fun <A> Option<A>.getOrElse(default: () -> A): A = TODO()
fun <A> Option<A>.orElse(ob: () -> Option<A>): Option<A> = TODO()
fun <A> Option<A>.filter(f: (A) -> Boolean): Option<A> = TODO()

fun variance(xs: List<Double>): Option<Double> = TODO()

fun <A, B, C> map2(a: Option<A>, b: Option<B>, f: (A, B) -> C): Option<C> = TODO()

fun <A> sequence(a: List<Option<A>>): Option<List<A>> = TODO()

