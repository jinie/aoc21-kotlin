import kotlin.math.abs

fun main() {
    fun part1(input: List<Int>): Int? {
        var arr = mutableListOf<Int>()
        for (i in input.minOf { it }..input.maxOf { it }) {
            var sum = 0
            for (j in input) {
                sum += abs(j - i)
            }
            arr.add(sum)
        }
        return arr.minOrNull()
    }

    fun part2(input: List<Int>): Int? {
        var arr = mutableListOf<Int>()
        for (i in input.minOf { it }..input.maxOf { it }) {
            var sum = 0
            for (j in input) {
                sum += (abs(i - j) * (abs(i - j) + 1)) / 2
            }
            arr.add(sum)
        }
        return arr.minOrNull()
    }

    val testInput = "16,1,2,0,4,2,7,1,2,14".split(",").map { it.toInt() }.sorted()
    check(part1(testInput) == 37)

    val input = readInput("Day07").map { it.split(",").map { it.trim().toInt() } }.flatten()
    println("Part 1 : " + part1(input))
    println("Part 2 : " + part2(input))
}
