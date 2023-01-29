package day6part1

import readInputLines

val exampleLines = ("bvwbjplbgvbhsrlpgdmjqwftvncz\n" +
        "nppdvjthqldpwncqszvftbrmjlhg\n" +
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg\n" +
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw").split("\n")

fun splitStringIntoSize(stream: String, size: Int): List<String> {
    val result = mutableListOf<String>()
    for (start in 0 until stream.length - size) {
        result.add(stream.substring(start until start + size))
    }
    return result
}

fun getCharacterSize(s: String): Int {
    return s.toSet().size
}

fun main(args: Array<String>) {
    val lines = readInputLines(args)
//    val lines = exampleLines
    val mappedMarkers =
        lines.map { it -> splitStringIntoSize(it, 4) }.map { markers ->
            markers
                .map(::getCharacterSize)
                .indexOfFirst { it == 4 } + 4
        }
    println(mappedMarkers)
}
