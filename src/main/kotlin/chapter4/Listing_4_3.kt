package chapter4

import chapter3.List

object Listing_4_3 {

    private fun <A, B> Option<A>.map(f: (A) -> B): Option<B> = TODO()

    private fun <A> Option<A>.filter(f: (A) -> Boolean): Option<A> = TODO()

    private fun <A> Option<A>.getOrElse(default: () -> A): A = TODO()

    data class Employee(val name: String, val department: String)

    fun lookupByName(name: String): Option<Employee> = TODO()

    fun joeDepartment(): Option<String> = lookupByName("Joe").map { it.department }

    val dept: String = lookupByName("Joe")
            .map { it.department }
            .filter { it != "Accounting" }
            .getOrElse { "Default department" }
}