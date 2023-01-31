package day8part1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TreesTest {
    private val emptyList = listOf<Int>()
    private fun treeToXLocations(tree: Tree): Int {
        return tree.location.first
    }

    private fun treeToYLocations(tree: Tree): Int {
        return tree.location.second
    }

    private fun getTrees(width: Int, height: Int): Trees {
        val trees = Trees(width, height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                val loc = Pair(x, y)
                trees[loc] = Tree(loc, 0)
            }
        }

        return trees
    }

    @Test
    fun leftTrees() {
        val trees = getTrees(3, 3)

        assertEquals(emptyList, trees.leftTrees(Pair(0, 0)).map(::treeToXLocations))
        assertEquals(listOf(0), trees.leftTrees(Pair(1, 0)).map(::treeToXLocations))
        assertEquals(listOf(1, 0), trees.leftTrees(Pair(2, 0)).map(::treeToXLocations))
        assertEquals(listOf(2, 1, 0), trees.leftTrees(Pair(3, 0)).map(::treeToXLocations))
    }

    @Test
    fun rightTrees() {
        val trees = getTrees(3, 3)

        assertEquals(emptyList, trees.rightTrees(Pair(2, 0)).map(::treeToXLocations))
        assertEquals(listOf(2), trees.rightTrees(Pair(1, 0)).map(::treeToXLocations))
        assertEquals(listOf(1, 2), trees.rightTrees(Pair(0, 0)).map(::treeToXLocations))
        assertEquals(listOf(0, 1, 2), trees.rightTrees(Pair(-1, 0)).map(::treeToXLocations))
    }

    @Test
    fun topTrees() {
        val trees = getTrees(3, 3)

        assertEquals(emptyList, trees.topTrees(Pair(0, 0)).map(::treeToYLocations))
        assertEquals(listOf(0), trees.topTrees(Pair(0, 1)).map(::treeToYLocations))
        assertEquals(listOf(1, 0), trees.topTrees(Pair(0, 2)).map(::treeToYLocations))
        assertEquals(listOf(2, 1, 0), trees.topTrees(Pair(0, 3)).map(::treeToYLocations))
    }

    @Test
    fun bottomTrees() {
        val trees = getTrees(3, 3)

        assertEquals(emptyList, trees.bottomTrees(Pair(0, 3)).map(::treeToYLocations))
        assertEquals(emptyList, trees.bottomTrees(Pair(0, 2)).map(::treeToYLocations))
        assertEquals(listOf(2), trees.bottomTrees(Pair(0, 1)).map(::treeToYLocations))
        assertEquals(listOf(1, 2), trees.bottomTrees(Pair(0, 0)).map(::treeToYLocations))
        assertEquals(listOf(0, 1, 2), trees.bottomTrees(Pair(0, -1)).map(::treeToYLocations))
    }
}