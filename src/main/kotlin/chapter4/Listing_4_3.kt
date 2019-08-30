package chapter4

import chapter3.List

object Listing_4_3 {

    fun <A, B> Option<A>.map(f: (A) -> B): Option<B> = TODO()

    fun <A, B> Option<A>.flatMap(f: (A) -> Option<B>): Option<B> = TODO()

    fun <A> Option<A>.getOrElse(default: () -> A): A = TODO()

    fun <A> Option<A>.filter(f: (A) -> Boolean): Option<A> = TODO()

    //tag::init[]
    data class Employee(val name: String, val department: String)

    fun lookupByName(name: String): Option<Employee> = TODO()

    fun joeDepartment(): Option<String> = lookupByName("Joe").map { it.department }
    //end::init[]

    //tag::init2[]
    val dept: String = lookupByName("Joe")
            .map { it.department }
            .filter { it != "Accounting" }
            .getOrElse { "Default department" }
    //end::init2[]
}