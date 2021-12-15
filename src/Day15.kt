typealias Point = Pair<Int, Int>

fun main() {

    fun enlargeRow(xs: List<Int>) = xs.map { new ->
        when (new + 1) {
            10 -> 1
            else -> new + 1
        }
    }

    tailrec fun enlargeRowTimes(xs: List<Int>, times: Int): List<Int> = when (times) {
        0 -> xs
        1 -> enlargeRow(xs)
        else -> enlargeRowTimes(enlargeRow(xs), times - 1)
    }

    fun enlargeMap(ys: List<List<Int>>): List<List<Int>> {
        val tmap = ys.map { xs -> (0..4).fold(emptyList<Int>()) { acc, i -> acc + enlargeRowTimes(xs, i) } }
        return (0..4).fold(emptyList()) { acc, i -> acc + tmap.map { enlargeRowTimes(it, i) } }
    }

    tailrec fun djikstra(input: List<List<Int>>, grid: Map<Point, Int>): Int {
        val w = input.first().size
        val h = input.size

        val newGrid = grid
            .flatMap { (point, cost) ->
                neighbours(input,point.first,point.second).map { (x, y) -> (x to y) to input[y][x] + cost } + listOf(point to cost)
            }
            .groupBy { it.first }
            .mapValues { (_, value) -> value.minOf { pp -> pp.second } }
            .toMap()

        if (newGrid == grid) {
            return grid[(h - 1) to (w - 1)]!!
        }
        return djikstra(input, newGrid)
    }


    fun part1(input: List<List<Int>>): Long {
        return djikstra(input, mapOf((0 to 0) to 0)).toLong()
    }

    fun part2(input: List<List<Int>>): Long {
        return djikstra(enlargeMap(input), mapOf((0 to 0) to 0)).toLong()
    }

    val testInput = readInput("Day15_test").map { it.trim().map { c -> c.digitToInt() } }
    check(part1(testInput) == 40L)

    measureTimeMillisPrint {
        val input = readInput("Day15").map { it.trim().map { c -> c.digitToInt() } }
        println("Part 1 : " + part1(input))
        println("Part 2 : " + part2(input))
    }

}
