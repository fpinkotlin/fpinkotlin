package chapter14.sec1

//tag::init1[]
fun quicksort(xs: List<Int>): List<Int> =
    if (xs.isEmpty()) xs else {
        val arr = xs.toIntArray()

        fun swap(x: Int, y: Int) { // <1>
            val tmp = arr[x]
            arr[x] = arr[y]
            arr[y] = tmp
        }

        fun partition(n: Int, r: Int, pivot: Int): Int { // <2>
            val pivotVal = arr[pivot]
            swap(pivot, r)
            var j = n
            for (i in n until r) if (arr[i] < pivotVal) {
                swap(i, j)
                j += 1
            }
            swap(j, r)
            return j
        }

        fun qs(n: Int, r: Int): Unit = if (n < r) { // <3>
            val pi = partition(n, r, n + (n - r) / 2)
            qs(n, pi - 1)
            qs(pi + 1, r)
        } else Unit

        qs(0, arr.size - 1)
        arr.toList()
    }
//end::init1[]
