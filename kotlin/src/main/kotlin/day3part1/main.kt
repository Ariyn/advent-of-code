package day3part1

import readInputLines

val priority = (('a'..'z') + ('A'..'Z')).toCharArray()

typealias Ranger = (Int) -> IntRange

fun firstHalfRanger(length: Int): IntRange = 0 until length / 2
fun lastHalfRanger(length: Int): IntRange = length / 2 until length

fun sliceHalf(target: String, ranger: Ranger): String {
    val range = ranger(target.length)
    return target.slice(range)
}

fun main(args: Array<String>) {
    val rucksack = readInputLines(args)
        .map { l ->
            Pair(
                sliceHalf(l, ::firstHalfRanger).toSet(),
                sliceHalf(l, ::lastHalfRanger).toSet(),
            )
        }.map { (a, b) ->
            a.intersect(b).first()
        }.fold(mapOf<Char, Int>()) { acc, c ->
            acc + Pair(c, acc.getOrDefault(c, 0) + 1)
        }.asSequence()
        .sumOf { (k, count) ->
            count * (priority.indexOf(k) + 1)
        }

    println(rucksack)
}