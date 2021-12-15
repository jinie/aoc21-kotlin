fun main() {

    /* Solution stolen from https://github.com/ClouddJR/AdventOfCode2021/blob/master/src/main/kotlin/com/clouddjr/advent2021/Day12.kt */

    val connections = readInput("Day12").map {
        it.split("-") }.flatMap { (b, e) -> listOf(b to e, e to b) }
        .filter { (_, end) -> end != "start" }.groupBy({ it.first }, { it.second })


    fun countPaths(
        cave: String = "start",
        path: List<String> = listOf(),
        isSmallCaveNotAllowed: (cave: String, currentPath: List<String>) -> Boolean
    ): Int {
        return when {
            cave == "end" -> 1
            cave.first().isLowerCase() && isSmallCaveNotAllowed(cave, path) -> 0
            else -> connections.getValue(cave).sumOf { countPaths(it, path + cave, isSmallCaveNotAllowed) }
        }
    }

    fun part1(): Int {
        return countPaths { cave, currentPath ->
            cave in currentPath
        }
    }

    fun part2(): Int {
        return countPaths { cave, currentPath ->
            val counts = currentPath.filter { it.first().isLowerCase() }.groupingBy { it }.eachCount()
            cave in counts.keys && counts.any { it.value > 1 }
        }
    }

    measureTimeMillisPrint {
        println("Part 1 : " + part1())
        println("Part 2 : " + part2())
    }

}
