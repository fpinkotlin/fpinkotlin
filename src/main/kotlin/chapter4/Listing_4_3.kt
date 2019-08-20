package chapter4

object Listing_4_3 {
    data class Employee(val name: String, val department: String)

    fun lookupByName(name: String): Option<Employee> = TODO()

    fun joeDepartment(): Option<String> = lookupByName("Joe").map { it.department }

    val dept: String = lookupByName("Joe")
            .map { it.department }
            .filter { it != "Accounting" }
            .getOrElse { "Default department" }
}