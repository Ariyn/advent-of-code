package day2part1

import readInputLines

fun opponentEncoder(op: String): RpsType = when (op) {
    "A" -> Rock()
    "B" -> Paper()
    "C" -> Scissor()
    else -> throw InvalidRpsType
}

fun mineEncoder(rock: String, paper: String, scissor: String): (mine: String) -> RpsType = {
    when (it) {
        rock -> Rock()
        paper -> Paper()
        scissor -> Scissor()
        else -> throw InvalidRpsType
    }
}

fun main(args: Array<String>) {
    val strategy = mineEncoder("X", "Y", "Z")
    val lines = readInputLines(args)

    val myScore = lines
        .map { l -> l.split(" ").toTypedArray() }
        .map { (a, b) -> Pair(opponentEncoder(a), strategy(b)) }
        .map { (oppo, me) -> me.typeScore() + me.result(oppo) }
        .sum()

    println("${myScore}")
}
