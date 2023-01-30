package day7part1

import readInputLines

const val maximumSizeOfDirectory = 100000
val exampleLines = ("\$ cd /\n" +
        "\$ ls\n" +
        "dir a\n" +
        "14848514 b.txt\n" +
        "8504156 c.dat\n" +
        "dir d\n" +
        "\$ cd a\n" +
        "\$ ls\n" +
        "dir e\n" +
        "29116 f\n" +
        "2557 g\n" +
        "62596 h.lst\n" +
        "\$ cd e\n" +
        "\$ ls\n" +
        "584 i\n" +
        "\$ cd ..\n" +
        "\$ cd e\n" +
        "\$ ls\n" +
        "584 i\n" +
        "\$ cd ..\n" +
        "\$ cd ..\n" +
        "\$ cd d\n" +
        "\$ ls\n" +
        "4060174 j\n" +
        "8033020 d.log\n" +
        "5626152 d.ext\n" +
        "7214296 k").split("\n")

class Directory(name: String) : File(name, 0) {
    constructor(name: String, parent: Directory) : this(name) {
        this.parent = parent
    }

    val files: MutableList<File> = mutableListOf()

    fun size(): Int = files.sumOf {
        if (it is Directory) it.size() else it.size
    }

    override fun toString(): String = "${this.name} (dir)"

    fun printDirectoryRecursive(indent: Int = 0) {
        val prefix = " ".repeat(indent)

        println("${prefix}-${this}")

        this.files.forEach { it ->
            if (it is Directory) {
                it.printDirectoryRecursive(indent + 1)
            } else {
                println("${" ".repeat(indent + 1)}-${it}")
            }
        }
    }

    fun contains(name: String): Boolean =
            this.files.map { it.name }.contains(name)

    fun contains(f: File): Boolean =
            this.files.contains(f)

    fun getDirectory(name: String): Directory? =
            if (contains(name)) {
                val sameNameFile = files.first { it.name == name }

                if (sameNameFile is Directory) sameNameFile else null
            } else {
                null
            }
}

open class File(
        val name: String,
        val size: Int,
) {
    var parent: File? = null

    constructor(name: String, size: Int, parent: File) : this(name, size) {
        this.parent = parent
    }

//    companion object {
//        fun parse(l: String, parent: File): File {
//            val tokens = l.split(" ")
//            return File(tokens[1], tokens[0].toInt(), parent)
//        }
//    }

    override fun toString(): String {
        return "$name (file, size=${size})"
    }
}

data class Command(
        val command: String,
        val target: String,
) {
    companion object {
        fun parse(l: String): Command? {
            if (!l.startsWith("$")) {
                return null
            }

            val tokens = l.split(" ")
            if (tokens[1] == "cd") {
                return Command(tokens[1], tokens[2])
            }

            return Command(tokens[1], "")
        }
    }
}

data class ListResult(
        var type: String,
        var size: Int,
        var name: String,
) {
    companion object {
        fun parse(l: String): ListResult {
            val tokens = l.split(" ")
            if (tokens[0] == "dir") {
                return ListResult("Directory", 0, tokens[1])
            }

            return ListResult("File", tokens[0].toInt(), tokens[1])
        }
    }
}

class Parser {
    val rootDirectory = Directory("/")
    private var isListingFiles = false
    private var currentDirectory = rootDirectory
    val entireDirectories = mutableListOf<Directory>()
    private var lines = listOf<String>()

    constructor(lines: List<String>) {
        rootDirectory.parent = rootDirectory
        this.lines = lines
    }

    fun parse() {
        for (l in lines) {
            parseLine(l)
        }
    }

    fun parseLine(l: String) {
        val cmd = Command.parse(l)
        cmd?.let {
            isListingFiles = false

            when (cmd.command to cmd.target) {
                "cd" to "/" -> currentDirectory = rootDirectory
                "cd" to ".." -> currentDirectory = currentDirectory.parent as Directory // TODO: null pointer error??
                "ls" to "" -> isListingFiles = true
                else -> {
                    currentDirectory =
                            if (currentDirectory.contains(cmd.target)) {
                                currentDirectory.getDirectory(cmd.target)!!
                            } else {
                                val nextDirectory = Directory(cmd.target, currentDirectory)

                                entireDirectories.add(nextDirectory)
                                currentDirectory.files.add(nextDirectory)

                                nextDirectory
                            }
                }
            }
        }

        cmd ?: let {
            if (isListingFiles) {
                val lr = ListResult.parse(l)
                if (!currentDirectory.contains(lr.name)) {
                    if (lr.type == "File") {
                        val f = File(lr.name, lr.size)
                        currentDirectory.files.add(f)
                    } else {
                        val d = Directory(lr.name, currentDirectory)
                        currentDirectory.files.add(d)
                        entireDirectories.add(d)
                    }
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    val lines = readInputLines(args)
//    val lines = exampleLines

    val parser = Parser(lines)
    parser.parse()

    parser.rootDirectory.printDirectoryRecursive()

    val directorySizes =
            parser.entireDirectories.map { it.size() }
    val filteredDirectories = directorySizes.filter { size -> size <= maximumSizeOfDirectory }
    val sum = filteredDirectories.sum()

    println(sum)
}