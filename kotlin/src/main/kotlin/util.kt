import java.io.File

fun readInputLines(args: Array<String>): List<String> {
    if (args.isEmpty() || args.size != 1) {
        throw Exception("")
    }

    return File(args[0]).useLines { it.toList() }
}