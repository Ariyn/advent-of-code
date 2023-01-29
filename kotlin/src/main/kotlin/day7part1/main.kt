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

data class File(
    val name: String,
    val size: Int,
    val parent: File?,
    val isDirectory: Boolean = false,
    val files: MutableList<File> = mutableListOf()
) {
    companion object {
        fun parse(l: String, parent: File): File {
            val tokens = l.split(" ")
            return File(tokens[1], tokens[0].toInt(), parent)
        }
    }

    fun size(): Int {
        var s = this.size
        if (this.isDirectory) {
            s += this.files.sumOf { it.size() }
        }

        return s
    }

    override fun toString(): String {
        val fileType = if (this.isDirectory) "dir" else "file, size=${this.size}"
        return "${this.name} (${fileType})"
    }

    fun printDirectoryRecursive(indent: Int = 0) {
        val prefix = " ".repeat(indent)

        println("${prefix}-${this}")

        this.files.forEach { it.printDirectoryRecursive(indent + 1) }
    }

    fun contains(name: String): Boolean =
        this.files.map { it.name }.contains(name)


    fun getDirectory(name: String): File? =
        if (this.contains(name)) {
            this.files.first { it.name == name }
        } else {
            null
        }
}

fun NewFile(name: String, size: Int, parent: File): File {
    return File(name, size, parent)
}

fun NewDirectory(name: String, parent: File?): File {
    return File(name, 0, isDirectory = true, parent = parent)
}

data class Command(
    val command: String,
    val target: String,
)

fun parseCommand(command: String): Command {
    val tokens = command.split(" ")
    if (tokens[1] == "cd") {
        return Command(tokens[1], tokens[2])
    }

    return Command(tokens[1], "")
}

fun main(args: Array<String>) {
    val lines = readInputLines(args)
    val rootDirectory = NewDirectory("/", null)

    var currentDirectory = rootDirectory
    val entireDirectories = mutableListOf(rootDirectory)
    var isListingFiles = false

    for (l in lines) {
        when {
            l.startsWith("$") -> {
                isListingFiles = false
                val cmd = parseCommand(l)
                when (cmd.command) {
                    "cd" -> {
                        when (cmd.target) {
                            "/" -> {
                                currentDirectory = rootDirectory
                            }

                            ".." -> {
                                currentDirectory = currentDirectory.parent!!
                            }

                            else -> {
                                val name = cmd.target
                                var nextDirectory: File?

                                if (currentDirectory.contains(name)) {
                                    nextDirectory = currentDirectory.getDirectory(name)
                                } else {
                                    nextDirectory = NewDirectory(cmd.target, parent = currentDirectory)
                                    entireDirectories.add(nextDirectory)

                                    currentDirectory.files.add(nextDirectory)
                                }

                                currentDirectory = nextDirectory!!
                            }
                        }
                    }

                    "ls" -> isListingFiles = true
                }
            }

            isListingFiles -> {
                if (l.startsWith("dir")) {
                    val (_, name) = l.split(" ")
                    val nd = NewDirectory(name, currentDirectory)
                    currentDirectory.files.add(nd)
                    entireDirectories.add(nd)
                } else {
                    val f = File.parse(l, currentDirectory)
                    if (!currentDirectory.contains(f.name)) {
                        currentDirectory.files.add(f)
                    }
                }
            }
        }
    }

    rootDirectory.printDirectoryRecursive()

    val directorySizes =
        entireDirectories.map { it.name to it.size() }
    val filteredDirectories = directorySizes.filter { (_, size) -> size <= maximumSizeOfDirectory }
    val sum = filteredDirectories.sumOf { (_, size) -> size }

    println(sum)
}