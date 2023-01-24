package day2part2

val InvalidRpsType = Exception("rps type should one of rock, paper, scissor")

abstract class RpsType {
    fun result(other: RpsType): Int = when (other::class) {
        winTo()::class -> 6
        drawTo()::class -> 3
        loseTo()::class -> 0
        else -> throw InvalidRpsType
    }

    abstract fun typeScore(): Int
    abstract fun winTo(): RpsType
    abstract fun loseTo(): RpsType
    abstract fun drawTo(): RpsType

}

class Rock : RpsType() {
    override fun typeScore(): Int = 1
    override fun winTo(): RpsType = Scissor()
    override fun loseTo(): RpsType = Paper()

    override fun drawTo(): RpsType = Rock()
}

class Paper : RpsType() {

    override fun typeScore(): Int = 2
    override fun winTo(): RpsType = Rock()
    override fun loseTo(): RpsType = Scissor()

    override fun drawTo(): RpsType = Paper()
}

class Scissor : RpsType() {

    override fun typeScore(): Int = 3
    override fun winTo(): RpsType = Paper()
    override fun loseTo(): RpsType = Rock()

    override fun drawTo(): RpsType = Scissor()
}