package chapter2

object Listing_2_1 {

    //tag::init[]
    object Example { //<1>
        private fun abs(n: Int): Int = //<2>
            if (n < 0) -n //<3>
            else n

        fun formatAbs(x: Int): String { //<4>
            val msg = "The absolute value of %d is %d" //<5>
            return msg.format(x, abs(x)) //<6>
        }
    }

    fun main() = println(Example.formatAbs(-42)) //<7>
    //end::init[]
}
