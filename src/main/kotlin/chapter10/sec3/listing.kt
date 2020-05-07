package chapter10.sec3

import arrow.core.extensions.list.foldable.foldLeft

val listing = {
    //tag::init1[]
    listOf("lorem", "impsum", "dolor", "sit")
        .foldLeft("") { a, b -> a + b }
    //end::init1[]

    //tag::init2[]
    listOf("lorem", "impsum", "dolor", "sit")
        .foldLeft("") { a, b -> a + b }

    listOf("impsum", "dolor", "sit")
        .foldLeft("lorem") { a, b -> a + b }

    listOf("dolor", "sit")
        .foldLeft("loremimpsum") { a, b -> a + b }

    listOf("sit")
        .foldLeft("loremimpsumdolor") { a, b -> a + b }

    listOf<String>()
        .foldLeft("loremimpsumdolorsit") { a, b -> a + b }

    "loremimpsumdolorsit"
    //end::init2[]
}