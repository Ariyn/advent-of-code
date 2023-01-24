package day3part2

import readInputLines

val priority = (('a'..'z') + ('A'..'Z')).toCharArray()

fun main(args: Array<String>) {
    val rucksack = readInputLines(args)
        .map { l -> l.toSet() }
        .foldIndexed(listOf<Set<Char>>()) { index, acc, set ->
            if (index % 3 == 0) {
                acc + listOf(set)
            } else {
                acc.slice(0 until acc.lastIndex) + listOf(acc.last().intersect(set))
            }
        }
        .sumOf { set -> priority.indexOf(set.first()) + 1 }
    
    println(rucksack)
}