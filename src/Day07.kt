import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<Int>): Int {
        return(input.minOrNull()!!..input.maxOrNull()!!).minOf { pos ->
            input.sumOf {
                (it-pos).absoluteValue
            }
        }
    }

    fun part2(input: List<Int>): Int {
        return(input.minOrNull()!!..input.maxOrNull()!!).minOf { pos ->
            input.sumOf {
                (it-pos).absoluteValue.let { it * (it+1) / 2 }
            }
        }
    }

    val testInput = "16,1,2,0,4,2,7,1,2,14".split(",").map { it.toInt() }.sorted()
    check(part1(testInput) == 37)

    val input = readInput("Day07")[0].split(",").map { it.trim().toInt() }
    println("Part 1 : " + part1(input))
    println("Part 2 : " + part2(input))
}
