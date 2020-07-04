package chapter10.sec3

import arrow.core.extensions.list.foldable.foldLeft

val listing = {
    //tag::init1[]
    listOf("lorem", "ipsum", "dolor", "sit")
        .foldLeft("") { a, b -> a + b }
    //end::init1[]

    //tag::init2[]
    listOf("lorem", "ipsum", "dolor", "sit")
        .foldLeft("") { a, b -> a + b }

    listOf("ipsum", "dolor", "sit")
        .foldLeft("lorem") { a, b -> a + b }

    listOf("dolor", "sit")
        .foldLeft("loremipsum") { a, b -> a + b }

    listOf("sit")
        .foldLeft("loremipsumdolor") { a, b -> a + b }

    listOf<String>()
        .foldLeft("loremipsumdolorsit") { a, b -> a + b }

    "loremipsumdolorsit"
    //end::init2[]
}
