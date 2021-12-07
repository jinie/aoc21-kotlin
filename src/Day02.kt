import kotlin.math.abs

fun main() {
    data class Instruction(val key: String, val inc: Int)
    data class SubState(var pos: Int =0, var depth: Int =0, var depth2: Int = 0)

    fun part1(instructions: List<Instruction>): List<Int> {
        var ss = SubState()
        for (p in instructions.iterator()){
            when(p.key){
                "forward" -> {
                    ss.pos += p.inc
                    ss.depth2 += p.inc * ss.depth //Part 2
                }
                "down" -> {
                    ss.depth -= p.inc
                }
                "up" -> {
                    ss.depth += p.inc
                }
                else -> throw IllegalStateException("$p.key is not valid direction")
            }
        }
        return listOf( abs(ss.pos * ss.depth), abs(ss.pos*ss.depth2))
    }

    val timeTaken = measureTimeMillis {
        val input = readInput("Day02").map {
            val (key, inc) = it.trim().split(" ")
            Instruction(key, inc.toInt())
        }
        println(part1(input))
    }
    println("Time Taken $timeTaken ms")
}
