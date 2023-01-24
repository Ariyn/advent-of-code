package day5part2

import readInputLines
import java.util.*

val exampleLines = ("    [D]    \n" +
        "[N] [C]    \n" +
        "[Z] [M] [P]\n" +
        " 1   2   3 \n" +
        "\n" +
        "move 1 from 2 to 1\n" +
        "move 3 from 1 to 3\n" +
        "move 2 from 2 to 1\n" +
        "move 1 from 1 to 2").split("\n")

fun splitInputLines(lines: List<String>): Pair<List<String>, List<String>> {
    val stacks = lines.takeWhile { it != "" }
    val commands = lines.takeLastWhile { it != "" }

    return Pair(stacks, commands)
}

private fun <String> List<String>.filterNotEmpty(): List<String> = this.filter { it != "" }

fun parseStacks(lines: List<String>): List<Stack<String>> {
    val stacks = lines.last()
        .split(" ")
        .filterNotEmpty()
        .map { Stack<String>() }

    val reversedValueLines = lines.slice(0 until lines.lastIndex).asReversed()
    for (line in reversedValueLines) {
        for (index in line.indices) { // TODO: (index, s) in line.indices zip line 이거 해보기
            val s = line[index].toString()

            if (index % 4 == 1 && s != " ") {
                stacks[index / 4].push(s)
            }
        }
    }

    return stacks
}

val operationCommandRegex = "move (?<size>\\d+) from (?<fromIndex>\\d+) to (?<toIndex>\\d+)".toRegex()
fun parseCommand(line: String): Command {
    val result = operationCommandRegex.find(line) ?: throw Exception("")

    return Command(
        result.groups["size"]?.value?.toInt() ?: 0,
        result.groups["fromIndex"]?.value?.toInt()?.minus(1) ?: 0,
        result.groups["toIndex"]?.value?.toInt()?.minus(1) ?: 0,
    )
}

data class Command(
    val size: Int,
    val fromIndex: Int,
    val toIndex: Int,
)

fun main(args: Array<String>) {
    val lines = readInputLines(args)

    val (stackLines, commandLines) = splitInputLines(lines)
    val stacks = parseStacks(stackLines)
    val commands = commandLines.map { parseCommand(it) }

    for (index in commands.indices) {
        val cmd = commands[index]

        (0 until cmd.size)
            .map { stacks[cmd.fromIndex].pop() }
            .asReversed()
            .forEach { stacks[cmd.toIndex].push(it) }
    }

    val topElements = stacks.joinToString("") { it.pop() }
    println(topElements)
}
