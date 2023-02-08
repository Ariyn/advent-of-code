package day10part1

import readInputLines

val exampleLines = ("addx 15\n" +
        "addx -11\n" +
        "addx 6\n" +
        "addx -3\n" +
        "addx 5\n" +
        "addx -1\n" +
        "addx -8\n" +
        "addx 13\n" +
        "addx 4\n" +
        "noop\n" +
        "addx -1\n" +
        "addx 5\n" +
        "addx -1\n" +
        "addx 5\n" +
        "addx -1\n" +
        "addx 5\n" +
        "addx -1\n" +
        "addx 5\n" +
        "addx -1\n" +
        "addx -35\n" +
        "addx 1\n" +
        "addx 24\n" +
        "addx -19\n" +
        "addx 1\n" +
        "addx 16\n" +
        "addx -11\n" +
        "noop\n" +
        "noop\n" +
        "addx 21\n" +
        "addx -15\n" +
        "noop\n" +
        "noop\n" +
        "addx -3\n" +
        "addx 9\n" +
        "addx 1\n" +
        "addx -3\n" +
        "addx 8\n" +
        "addx 1\n" +
        "addx 5\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx -36\n" +
        "noop\n" +
        "addx 1\n" +
        "addx 7\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx 2\n" +
        "addx 6\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx 1\n" +
        "noop\n" +
        "noop\n" +
        "addx 7\n" +
        "addx 1\n" +
        "noop\n" +
        "addx -13\n" +
        "addx 13\n" +
        "addx 7\n" +
        "noop\n" +
        "addx 1\n" +
        "addx -33\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx 2\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx 8\n" +
        "noop\n" +
        "addx -1\n" +
        "addx 2\n" +
        "addx 1\n" +
        "noop\n" +
        "addx 17\n" +
        "addx -9\n" +
        "addx 1\n" +
        "addx 1\n" +
        "addx -3\n" +
        "addx 11\n" +
        "noop\n" +
        "noop\n" +
        "addx 1\n" +
        "noop\n" +
        "addx 1\n" +
        "noop\n" +
        "noop\n" +
        "addx -13\n" +
        "addx -19\n" +
        "addx 1\n" +
        "addx 3\n" +
        "addx 26\n" +
        "addx -30\n" +
        "addx 12\n" +
        "addx -1\n" +
        "addx 3\n" +
        "addx 1\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx -9\n" +
        "addx 18\n" +
        "addx 1\n" +
        "addx 2\n" +
        "noop\n" +
        "noop\n" +
        "addx 9\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx -1\n" +
        "addx 2\n" +
        "addx -37\n" +
        "addx 1\n" +
        "addx 3\n" +
        "noop\n" +
        "addx 15\n" +
        "addx -21\n" +
        "addx 22\n" +
        "addx -6\n" +
        "addx 1\n" +
        "noop\n" +
        "addx 2\n" +
        "addx 1\n" +
        "noop\n" +
        "addx -10\n" +
        "noop\n" +
        "noop\n" +
        "addx 20\n" +
        "addx 1\n" +
        "addx 2\n" +
        "addx 2\n" +
        "addx -6\n" +
        "addx -11\n" +
        "noop\n" +
        "noop\n" +
        "noop").split("\n")

abstract class Command {
    abstract fun beforeCycleStart(clock: Int): Boolean
    abstract fun beforeCycleDone(clock: Int): Boolean
    abstract fun doAction(registers: MutableMap<String, Int>)
    abstract fun clocksToComplete(): Int
}

class AddX(
    val value: Int,
) : Command() {
    var startClock: Int = 0
    private val completeClock = 2
    private val register = "X"

    companion object {
        fun parse(line: String): AddX {
            val tokens = line.split(" ")
            val value = tokens[1].toInt()

            return AddX(value)
        }
    }

    override fun beforeCycleStart(clock: Int): Boolean {
        return false
    }

    override fun beforeCycleDone(clock: Int): Boolean {
        return startClock + completeClock == clock + 1
    }

    override fun doAction(registers: MutableMap<String, Int>) {
        registers[register] = registers[register]!! + value
    }

    override fun clocksToComplete(): Int {
        return completeClock
    }
}

class Noop : Command() {
    override fun beforeCycleStart(clock: Int): Boolean {
        return true
    }

    override fun beforeCycleDone(clock: Int): Boolean {
        return true
    }

    override fun doAction(registers: MutableMap<String, Int>) {

    }

    override fun clocksToComplete(): Int = 1
}

class CPU {
    var lines = listOf<String>()
    private var registers = mutableMapOf("X" to 1)
    private var clock = 0
    private var command: Command? = null

    var signalSum: Int = 0
    fun run() {
        var i = 0
        while (i < lines.size) {
            clock += 1

            if (command == null) {
                command = when {
                    lines[i].startsWith("addx") -> {
                        val addX = AddX.parse(lines[i])
                        addX.startClock = clock
                        addX
                    }

                    lines[i] == "noop" -> Noop()
                    else -> throw Exception()
                }
            }

            if ((clock - 20) % 40 == 0)
                signalSum += registers["X"]!! * clock

            if (command!!.beforeCycleDone(clock)) {
                command!!.doAction(registers)
                command = null
                i += 1
            }
        }
    }
}

fun main(args: Array<String>) {
    var lines = readInputLines(args)
//    lines = exampleLines
    println(lines)

    val cpu = CPU()
    cpu.lines = lines

    cpu.run()
    println(cpu.signalSum)
}
