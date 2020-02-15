package chapter8.solutions.ex3

open class Prop {
    open fun check(): Boolean = TODO()
    fun and(p: Prop): Prop {
        val checked = this.check() && p.check()
        return object : Prop() {
            override fun check() = checked
        }
    }
}