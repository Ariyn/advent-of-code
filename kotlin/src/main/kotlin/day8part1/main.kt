package day8part1

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

    fun leftTrees(location: Pair<Int, Int>): List<Tree> =
            (location.first - 1 downTo 0)
                    .map { trees[Pair(it, location.second)]!! }
                    .toList()

    fun rightTrees(location: Pair<Int, Int>): List<Tree> =
            (location.first + 1 until width)
                    .map { trees[Pair(it, location.second)]!! }
                    .toList()

    fun topTrees(location: Pair<Int, Int>): List<Tree> =
            (location.second - 1 downTo 0)
                    .map { trees[Pair(location.first, it)]!! }
                    .toList()

    fun bottomTrees(location: Pair<Int, Int>): List<Tree> =
            (location.second + 1 until height)
                    .map { trees[Pair(location.first, it)]!! }
                    .toList()

    fun isVisible(tree: Tree): Boolean {
        if (tree.location.first == 0 || tree.location.first == width - 1 || tree.location.second == 0 || tree.location.second == height - 1) {
            return true
        }
        val location = tree.location

        val isLeftVisible = leftTrees(location).map(tree::isVisible).reduce { acc, b -> acc && b }
        val isRightVisible = rightTrees(location).map(tree::isVisible).reduce { acc, b -> acc && b }
        val isTopVisible = topTrees(location).map(tree::isVisible).reduce { acc, b -> acc && b }
        val isBottomVisible = bottomTrees(location).map(tree::isVisible).reduce { acc, b -> acc && b }

        return (isLeftVisible || isRightVisible || isTopVisible || isBottomVisible)
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

    val visibleTrees = trees.trees.map { (loc, t) -> Pair(loc, trees.isVisible(t)) }.toMap()
    println(visibleTrees.count { (_, v) -> v })
}