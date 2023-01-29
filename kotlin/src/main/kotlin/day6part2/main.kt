package day6part2

import readInputLines

val exampleLines = ("mjqjpqmgbljsphdztnvjfqwrcgsmlb\n" +
        "bvwbjplbgvbhsrlpgdmjqwftvncz\n" +
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
        lines.map { it -> splitStringIntoSize(it, 14) }

    val messageMarker = mappedMarkers.map { markers ->
        markers
            .map(::getCharacterSize)
            .indexOfFirst { it == 14 } + 14
    }
    println(messageMarker)
}
//nppdvjthql dpwncqszvf tbrmjlhg
//nppdvjthqldpwncqszvftbr