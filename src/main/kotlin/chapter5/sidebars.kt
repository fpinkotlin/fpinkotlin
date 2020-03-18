package chapter5

object Listings {

    fun expensiveOp(): Int = TODO()

    //tag::lazy[]
    val x: Int by lazy { expensiveOp() } // <1>

    fun useit() =
        if (x > 10) "hi" // <2>
        else if (x == 0) "zero" // <3>
        else ("lo")
    //end::lazy[]
}
