fun main() {
    fun part1(input: List<Int>): Int {
        return input.zipWithNext().count { (x,y) -> y > x }
    }

    fun part2(input: List<Int>): Int {
        return input.windowed(3).map { it.sum() }.zipWithNext().count { (x,y) -> y > x }
    }

    val testInput = readInput("Day01_test").map { it.trim().toInt() }
    check(part1(testInput) == 7)

    measureTimeMillisPrint {
        val input = readInput("Day01").map { it.trim().toInt() }
        println("Part 1 : " + part1(input))
        println("Part 2 : " + part2(input))
    }

}
