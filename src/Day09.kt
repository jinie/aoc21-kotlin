fun main() {
    /**
     * Part 2 Solution partially stolen from https://github.com/ClouddJR/AdventOfCode2021/blob/master/src/main/kotlin/com/clouddjr/advent2021/Day09.kt for further analysis
     */

    fun neighbours(input: List<List<Int>>, rowIdx: Int, colIdx: Int): List<Pair<Int, Int>> {
        return arrayOf((-1 to 0), (1 to 0), (0 to -1), (0 to 1)).map { (dx, dy) -> rowIdx + dx to colIdx + dy }
            .filter { (x, y) -> x in input.indices && y in input.first().indices }
    }

    fun basin(input: List<List<Int>>, rowIdx: Int, colIdx: Int): List<Pair<Int, Int>> {
        return neighbours(input, rowIdx, colIdx).filterNot { (x, y) -> input[x][y] == 9 }
            .fold(listOf((rowIdx to colIdx))) { points, (x, y) ->
                points + if (input[x][y] - input[rowIdx][colIdx] >= 1) basin(input, x, y) else emptyList()
            }
    }

    /*    fun lowPoints(): List<Pair<Int, Int>> {
            return heightmap.foldIndexed(emptyList()) { rowIdx, allPoints, row ->
                row.foldIndexed(allPoints) { colIdx, points, height ->
                    neighbours(rowIdx, colIdx).all { (x, y) -> heightmap[x][y] > height }
                        .let { isLowest -> if (isLowest) points + (rowIdx to colIdx) else points }
                }
            }
        }*/

    fun floodFill(input: List<List<Int>>, lowPoints: List<Pair<Int,Int>>, threshold: Int): MutableSet<Pair<Int, Int>>  {
        var floodArea = lowPoints.toMutableSet()
        var added = Int.MAX_VALUE
        while(added > 0) {
            var tset = mutableSetOf<Pair<Int,Int>>()
            for (pt in floodArea) {
                for(np in neighbours(input,pt.first, pt.second).filterNot { input[pt.first][pt.second] >= threshold }){
                    if(input[np.first][np.second] < threshold) tset.add(np)
                }
            }
            added = tset.minus(tset.intersect(floodArea)).size
            floodArea.addAll(tset.minus(tset.intersect(floodArea)))
        }
        return floodArea
    }

    fun lowPoints(input: List<List<Int>>): List<Pair<Int, Int>> {
        var ret = mutableListOf<Pair<Int, Int>>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                if( neighbours(input, i, j).map{ input[it.first][it.second]}.minOf { it } > input[i][j]) ret.add(Pair(i,j))
            }
        }
        return ret.toList()
    }

    fun solvePart2(input: List<List<Int>>): Int {
        return lowPoints(input).map { (rowIdx, colIdx) -> basin(input, rowIdx, colIdx).toSet().size }.sortedDescending()
            .take(3).reduce { acc, i -> acc * i }
    }

    fun part2(input: List<List<Int>>): Int? {
        var ret = floodFill(input, lowPoints(input),9).count()
        return ret
    }

    fun part1(input: List<List<Int>>): Int {
        return lowPoints(input).sumOf { input[it.first][it.second] + 1 }
    }

    measureTimeMillisPrint {
        val input = readInput("Day09").map { row -> row.map { it.digitToInt() } }
        println("Part 1 : " + part1(input))
        println("Part 2 : " + solvePart2(input))
        println(part2(input))
    }

}
