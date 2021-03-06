import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<Int>): Int {
        return (input.minOrNull()!!..input.maxOrNull()!!).minOf { pos ->
            input.sumOf {
                (it - pos).absoluteValue
            }
        }
    }

    fun part2(input: List<Int>): Int {
        return (input.minOrNull()!!..input.maxOrNull()!!).minOf { pos ->
            input.sumOf {
                (it - pos).absoluteValue.let { it2 -> it2 * (it2 + 1) / 2 }
            }
        }
    }

    val testInput = "16,1,2,0,4,2,7,1,2,14".split(",").map { it.toInt() }
    check(part1(testInput) == 37)

    measureTimeMillisPrint {
        val input = readInput("Day07").map { it.trim().split(",").map { it.toInt() } }.flatten()
        println("Part 1 : " + part1(input))
        println("Part 2 : " + part2(input))
    }
}
