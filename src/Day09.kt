fun main() {

    fun neighbours(input: List<List<Int>>, rowIdx: Int, colIdx: Int): List<Pair<Int, Int>> {
        return arrayOf((-1 to 0), (1 to 0), (0 to -1), (0 to 1)).map { (dx, dy) -> rowIdx + dx to colIdx + dy }
            .filter { (x, y) -> x in input.indices && y in input.first().indices }
    }

    fun floodFill(input: List<List<Int>>, lp: Pair<Int,Int>, threshold: Int): List<Pair<Int, Int>> {
        var toSearch = mutableSetOf(lp)
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

    fun lowPoints(input: List<List<Int>>): List<Pair<Int, Int>> {
        var ret = mutableListOf<Pair<Int, Int>>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                var neighbours = mutableListOf<Int>()

                if (j > 0) neighbours.add(input[i][j - 1]) // Check left
                if (j < input[i].size - 1) neighbours.add(input[i][j + 1]) // Check right
                if (i > 0) neighbours.add(input[i - 1][j]) // Check up
                if (i < input.size - 1) neighbours.add(input[i + 1][j])

                if (neighbours.minOf { it } > input[i][j]) ret.add(Pair(i, j))
            }
        }
        return ret.toList()
    }

    fun part2(input: List<List<Int>>): Int {
        return lowPoints(input).map { floodFill(input, it, 9).toSet().size }.sortedDescending().take(3).reduce { acc, i -> acc * i }
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
