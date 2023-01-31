package day8part2

import readInputLines

val exampleLines = ("30373\n" +
        "25512\n" +
        "65332\n" +
        "33549\n" +
        "35390").split("\n")

data class Tree(
        val location: Pair<Int, Int>,
        val size: Int,
) {
    override fun toString(): String {
        return "${location}-${size}"
    }

    fun isVisible(other: Tree): Boolean {
        return other.size < size
    }
}

class Trees(
        private val width: Int,
        private val height: Int,
) {

    val trees = mutableMapOf<Pair<Int, Int>, Tree>()

    operator fun set(location: Pair<Int, Int>, value: Tree) {
        trees[location] = value
    }

    private fun leftRanges(location: Pair<Int, Int>): List<Pair<Int, Int>> =
            (location.first - 1 downTo 0).map { Pair(it, location.second) }

    private fun leftMostTreeLocation(location: Pair<Int, Int>) = Pair(0, location.second)

    private fun rightRanges(location: Pair<Int, Int>): List<Pair<Int, Int>> =
            (location.first + 1 until width).map { Pair(it, location.second) }

    private fun rightMostTreeLocation(location: Pair<Int, Int>) = Pair(width + 1, location.second)

    private fun topRanges(location: Pair<Int, Int>): List<Pair<Int, Int>> =
            (location.second - 1 downTo 0).map { Pair(location.first, it) }

    private fun topMostTreeLocation(location: Pair<Int, Int>) = Pair(location.second, 0)

    private fun bottomRanges(location: Pair<Int, Int>): List<Pair<Int, Int>> =
            (location.second + 1 until height).map { Pair(location.first, it) }

    private fun bottomMostTreeLocation(location: Pair<Int, Int>) = Pair(location.first, height - 1)

    fun visibleLength(tree: Tree): Int {
        val location = tree.location

        val leftVisibleLoc = leftRanges(location)
                .firstOrNull { loc -> !tree.isVisible(trees[loc]!!) }
                ?: Pair(0, location.second)
        val leftVisibleLength = kotlin.math.abs(leftVisibleLoc.first - location.first)

        val rightVisibleLoc = rightRanges(location).firstOrNull { loc -> !tree.isVisible(trees[loc]!!) }
                ?: Pair(width - 1, location.second)
        val rightVisibleLocLength = kotlin.math.abs(rightVisibleLoc.first - location.first)

        val topVisibleLoc = topRanges(location).firstOrNull { loc -> !tree.isVisible(trees[loc]!!) }
                ?: Pair(location.first, 0)
        val topVisibleLength = kotlin.math.abs(topVisibleLoc.second - location.second)

        val bottomVisibleLoc = bottomRanges(location).firstOrNull { loc -> !tree.isVisible(trees[loc]!!) }
                ?: Pair(location.first, height - 1)
        val bottomVisibleLength = kotlin.math.abs(bottomVisibleLoc.second - location.second)

        return leftVisibleLength * rightVisibleLocLength * topVisibleLength * bottomVisibleLength
    }
}

fun main(args: Array<String>) {
    var lines = readInputLines(args)
//    lines = exampleLines

    val trees = Trees(lines[0].length, lines.size)
    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, size ->
            val tree = Tree(Pair(x, y), size.toString().toInt())
            trees[tree.location] = tree
        }
    }

    val visibleTreeLength = trees.trees.map { (loc, t) -> Pair(loc, trees.visibleLength(t)) }

    val maxLengthTree = visibleTreeLength.maxBy { (_, l) -> l }
    println(maxLengthTree)

    trees.visibleLength(trees.trees[maxLengthTree.first]!!)
}