package chapter4

object Listing_4_1 {
    fun failingFn(i: Int): Int {
        val y: Int = (throw Exception("boom")) as Int
        return try {
            val x = 42 + 5
            x + y
        } catch (e: Exception) {
            43
        }
    }

    fun failingFn2(i: Int): Int =
            try {
                val x = 42 + 5
                x + (throw Exception("boom")) as Int
            } catch (e: Exception) {
                43
            }
}
