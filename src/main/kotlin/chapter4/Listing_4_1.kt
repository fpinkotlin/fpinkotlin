package chapter4

object Listing_4_1 {

    //tag::init[]
    fun failingFn(i: Int): Int {
        val y: Int = throw Exception("boom") // <1>
        return try {
            val x = 42 + 5
            x + y
        } catch (e: Exception) {
            43 // <2>
        }
    }
    //end::init[]

    //tag::init2[]
    fun failingFn2(i: Int): Int =
        try {
            val x = 42 + 5
            x + (throw Exception("boom!")) as Int // <1>
        } catch (e: Exception) {
            43 // <2>
        }
    //end::init2[]
}
