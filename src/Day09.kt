fun main() {
    /**
     * Part 2 Solution stolen from https://github.com/ClouddJR/AdventOfCode2021/blob/master/src/main/kotlin/com/clouddjr/advent2021/Day09.kt for further analysis
     */

    val heightmap = readInput("Day09").map { row -> row.map { it.digitToInt() } }

    fun neighbours(rowIdx: Int, colIdx: Int): List<Pair<Int, Int>> {
        return arrayOf((-1 to 0), (1 to 0), (0 to -1), (0 to 1)).map { (dx, dy) -> rowIdx + dx to colIdx + dy }
            .filter { (x, y) -> x in heightmap.indices && y in heightmap.first().indices }
    }

    fun basin(rowIdx: Int, colIdx: Int): List<Pair<Int, Int>> {
        return neighbours(rowIdx, colIdx).filterNot { (x, y) -> heightmap[x][y] == 9 }
            .fold(listOf((rowIdx to colIdx))) { points, (x, y) ->
                points + if (heightmap[x][y] - heightmap[rowIdx][colIdx] >= 1) basin(x, y) else emptyList()
            }
    }

    fun lowPoints(): List<Pair<Int, Int>> {
        return heightmap.foldIndexed(emptyList()) { rowIdx, allPoints, row ->
            row.foldIndexed(allPoints) { colIdx, points, height ->
                neighbours(rowIdx, colIdx).all { (x, y) -> heightmap[x][y] > height }
                    .let { isLowest -> if (isLowest) points + (rowIdx to colIdx) else points }
            }
        }
    }

    fun solvePart1(): Int {
        return lowPoints().sumOf { (x, y) -> heightmap[x][y] + 1 }
    }

    fun solvePart2(): Int {
        return lowPoints().map { (rowIdx, colIdx) -> basin(rowIdx, colIdx).toSet().size }.sortedDescending().take(3)
            .reduce { acc, i -> acc * i }
    }

    fun part1(input: List<List<Int>>): Int {
        var sum = 0
        for (i in input.indices) {
            for (j in input[i].indices) {
                var neighbours = mutableListOf<Int>()

                if (j > 0) neighbours.add(input[i][j - 1]) // Check left
                if (j < input[i].size - 1) neighbours.add(input[i][j + 1]) // Check right
                if (i > 0) neighbours.add(input[i - 1][j]) // Check up
                if (i < input.size - 1) neighbours.add(input[i + 1][j])

                if (neighbours.minOf { it } > input[i][j]) sum += input[i][j] + 1
            }
        }
        return sum
    }

    measureTimeMillisPrint {
        val input = readInput("Day09").map { it.trim().toCharArray().map { it.toString().toInt() } }
        println("Part 1 : " + part1(input))
        println("Part 2 : " + solvePart2())
    }

}
