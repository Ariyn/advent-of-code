package day9part2

import readInputLines
import kotlin.math.abs

val exampleLines = ("R 5\n" +
        "U 8\n" +
        "L 8\n" +
        "D 3\n" +
        "R 17\n" +
        "D 10\n" +
        "L 25\n" +
        "U 20").split("\n")

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

class Node(
    val id: String = "",
    x: Int = 0,
    y: Int = 0,
    private val parent: Node?,
) {
    private var lastHeadLocation: Coordinate = Pair(0, 0)
    private var nearLocations = listOf<Coordinate>()
    var location = Pair(x, y)
    val history = mutableListOf<Coordinate>()

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

    fun move(movement: Movement): Pair<Int, Int> {
        history.add(location)

        val x = movement.vector.first + location.first
        val y = movement.vector.second + location.second

        location = Pair(x, y)
        return location
    }

    fun move(): Pair<Int, Int> {
        if (parent == null) {
            return location
        }

        if (isHeadNear(parent.location)) {
            lastHeadLocation = parent.location
            return location
        }

        history.add(location)
        location = calculateNextLocation(location, parent.location)
        nearLocations = getNearLocations(location)

        lastHeadLocation = parent.location
        return location
    }

    fun calculateNextLocation(curr: Coordinate, parent: Coordinate): Coordinate {
        val xDelta = if (parent.first > curr.first) 1 else -1
        val yDelta = if (parent.second > curr.second) 1 else -1

        if (curr.first == parent.first) {
            if (1 < abs(parent.second - curr.second)) {
                return Pair(curr.first, curr.second + yDelta)
            }
            return curr
        } else if (curr.second == parent.second) {
            if (1 < abs(parent.first - curr.first)) {
                return Pair(curr.first + xDelta, curr.second)
            }
            return curr
        }

        if (isHeadNear(parent)) {
            return curr
        }

        return Pair(curr.first + xDelta, curr.second + yDelta)
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

    val head = Node("H", 0, 0, null)
    val nodes = mutableListOf(head)

    for (i in 1..9) {
        nodes.add(Node(i.toString(), 0, 0, nodes[i - 1]))
    }

    for (i in movements.indices) {
        val m = movements[i]
        head.move(m)
        for (n in nodes) {
            n.move()
        }
    }

    val tail = nodes.last()
    val tailHistory = (tail.history + listOf(tail.location)).toSet()
    println(tailHistory)
    println(tailHistory.size)
}

fun printHeadAndTail(nodes: List<Node>, size: Pair<Int, Int>) {
    for (y in size.second - 1 downTo 0) {
        for (x in 0 until size.first) {
            val currentNode = nodes.filter { n -> n.location == Pair(x, y) }.firstOrNull()
            if (currentNode != null) {
                print("${currentNode.id} ")
            } else {
                print(". ")
            }
//            if (head.location == Pair(x, y)) {
//                print("H ")
//            } else if (tail.location == Pair(x, y)) {
//                print("T ")
//            } else {
//                print(". ")
//            }
        }
        println()
    }
    println()
//    println("${head.location}, ${tail.location}")
}
