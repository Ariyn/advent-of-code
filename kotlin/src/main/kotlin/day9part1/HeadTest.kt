package day9part1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HeadTest {

    @Test
    fun move() {
        assertEquals(Pair(-1, 0), Head().move(Movement.L))
        assertEquals(Pair(0, -1), Head().move(Movement.U))
        assertEquals(Pair(1, 0), Head().move(Movement.R))
        assertEquals(Pair(0, 1), Head().move(Movement.D))
        assertEquals(Pair(-1, -1), Head().move(Movement.LU))
        assertEquals(Pair(1, -1), Head().move(Movement.UR))
        assertEquals(Pair(1, 1), Head().move(Movement.RD))
        assertEquals(Pair(-1, 1), Head().move(Movement.DL))
    }
}