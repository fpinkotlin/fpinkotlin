package chapter13.sec1

import chapter10.None
import chapter10.Option
import chapter10.Some

val listing1 = {
    //tag::init1[]
    data class Player(val name: String, val score: Int)

    fun contest(p1: Player, p2: Player): Unit =
        when {
            p1.score > p2.score ->
                println("${p1.name} is the winner!")
            p1.score < p2.score ->
                println("${p2.name} is the winner!")
            else ->
                println("It's a draw!")
        }
    //end::init1[]
}

data class Player(val name: String, val score: Int)

val listing2 = {
    //tag::init2[]
    fun winner(p1: Player, p2: Player): Option<Player> = // <1>
        when {
            p1.score > p2.score -> Some(p1)
            p1.score < p2.score -> Some(p2)
            else -> None
        }

    fun contest(p1: Player, p2: Player): Unit = // <2>
        when (val player = winner(p1, p2)) {
            is Some ->
                println("${player.get.name} is the winner!")
            is None ->
                println("It's a draw!")
        }
    //end::init2[]
}

val listing3 = {
    fun winner(p1: Player, p2: Player): Option<Player> = // <1>
        when {
            p1.score > p2.score -> Some(p1)
            p1.score < p2.score -> Some(p2)
            else -> None
        }

    //tag::init3[]

    fun winnerMsg(op: Option<Player>): String = // <1>
        when (op) {
            is Some -> "${op.get.name} is the winner"
            is None -> "It's a draw"
        }

    fun contest(p1: Player, p2: Player): Unit = // <2>
        println(winnerMsg(winner(p1, p2))) // <3>
    //end::init3[]
}
