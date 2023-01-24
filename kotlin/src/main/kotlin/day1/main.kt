package day1

import readInputLines

// Answer = 69883
fun main(args: Array<String>) {
    val lines = readInputLines(args)

    val maxCalorieWithReduce = answerWithReduce(lines)
    println(maxCalorieWithReduce)

    val maxCalorieWithFold = answerWithFold(lines)
    println(maxCalorieWithFold)

    assert(maxCalorieWithReduce == maxCalorieWithFold)
}

fun answerWithReduce(lines: List<String>): Int {
    return lines
        .reduce { acc, s ->
            if (s != "") {
                "${acc},${s}"
            } else {
                "${acc}|0"
            }
        }
        .split("|")
        .map { x -> x.split(",").map { a -> a.toInt() }.sum() }
        .max()
}

fun answerWithFold(lines: List<String>): Int =
    lines.fold(listOf(0)) { acc, s ->
        if (s == "") {
            listOf(*acc.toTypedArray(), 0)
        } else {
            listOf(*acc.subList(0, acc.lastIndex).toTypedArray(), acc.last() + s.toInt())
        }
    }.max()

