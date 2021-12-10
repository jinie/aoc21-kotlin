fun main() {

    /**
     * Finds neighbours in a grid
     */
    fun neighbours(input: List<List<Int>>, rowIdx: Int, colIdx: Int): List<Pair<Int, Int>> {
        return arrayOf((-1 to 0), (1 to 0), (0 to -1), (0 to 1)).map { (dx, dy) -> rowIdx + dx to colIdx + dy }
            .filter { (x, y) -> x in input.indices && y in input.first().indices }
    }

    /**
     * Performs flood fill over a list of low points
     */
    fun floodFill(input: List<List<Int>>, lowPoints: Pair<Int, Int>, threshold: Int): List<Pair<Int, Int>> {
        val toSearch = mutableSetOf(lowPoints)
        val visited = mutableSetOf<Pair<Int, Int>>()
        val filled = mutableSetOf<Pair<Int, Int>>()
        while (toSearch.isNotEmpty()) {
            val current = toSearch.first()
            toSearch.remove(current)
            visited.add(current)
            if (input[current.first][current.second] < threshold) {
                filled.add(current)
                neighbours(input, current.first, current.second).filter { it !in visited }.forEach { toSearch.add(it) }
            }
        }
        return filled.toList()
    }

    /**
     * Finds the lowest points in the input
     */
    fun lowPoints(input: List<List<Int>>): List<Pair<Int, Int>> {
        val lowPoints = mutableSetOf<Pair<Int, Int>>()
        for (row in input.indices) {
            for (col in input.first().indices) {
                if (neighbours(input, row, col).map { input[it.first][it.second] }.minOf { it } > input[row][col]) {
                    lowPoints.add(row to col)
                }
            }
        }
        return lowPoints.toList()
    }

    fun part2(input: List<List<Int>>): Int {
        return lowPoints(input).map { floodFill(input, it, 9).toSet().size }.sortedDescending().take(3)
            .reduce { acc, i -> acc * i }
    }

    fun part1(input: List<List<Int>>): Int {
        return lowPoints(input).sumOf { input[it.first][it.second] + 1 }
    }

    measureTimeMillisPrint {
        val input = readInput("Day09").map { row -> row.map { it.digitToInt() } }
        println("Part 1 : " + part1(input))
        println("Part 2 : " + part2(input))
    }
}
