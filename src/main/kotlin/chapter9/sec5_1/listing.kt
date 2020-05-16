package chapter9.sec5_1

import chapter9.ParseError
import chapter9.Parser
import chapter9.Parsers

interface Listing : Parsers<ParseError> {

    fun listing0() {
        //tag::init0[]
        val spaces = string(" ").many()

        string("abra") product spaces product string("cadabra")
        //end::init0[]
    }

    override
    //tag::init1[]
    fun <A> tag(msg: String, p: Parser<A>): Parser<A>
    //end::init1[]

    //tag::init2[]
    data class Location(val input: String, val offset: Int = 0) {

        private val slice by lazy { input.slice(0..offset + 1) } // <1>

        val line by lazy { slice.count { it == '\n' } + 1 } // <2>

        val column by lazy {
            when (val n = slice.lastIndexOf('\n')) { // <3>
                -1 -> offset + 1
                else -> offset - n
            }
        }
    }

    fun errorLocation(e: ParseError): Location

    fun errorMessage(e: ParseError): String
    //end::init2[]
}
