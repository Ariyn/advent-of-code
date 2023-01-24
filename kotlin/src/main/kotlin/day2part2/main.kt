package day2part2

import readInputLines

fun opponentEncoder(op: String): RpsType = when (op) {
    "A" -> Rock()
    "B" -> Paper()
    "C" -> Scissor()
    else -> throw InvalidRpsType
}

fun strategyEncoder(opponent: RpsType, type: String): RpsType = when (type) {
    "X" -> opponent.winTo()
    "Y" -> opponent.drawTo()
    "Z" -> opponent.loseTo()
    else -> throw InvalidRpsType
}

fun main(args: Array<String>) {
    val lines = readInputLines(args)

    val score = lines
        .map { l -> l.split(" ") }
        .map { l ->
            Pair(opponentEncoder(l[0]), l[1])
        }.map { (oppo, strategy) ->
            Pair(oppo, strategyEncoder(oppo, strategy))
        }.sumOf { (oppo, mine) ->
            mine.typeScore() + mine.result(oppo)
        }

    println("$score")
}
