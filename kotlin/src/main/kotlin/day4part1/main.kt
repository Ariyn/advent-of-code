package day4part1

import readInputLines

fun parseSectionStringToIntRange(sec: String): IntRange {
    val (from, to) = sec.split("-").map { a -> a.toInt() }
    return from..to
}

fun rangeContainsRange(a: IntRange, b: IntRange): Boolean {
    return b.first in a && b.last in a
}

val exampleLines = arrayOf(
    "2-4,6-8",
    "2-3,4-5",
    "5-7,7-9",
    "2-8,3-7",
    "6-6,4-6",
    "2-6,4-8",
)

fun main(args: Array<String>) {
    val containingCount = readInputLines(args)
        .map { l -> l.split(",").toTypedArray() }
        .map { (elf1, elf2) -> Pair(parseSectionStringToIntRange(elf1), parseSectionStringToIntRange(elf2)) }
        .count { (elf1, elf2) ->
            rangeContainsRange(elf1, elf2) || rangeContainsRange(elf2, elf1)
        }

    println(containingCount)

}