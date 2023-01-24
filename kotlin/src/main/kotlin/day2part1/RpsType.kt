package day2part1

val InvalidRpsType = Exception("rps type should one of rock, paper, scissor")

abstract class RpsType {
    abstract fun result(other: RpsType): Int
    abstract fun typeScore(): Int
}

class Rock : RpsType() {
    override fun result(other: RpsType): Int {
        return when (other::class) {
            Rock::class -> 1
            Paper::class -> 0
            Scissor::class -> 2
            else -> throw InvalidRpsType
        } * 3
    }

    override fun typeScore(): Int = 1
}

class Paper : RpsType() {
    override fun result(other: RpsType): Int {
        return when (other::class) {
            Rock::class -> 2
            Paper::class -> 1
            Scissor::class -> 0
            else -> throw InvalidRpsType
        } * 3
    }

    override fun typeScore(): Int = 2
}

class Scissor : RpsType() {
    override fun result(other: RpsType): Int {
        return when (other::class) {
            Rock::class -> 0
            Paper::class -> 2
            Scissor::class -> 1
            else -> throw InvalidRpsType
        } * 3
    }

    override fun typeScore(): Int = 3
}