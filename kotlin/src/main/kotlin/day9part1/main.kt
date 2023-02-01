package day9part1

import readInputLines

val exampleLines = ("R 4\n" +
        "U 4\n" +
        "L 3\n" +
        "D 1\n" +
        "R 4\n" +
        "D 1\n" +
        "L 5\n" +
        "R 2").split("\n")

typealias Coordinate = Pair<Int, Int>

enum class Movement(val vector: Coordinate) {
    L(Pair(-1, 0)),
    U(Pair(0, 1)),
    R(Pair(1, 0)),
    D(Pair(0, -1)),
    LU(Pair(-1, 1)),
    UR(Pair(1, 1)),
    RD(Pair(1, -1)),
    DL(Pair(-1, -1)),
}

open class Head(
        x: Int = 0,
        y: Int = 0,
) {
    var location = Pair(x, y)
    val history = mutableListOf<Coordinate>()

    fun move(movement: Movement): Coordinate {
        history.add(location)

        val x = movement.vector.first + location.first
        val y = movement.vector.second + location.second

        location = Pair(x, y)
        return location
    }
}

class Tail(
        x: Int = 0,
        y: Int = 0
) : Head(x, y) {
    private var lastHeadLocation: Coordinate = Pair(0, 0)
    private var nearLocations = listOf<Coordinate>()

    init {
        nearLocations = getNearLocations(location)
    }

    private fun getNearLocations(current: Coordinate): List<Coordinate> {
        val pairs = (current.first - 1..current.first + 1).map { x ->
            (current.second - 1..current.second + 1).map { y -> Pair(x, y) }
        }

        return pairs.reduce { acc, p -> acc + p }
    }

    private fun isHeadNear(headLoc: Coordinate): Boolean {
        return nearLocations.contains(headLoc)
    }

    fun move(head: Head): Pair<Int, Int> {
        if (isHeadNear(head.location)) {
            lastHeadLocation = head.location
            return location
        }

        history.add(location)
        location = lastHeadLocation
        nearLocations = getNearLocations(location)

        lastHeadLocation = head.location
        return location
    }
}

fun unfoldMovements(lines: List<String>): List<Movement> {
    val commands = mutableListOf<Movement>()

    for (i in lines.indices) {
        val l = lines[i]

        val tokens = l.split(" ")
        val movement = Movement.valueOf(tokens[0])

        for (j in 1..tokens[1].toInt()) {
            commands.add(movement)
        }
    }

    return commands.toList()
}

fun main(args: Array<String>) {
    var lines = readInputLines(args)
//    lines = exampleLines

    val movements = unfoldMovements(lines)

    val head = Head(0, 0)
    val tail = Tail(0, 0)

    for (i in movements.indices) {
        val m = movements[i]
        head.move(m)
        tail.move(head)
    }

    val tailHistory = (tail.history + listOf(tail.location)).toSet()
    println(tailHistory)
    println(tailHistory.size)
}

fun printHeadAndTail(head: Head, tail: Tail, size: Pair<Int, Int>) {
    for (y in size.second - 1 downTo 0) {
        for (x in 0 until size.first) {
            if (head.location == Pair(x, y)) {
                print("H ")
            } else if (tail.location == Pair(x, y)) {
                print("T ")
            } else {
                print(". ")
            }
        }
        println()
    }
    println("${head.location}, ${tail.location}")
}
